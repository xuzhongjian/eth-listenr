package com.ganten.ethlistener.service;

import com.ganten.ethlistener.analysis.Analyst;
import com.ganten.ethlistener.converter.ExportConverter;
import com.ganten.ethlistener.feishu.FeishuApiService;
import com.ganten.ethlistener.feishu.FeishuResponse;
import com.ganten.ethlistener.feishu.MessageBuilder;
import com.ganten.ethlistener.feishu.request.Message;
import com.ganten.ethlistener.model.EventExport;
import com.ganten.ethlistener.model.MintAnalysisResult;
import com.ganten.ethlistener.model.TransferEvent;
import com.ganten.ethlistener.util.CSVExporter;
import com.ganten.ethlistener.util.CSVImporter;
import com.ganten.ethlistener.util.JacksonUtils;
import com.ganten.ethlistener.websockect.WquilTransferListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransferMonitoringService {
    private static final SimpleDateFormat HOUR_FORMAT = new SimpleDateFormat("yyyyMMdd_HH");

    private WquilTransferListener transferListener;
    private FeishuApiService feishuApiService;

    private final List<TransferEvent> events = new ArrayList<>();

    @Value("${feishu.token}")
    private String token;

    @PostConstruct
    public void startMonitoring() {
        transferListener.listenToTransferEvents(this::handleTransferEvent);
    }

    private void handleTransferEvent(TransferEvent event) {
        this.events.add(event);

    }

    @Scheduled(cron = "0 */5 * * * ?")
    public void executeTask() {
        List<TransferEvent> transferEvents = new ArrayList<>(events);
        this.mintAnalysis(transferEvents, "每 5 分钟 bridge 统计");
        List<EventExport> eventExports = events.stream().map(ExportConverter::convert).collect(Collectors.toList());
        CSVExporter.exportToCSV(eventExports, this.genFilePath(System.currentTimeMillis() - 300_000));
        events.clear();
    }

    @Scheduled(cron = "0 5 * * * ?")
    public void hourlyTask() {
        String filePath = this.genFilePath(System.currentTimeMillis() - 3600_000);
        List<EventExport> exports = CSVImporter.importFromCSV(filePath, EventExport.class);
        List<TransferEvent> transferEvents = exports.stream().map(ExportConverter::convert).collect(Collectors.toList());
        this.mintAnalysis(transferEvents, "每小时 bridge 统计");
    }

    private void mintAnalysis(List<TransferEvent> transferEvents, String title) {
        if (transferEvents.size() == 0) {
            Message message = MessageBuilder.buildSampleMessage(title + ": 无数据");
            this.sendFeishu(message);
            return;
        }
        MintAnalysisResult result = Analyst.mintAnalysisResult(transferEvents);
        Set<Map.Entry<String, Double>> entries = result.getMintMap().entrySet();
        List<String> messageList = entries.stream().map(this::buildMessage).collect(Collectors.toList());
        messageList.add(this.buildTimeRangeMessage(result));
        Message message = MessageBuilder.buildListMessage(title, messageList);
        this.sendFeishu(message);
    }

    public String buildMessage(Map.Entry<String, Double> o) {
        return "地址:" + this.snowflake(o.getKey()) + ", 数量:" + o.getValue() + "\n";
    }

    public String buildTimeRangeMessage(MintAnalysisResult result) {
        return "本时间周期内，bridge 了" + result.getTotalValue() + " 枚。";
    }

    private void sendFeishu(Message message) {
        Call<FeishuResponse> send = feishuApiService.send(token, message);
        try {
            Response<FeishuResponse> execute = send.execute();
            if (execute.isSuccessful()) {
                FeishuResponse response = execute.body();
                if (response != null && response.getStatusCode() == 0) {
                    log.info("Message sent to Feishu successfully: {}", message);
                } else {
                    log.error("Failed to send message to Feishu: {}", JacksonUtils.toJson(response));
                }
            } else {
                log.error("HTTP error when sending message to Feishu: {} {}", execute.code(), execute.message());
            }
        } catch (IOException e) {
            log.error("Exception when sending message to Feishu", e);
        }
    }

    @Autowired
    public void setWquilTransferListener(WquilTransferListener transferListener) {
        this.transferListener = transferListener;
    }

    @Autowired
    public void setFeishuApiService(FeishuApiService feishuApiService) {
        this.feishuApiService = feishuApiService;
    }

    public String genFilePath(long ts) {
        String timestamp = HOUR_FORMAT.format(new Date(ts));
        return "transfer/" + timestamp + ".csv";
    }

    public String snowflake(String address) {
        return address.substring(0, 6) + "..." + address.substring(address.length() - 4);
    }
}
