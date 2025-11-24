package org.learning.service.v1;

import lombok.extern.slf4j.Slf4j;
import org.learning.domain.Loan;
import org.springframework.stereotype.Service;

import org.learning.domain.Member;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class MemberServiceV1 {

    private LoanServiceV1 loanServiceV1;

    private final Map<UUID, Member> members = new ConcurrentHashMap<>();

    public List<Member> findAll(Integer page, Integer size, String email, String status) {
        log.info("Finding members");
        return new ArrayList<>(members.values());
    }

    public Optional<Member> findById(UUID id) {
        log.info("Finding member by id: {}", id);
        return Optional.ofNullable(members.get(id));
    }

    public Member create(Member member) {
        member.setId(UUID.randomUUID());
        member.setMemberSince(LocalDate.now());
        member.setExpiresAt(LocalDate.now().plusYears(1));
        members.put(member.getId(), member);
        log.info("Created member: {}", member.getId());
        return member;
    }

    public Member update(UUID id, Member member) {
        member.setId(id);
        members.put(id, member);
        log.info("Updated member: {}", id);
        return member;
    }

    public List<Loan> getMemberLoans(UUID memberId, String status) {
        log.info("Getting loans for member: {}", memberId);
        return loanServiceV1.findByMemberId(memberId, status);
    }

    public Member suspend(UUID id, String reason, LocalDate until) {
        Member member = members.get(id);
        if (member != null) {
            member.setStatus(Member.MemberStatus.SUSPENDED);
            log.info("Suspended member: {} until: {}", id, until);
        }
        return member;
    }

    public Member activate(UUID id) {
        Member member = members.get(id);
        if (member != null) {
            member.setStatus(Member.MemberStatus.ACTIVE);
            log.info("Activated member: {}", id);
        }
        return member;
    }

    public int getTotalCount() {
        return members.size();
    }
}