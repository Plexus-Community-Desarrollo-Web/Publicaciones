package org.learning.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {
    private UUID id;
    private UUID bookId;
    private String bookTitle;
    private UUID memberId;
    private String memberName;
    private OffsetDateTime loanDate;
    private LocalDate dueDate;
    private OffsetDateTime returnDate;
    private Integer renewalCount;
    private LoanStatus status;
    private Double fine;

    public enum LoanStatus {
        ACTIVE, RETURNED, OVERDUE
    }
}