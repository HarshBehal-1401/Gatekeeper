package in.smartgatekeeper.gatekeeper.api_management_service.dto.requestdto;

import in.smartgatekeeper.gatekeeper.api_management_service.entity.OrganizationStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrganizationStatusRequest {
    private OrganizationStatus status;
}
