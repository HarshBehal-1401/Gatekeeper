package in.smartgatekeeper.gatekeeper.api_management_service.dto.responsedto;

import in.smartgatekeeper.gatekeeper.api_management_service.entity.ManagementUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TenantAdminResponse {
    private Long id;
    private String organizationName;
    private String username;
    private boolean enabled;

    public static TenantAdminResponse from(ManagementUser managementUser) {
        return new TenantAdminResponse(managementUser.getId(),
                managementUser.getOrganization().getName(),
                managementUser.getUsername(),
                managementUser.isEnabled());

    }
}
