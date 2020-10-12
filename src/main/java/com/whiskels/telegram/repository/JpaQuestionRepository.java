package com.whiskels.telegram.repository;

import com.whiskels.telegram.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaQuestionRepository extends JpaRepository<Question, Integer> {
    @Query(nativeQuery = true, value = "SELECT *  FROM java_quiz ORDER BY random() LIMIT 1")
    Question getRandomQuestion();
}
