package org.learning.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Address address;
    private MembershipType membershipType;
    private MemberStatus status;
    private LocalDate memberSince;
    private LocalDate expiresAt;
    private Integer activeLoans;
    private Integer totalLoans;
    private Double currentFines;

    public enum MembershipType {
        BASIC, PREMIUM, STUDENT
    }

    public enum MemberStatus {
        ACTIVE, SUSPENDED, EXPIRED
    }
}