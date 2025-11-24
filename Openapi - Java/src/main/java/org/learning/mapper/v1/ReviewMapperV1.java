package org.learning.mapper.v1;

import com.example.library.model.CreateReviewRequestV1;
import com.example.library.model.ReviewResponseV1;
import org.learning.domain.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReviewMapperV1 {

    ReviewResponseV1 toResponse(Review review);

    List<ReviewResponseV1> toResponseList(List<Review> reviews);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "memberName", ignore = true)
    Review fromCreateRequest(CreateReviewRequestV1 request);
}