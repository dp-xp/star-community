package priv.alisa.community.model;

import lombok.Data;

@Data
public class Comment {
    private Integer id;
    private Long parentId;
    private String type;
    private Integer commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
}
