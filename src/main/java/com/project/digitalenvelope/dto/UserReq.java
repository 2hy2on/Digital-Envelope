package com.project.digitalenvelope.dto;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Getter
@Builder
public class UserReq implements Serializable {
    private String passportId;
    private String birth;
    private String fistName;
    private String lastName;
    private String country;
    private String phone;
    private String addr;
}
