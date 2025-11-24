package org.learning.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private UUID id;
    private String title;
    private String author;
    private String isbn;
    private Integer publishedYear;
    private String category;
    private String language;
    private Integer pages;
    private String publisher;
    private String description;
    private Boolean available;
    private Integer totalCopies;
    private Integer availableCopies;
    private Double averageRating;
    private Integer totalReviews;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}