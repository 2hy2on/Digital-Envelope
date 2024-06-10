package com.project.digitalenvelope.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
@AllArgsConstructor
public class UserReq implements Serializable {
    private char[] passportId;
    private char[] birth;
    private char[] firstName;
    private char[] lastName;
    private char[] country;
}
