package com.project.digitalenvelope.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Member {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    private char[] firstName;

    private String privateKeyPath;

    private String publicKeyPath;
    private String secretKey;

    private byte[] signature;

}
