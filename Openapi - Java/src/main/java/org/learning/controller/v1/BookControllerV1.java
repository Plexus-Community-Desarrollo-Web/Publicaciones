package org.learning.controller.v1;

import com.example.library.api.BooksApi;
import com.example.library.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.learning.domain.Book;
import org.learning.mapper.v1.BookMapperV1;
import org.learning.service.v1.BookServiceV1;
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
public class BookControllerV1 implements BooksApi {

    private final BookServiceV1 bookService;
    private final BookMapperV1 bookMapper;

    @Override
    public ResponseEntity<BookCollectionResponseV1> getBooksV1(
            Integer page, Integer size, String sort, String direction,
            String title, String author, String isbn, String category,
            Boolean available, Integer minYear, Integer maxYear) {

        List<Book> books = bookService.findAll(page, size, sort, direction,
                title, author, isbn, category, available, minYear, maxYear);

        List<BookResponseV1> responses = bookMapper.toResponseList(books);

        int total = bookService.getTotalCount();
        int totalPages = (int) Math.ceil((double) total / size);

        BookCollectionResponseV1 collection = new BookCollectionResponseV1();
        collection.setItems(responses);
        collection.setPage(page);
        collection.setSize(size);
        collection.setTotalElements(total);
        collection.setTotalPages(totalPages);

        return ResponseEntity.ok(collection);
    }

    @Override
    public ResponseEntity<BookResponseV1> getBookV1(UUID bookId) {
        return bookService.findById(bookId)
                .map(bookMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<BookResponseV1> createBookV1(CreateBookRequestV1 createBookRequestV1) {
        Book book = bookMapper.fromCreateRequest(createBookRequestV1);
        Book created = bookService.create(book);
        BookResponseV1 response = bookMapper.toResponse(created);

        return ResponseEntity.created(URI.create("/api/v1/books/" + created.getId()))
                .body(response);
    }

    @Override
    public ResponseEntity<BookResponseV1> updateBookV1(UUID bookId, UpdateBookRequestV1 updateBookRequestV1) {
        return bookService.findById(bookId)
                .map(existing -> {
                    bookMapper.updateBookFromRequest(updateBookRequestV1, existing);
                    Book updated = bookService.update(bookId, existing);
                    return ResponseEntity.ok(bookMapper.toResponse(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<BookResponseV1> patchBookV1(UUID bookId, PatchBookRequestV1 patchBookRequestV1) {
        return bookService.findById(bookId)
                .map(existing -> {
                    bookMapper.patchBookFromRequest(patchBookRequestV1, existing);
                    Book updated = bookService.update(bookId, existing);
                    return ResponseEntity.ok(bookMapper.toResponse(updated));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<Void> deleteBookV1(UUID bookId) {
        if (bookService.findById(bookId).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        bookService.delete(bookId);
        return ResponseEntity.noContent().build();
    }
}