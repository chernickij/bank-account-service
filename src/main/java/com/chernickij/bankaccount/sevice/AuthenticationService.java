package com.chernickij.bankaccount.sevice;

import com.chernickij.bankaccount.dto.*;

public interface AuthenticationService {

    JwtAuthenticationResponse signUp(SignUpRequest request);

    JwtAuthenticationResponse signIn(SignInRequest request);
}
