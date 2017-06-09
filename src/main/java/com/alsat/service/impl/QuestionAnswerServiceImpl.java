package com.alsat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alsat.domain.Answer;
import com.alsat.domain.Question;
import com.alsat.domain.User;
import com.alsat.repository.AnswerRepository;
import com.alsat.repository.QuestionRepository;
import com.alsat.service.QuestionAnswerService;

@Service
public class QuestionAnswerServiceImpl implements QuestionAnswerService {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Override
	public void addQuestion(Question question){

		questionRepository.save(question);

	}

	@Override
	public Answer addAnswer(Answer answer) {
		return answerRepository.save(answer);

	}

	@Override
	public List<Answer> getAnswersByQuestionId(Long questionId) {
				
		return answerRepository.getAnswersByQuestionId(questionId);
	}

	@Override
	public void deleteQuestion(Long id) {
		
		questionRepository.delete(id);
		
	}

	@Override
	public void deleteAnswer(Long id) {
		
		answerRepository.delete(id);

	}

	@Override
	public List<Question> getQuestionByToUser(Long id) {
		
		return questionRepository.getQuestionByToUserId(id);
	}

	@Override
	public List<Question> getQuestionByFromUser(User user) {
		
		return questionRepository.getQuestionByFromUser(user);
	}

	@Override
	public Question finOneQuestion(Long id) {
		
		return questionRepository.findOne(id);
	}

	@Override
	public Answer findOneAnswer(Long id) {
		
		return answerRepository.findOne(id);
	}

	@Override
	public void deleteAnswerByQuestionId(Long id) {
		List<Answer> answers=answerRepository.getAnswersByQuestionId(id);
		for (Answer answer : answers) {
			deleteAnswer(answer.getId());
		}
		
	}

}
