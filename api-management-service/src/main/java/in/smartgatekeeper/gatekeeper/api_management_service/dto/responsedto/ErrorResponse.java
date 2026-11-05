package in.smartgatekeeper.gatekeeper.api_management_service.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private int status;
    private String message;
    private Map<String,String> error;
    private LocalDateTime timestamp;
}
