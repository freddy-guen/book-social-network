package com.guen.book.feedback;

import com.guen.book.book.Book;
import com.guen.book.book.BookRepository;
import com.guen.book.exception.OperationNotPermittedException;
import com.guen.book.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FeedbackService
{
    private final BookRepository bookRepository;
    private final FeedbackMapper feedbackMapper;
    private final FeedbackRepository feedbackRepository;

    public Integer saveFeedback(FeedbackRequest request, Authentication connectedUser)
    {
        Book book = bookRepository.findById(request.bookId())
                .orElseThrow(() -> new EntityNotFoundException("Aucun livre trouvé avec cet ID:: " + request.bookId()));
        if (book.isArchived() || !book.isShareable())
        {
            throw new OperationNotPermittedException("Vous ne pouvez pas laisser un avis pour un livre archivé ou non partageable");
        }

        User user = ((User) connectedUser.getPrincipal());
        if (Objects.equals(book.getOwner().getId(), user.getId()))
        {
            throw new OperationNotPermittedException("Vous ne pouvez pas laisser un avis pour un livre qui vous appartient");
        }

        Feedback feedback = feedbackMapper.toFeedback(request);

        return feedbackRepository.save(feedback).getId();
    }
}
