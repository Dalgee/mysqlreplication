package com.ksy.mysqlreplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class MysqlreplicationApplication {
    private final MemberDAO memberDAO;

    public static void main(String... arguments) {
        SpringApplication.run(MysqlreplicationApplication.class, arguments);
    }


    @Bean
    public CommandLineRunner getRunner() {
        return arguments -> {
            Member member1 = new Member();
            Member member2 = new Member();
            Member member3 = new Member();

            member1.setName("name1");
            member2.setName("name2");
            member3.setName("name3");

            member1.setBalance(null);
            member2.setBalance(new BigDecimal("0.1111111"));
            member3.setBalance(new BigDecimal("1111366347"));
//            member1.setBalance(1.0d);
//            member2.setBalance(11.111111111111111d);
//            member3.setBalance(20.2222222222222222d);

            member1 = memberDAO.save(member1);
            member2 = memberDAO.save(member2);
            member3 = memberDAO.save(member3);


        };
    }
}



@Entity
@Data
class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String name;


    private BigDecimal balance;

}


@RestController
@RequestMapping("/")
@AllArgsConstructor
class MemberController {

    private final MemberDTO memberDTO;

    @GetMapping
    public List<Member> getMembers() {
        return memberDTO.getMembers();
    }

    @GetMapping("/save")
    public Member save() {
        return memberDTO.save();
    }


    @GetMapping("/get")
    public Member get() {
        return memberDTO.get();
    }
}


@Service
class MemberDTO {
    private final MemberDAO memberDAO;
    private String data = "name10";

    public MemberDTO(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

//    @Transactional
    public List<Member> getMembers() {
        return memberDAO.findAll();
    }


//    @Transactional
    public Member save() {
        return memberDAO.save(new Member());
    }


//    @Transactional
    public Member get() {
        Member member = memberDAO.findById(1L).get();

        String n = new String(data);
        System.out.println(n == data);
        member.setName(n);

        return memberDAO.save(member);
    }
}


@Repository
interface MemberDAO extends JpaRepository<Member, Long> {

}
