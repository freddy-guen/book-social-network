package com.guen.book.book;

import com.guen.book.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name = "Book", description = "Opérations sur les livres (ajout, emprunt, archivage, etc)")
public class BookController
{
    private final BookService bookService;

    @PostMapping
    @Operation(summary = "Créer un nouveau livre")
    public ResponseEntity<Integer> saveBook(
            @Valid @RequestBody BookRequest request,
            Authentication connectedUser)
    {
        return ResponseEntity.ok(bookService.save(request, connectedUser));
    }

    @GetMapping("{book-id}")
    @Operation(summary = "Récupérer un livre par son ID")
    public ResponseEntity<BookResponse> findBookById(
            @Parameter(description = "ID du livre")
            @PathVariable("book-id") Integer bookId
    )
    {
        return ResponseEntity.ok(bookService.findById(bookId));
    }

    @GetMapping
    @Operation(summary = "Lister tous les livres disponibles")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser)
    {
        return ResponseEntity.ok(bookService.findAllBooks(page, size, connectedUser));
    }

    @GetMapping("/owner")
    @Operation(summary = "Lister tous les livres disponibles pour l'utilisateur connecté")
    public ResponseEntity<PageResponse<BookResponse>> findAllBooksByOwner(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser)
    {
        return ResponseEntity.ok(bookService.findAllBooksByOwner(page, size, connectedUser));
    }

    @GetMapping("/borrowed")
    @Operation(summary = "Récupérer tous les livres empruntés par l'utilisateur connecté")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser)
    {
        return ResponseEntity.ok(bookService.findAllBorrowedBooks(page, size, connectedUser));
    }

    @GetMapping("/returned")
    @Operation(summary = "Récupérer tous les livres rendus par l'utilisateur connecté")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooks(
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "10", required = false) int size,
            Authentication connectedUser)
    {
        return ResponseEntity.ok(bookService.findAllReturnedBooks(page, size, connectedUser));
    }

    @PatchMapping("/shareable/{book-id}")
    @Operation(summary = "Mettre à jour le statut partageable d'un livre")
    public ResponseEntity<Integer> updateShareableStatus(
            @Parameter(description = "ID du livre")
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    )
    {
        return ResponseEntity.ok(bookService.updateShareableStatus(bookId, connectedUser));
    }

    @PatchMapping("/archived/{book-id}")
    @Operation(summary = "Archiver un livre")
    public ResponseEntity<Integer> updateArchivedStatus(
            @Parameter(description = "ID du livre")
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    )
    {
        return ResponseEntity.ok(bookService.updateArchivedStatus(bookId, connectedUser));
    }

    @PostMapping("/borrow/{book-id}")
    @Operation(summary = "Emprunter un livre")
    public ResponseEntity<Integer> borrowBook(
            @Parameter(description = "ID du livre")
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    )
    {
        return ResponseEntity.ok(bookService.borrowBook(bookId, connectedUser));
    }

    @PatchMapping("/borrow/return/{book-id}")
    @Operation(summary = "Rendre un livre")
    public ResponseEntity<Integer> returnBorrowedBook(
            @Parameter(description = "ID du livre")
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    )
    {
        return ResponseEntity.ok(bookService.returnBorrowedBook(bookId, connectedUser));
    }

    @PatchMapping("/borrow/return/approve/{book-id}")
    @Operation(summary = "Approuver un livre rendu")
    public ResponseEntity<Integer> approveReturnBorrowedBook(
            @Parameter(description = "ID du livre")
            @PathVariable("book-id") Integer bookId,
            Authentication connectedUser
    )
    {
        return ResponseEntity.ok(bookService.approveReturnBorrowedBook(bookId, connectedUser));
    }

    @PostMapping(value = "/cover/{book-id}", consumes = "multipart/form-data")
    @Operation(summary = "Ajouter la couverture d'un livre")
    public ResponseEntity<?> uploadBookCoverPicture(
            @Parameter(description = "ID du livre")
            @PathVariable("book-id") Integer bookId,
            @Parameter()
            @RequestPart("file") MultipartFile file,
            Authentication connectedUser
    )
    {
        bookService.uploadBookCoverPicture(file, connectedUser, bookId);
        return ResponseEntity.accepted().build();
    }
}
