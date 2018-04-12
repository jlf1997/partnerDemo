package com.yunyij.partner.config.kafka;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import com.yunyij.partner.kafka.MyListener;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {
//	@Value("${kafka.consumer.servers}")
//    private String servers;
//    @Value("${kafka.consumer.enable.auto.commit}")
//    private boolean enableAutoCommit;
//    @Value("${kafka.consumer.session.timeout}")
//    private String sessionTimeout;
//    @Value("${kafka.consumer.auto.commit.interval}")
//    private String autoCommitInterval;
//    @Value("${kafka.consumer.group.id}")
//    private String groupId;
//    @Value("${kafka.consumer.auto.offset.reset}")
//    private String autoOffsetReset;
	@Value("${kafka.type.ssl}")
	private String sslType;
    @Value("${kafka.consumer.concurrency}")
    private int concurrency;
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() throws IOException {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(concurrency);
        factory.getContainerProperties().setPollTimeout(1500);
        return factory;
    }

    public ConsumerFactory<String, String> consumerFactory() throws IOException {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }


    public Map<String, Object> consumerConfigs() throws IOException {
    	Properties consumerConfig = Config.getConsumerConfig();
    	if("true".equals(sslType)) {
    		consumerConfig.put("ssl.truststore.location", Config.getTrustStorePath());
    		consumerConfig.put("ssl.truststore.password","dms@kafka");
    		consumerConfig.put("security.protocol","SASL_SSL");
    		consumerConfig.put("sasl.mechanism","DMS");
            System.setProperty("java.security.auth.login.config", Config.getSaslConfig());
        }    	
        Map<String, Object> propsMap = new HashMap<>((Map)consumerConfig);
        return propsMap;
    }
    
//    public Map<String, Object> consumerConfigs() throws IOException {
//
//        Map<String, Object> propsMap = new HashMap<>();       
//        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
//        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
//        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
//        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, sessionTimeout);
//        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
//        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
//        return propsMap;
//    }

    @Bean
    public MyListener listener() {
        return new MyListener();
    }
}
