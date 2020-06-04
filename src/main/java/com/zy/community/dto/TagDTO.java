package com.zy.community.dto;

import lombok.Data;

import java.util.List;

/**
 * 标签库
 */
@Data
public class TagDTO {
    private String categoryName;//大类
    private List<String> tags;//小类
}
