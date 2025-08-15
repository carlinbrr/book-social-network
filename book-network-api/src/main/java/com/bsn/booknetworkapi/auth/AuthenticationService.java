package com.bsn.booknetworkapi.auth;

import com.bsn.booknetworkapi.email.EmailService;
import com.bsn.booknetworkapi.email.EmailTemplateName;
import com.bsn.booknetworkapi.roles.Role;
import com.bsn.booknetworkapi.roles.RoleRepository;
import com.bsn.booknetworkapi.user.Token;
import com.bsn.booknetworkapi.user.TokenRepository;
import com.bsn.booknetworkapi.user.User;
import com.bsn.booknetworkapi.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthenticationService {

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final TokenRepository tokenRepository;

    private final EmailService emailService;

    private final String activationUrl;

    public AuthenticationService(RoleRepository roleRepository,
                                 PasswordEncoder passwordEncoder,
                                 UserRepository userRepository,
                                 TokenRepository tokenRepository,
                                 EmailService emailService,
                                 @Value("${application.mailing.frontend.activation-url}") String activationUrl ) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
        this.activationUrl = activationUrl;
    }

    public void register(RegistrationRequest request) throws MessagingException {
        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initialized"));
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .accountLocked(false)
                .enabled(false)
                .roles(List.of(role))
                .build();

        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        String newToken = generateAndSaveActivationToken(user);
        // Send email
        emailService.sendEmail(
                user.getEmail(),
                user.getFullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Account activation"
        );
    }

    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(6);
        Token token = Token.builder()
                .token(generatedToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            codeBuilder.append(characters.charAt(secureRandom.nextInt(characters.length())));
        }
        return codeBuilder.toString();
    }

}
