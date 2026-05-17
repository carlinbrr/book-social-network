package com.bsn.api.adapters.input.rest;

import com.bsn.api.adapters.input.rest.mapper.BookMapper;
import com.bsn.api.core.entity.Book;
import com.bsn.api.core.model.BookDetails;
import com.bsn.api.core.port.input.FindBookDetailsUseCase;
import com.bsn.api.core.port.input.SaveBookUseCase;
import com.bsn.api.adapters.input.rest.dto.BookRequest;
import com.bsn.api.legacy.book.BookResponse;
import com.bsn.api.legacy.book.BookService;
import com.bsn.api.legacy.book.BorrowedBookResponse;
import com.bsn.api.legacy.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("books")
@Tag(name = "Book", description = "API for Books")
public class BookRestController {

    private final BookService bookService;

    private final SaveBookUseCase saveBookUseCase;

    private final FindBookDetailsUseCase findBookDetailsUseCase;


    public BookRestController(BookService bookService, SaveBookUseCase saveBookUseCase,
                              FindBookDetailsUseCase findBookDetailsUseCase) {
        this.bookService = bookService;
        this.saveBookUseCase = saveBookUseCase;
        this.findBookDetailsUseCase = findBookDetailsUseCase;
    }

    @PostMapping
    @Operation(summary = "Save a new book")
    public ResponseEntity<Integer> saveBook(
            @Valid @RequestBody BookRequest bookRequest,
            Authentication connectedUser
    ) {
        Book book = saveBookUseCase.save(BookMapper.toSaveBookCommand(bookRequest, connectedUser));
        return ResponseEntity.ok(book.getId().getValue());
    }

    @GetMapping("/{book-id}")
    @Operation(summary = "Find a book by book-id")
    public ResponseEntity<BookResponse> findById(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        // TODO: Use this endpoint to return only pure book data
        // TODO: Separate endpoint for book and image. E.g. /books/{bookId}/cover
        // TODO: Separate endpoint for details. E.g. /books/{bookId}/details - Including all feedbacks, rates, etc.
        // TODO: Separate endpoint for book preview. E.g. /books/{bookId}/preview - isInWaitingList, averageRate, etc.
        BookDetails bookDetails = findBookDetailsUseCase.findBookDetails(bookId, connectedUser.getName());

        BookResponse bookResponse = new BookResponse(bookDetails.id(), bookDetails.title(), bookDetails.authorName(),
                bookDetails.isbn(), bookDetails.synopsis(), bookDetails.ownerFullName(), bookDetails.coverImage(),
                bookDetails.averageRating(), bookDetails.archived(), bookDetails.shareable(), bookDetails.isInWaitingList());

        return ResponseEntity.ok(bookResponse);
    }

    @GetMapping
    @Operation(summary = "Find all displayable books")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            @RequestParam(name = "searchTerm", defaultValue = "", required = false) String searchTerm,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookService.findAllBooks(page, size, searchTerm, connectedUser));
    }

    @GetMapping("/owner")
    @Operation(summary = "Find all owner's books")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(bookService.findAllBooksByOwner(page, size, connectedUser));
    }

    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(bookService.findAllBorrowedBooks(page, size, connectedUser));
    }

    @GetMapping("/returned")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ){
        return ResponseEntity.ok(bookService.findAllReturnedBooks(page, size, connectedUser));
    }

    @PatchMapping("/shareable/{book-id}")
    public ResponseEntity<Integer> updateShareableStatus(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookService.updateShareableState(bookId, connectedUser));
    }

    @PatchMapping("/archived/{book-id}")
    public ResponseEntity<Integer> updateArchivedStatus(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookService.updateArchivedStatus(bookId, connectedUser));
    }

    @PostMapping("/borrow/{book-id}")
    public ResponseEntity<Integer> borrowBook(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookService.borrowBook(bookId, connectedUser));
    }

    @PatchMapping("/borrow/return/{book-id}")
    public ResponseEntity<Integer> returnBorrowedBook(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookService.returnBorrowedBook(bookId, connectedUser));
    }

    @PatchMapping("/borrow/return/approve/{book-id}")
    public ResponseEntity<Integer> approveReturnBorrowedBook(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookService.approveReturnBorrowedBook(bookId, connectedUser));
    }

    @PostMapping(value = "/cover/{book-id}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadBookCoverPicture(
            @PathVariable("book-id") Integer bookId,
            @Parameter()
            @RequestPart("file") MultipartFile file,
            Authentication connectedUser
            ) {
        bookService.uploadBookCoverPicture(file, connectedUser, bookId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/waitingList")
    public ResponseEntity<PageResponse<BookResponse>> getWaitingList(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser
    ) {
        return ResponseEntity.ok(bookService.getWaitingList(page, size, connectedUser));
    }

    @PostMapping("/addToWaitingList/{book-id}")
    public ResponseEntity<Integer> addToWaitingList(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        bookService.addBookToWaitingList( bookId, connectedUser );
        return ResponseEntity.ok().build();
    }

    @PostMapping("/removeFromWaitingList/{book-id}")
    public ResponseEntity<Integer> removeFromWaitingList(
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    ) {
        bookService.removeBookFromWaitingList( bookId, connectedUser );
        return ResponseEntity.ok().build();
    }

}
