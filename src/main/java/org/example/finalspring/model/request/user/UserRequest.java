package org.example.finalspring.model.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    @NotBlank(message = "Поле username не может быть пустым")
    @Size(min = 2, max = 15, message = "Username должен быть не менее 2 и не более 15 символов")
    private String username;

    @NotBlank(message = "Поле password не может быть пустым")
    @Size(min = 5, max = 15, message = "Password должен быть не менее 5 и не более 15 символов")
    private String password;

    @Email(message = "Некорректный email адрес")
    private String email;
}
