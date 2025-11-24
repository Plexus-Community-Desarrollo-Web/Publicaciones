package org.learning.service.v1;

import lombok.extern.slf4j.Slf4j;
import org.learning.domain.Book;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class BookServiceV1 {

    private final Map<UUID, Book> books = new ConcurrentHashMap<>();

    public List<Book> findAll(Integer page, Integer size, String sort, String direction,
                              String title, String author, String isbn, String category,
                              Boolean available, Integer minYear, Integer maxYear) {
        log.info("Finding books with filters");
        return new ArrayList<>(books.values());
    }

    public Optional<Book> findById(UUID id) {
        log.info("Finding book by id: {}", id);
        return Optional.ofNullable(books.get(id));
    }

    public Book create(Book book) {
        book.setId(UUID.randomUUID());
        book.setCreatedAt(OffsetDateTime.now());
        book.setUpdatedAt(OffsetDateTime.now());
        books.put(book.getId(), book);
        log.info("Created book: {}", book.getId());
        return book;
    }

    public Book update(UUID id, Book book) {
        book.setId(id);
        book.setUpdatedAt(OffsetDateTime.now());
        books.put(id, book);
        log.info("Updated book: {}", id);
        return book;
    }

    public void delete(UUID id) {
        books.remove(id);
        log.info("Deleted book: {}", id);
    }

    public int getTotalCount() {
        return books.size();
    }
}