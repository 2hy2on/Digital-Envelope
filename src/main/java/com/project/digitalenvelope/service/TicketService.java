package com.project.digitalenvelope.service;

import com.project.digitalenvelope.KeyManager;
import com.project.digitalenvelope.SignatureManager;
import com.project.digitalenvelope.SymmetricKeyManager;
import com.project.digitalenvelope.dto.TicketReq;
import com.project.digitalenvelope.dto.UserReq;
import com.project.digitalenvelope.entity.Member;
import com.project.digitalenvelope.entity.Ticket;
import com.project.digitalenvelope.repository.MemberRepository;
import com.project.digitalenvelope.repository.TicketRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.crypto.SecretKey;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Random;


@Service
@RequiredArgsConstructor
@Slf4j
public class TicketService {


    final MemberRepository memberRepository;

    final TicketRepository ticketRepository;

    public boolean bookTicket(UserReq userReq, TicketReq ticketReq){ //전자 서명 생성
        Random random = new Random();
        int randomNum = random.nextInt(1001);

        //key 생성 및 저장
        KeyManager keyManager = new KeyManager();
        keyManager.create(1024);
        //로컬에 저장
        keyManager.saveKey(keyManager.getPrivateKey(),randomNum+"privateKey");
        keyManager.saveKey(keyManager.getPublicKey(), randomNum+"publicKey");

        //직렬화
        byte[] data = serializeObject(userReq);

        //전자봉투 생성
        SignatureManager signatureManager = new SignatureManager();
        byte[] signatureData = signatureManager.create( keyManager.getPrivateKey(), data);

        //항공권 정보 암호화
        byte[] ticketData = serializeObject(ticketReq);
        SymmetricKeyManager symmetricKeyManager = new SymmetricKeyManager();
        symmetricKeyManager.create(128);
        byte[] encryptedTicketInfo = symmetricKeyManager.encrypt(ticketData, symmetricKeyManager.getSecretKey());
        //대칭키 저장
        symmetricKeyManager.saveKey(symmetricKeyManager.getSecretKey(),  randomNum+"secretKey");

        //Data DB에 저장
        Member member = Member.builder()
                .privateKeyPath(randomNum+"privateKey")
                .publicKeyPath(randomNum+"publicKey")
                .secretKey(randomNum+"secretKey")
                .firstName(userReq.getFistName())
                .signature(signatureData)
                .build();
        Member savedMember = memberRepository.save(member);

        Ticket ticket = Ticket.builder()
                .info(encryptedTicketInfo)
                .memberId(savedMember.getMemberId()).build();

//        log.info("디비에 넣기 전" + Arrays.toString(data));


        ticketRepository.save(ticket);



        return true;
    }

    public TicketReq readTicketHistory(UserReq userReq){


        Member member = memberRepository.findByFirstName(userReq.getFistName());

        //퍼블릭키 가져오기
        String publicKeyName = member.getPublicKeyPath();
        KeyManager keyManager = new KeyManager();
        keyManager.create(1024);
        PublicKey publicKey = (PublicKey) keyManager.loadKey(publicKeyName);

        SignatureManager signatureManager = new SignatureManager();
//        log.info(userReq.getPassportId().equals(member.getP));
        //입력된 직렬화
        byte[] data = serializeObject(userReq);

        //verify
        boolean res = signatureManager.verify(data, member.getSignature(),publicKey);

        log.info(String.valueOf(res));
        if (res){
            //데이터 가져오기
            Ticket ticket = ticketRepository.findByMemberId(member.getMemberId());
            byte[] encryptedData = ticket.getInfo();
            //시크릿키 가져오기
            SecretKey secretKey = (SecretKey) keyManager.loadKey(member.getSecretKey());

            SymmetricKeyManager symmetricKeyManager = new SymmetricKeyManager();
            symmetricKeyManager.create(128);
            byte[] ticketData= symmetricKeyManager.decrypt(encryptedData, secretKey);
            return deserializeTicket(ticketData);
        }
        else {
            throw new RuntimeException();
        }

    }

    public TicketReq deserializeTicket(byte[] ticketData) {
        TicketReq ticketReq = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(ticketData);
            ObjectInputStream ois = new ObjectInputStream(bis);
            ticketReq = (TicketReq) ois.readObject();
            ois.close();
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ticketReq;
    }
    private byte[] serializeObject(Object obj) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            return baos.toByteArray();
        } catch (Exception e) {
            // 예외 처리
            e.printStackTrace();
            return null;
        }
    }

}
