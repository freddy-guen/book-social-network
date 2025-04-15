package com.guen.book.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegistrationRequest
{
    @NotEmpty(message = "Le prénom est obligatoire")
    @NotBlank(message = "Le prénom est obligatoire")
    private String firstname;

    @NotEmpty(message = "Le nom est obligatoire")
    @NotBlank(message = "Le nom est obligatoire")
    private String lastname;

    @Email(message = "Format d'email invalide")
    @NotEmpty(message = "L'email est obligatoire")
    @NotBlank(message = "L'email' est obligatoire")
    private String email;

    @NotEmpty(message = "Le mot de passe est obligatoire")
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    private String password;
}
