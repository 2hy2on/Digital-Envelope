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
    private char[] passportId;
    private char[] birth;
    private char[] firstName;
    private char[] lastName;
    private char[] country;
}
