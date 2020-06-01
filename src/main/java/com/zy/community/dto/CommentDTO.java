package com.zy.community.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Integer parentID;
    private String content;
    private Integer type;

}
