package in.smartgatekeeper.gatekeeper.api_management_service.controller;

import in.smartgatekeeper.gatekeeper.api_management_service.dto.responsedto.HealthResponse;
import in.smartgatekeeper.gatekeeper.api_management_service.service.HealthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/health")
public class HealthController {
    public HealthService healthService;

    public HealthController(HealthService healthService) {
        this.healthService =healthService;
    }
    @GetMapping
    public ResponseEntity<HealthResponse> health() {
        String dbstatus= healthService.gethealth();
        return ResponseEntity.ok(
                new HealthResponse("UP",dbstatus, LocalDateTime.now())
        );
    }



}
