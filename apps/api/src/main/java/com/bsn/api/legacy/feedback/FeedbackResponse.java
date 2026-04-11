package com.bsn.api.legacy.feedback;

import com.bsn.api.legacy.user.UserResponse;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {

    private Double note;

    private String comment;

    private boolean ownFeedback;

    private UserResponse user;

    private String createdDate;
}
