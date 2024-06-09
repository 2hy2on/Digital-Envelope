package com.project.digitalenvelope.repository;

import com.project.digitalenvelope.entity.Member;
import com.project.digitalenvelope.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Ticket findByMemberId(Long id);
}
