package com.chernickij.bankaccount.sevice;

import com.chernickij.bankaccount.dto.AuthenticationRequest;
import com.chernickij.bankaccount.dto.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse authenticate(final AuthenticationRequest request);
}
