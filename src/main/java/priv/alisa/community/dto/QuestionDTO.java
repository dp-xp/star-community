package priv.alisa.community.dto;

import lombok.Data;
import priv.alisa.community.model.Question;
import priv.alisa.community.model.User;

@Data
public class QuestionDTO extends Question {
    private User user;
}
