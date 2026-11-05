package in.smartgatekeeper.gatekeeper.smartgateway.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Component;

@Component
public class KafkaConfig {

    @Bean
    public NewTopic gatewayConfigTopic(@Value("${gateway.kafka.configuration-topic}") String topic) {
        return TopicBuilder.name(topic).partitions(1).build();
    }
}
