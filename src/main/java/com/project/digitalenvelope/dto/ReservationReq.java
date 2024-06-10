package com.project.digitalenvelope.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReservationReq {
    private String from;
    private String to;
    private String seat;
    private String date;
    private String passportId;
    private String birth;
    private String firstName;
    private String lastName;
    private String country;
    private String phone;
    private String addr;
}
