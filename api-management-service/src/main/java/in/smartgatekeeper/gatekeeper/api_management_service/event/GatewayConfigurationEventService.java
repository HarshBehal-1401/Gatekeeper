package in.smartgatekeeper.gatekeeper.api_management_service.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class GatewayConfigurationEventService {
    private ApplicationEventPublisher eventPublisher;

    public GatewayConfigurationEventService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publish(String changeType,Long organizationId,String routeId,
                        Long configurationVersion){
        eventPublisher.publishEvent(new GatewayConfigurationChangeEvent(
                UUID.randomUUID().toString(),"Gateway Configuration Change",changeType,
                organizationId,routeId,configurationVersion, LocalDateTime.now()
        ));
    }
}
