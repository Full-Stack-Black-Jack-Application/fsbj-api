package com.revature.dtos;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @NotBlank
    @Email
    private @NonNull String email;

    @NotBlank
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$")
    private @NonNull String password;

    @NotBlank
    private @NonNull String referralCode;

    @NotBlank
    @Length(min = 2)
    private @NonNull String firstName;

    @NotBlank
    @Length(min = 2)
    private @NonNull String lastName;

    private double balance;

}
