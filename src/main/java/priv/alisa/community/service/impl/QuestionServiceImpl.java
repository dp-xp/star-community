package priv.alisa.community.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.alisa.community.dto.QuestionDTO;
import priv.alisa.community.mapper.QuestionMapper;
import priv.alisa.community.mapper.UserMapper;
import priv.alisa.community.model.Question;
import priv.alisa.community.model.User;
import priv.alisa.community.service.QuestionService;

import java.util.ArrayList;
import java.util.List;
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<QuestionDTO> queryAllQuestion() {
        List<Question> questions = questionMapper.queryAllQuestion();
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
