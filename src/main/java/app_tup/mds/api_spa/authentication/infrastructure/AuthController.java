package app_tup.mds.api_spa.authentication.infrastructure;

import app_tup.mds.api_spa.authentication.application.AuthService;
import app_tup.mds.api_spa.authentication.infrastructure.annotations.LoginApiDoc;
import app_tup.mds.api_spa.authentication.infrastructure.annotations.RegisterApiDoc;
import app_tup.mds.api_spa.authentication.infrastructure.dto.AuthRequest;
import app_tup.mds.api_spa.authentication.infrastructure.dto.AuthResponse;
import app_tup.mds.api_spa.authentication.infrastructure.dto.SingUpRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "Authentication endpoints")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController implements IAuthController {

    private final AuthService service;

    @LoginApiDoc
    @PostMapping("/login")
    @Override
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody AuthRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @RegisterApiDoc
    @PostMapping("/register")
    @Override
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody SingUpRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

}
