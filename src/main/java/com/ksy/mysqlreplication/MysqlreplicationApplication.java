package com.ksy.mysqlreplication;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
import java.util.List;

@SpringBootApplication
@AllArgsConstructor
public class MysqlreplicationApplication {
    private final MemberDAO memberDAO;

    public static void main(String... arguments) {
        SpringApplication.run(MysqlreplicationApplication.class, arguments);
    }


//    @Bean
    public CommandLineRunner getRunner() {
        return arguments -> {
            Member member1 = new Member();
            Member member2 = new Member();
            Member member3 = new Member();

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
}


@Service
@AllArgsConstructor
class MemberDTO {
    private final MemberDAO memberDAO;

//    @Transactional
    public List<Member> getMembers() {
        return memberDAO.findAll();
    }


//    @Transactional
    public Member save() {
        return memberDAO.save(new Member());
    }
}


@Repository
interface MemberDAO extends JpaRepository<Member, Long> {

}
