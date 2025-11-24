package org.learning.controller.v1;

import com.example.library.api.MembersApi;
import com.example.library.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.learning.domain.Loan;
import org.learning.domain.Member;
import org.learning.mapper.v1.MemberMapperV1;
import org.learning.service.v1.MemberServiceV1;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class MemberControllerV1 implements MembersApi {

    private final MemberServiceV1 memberService;
    private final MemberMapperV1 memberMapper;

    @Override
    public ResponseEntity<MemberCollectionResponseV1> getMembersV1(Integer page, Integer size, String email, String status) {
        List<Member> members = memberService.findAll(page, size, email, status);
        List<MemberResponseV1> responses = memberMapper.toResponseList(members);

        MemberCollectionResponseV1 collection = new MemberCollectionResponseV1();
        collection.setItems(responses);
        collection.setPage(page != null ? page : 0);
        collection.setSize(size != null ? size : 20);
        collection.setTotalElements(responses.size());
        collection.setTotalPages(1);

        return ResponseEntity.ok(collection);
    }

    @Override
    public ResponseEntity<MemberResponseV1> getMemberV1(UUID memberId) {
        return memberService.findById(memberId)
                .map(memberMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<MemberResponseV1> createMemberV1(CreateMemberRequestV1 createMemberRequestV1) {
        Member member = memberMapper.fromCreateRequest(createMemberRequestV1);
        Member created = memberService.create(member);
        MemberResponseV1 response = memberMapper.toResponse(created);

        return ResponseEntity.created(URI.create("/api/v1/members/" + created.getId()))
                .body(response);
    }

    @Override
    public ResponseEntity<MemberResponseV1> updateMemberV1(UUID memberId, UpdateMemberRequestV1 updateMemberRequestV1) {
        return memberService.findById(memberId)
                .map(existing -> {
                    memberMapper.updateMemberFromRequest(updateMemberRequestV1, existing);
                    Member updated = memberService.update(memberId, existing);
                    return ResponseEntity.ok(memberMapper.toResponse(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<LoanCollectionResponseV1> getMemberLoansV1(UUID memberId, String status) {
        List<Loan> loans = memberService.getMemberLoans(memberId, status);

        List<LoanResponseV1> responses = loans.stream()
                .map(loan -> {
                    LoanResponseV1 response = new LoanResponseV1();
                    response.setId(loan.getId());
                    response.setBookId(loan.getBookId());
                    response.setBookTitle(loan.getBookTitle());
                    response.setMemberId(loan.getMemberId());
                    response.setMemberName(loan.getMemberName());
                    response.setLoanDate(loan.getLoanDate());
                    response.setDueDate(loan.getDueDate());
                    response.setReturnDate(loan.getReturnDate());
                    response.setRenewalCount(loan.getRenewalCount());
                    response.setStatus(LoanResponseV1.StatusEnum.fromValue(loan.getStatus().name()));
                    response.setFine(loan.getFine());
                    return response;
                })
                .toList();

        LoanCollectionResponseV1 collection = new LoanCollectionResponseV1();
        collection.setItems(responses);
        collection.setPage(0);
        collection.setSize(responses.size());
        collection.setTotalElements(responses.size());
        collection.setTotalPages(1);

        return ResponseEntity.ok(collection);
    }

    @Override
    public ResponseEntity<MemberResponseV1> suspendMemberV1(UUID memberId, SuspendMemberRequestV1 suspendMemberRequestV1) {
        Member suspended = memberService.suspend(memberId,
                suspendMemberRequestV1.getReason(),
                suspendMemberRequestV1.getUntil());

        if (suspended == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(memberMapper.toResponse(suspended));
    }

    @Override
    public ResponseEntity<MemberResponseV1> activateMemberV1(UUID memberId) {
        Member activated = memberService.activate(memberId);

        if (activated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(memberMapper.toResponse(activated));
    }
}