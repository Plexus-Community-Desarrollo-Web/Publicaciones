package org.learning.mapper.v1;


import com.example.library.model.BookResponseV1;
import com.example.library.model.CreateBookRequestV1;
import com.example.library.model.PatchBookRequestV1;
import com.example.library.model.UpdateBookRequestV1;
import org.learning.domain.Book;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookMapperV1 {

    BookResponseV1 toResponse(Book book);

    List<BookResponseV1> toResponseList(List<Book> books);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "availableCopies", source = "totalCopies")
    @Mapping(target = "available", constant = "true")
    @Mapping(target = "averageRating", constant = "0.0")
    @Mapping(target = "totalReviews", constant = "0")
    Book fromCreateRequest(CreateBookRequestV1 request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBookFromRequest(UpdateBookRequestV1 request, @MappingTarget Book book);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void patchBookFromRequest(PatchBookRequestV1 request, @MappingTarget Book book);
}