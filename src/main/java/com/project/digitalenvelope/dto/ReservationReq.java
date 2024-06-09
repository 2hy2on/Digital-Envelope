package com.project.digitalenvelope.dto;

import lombok.Getter;

@Getter
public class ReservationReq {
    private String from;
    private String to;
    private String seat;
    private String date;
    private String gate;
    private String passportId;
    private String birth;
    private String fistName;
    private String lastName;
    private String country;
    private String phone;
    private String addr;
}
