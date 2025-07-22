package app_tup.mds.api_spa.authentication.infrastructure.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
        String message,
        String accessToken,
        String refreshToken
) {}
