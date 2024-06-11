package com.project.digitalenvelope.dto;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketReq implements Serializable {

    private String from;
    private String to;
    private String seat;
    private String date;
}
