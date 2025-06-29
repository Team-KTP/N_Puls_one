package org.kwakmunsu.npulsone;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TeamTest {

    private Team team;

    @BeforeEach
    void setUp() {
        team = new Team("testTeam");
    }

    @DisplayName("팀을 생성한다")
    @Test
    void createTeam() {
        assertThat(team.getName()).isEqualTo("testTeam");
    }

}