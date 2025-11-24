package org.learning.mapper.v1;

import com.example.library.model.CreateMemberRequestV1;
import com.example.library.model.MemberResponseV1;
import com.example.library.model.UpdateMemberRequestV1;
import org.learning.domain.Member;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapperV1 {

    @Mapping(target = "membershipType", source = "membershipType")
    @Mapping(target = "status", source = "status")
    MemberResponseV1 toResponse(Member member);

    List<MemberResponseV1> toResponseList(List<Member> members);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "memberSince", ignore = true)
    @Mapping(target = "expiresAt", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "activeLoans", constant = "0")
    @Mapping(target = "totalLoans", constant = "0")
    @Mapping(target = "currentFines", constant = "0.0")
    Member fromCreateRequest(CreateMemberRequestV1 request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateMemberFromRequest(UpdateMemberRequestV1 request, @MappingTarget Member member);

    default MemberResponseV1.MembershipTypeEnum mapMembershipType(Member.MembershipType type) {
        return type != null ? MemberResponseV1.MembershipTypeEnum.fromValue(type.name()) : null;
    }

    default MemberResponseV1.StatusEnum mapStatus(Member.MemberStatus status) {
        return status != null ? MemberResponseV1.StatusEnum.fromValue(status.name()) : null;
    }
}