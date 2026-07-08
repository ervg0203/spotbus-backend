package com.spotBus.backend.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponseDTO {

    private String accessToken;
    private String refreshToken;
    private Long id;
    private String email;
    private String name;
}
