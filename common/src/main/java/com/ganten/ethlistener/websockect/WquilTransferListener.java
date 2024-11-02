package com.ganten.ethlistener.websockect;

import com.ganten.ethlistener.model.TransferEvent;
import io.reactivex.disposables.Disposable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.function.Consumer;

@Slf4j
@Service
public class WquilTransferListener {

    // 100000000
    private static final BigDecimal DIVISOR = new BigDecimal("100000000");

    private EthFilter filter;
    private Web3j web3j;

    public void listenToTransferEvents(Consumer<TransferEvent> eventHandler) {
        Disposable disposable = web3j.ethLogFlowable(filter).subscribe(log -> {
            TransferEvent event = decodeTransferEvent(log);
            eventHandler.accept(event);
        }, error -> log.error("Error in transfer event listener", error));
    }

    private TransferEvent decodeTransferEvent(Log log) {
        String from = "0x" + log.getTopics().get(1).substring(26);
        String to = "0x" + log.getTopics().get(2).substring(26);
        BigDecimal value = new BigDecimal(Numeric.toBigInt(log.getData()));
        BigDecimal result = value.divide(DIVISOR, 3, RoundingMode.HALF_UP);
        double resultAsDouble = result.doubleValue();
        return new TransferEvent(from, to, resultAsDouble, new Date());
    }

    @Autowired
    public void setWeb3j(Web3j web3j) {
        this.web3j = web3j;
    }

    @Autowired
    @Qualifier("wquilFilter")
    public void setFilter(EthFilter filter) {
        this.filter = filter;
    }
}