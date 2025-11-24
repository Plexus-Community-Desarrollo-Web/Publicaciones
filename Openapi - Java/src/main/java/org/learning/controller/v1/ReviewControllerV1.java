package org.learning.controller.v1;

import com.example.library.api.ReviewsApi;
import com.example.library.model.CreateReviewRequestV1;
import com.example.library.model.ReviewCollectionResponseV1;
import com.example.library.model.ReviewResponseV1;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.learning.domain.Review;
import org.learning.mapper.v1.ReviewMapperV1;
import org.learning.service.v1.ReviewServiceV1;
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
public class ReviewControllerV1 implements ReviewsApi {

    private final ReviewServiceV1 reviewService;
    private final ReviewMapperV1 reviewMapper;

    @Override
    public ResponseEntity<ReviewCollectionResponseV1> getBookReviewsV1(UUID bookId, Integer page, Integer size) {
        List<Review> reviews = reviewService.findByBookId(bookId, page, size);
        List<ReviewResponseV1> responses = reviewMapper.toResponseList(reviews);

        ReviewCollectionResponseV1 collection = new ReviewCollectionResponseV1();
        collection.setItems(responses);
        collection.setPage(page);
        collection.setSize(size);
        collection.setTotalElements(reviews.size());
        collection.setTotalPages(1);

        return ResponseEntity.ok(collection);
    }

    @Override
    public ResponseEntity<ReviewResponseV1> createBookReviewV1(UUID bookId, CreateReviewRequestV1 createReviewRequestV1) {
        Review review = reviewMapper.fromCreateRequest(createReviewRequestV1);
        Review created = reviewService.create(bookId, review);
        ReviewResponseV1 response = reviewMapper.toResponse(created);

        return ResponseEntity.created(URI.create("/api/v1/books/" + bookId + "/reviews/" + created.getId()))
                .body(response);
    }
}