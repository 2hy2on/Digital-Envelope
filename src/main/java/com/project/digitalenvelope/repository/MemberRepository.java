package com.project.digitalenvelope.repository;

import com.project.digitalenvelope.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByFirstName(String firstName);
}
