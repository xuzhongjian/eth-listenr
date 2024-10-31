package com.ganten.ethlistener.websockect;

import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;

public class ERC20TransferListener {

    private static final String CONTRACT_ADDRESS = "your_erc20_contract_address_here";

    public static void main(String[] args) {
        // 假设你已经设置了 Web3j 实例
        String infuraUrl = "https://mainnet.infura.io/v3/YOUR-PROJECT-ID";
        Web3j web3j = Web3j.build(new HttpService(infuraUrl));

        // 创建 Transfer 事件的签名
        Event transferEvent = new Event("Transfer",
                Arrays.asList(
                        new TypeReference<Address>(true) {
                        },  // from
                        new TypeReference<Address>(true) {
                        },  // to
                        new TypeReference<Uint256>() {
                        }       // value
                ));
        String transferEventSignature = EventEncoder.encode(transferEvent);

        // 创建过滤器
        EthFilter filter = new EthFilter(
                DefaultBlockParameterName.LATEST,
                DefaultBlockParameterName.LATEST,
                CONTRACT_ADDRESS
        );
        filter.addSingleTopic(transferEventSignature);

        // 订阅事件
        web3j.ethLogFlowable(filter).subscribe(log -> {
            // 解码事件数据
            String from = "0x" + log.getTopics().get(1).substring(26);
            String to = "0x" + log.getTopics().get(2).substring(26);
            BigInteger value = Numeric.toBigInt(log.getData());

            System.out.println("Transfer detected:");
            System.out.println("From: " + from);
            System.out.println("To: " + to);
            System.out.println("Value: " + value);
        }, error -> {
            System.err.println("Error: " + error.getMessage());
        });

        // 保持程序运行
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
