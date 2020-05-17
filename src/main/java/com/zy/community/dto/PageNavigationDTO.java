package com.zy.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageNavigationDTO {
    private List<QuestionDTO> questionDTOS;
    private boolean showFirst;
    private boolean showEnd;
    private boolean previous;
    private boolean next;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage=0;

    public void setPageNavigation(Integer count,Integer page,Integer size){
        if (page<0) page =1;
        this.page=page;
        //计算总页数
        if(count%size == 0){
            totalPage = count / size;
        }else {
            totalPage = count / size + 1;
        }
        if (page>totalPage) page=totalPage;

        pages.add(page);
        //计算pages
        for(int i=1;i<=3;i++){
            if(page-i>0){
                pages.add(0,page-i);
            }
            if (page+i<=totalPage){
                pages.add(page+i);
            }
        }

        //是否展示首页和尾页按钮
        if (pages.contains(1)){
            showFirst=false;
        }else{
            showFirst=true;
        }

        if(pages.contains(totalPage)){
            showEnd=false;
        }else {
            showEnd=true;
        }

        //是否展示上一页和下一页
        if (page == 1){
            previous = false;
        }else{
            previous = true;
        }
        if (page == totalPage){
            next = false;
        }else{
            next = true;
        }

    }
}
