package com.bn.library.service;

import com.bn.library.dto.auth.AuthenticationRequest;
import com.bn.library.dto.auth.AuthenticationResponse;
import com.bn.library.dto.auth.RegisterRequest;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
