package in.smartgatekeeper.gatekeeper.smartgateway.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class HealthResponse {

    private String servicename;
    private String servicedescription;
    private String appStatus;
}
