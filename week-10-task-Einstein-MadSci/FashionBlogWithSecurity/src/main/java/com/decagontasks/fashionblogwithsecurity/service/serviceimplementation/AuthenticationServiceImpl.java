package com.decagontasks.fashionblogwithsecurity.service.serviceimplementation;

import com.decagontasks.fashionblogwithsecurity.auth.AuthenticationRequest;
import com.decagontasks.fashionblogwithsecurity.auth.AuthenticationResponse;
import com.decagontasks.fashionblogwithsecurity.auth.RegisterRequest;
import com.decagontasks.fashionblogwithsecurity.enums.TokenType;
import com.decagontasks.fashionblogwithsecurity.model.Token;
import com.decagontasks.fashionblogwithsecurity.model.UserModel;
import com.decagontasks.fashionblogwithsecurity.repository.TokenRepo;
import com.decagontasks.fashionblogwithsecurity.repository.UserRepo;
import com.decagontasks.fashionblogwithsecurity.service.AuthenticationService;
import com.decagontasks.fashionblogwithsecurity.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepo tokenRepo;
    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        var user = UserModel.builder()
                .userName(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        var savedUser = userRepo.save(user);
        var jwtToken = jwtService.generateToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepo.findUserModelByUserName(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        var jwtToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private void revokeAllUserTokens(UserModel user){
        var validUserTokens = tokenRepo.findAllValidTokensByUser(user.getId());
        if(validUserTokens.isEmpty()){
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepo.saveAll(validUserTokens);
    }

    private void saveUserToken(UserModel user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepo.save(token);
    }
}