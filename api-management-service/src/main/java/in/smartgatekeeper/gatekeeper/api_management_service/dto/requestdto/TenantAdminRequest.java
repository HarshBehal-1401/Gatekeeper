package in.smartgatekeeper.gatekeeper.api_management_service.dto.requestdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class TenantAdminRequest {
    @NotBlank
    @Size(max=150)
    private String username;
    @NotBlank
    @Size(max=100)
    private String password;

    private boolean isEnabled=true;
}
