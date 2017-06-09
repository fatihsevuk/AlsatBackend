package com.alsat.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alsat.domain.Answer;
import com.alsat.domain.Question;

@Repository
public interface AnswerRepository extends CrudRepository<Answer,Long>{
	
	List<Answer> getAnswersByQuestionId(Long questionId);
	

}
