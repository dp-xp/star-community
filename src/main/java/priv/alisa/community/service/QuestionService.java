package priv.alisa.community.service;

import org.springframework.stereotype.Service;
import priv.alisa.community.dto.PageDTO;

@Service
public interface QuestionService {
    PageDTO queryAllQuestion(Integer pageNum, Integer size);

    PageDTO queryAllQuestion(Integer userId, Integer page, Integer size);
}
