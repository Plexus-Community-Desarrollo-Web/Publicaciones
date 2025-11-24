package com.example.library.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    private UUID id;
    private String name;
    private String biography;
    private String birthDate;
    private String nationality;
    private String website;
    private Integer totalBooks;
}