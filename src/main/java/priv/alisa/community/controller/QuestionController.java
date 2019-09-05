package priv.alisa.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import priv.alisa.community.dto.QuestionDTO;
import priv.alisa.community.mapper.QuestionMapper;
import priv.alisa.community.service.QuestionService;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model){
        QuestionDTO questionDTO = questionService.queryById(id);
        //累加阅读数
        questionService.addView(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
