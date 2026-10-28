package in.smartgatekeeper.gatekeeper.api_management_service.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class HealthResponse {
    private String servicestatus;
    private String dbstatus;
    private LocalDateTime timeStamp ;
}
