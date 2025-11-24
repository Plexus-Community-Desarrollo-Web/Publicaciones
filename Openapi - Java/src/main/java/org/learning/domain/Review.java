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
public class Review {
    private UUID id;
    private UUID bookId;
    private UUID memberId;
    private String memberName;
    private Integer rating;
    private String comment;
    private OffsetDateTime createdAt;
}