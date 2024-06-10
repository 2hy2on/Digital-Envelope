package com.project.digitalenvelope.controller;

import com.project.digitalenvelope.dto.ReservationReq;
import com.project.digitalenvelope.dto.ReservationRes;
import com.project.digitalenvelope.dto.TicketReq;
import com.project.digitalenvelope.dto.UserReq;
import com.project.digitalenvelope.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class TicketController {

    @Autowired
    private final TicketService ticketService;


    //티켓 예매
    @PostMapping("/ticket")
    public String create(ReservationReq reservationReq ){
        log.info(reservationReq.getAddr());
        TicketReq ticketReq = TicketReq.builder()
                .date(reservationReq.getDate())
                .from(reservationReq.getFrom())
                .seat(reservationReq.getSeat())
                .to(reservationReq.getTo())
                .build();
        UserReq userReq = UserReq.builder()
                .addr(reservationReq.getAddr())
                .birth(reservationReq.getBirth())
                .phone(reservationReq.getPhone())
                .country(reservationReq.getCountry())
                .firstName(reservationReq.getFirstName())
                .lastName(reservationReq.getLastName())
                .passportId(reservationReq.getPassportId())
                .build();
        ticketService.bookTicket(userReq, ticketReq);

        return "ticket/finishReservation";
    }

    @PostMapping("/ticket/history")
    public String showMemberReservation(Model model, UserReq userReq) {
        TicketReq ticketReq = ticketService.readTicketHistory(userReq);
        ReservationRes reservationRes = ReservationRes.builder()
                .seat(ticketReq.getSeat())
                .passportId(userReq.getPassportId())
                .lastName(userReq.getLastName())
                .firstName(userReq.getFirstName())
                .addr(userReq.getAddr())
                .from(ticketReq.getFrom())
                .to(ticketReq.getTo())
                .date(ticketReq.getDate())
                .birth(userReq.getBirth())
                .country(userReq.getCountry())
                .build();
        model.addAttribute("reservationRes", reservationRes);
        return "/member/ticketHistory";
    }


    @GetMapping("/ticket/reservationForm")
    public String showReservationForm() {
        return "ticket/reservationForm";
    }

    @GetMapping("/ticket/checkMember")
    public String showMemberReservation() {
        return "member/mypage";
    }


}
