package it.macgood.opus.auth.service;

import it.macgood.opus.auth.model.AuthenticationRequest;
import it.macgood.opus.auth.model.AuthenticationResponse;
import it.macgood.opus.auth.model.RegisterRequest;
import it.macgood.opus.auth.model.VkRegisterRequest;
import it.macgood.opus.config.JwtService;
import it.macgood.opus.exception.HttpCodes;
import it.macgood.opus.exception.StandardErrorMessages;
import it.macgood.opus.user.model.Role;
import it.macgood.opus.user.model.User;
import it.macgood.opus.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final String exceptionUrl = "http://localhost:8080/api/v1/exception";


    public AuthenticationResponse register(
            HttpServletResponse response,
            RegisterRequest request
    ) throws IOException {

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();

        var jwtToken = jwtService.generateToken(user);

        user.setCurrentToken(jwtToken);

        try {
            repository.save(user);
        } catch (RuntimeException e) {
            response.sendRedirect(exceptionUrl
                    + "?code=" + HttpCodes.FORBIDDEN.getCode()
                    + "&explain=" + StandardErrorMessages.ALREADY_EXISTS.getText()
            );
        }

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();

        var jwtToken = jwtService.generateToken(user);

        user.setCurrentToken(jwtToken);

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse enterFromVk(
            HttpServletResponse response,
            VkRegisterRequest request
    ) throws IOException {

        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .dateOfBirth(request.getDateOfBirth())
                .photo(request.getPhoto())
                .role(Role.USER)
                .build();


        var jwtToken = jwtService.generateToken(user);

        user.setCurrentToken(jwtToken);

        try {
            repository.save(user);
        } catch (RuntimeException ignored) {

        }

        return AuthenticationResponse
                .builder()
                .token(jwtToken)
                .build();
    }
}
