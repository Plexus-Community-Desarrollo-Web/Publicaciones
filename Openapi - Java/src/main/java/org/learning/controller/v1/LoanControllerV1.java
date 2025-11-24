package org.learning.controller.v1;

import com.example.library.api.LoansApi;
import com.example.library.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.learning.domain.Loan;
import org.learning.mapper.v1.LoanMapperV1;
import org.learning.service.v1.LoanServiceV1;
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
public class LoanControllerV1 implements LoansApi {

    private final LoanServiceV1 loanService;
    private final LoanMapperV1 loanMapper;

    @Override
    public ResponseEntity<LoanCollectionResponseV1> getBookLoansV1(UUID bookId, String status) {
        List<Loan> loans = loanService.findByBookId(bookId, status);
        List<LoanResponseV1> responses = loanMapper.toResponseList(loans);

        LoanCollectionResponseV1 collection = new LoanCollectionResponseV1();
        collection.setItems(responses);
        collection.setPage(0);
        collection.setSize(responses.size());
        collection.setTotalElements(responses.size());
        collection.setTotalPages(1);

        return ResponseEntity.ok(collection);
    }

    @Override
    public ResponseEntity<LoanResponseV1> loanBookV1(UUID bookId, CreateLoanRequestV1 createLoanRequestV1) {
        Loan loan = loanMapper.fromCreateRequest(createLoanRequestV1);
        Loan created = loanService.create(bookId, loan);
        LoanResponseV1 response = loanMapper.toResponse(created);

        return ResponseEntity.created(URI.create("/api/v1/loans/" + created.getId()))
                .body(response);
    }

    @Override
    public ResponseEntity<LoanResponseV1> getLoanV1(UUID loanId) {
        return loanService.findById(loanId)
                .map(loanMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<LoanResponseV1> returnLoanV1(UUID loanId, ReturnLoanRequestV1 returnLoanRequestV1) {
        String condition = returnLoanRequestV1 != null && returnLoanRequestV1.getCondition() != null
                ? returnLoanRequestV1.getCondition().getValue() : "GOOD";
        String notes = returnLoanRequestV1 != null ? returnLoanRequestV1.getNotes() : null;

        Loan returned = loanService.returnLoan(loanId, condition, notes);

        if (returned == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(loanMapper.toResponse(returned));
    }

    @Override
    public ResponseEntity<LoanResponseV1> renewLoanV1(UUID loanId, RenewLoanRequestV1 renewLoanRequestV1) {
        String newDueDate = renewLoanRequestV1 != null ? renewLoanRequestV1.getNewDueDate().toString() : null;

        Loan renewed = loanService.renewLoan(loanId, newDueDate);

        if (renewed == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(loanMapper.toResponse(renewed));
    }
}