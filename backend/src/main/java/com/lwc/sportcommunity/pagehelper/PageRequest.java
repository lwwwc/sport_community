package com.lwc.sportcommunity.pagehelper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Create by LWC on 2023/4/1 23:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageRequest {
    private int pageNum;
    private int pageSize;
    //1 通过时间降序 2 通过浏览量降序 0 不排序
    private int method;
    private String telephone;
}
