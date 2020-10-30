package com.cinema.model.dto;

import com.cinema.annotations.FieldsValueMatchValidation;
import com.cinema.annotations.UserEmailValidation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@FieldsValueMatchValidation(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Password doesn't match"
)
public class UserRequestDto {
    @UserEmailValidation
    private String email;
    @NotNull
    @Size(min = 3)
    private String password;
    @NotNull
    @Size(min = 3)
    private String repeatPassword;
}
