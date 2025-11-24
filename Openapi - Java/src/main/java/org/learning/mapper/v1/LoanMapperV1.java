package org.learning.mapper.v1;

import com.example.library.model.CreateLoanRequestV1;
import com.example.library.model.LoanResponseV1;
import org.learning.domain.Loan;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LoanMapperV1 {

    @Mapping(target = "status", source = "status")
    LoanResponseV1 toResponse(Loan loan);

    List<LoanResponseV1> toResponseList(List<Loan> loans);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "loanDate", ignore = true)
    @Mapping(target = "returnDate", ignore = true)
    @Mapping(target = "renewalCount", constant = "0")
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "fine", constant = "0.0")
    @Mapping(target = "bookTitle", ignore = true)
    @Mapping(target = "memberName", ignore = true)
    Loan fromCreateRequest(CreateLoanRequestV1 request);

    default LoanResponseV1.StatusEnum mapStatus(Loan.LoanStatus status) {
        return status != null ? LoanResponseV1.StatusEnum.fromValue(status.name()) : null;
    }
}