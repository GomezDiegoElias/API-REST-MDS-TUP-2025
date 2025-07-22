package app_tup.mds.api_spa.authentication.infrastructure;

import app_tup.mds.api_spa.authentication.infrastructure.dto.AuthRequest;
import app_tup.mds.api_spa.authentication.infrastructure.dto.AuthResponse;
import app_tup.mds.api_spa.authentication.infrastructure.dto.SingUpRequest;
import org.springframework.http.ResponseEntity;

public interface IAuthController {
    ResponseEntity<AuthResponse> authenticate(AuthRequest request);
    ResponseEntity<AuthResponse> register(SingUpRequest request);
}
