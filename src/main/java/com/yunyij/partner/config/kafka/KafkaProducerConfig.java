package com.yunyij.partner.config.kafka;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;




@Configuration
@EnableKafka
public class KafkaProducerConfig {

//	@Value("${kafka.producer.servers}")
//    private String servers;
//    @Value("${kafka.producer.retries}")
//    private int retries;
//    @Value("${kafka.producer.batch.size}")
//    private int batchSize;
//    @Value("${kafka.producer.linger}")
//    private int linger;
//    @Value("${kafka.producer.buffer.memory}")
//    private int bufferMemory;
	@Value("${kafka.type.ssl}")
	private String sslType;
	
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Map<String, Object> producerConfigs() throws IOException {
    	Properties  producerConfig = Config.getProducerConfig();
    	if("true".equals(sslType)) {
    		producerConfig.put("ssl.truststore.location", Config.getTrustStorePath());
    		producerConfig.put("ssl.truststore.password","dms@kafka");
    		producerConfig.put("security.protocol","SASL_SSL");
    		producerConfig.put("sasl.mechanism","DMS");
            System.setProperty("java.security.auth.login.config", Config.getSaslConfig());
        }     	
		Map<String, Object> props = new HashMap<String, Object>((Map) producerConfig);
        return props;
    }
//    
//    @SuppressWarnings({ "unchecked", "rawtypes" })
//	public Map<String, Object> producerConfigs() throws IOException {    	
//		Map<String, Object> props = new HashMap<String, Object>();
//		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
//        props.put(ProducerConfig.RETRIES_CONFIG, retries);
//        props.put(ProducerConfig.BATCH_SIZE_CONFIG, batchSize);
//        props.put(ProducerConfig.LINGER_MS_CONFIG, linger);
//        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, bufferMemory);
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        return props;
//    }

    public ProducerFactory<String, String> producerFactory() throws IOException {
    	
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() throws IOException {
        return new KafkaTemplate<String, String>(producerFactory());
    }
}
