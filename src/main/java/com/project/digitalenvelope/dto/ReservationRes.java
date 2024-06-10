package com.project.digitalenvelope.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationRes {
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
