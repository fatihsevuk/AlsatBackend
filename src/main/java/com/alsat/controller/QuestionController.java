package com.alsat.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alsat.domain.Question;
import com.alsat.domain.User;
import com.alsat.service.UserService;
import com.alsat.service.impl.QuestionAnswerServiceImpl;

@RestController
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	private QuestionAnswerServiceImpl questionAnswerService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ResponseEntity addQuestion(@RequestBody Question question, Principal principal) {

		User fromUser = userService.findByUsername(principal.getName());
		User toUser=userService.findOne(question.getToUserId());

		question.setFromUser(fromUser);
		question.setToUsername(toUser.getUsername());
		question.setDate(new Date());

		try {

			questionAnswerService.addQuestion(question);

			return new ResponseEntity("Soru Soruldu", HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity("Soru Sorma Hatası", HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public ResponseEntity removeQuestion(@RequestBody String id , Principal principal) {
		
		Question question=questionAnswerService.finOneQuestion(Long.parseLong(id));
		
		questionAnswerService.deleteQuestion(Long.parseLong(id));

		return new ResponseEntity("Soru silindi.", HttpStatus.OK);
		
	}

	@RequestMapping(value = "/fromQuestion", method = RequestMethod.GET)
	public List<Question> findFromQuestion(Principal principal) {

		User fromUser = userService.findByUsername(principal.getName());

		return questionAnswerService.getQuestionByFromUser(fromUser);

	}

	@RequestMapping(value = "/toQuestion", method = RequestMethod.GET)
	public List<Question> findToQuestion(Principal principal) {

		User toUser = userService.findByUsername(principal.getName());
		
		List<Question> questionNotAnswered=new ArrayList<Question>();
		List<Question> questions=questionAnswerService.getQuestionByToUser(toUser.getId());
		
		for (Question question : questions) {
			if(question.isAnswered()==false){
				questionNotAnswered.add(question);
			}
		}

		return questionNotAnswered;

	}

	@RequestMapping("/question")
	public Question getQuestion(@RequestBody Long id) {
		Question question = questionAnswerService.finOneQuestion(id);
		return question;
	}

}
