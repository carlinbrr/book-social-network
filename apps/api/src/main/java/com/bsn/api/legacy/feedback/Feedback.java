package com.bsn.api.legacy.feedback;

import com.bsn.api.adapters.output.presitence.entity.Book;
import com.bsn.api.legacy.common.BaseEntity;
import com.bsn.api.adapters.output.presitence.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Feedback extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "feedback_seq")
    @SequenceGenerator(
            name = "feedback_seq",
            sequenceName = "feedback_seq",
            allocationSize = 25
    )
    private Integer id;

    private Double note;

    @Column(length = 1025)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
