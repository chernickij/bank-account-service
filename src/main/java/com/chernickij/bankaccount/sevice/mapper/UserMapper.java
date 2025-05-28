package com.chernickij.bankaccount.sevice.mapper;

import com.chernickij.bankaccount.dto.UserResponse;
import com.chernickij.bankaccount.entity.Email;
import com.chernickij.bankaccount.entity.Phone;
import com.chernickij.bankaccount.entity.User;

public class UserMapper {

    public static UserResponse userToGetUserResponse(final User user) {
        return UserResponse.builder()
                .id(user.getId())
                .dateOfBirth(user.getDateOfBirth())
                .name(user.getName())
                .emails(user.getEmails().stream().map(Email::getEmail).toList())
                .phones(user.getPhones().stream().map(Phone::getPhone).toList())
                .build();
    }
}
