package priv.alisa.community.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.alisa.community.dto.PageDTO;
import priv.alisa.community.dto.QuestionDTO;
import priv.alisa.community.exception.CustomizeErrorCode;
import priv.alisa.community.exception.CustomizeException;
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

    @Override
    public PageDTO queryAllQuestion(Integer userId, Integer page, Integer size) {
        //根据页码和页面大小计算偏移量
        Integer totalCount = questionMapper.countByUserId(userId);
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPagination(totalCount,page,size);
        if (page < 1){
            page = 1;
        }
        if (page > pageDTO.getTotalPage()){
            page = pageDTO.getTotalPage();
        }
        Integer offset = page>0?size * (page -1):0;
        List<Question> questions = questionMapper.queryAllQuestionByUserId(userId,offset,size);
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

    @Override
    public QuestionDTO queryById(Integer id) {
        Question question = questionMapper.queryById(id);
        if (question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    @Override
    public void createOrUpdate(Question question) {
        if (question.getId() == null){
            //创建
            question.setGmtCreate(System.currentTimeMillis());
            questionMapper.create(question);
        }else {
            //更新
            question.setGmtModified(System.currentTimeMillis());
            int update = questionMapper.update(question);
            if (update != 1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    @Override
    public void addView(Integer id) {
        Question question = questionMapper.queryById(id);
        questionMapper.update(question);
    }
}
