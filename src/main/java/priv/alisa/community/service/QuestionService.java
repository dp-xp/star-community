package priv.alisa.community.service;

import org.springframework.stereotype.Service;
import priv.alisa.community.dto.QuestionDTO;

import java.util.List;

@Service
public interface QuestionService {
    List<QuestionDTO> queryAllQuestion();
}
