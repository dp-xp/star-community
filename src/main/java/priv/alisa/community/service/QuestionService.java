package priv.alisa.community.service;

import org.springframework.stereotype.Service;
import priv.alisa.community.dto.PageDTO;
import priv.alisa.community.dto.QuestionDTO;
import priv.alisa.community.model.Question;

@Service
public interface QuestionService {
    PageDTO queryAllQuestion(Integer pageNum, Integer size);

    PageDTO queryAllQuestion(Integer userId, Integer page, Integer size);

    QuestionDTO queryById(Integer id);

    void createOrUpdate(Question question);

    void addView(Integer id);
}
