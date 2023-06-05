package com.lwc.sportcommunity.pagehelper;

import com.github.pagehelper.PageInfo;

/**
 * Create by LWC on 2023/4/1 23:29
 */
public class PageUtils {

    public static PageResult getPageResult(PageRequest pageRequest,
                                           PageInfo<?> pageInfo){
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotalSize(pageInfo.getTotal());
        pageResult.setTotalPages(pageInfo.getPages());
        pageResult.setContent(pageInfo.getList());
        return pageResult;
    }
}
