package in.smartgatekeeper.gatekeeper.smartgateway.event;

import in.smartgatekeeper.gatekeeper.smartgateway.service.GatewayConfigurationLoader;
import org.springframework.kafka.annotation.KafkaListener;

public class ConfigurationEventConsumer {

    private final GatewayConfigurationLoader configurationLoader;

    public ConfigurationEventConsumer(GatewayConfigurationLoader configurationLoader) {
        this.configurationLoader = configurationLoader;
    }

    @KafkaListener(topics = "${gateway.kafka.configuration-topic}",
            groupId = "${gateway.kafka.configuration-group}")
    public void configurationChanged(String event) {
        System.out.println("Gateway configuration notification received: " + event);

        configurationLoader.refresh();

        System.out.println("Gateway configuration updated: " + event);
    }
}
