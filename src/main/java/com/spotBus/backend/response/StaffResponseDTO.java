package com.spotBus.backend.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
}
