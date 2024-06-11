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
        TicketReq ticketReq = TicketReq.builder()
                .date(reservationReq.getDate())
                .from(reservationReq.getFrom())
                .seat(reservationReq.getSeat())
                .to(reservationReq.getTo())
                .build();
        UserReq userReq = UserReq.builder()
                .birth(reservationReq.getBirth())
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
        if(ticketReq.getDate() == null){
            model.addAttribute("result", false);
            log.info("들어옴");
            return "/member/mypage";
        }
        else{
            ReservationRes reservationRes = ReservationRes.builder()
                    .seat(ticketReq.getSeat())
                    .passportId(userReq.getPassportId())
                    .lastName(userReq.getLastName())
                    .firstName(userReq.getFirstName())
                    .from(ticketReq.getFrom())
                    .to(ticketReq.getTo())
                    .date(ticketReq.getDate())
                    .birth(userReq.getBirth())
                    .country(userReq.getCountry())
                    .build();
            model.addAttribute("reservationRes", reservationRes);
            model.addAttribute("lastName", String.valueOf(reservationRes.getLastName()));
            model.addAttribute("firstName", String.valueOf(reservationRes.getFirstName()));
            model.addAttribute("passportId", String.valueOf(reservationRes.getPassportId()));
            model.addAttribute("birth", String.valueOf(reservationRes.getBirth()));
            model.addAttribute("country", String.valueOf(reservationRes.getCountry()));
            return "/member/ticketHistory";
        }
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
