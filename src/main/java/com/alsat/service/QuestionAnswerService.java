package com.alsat.service;

import java.util.List;

import com.alsat.domain.Answer;
import com.alsat.domain.Question;
import com.alsat.domain.User;

public interface QuestionAnswerService {
	
	void addQuestion(Question question);
	Answer addAnswer(Answer answer);
	Question finOneQuestion(Long id);
	Answer findOneAnswer(Long id);
	List<Answer> getAnswersByQuestionId(Long questionId);
	void deleteQuestion(Long id);
	void deleteAnswer(Long id);
	void deleteAnswerByQuestionId(Long id);
	List<Question> getQuestionByToUser(Long id);
	List<Question> getQuestionByFromUser(User user);
	
	

}
