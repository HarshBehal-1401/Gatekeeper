package in.smartgatekeeper.gatekeeper.analytics_service.controller;


import in.smartgatekeeper.gatekeeper.analytics_service.dto.responsedto.HealthResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthController {
    private String servicename;
    private String servicedescription;

    public HealthController(@Value("${info.app.name}") String servicename,
                            @Value("${info.app.description") String servicedescription) {
        this.servicename = servicename;
        this.servicedescription = servicedescription;
    }
    @GetMapping
    public ResponseEntity<HealthResponse> health() {
        return ResponseEntity.ok(
                new HealthResponse(servicename,servicedescription,"Up")
        );
    }


}
