package in.smartgatekeeper.gatekeeper.smartgateway.service;

import in.smartgatekeeper.gatekeeper.smartgateway.client.ManagementApiClient;
import in.smartgatekeeper.gatekeeper.smartgateway.dto.responsedto.GatewayRoute;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GatewayConfigurationLoader implements ApplicationRunner  {

    private final ManagementApiClient managementApiClient;

    public GatewayConfigurationLoader( ManagementApiClient managementApiClient){
        this.managementApiClient = managementApiClient;

    }

    @Override
    public void run(ApplicationArguments args){
        refresh();
    }

    public void refresh() {
        List<GatewayRoute> routes = managementApiClient.fetchActiveRoutes();


        for(GatewayRoute route : routes) {
            System.out.println("Route : " + route.getTargetPathPrefix());
        }
    }

}
