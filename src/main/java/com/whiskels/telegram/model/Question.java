package com.whiskels.telegram.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "java_quiz")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Question extends AbstractBaseEntity {
    @Column(name = "question", nullable = false)
    @NotBlank
    private String question;

    @Column(name = "answer_correct", nullable = false)
    @NotBlank
    private String correctAnswer;

    @Column(name = "option2", nullable = false)
    @NotBlank
    private String optionOne;

    @Column(name = "option1", nullable = false)
    @NotBlank
    private String optionTwo;

    @Column(name = "option3", nullable = false)
    @NotBlank
    private String optionThree;

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", optionOne='" + optionOne + '\'' +
                ", optionTwo='" + optionTwo + '\'' +
                ", optionThree='" + optionThree + '\'' +
                '}';
    }
}
