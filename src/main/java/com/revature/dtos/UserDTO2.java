package com.revature.dtos;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
// @RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO2 {

    private @NonNull double balance;

}
