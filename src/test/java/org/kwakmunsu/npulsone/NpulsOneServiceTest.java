package org.kwakmunsu.npulsone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.persistence.EntityManager;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
record NpulsOneServiceTest(NpulsOneService npulsOneService, MemberRepository memberRepository, TeamRepository teamRepository, EntityManager entityManager) {

    @DisplayName("멤버 생성")
    @Test
    void createMember() {
        Member member = npulsOneService.createMember("kwakmunsu");

        System.out.println(member);

        assertThat(member.getId()).isNotNull();
        assertThat(member.getName()).isEqualTo("kwakmunsu");
    }

    @DisplayName("팀 생성")
    @Test
    void createTeam() {
        Team team = npulsOneService.createTeam("testTeam");

        System.out.println(team);

        assertThat(team.getId()).isNotNull();
        assertThat(team.getName()).isEqualTo("testTeam");
    }

    @DisplayName("팀 등록")
    @Test
    void registerTeam() {
        Member member = npulsOneService.createMember("kwakmunsu");
        Team team = npulsOneService.createTeam("testTeam");
        assertThat(member.getTeam()).isNull();

        npulsOneService.registerTeam(member.getId(), team);

        entityManager.flush();

        assertThat(member.getTeam().getName()).isEqualTo("testTeam");
    }

    @DisplayName("팀 등록 실패")
    @Test
    void registerTeamFail() {
        Team team = npulsOneService.createTeam("testTeam");

        assertThatThrownBy(() -> npulsOneService.registerTeam(912312313139L, team))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("N+1 발생")
    @Test
    void findAll() {
        extracted();

        entityManager.flush();
        entityManager.clear();

        List<Member> allMember = npulsOneService.findAllMember();

    }

    @DisplayName("N+1 해결 by Fetch Join")
    @Test
    void findAllByFetchJoin() {
        extracted();

        entityManager.flush();
        entityManager.clear();

        List<Member> allMember = npulsOneService.findAllByFetchJoin();

    }

    private void extracted() {
        Member m1 = npulsOneService.createMember("1");
        Member m2 = npulsOneService.createMember("2");
        Member m3 = npulsOneService.createMember("3");
        Team t1 = npulsOneService.createTeam("t1");
        Team t2 = npulsOneService.createTeam("t2");
        Team t3 = npulsOneService.createTeam("t3");

        npulsOneService.registerTeam(m1.getId(), t1);
        npulsOneService.registerTeam(m2.getId(), t2);
        npulsOneService.registerTeam(m3.getId(), t3);
    }

}