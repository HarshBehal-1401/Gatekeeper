package in.smartgatekeeper.gatekeeper.api_management_service.controller;
import java.util.*;

import in.smartgatekeeper.gatekeeper.api_management_service.dto.requestdto.RouteRequest;
import in.smartgatekeeper.gatekeeper.api_management_service.dto.requestdto.RouteStatusRequest;
import in.smartgatekeeper.gatekeeper.api_management_service.dto.responsedto.AuditRecordResponse;
import in.smartgatekeeper.gatekeeper.api_management_service.dto.responsedto.RouteResponse;
import in.smartgatekeeper.gatekeeper.api_management_service.dto.responsedto.RouteVersionResponse;
import in.smartgatekeeper.gatekeeper.api_management_service.service.RouteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenant")
public class TenantRouteController {
    private final RouteService routeService;

    public TenantRouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping("/routes")
    public ResponseEntity<RouteResponse> create(@Valid @RequestBody RouteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(routeService.create(request));
    }

    @GetMapping("/routes")
    public ResponseEntity<List<RouteResponse>> findAll() {
        return ResponseEntity.ok(routeService.findAllForTenant());
    }

    @GetMapping("/routes/{id}")
    public ResponseEntity<RouteResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(routeService.findById(id));
    }

    @PutMapping("/routes/{id}")
    public ResponseEntity<RouteResponse> update(@PathVariable Long id,
                                                @Valid @RequestBody RouteRequest request) {
        return ResponseEntity.ok(routeService.update(id, request));
    }

    @PatchMapping("/routes/{id}/status")
    public ResponseEntity<RouteResponse> changeStatus(@PathVariable Long id,
                                                      @Valid @RequestBody RouteStatusRequest request) {
        return ResponseEntity.ok(routeService.changeStatus(id, request));
    }

    @DeleteMapping("/routes/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        routeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/route-versions/{routeId}")
    public ResponseEntity<List<RouteVersionResponse>> getVersions(
            @PathVariable String routeId) {
        return ResponseEntity.ok(routeService.getVersions(routeId));
    }

    @GetMapping("/audit-records")
    public ResponseEntity<List<AuditRecordResponse>> getAuditRecords() {
        return ResponseEntity.ok(routeService.getAuditRecords());
    }
}
