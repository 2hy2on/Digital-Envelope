package com.project.digitalenvelope.controller;

import com.project.digitalenvelope.dto.ReservationReq;
import com.project.digitalenvelope.dto.TicketReq;
import com.project.digitalenvelope.dto.UserReq;
import com.project.digitalenvelope.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TicketController {

    @Autowired
    private final TicketService ticketService;


    @PostMapping("/ticket")
    public String create(@RequestBody ReservationReq reservationReq ){
        TicketReq ticketReq = TicketReq.builder()
                .date(reservationReq.getDate())
                .from(reservationReq.getFrom())
                .gate(reservationReq.getGate())
                .seat(reservationReq.getSeat())
                .to(reservationReq.getTo())
                .build();
        UserReq userReq = UserReq.builder()
                .addr(reservationReq.getAddr())
                .birth(reservationReq.getBirth())
                .phone(reservationReq.getPhone())
                .country(reservationReq.getCountry())
                .fistName(reservationReq.getFistName())
                .lastName(reservationReq.getLastName())
                .passportId(reservationReq.getPassportId())
                .build();
        ticketService.bookTicket(userReq, ticketReq);

        return "ticket/makeReservation";
    }

    @PostMapping("/ticket/reservation")
    public String read(){
        return "ticket/readReservation";
    }

    @PostMapping("/ticket/mypage")
    public TicketReq readTicket(@RequestBody UserReq userReq){
//        ticketService.readTicketHistory(userReq);

        return ticketService.readTicketHistory(userReq);
    }



}
