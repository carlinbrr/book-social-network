package com.bsn.booknetworkapi.feedback;

import com.bsn.booknetworkapi.book.Book;
import com.bsn.booknetworkapi.user.User;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class FeedbackMapper {

    public Feedback toFeedback(FeedbackRequest feedbackRequest, Book book, User user) {
        return Feedback.builder()
                .note(feedbackRequest.note())
                .comment(feedbackRequest.comment())
                .book(book)
                .user(user)
                .build();
    }

    public FeedbackResponse toFeedbackResponse(Feedback feedback, String id) {
        return FeedbackResponse.builder()
                .note(feedback.getNote())
                .comment(feedback.getComment())
                .ownFeedback(Objects.equals(feedback.getUser().getKeycloakId(), id))
                .build();
    }
}
