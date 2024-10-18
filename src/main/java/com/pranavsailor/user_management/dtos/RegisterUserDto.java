package com.pranavsailor.user_management.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {
    private String email;

    private String password;

    private String fullName;

}
