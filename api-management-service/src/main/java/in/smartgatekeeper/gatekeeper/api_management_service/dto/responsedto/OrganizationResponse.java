package in.smartgatekeeper.gatekeeper.api_management_service.dto.responsedto;


import in.smartgatekeeper.gatekeeper.api_management_service.dto.requestdto.OrganizationRequest;
import in.smartgatekeeper.gatekeeper.api_management_service.entity.Organization;
import in.smartgatekeeper.gatekeeper.api_management_service.entity.OrganizationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@AllArgsConstructor
public class OrganizationResponse {
    private Long id;
    private String name;
    private String slug;
    private OrganizationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static OrganizationResponse from(Organization organization) {
        return new OrganizationResponse(organization.getId(), organization.getName(), organization.getSlug(),
                organization.getStatus(), organization.getCreatedAt(), organization.getUpdatedAt());

    }
}
