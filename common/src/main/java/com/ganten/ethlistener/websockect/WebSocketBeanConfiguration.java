package com.ganten.ethlistener.websockect;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.http.HttpService;

import java.util.Arrays;

@Configuration
public class WebSocketBeanConfiguration {
    @Value("${ethereum.contract.address}")
    private String contractAddress;

    @Value("${ethereum.infura.url}")
    private String infuraUrl;

    @Bean
    public Web3j infuraWeb3j() {
        return Web3j.build(new HttpService(infuraUrl));
    }

    @Bean
    @Qualifier("wquilFilter")
    public EthFilter wquilFilter() {
        Event transferEvent = new Event("Transfer", Arrays.asList(new TypeReference<Address>(true) {
        }, new TypeReference<Address>(true) {
        }, new TypeReference<Uint256>() {
        }));
        // 创建 Transfer 事件的签名
        String transferEventSignature = EventEncoder.encode(transferEvent);
        EthFilter filter = new EthFilter(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST, contractAddress);
        filter.addSingleTopic(transferEventSignature);
        return filter;
    }
}
