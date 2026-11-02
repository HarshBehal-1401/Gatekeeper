package in.smartgatekeeper.gatekeeper.api_management_service.controller;

import in.smartgatekeeper.gatekeeper.api_management_service.dto.requestdto.TenantOrganizationRequest;
import in.smartgatekeeper.gatekeeper.api_management_service.dto.responsedto.OrganizationResponse;
import in.smartgatekeeper.gatekeeper.api_management_service.service.OrganizationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenant/organization")
public class TenantOrganizationController {
    private final OrganizationService organizationService;

    public TenantOrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping
    public ResponseEntity<OrganizationResponse> getOwnOrganization() {
        return ResponseEntity.ok(organizationService.getOwnOrganization());
    }

    @PutMapping
    public ResponseEntity<OrganizationResponse> updateOwnOrganization(
            @Valid @RequestBody TenantOrganizationRequest request) {
        return ResponseEntity.ok(organizationService.updateOwnOrganization(request));
    }
}
