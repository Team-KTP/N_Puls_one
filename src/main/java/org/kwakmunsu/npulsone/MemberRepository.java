package org.kwakmunsu.npulsone;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m join fetch m.team")
    List<Member> finAllByFetchJoin();

    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findAllEntityGraph();

}