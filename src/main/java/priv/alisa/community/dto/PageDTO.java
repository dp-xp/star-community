package priv.alisa.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageDTO {
    private List<QuestionDTO> questions;
    //是否有前一页按钮
    private Boolean showPrevious=true;
    //是否有首页按钮
    private Boolean showFirstPage=true;
    //是否有后一页按钮
    private Boolean showNext=true;
    //是否有最后一页
    private Boolean showEndPage=true;
    //当前页面
    private Integer page;
    //总page数
    private Integer totalPage;
    //当前的page集合
    private List<Integer> pages = new ArrayList<>();

    public void setPagination(Integer totalCount, Integer page, Integer size) {
        //计算总页数：（两种情况）
        totalPage = totalCount % size ==0 ? totalCount/size : totalCount/size+1;
        if (page < 1){
            page = 1;
        }
        if (page > totalPage){
            page = totalPage;
        }
        this.page = page;
        //将当前页面加入pages
        pages.add(page);
        for (int i = 1; i <= 3; i++){
            //左侧数据不超过3
            if (page - i >0){
                pages.add(0,page-i);
            }
            //右侧数据不超过3
            if (page + i <= totalPage){
                pages.add(page+i);
            }
        }

        //第一页不显示上一页
        if (page == 1){
            showPrevious = false;
        }
        //最后一页不显示下一页
        if (page==totalPage){
            showNext = false;
        }
        //是否展示首页
        showFirstPage = pages.contains(1) ? false : true;
        //是否展示尾页
        showEndPage = pages.contains(totalCount) ? false : true;
    }
}
