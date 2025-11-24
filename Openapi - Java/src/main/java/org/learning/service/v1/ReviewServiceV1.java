package org.learning.service.v1;

import lombok.extern.slf4j.Slf4j;
import org.learning.domain.Review;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ReviewServiceV1 {

    private final Map<UUID, Review> reviews = new ConcurrentHashMap<>();

    public List<Review> findByBookId(UUID bookId, Integer page, Integer size) {
        log.info("Finding reviews for book: {}", bookId);
        return reviews.values().stream()
                .filter(r -> r.getBookId().equals(bookId))
                .collect(Collectors.toList());
    }

    public Review create(UUID bookId, Review review) {
        review.setId(UUID.randomUUID());
        review.setBookId(bookId);
        review.setCreatedAt(OffsetDateTime.now());
        review.setMemberName("John Doe"); // Mock
        reviews.put(review.getId(), review);
        log.info("Created review: {}", review.getId());
        return review;
    }
}