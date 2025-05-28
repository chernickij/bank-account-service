package com.chernickij.bankaccount.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearch {
    private String name;
    private String email;
    private String phone;
    private LocalDateTime dateOfBirth;
    private int page;
    private int size;
}
