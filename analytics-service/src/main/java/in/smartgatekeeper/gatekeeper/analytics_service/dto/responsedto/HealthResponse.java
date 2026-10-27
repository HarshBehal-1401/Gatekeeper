package in.smartgatekeeper.gatekeeper.analytics_service.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class HealthResponse {
    private String servicename;
    private String servicedescription;
    private String status;
}
