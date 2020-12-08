package ru.otus.spring.services;

import org.springframework.stereotype.Service;
import ru.otus.spring.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class QuestionListRandomizerService implements ModifierService<List<Question>> {
    @Override
    public List<Question> modify(final List<Question> collection) {
        final List<Question> questions = new ArrayList<>(collection);
        Collections.shuffle(questions);
        return questions;
    }
}
