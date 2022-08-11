package com.ll.exam.sbb;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;


@SpringBootTest
public class AnswerRepositoryTests {
    @Autowired
    private AnswerRepository answerRepository;
    private int lastSampleDataId;

    @Autowired
    private QuestionRepository questionRepository;

    @BeforeEach
    void beforeEach() {
        clearData();
        createSampleData();
    }

    private void clearData() {
        questionRepository.disableForeignKeyChecks();
        answerRepository.truncate();
        questionRepository.enableForeignKeyChecks();
    }

    private void createSampleData() {
    }

    @Test
    void 저장() {
        Question q = questionRepository.findById(2).get();

        Answer a = new Answer();
        a.setContent("네 자동으로 생성됩니다.");
        a.setQuestion(q);  // 어떤 질문의 답변인지 알기위해서 Question 객체가 필요하다.
        a.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(a);
    }
}
