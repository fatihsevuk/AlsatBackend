package com.alsat.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.alsat.domain.Question;
import com.alsat.domain.User;

@Repository
public interface QuestionRepository extends CrudRepository<Question,Long>{
	
	List<Question> getQuestionByToUserId(Long id);
	List<Question> getQuestionByFromUser(User user);

}
