package com.guen.book.feedback;

import com.guen.book.common.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("feedbacks")
@RequiredArgsConstructor
@Tag(name = "Feedback", description = "Gestion des avis utilisateurs sur les livres")
public class FeedbackController
{
    private final FeedbackService feedbackService;

    @PostMapping
    @Operation(summary = "Publier un avis sur un livre")
    public ResponseEntity<Integer> saveFeedback(
            @Valid @RequestBody FeedbackRequest request,
            Authentication connectedUser
    )
    {
        return ResponseEntity.ok(feedbackService.saveFeedback(request, connectedUser));
    }

    @GetMapping("/book/{book-id}")
    @Operation(summary = "Récupérer tous les avis pour un livre")
    public ResponseEntity<PageResponse<FeedbackResponse>> findAllFeedbackByBook(
            @Parameter(description = "ID du livre")
            @PathVariable("book-id") Integer bookId,
            @RequestParam(name = "page", defaultValue = "0", required = false) int page,
            @RequestParam(name = "size", defaultValue = "0", required = false) int size,
            Authentication connectedUser
    )
    {
        return ResponseEntity.ok(feedbackService.findAllFeedbackByBook(bookId, page, size, connectedUser));
    }
}
