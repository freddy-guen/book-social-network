package com.guen.book.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Enregistrement, connexion, activation de compte")
public class AuthenticationController
{

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @Operation(summary = "Inscription d'un nouvel utilisateur")
    public ResponseEntity<?> register (@RequestBody @Valid RegistrationRequest request) throws MessagingException
    {
        authenticationService.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/authenticate")
    @Operation(summary = "Authentification d'un utilisateur")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody @Valid AuthenticationRequest request)
    {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/activate-account")
    @Operation(summary = "Activation du compte via token envoyé par mail")
    public void confirm(@RequestParam String token) throws MessagingException
    {
        authenticationService.activateAccount(token);
    }

}