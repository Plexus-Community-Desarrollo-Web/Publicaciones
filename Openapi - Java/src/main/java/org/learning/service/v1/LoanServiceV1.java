package org.learning.service.v1;


import lombok.extern.slf4j.Slf4j;
import org.learning.domain.Loan;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LoanServiceV1 {

    private final Map<UUID, Loan> loans = new ConcurrentHashMap<>();

    public List<Loan> findByBookId(UUID bookId, String status) {
        log.info("Finding loans for book: {}", bookId);
        return loans.values().stream()
                .filter(l -> l.getBookId().equals(bookId))
                .collect(Collectors.toList());
    }

    public List<Loan> findByMemberId(UUID memberId, String status) {
        log.info("Finding loans for member: {}", memberId);
        return loans.values().stream()
                .filter(l -> l.getMemberId().equals(memberId))
                .collect(Collectors.toList());
    }

    public Optional<Loan> findById(UUID id) {
        return Optional.ofNullable(loans.get(id));
    }

    public Loan create(UUID bookId, Loan loan) {
        loan.setId(UUID.randomUUID());
        loan.setBookId(bookId);
        loan.setLoanDate(OffsetDateTime.now());
        loan.setBookTitle("Sample Book");
        loan.setMemberName("Jane Doe");
        loans.put(loan.getId(), loan);
        log.info("Created loan: {}", loan.getId());
        return loan;
    }

    public Loan returnLoan(UUID id, String condition, String notes) {
        Loan loan = loans.get(id);
        if (loan != null) {
            loan.setReturnDate(OffsetDateTime.now());
            loan.setStatus(Loan.LoanStatus.RETURNED);
            log.info("Returned loan: {}", id);
        }
        return loan;
    }

    public Loan renewLoan(UUID id, String newDueDate) {
        Loan loan = loans.get(id);
        if (loan != null) {
            loan.setRenewalCount(loan.getRenewalCount() + 1);
            log.info("Renewed loan: {}", id);
        }
        return loan;
    }
}