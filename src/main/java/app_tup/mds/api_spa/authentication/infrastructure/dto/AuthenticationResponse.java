package app_tup.mds.api_spa.authentication.infrastructure.dto;

import lombok.Builder;

@Builder
public record AuthenticationResponse (
        String message,
        String accessToken,
        String refreshToken
) {}
