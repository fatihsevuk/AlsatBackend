package com.alsat.controller;

import java.security.Principal;
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

import com.alsat.domain.Answer;
import com.alsat.domain.Question;
import com.alsat.domain.User;
import com.alsat.service.UserService;
import com.alsat.service.impl.QuestionAnswerServiceImpl;

@RestController
@RequestMapping("/answer")
public class AnswerController {

	@Autowired
	private QuestionAnswerServiceImpl questionAnswerService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/add" , method=RequestMethod.POST)
	public Answer addAnswer(@RequestBody Answer answer , Principal principal){
		
		User fromUser=userService.findByUsername(principal.getName());
		Question question=questionAnswerService.finOneQuestion(answer.getQuestionId());
		question.setAnswered(true);
		questionAnswerService.addQuestion(question);
		
		answer.setToUserId(question.getFromUser().getId());
		answer.setToUsername(question.getFromUser().getUsername());
		answer.setFromUser(fromUser);
		answer.setDate(new Date());
		
		return questionAnswerService.addAnswer(answer);
	}
	
	@RequestMapping(value="/remove",method=RequestMethod.POST)
	public ResponseEntity removeAnswer(@RequestBody String id){
		
		questionAnswerService.deleteAnswer(Long.parseLong(id));
		
		return new ResponseEntity("Cevap silindi.", HttpStatus.OK);
	}
	

	
	@RequestMapping("/{id}")
	public Answer getAnswer(@PathVariable("id") Long id){
		Answer answer=questionAnswerService.findOneAnswer(id);
		return answer;
	}

	@RequestMapping(value="/answers" , method=RequestMethod.POST)
	public List<Answer> getAnswers(@RequestBody String id){
		
		
		return questionAnswerService.getAnswersByQuestionId(Long.parseLong(id));
	}
	
	@RequestMapping(value="/removeByQuuestion",method=RequestMethod.POST)
	public ResponseEntity removeAnswerByQuestion(@RequestBody String id){
		
		questionAnswerService.deleteAnswerByQuestionId(Long.parseLong(id));
		
		return new ResponseEntity("Cevaplar silindi.", HttpStatus.OK);
	}
	
	
}
