package org.kwakmunsu.npulsone;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    private Member member;

    @BeforeEach
    void setUp() {
        member = new Member("kwakmunsu");
    }

    @DisplayName("멤버 생성")
    @Test
    void createMember() {
        assertThat(member.getName()).isEqualTo("kwakmunsu");
    }

    @DisplayName("팀을 등록한다")
    @Test
    void registerTeam() {
        member.registerTeam(new Team("testTeam"));

        assertThat(member.getTeam()).isNotNull();
        assertThat(member.getTeam().getName()).isEqualTo("testTeam");
    }

}