package com.project.digitalenvelope.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
public class TicketReq implements Serializable {

    private String from;
    private String to;
    private String seat;
    private String date;
    private String gate;
}
