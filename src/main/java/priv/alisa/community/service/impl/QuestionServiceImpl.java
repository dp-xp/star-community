package priv.alisa.community.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.alisa.community.dto.PageDTO;
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
    public PageDTO queryAllQuestion(Integer page, Integer size) {
        //根据页码和页面大小计算偏移量
        Integer totalCount = questionMapper.count();
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPagination(totalCount,page,size);
        if (page < 1){
            page = 1;
        }
        if (page > pageDTO.getTotalPage()){
            page = pageDTO.getTotalPage();
        }
        Integer offset = size * (page -1);
        List<Question> questions = questionMapper.queryAllQuestion(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOList);
        return pageDTO;
    }
}
