package com.chernickij.bankaccount.sevice;

import com.chernickij.bankaccount.dto.UserResponse;
import com.chernickij.bankaccount.dto.UserSearch;
import org.springframework.data.domain.Page;

public interface UserService {
    UserResponse getUser(final Long userId);

    Page<UserResponse> searchUsers(final UserSearch userSearch);
}
