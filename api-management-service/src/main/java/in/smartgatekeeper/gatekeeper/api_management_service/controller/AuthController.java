package in.smartgatekeeper.gatekeeper.api_management_service.controller;

import in.smartgatekeeper.gatekeeper.api_management_service.dto.requestdto.LoginRequest;
import in.smartgatekeeper.gatekeeper.api_management_service.dto.responsedto.LoginResponse;
import in.smartgatekeeper.gatekeeper.api_management_service.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authenticationService.login(loginRequest));
    }
}
