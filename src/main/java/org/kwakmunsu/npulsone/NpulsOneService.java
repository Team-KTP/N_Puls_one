package org.kwakmunsu.npulsone;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class NpulsOneService {

    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    public Member createMember(String name) {
        return memberRepository.save(new Member(name));
    }

    public Team createTeam(String name) {
        return teamRepository.save(new Team(name));
    }

    @Transactional
    public void registerTeam(Long memberId, Team team) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);

        if (optionalMember.isEmpty()) {
            throw new IllegalArgumentException(String.format("Member with id %d does not exist", memberId));
        }

        Member member = optionalMember.get();
        member.registerTeam(team);
    }

    // N + 1 상황 발생
    public List<Member> findAllMember() {
        List<Member> members = memberRepository.findAll();

        printTeamNameOfMember(members);

        return members;
    }

    public List<Member> findAllByFetchJoin() {
        List<Member> members = memberRepository.finAllByFetchJoin();

        printTeamNameOfMember(members);

        return members;
    }

    private void printTeamNameOfMember(List<Member> members) {
        members.forEach(member -> System.out.println(member.getTeam().getName()));
    }

}