package com.bsn.booknetworkapi.feedback;

import com.bsn.booknetworkapi.book.Book;
import com.bsn.booknetworkapi.book.BookRepository;
import com.bsn.booknetworkapi.common.PageResponse;
import com.bsn.booknetworkapi.exception.OperationNotPermittedException;
import com.bsn.booknetworkapi.user.User;
import com.bsn.booknetworkapi.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FeedBackService {

    private final BookRepository bookRepository;

    private final FeedbackMapper feedbackMapper;

    private final FeedbackRepository feedbackRepository;

    private final UserRepository userRepository;

    public FeedBackService(BookRepository bookRepository,
                           FeedbackMapper feedbackMapper,
                           FeedbackRepository feedbackRepository,
                           UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.feedbackMapper = feedbackMapper;
        this.feedbackRepository = feedbackRepository;
        this.userRepository = userRepository;
    }

    public Integer save(FeedbackRequest request, Authentication connectedUser) {
        User user = userRepository.findByKeyCloakId(connectedUser.getName()).orElseThrow(
                () -> new EntityNotFoundException("User with id: " + connectedUser.getName() + "not found")
        );

        Book book = bookRepository.findById(request.bookId()).orElseThrow(
                () -> new EntityNotFoundException("Book with id: " + request.bookId() + "not found")
        );

        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotPermittedException("You cannot give a feedback for an archived or not shareable book");
        }

        if (Objects.equals(connectedUser.getName(), book.getOwner().getKeyCloakId())) {
            throw new OperationNotPermittedException("You cannot give a feedback your own book");
        }

        Feedback feedback = feedbackMapper.toFeedback(request, book, user);
        return feedbackRepository.save(feedback).getId();
    }

    public PageResponse<FeedbackResponse> findAllFeedbacksByBook(Integer bookId, int page, int size, Authentication connectedUser) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Feedback> feedbacks = feedbackRepository.findAllByBookId(bookId, pageable);
        List<FeedbackResponse> feedbackResponseList = feedbacks.stream()
                .map( feedback -> feedbackMapper.toFeedbackResponse(feedback, connectedUser.getName()))
                .toList();
        return new PageResponse<>(
                feedbackResponseList,
                feedbacks.getNumber(),
                feedbacks.getSize(),
                feedbacks.getTotalElements(),
                feedbacks.getTotalPages(),
                feedbacks.isFirst(),
                feedbacks.isLast()
        );
    }
}
