package in.smartgatekeeper.gatekeeper.api_management_service.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {

    private String token;
    private String tokenType;
    private long expireInSeconds;
    private String role;
    private Long organizationId;
}
