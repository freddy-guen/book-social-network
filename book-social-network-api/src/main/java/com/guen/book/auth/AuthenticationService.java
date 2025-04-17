package com.guen.book.auth;

import com.guen.book.role.RoleRepository;
import com.guen.book.user.Token;
import com.guen.book.user.TokenRepository;
import com.guen.book.user.User;
import com.guen.book.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService
{

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;

    public void register(RegistrationRequest request)
    {
        var userRole = roleRepository.findByName("USER")
                //todo : Exception à gérer plus tard
                .orElseThrow(() -> new IllegalStateException("Le role USER n'est pas initialisé"));
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);

        //Envoi du mail de validation
        sendValidationemail(user);
    }

    private void sendValidationemail(User user)
    {
        var newToken = generateAndSaveActivationToken(user);
        // send email
    }

    private String generateAndSaveActivationToken(User user)
    {
        // generate a token
        String generatedToken = generateActivationCode(6);

        // On l'enregistre en base
        var token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(15)) //expire dans 15 minutes
                .user(user)
                .build();
        tokenRepository.save(token);

        return generatedToken;
    }

    private String generateActivationCode(int length)
    {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();

        for (int i = 0; i<length; i++)
        {
            int randomIndex = secureRandom.nextInt(characters.length()); //génère un chiffre entre 0..9
            codeBuilder.append(characters.charAt(randomIndex));
        }

        return codeBuilder.toString();
    }
}
