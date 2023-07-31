package com.decagontasks.fashionblogwithsecurity.service;

import com.decagontasks.fashionblogwithsecurity.auth.AuthenticationRequest;
import com.decagontasks.fashionblogwithsecurity.auth.AuthenticationResponse;
import com.decagontasks.fashionblogwithsecurity.auth.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
