package com.lwt.hmall.api.util;

import com.lwt.hmall.api.bean.Page;

import java.util.List;

/**
 * @Author lwt
 * @Date 2020/3/25 20:08
 * @Description
 */
public class PageUtils {

    public static<T> Page<T> page(List<T> list, int total, int pageNum, int pageSize) {
        return page(list,total,pageNum,pageSize,5);
    }

    public static<T> Page<T> page(List<T> list, int total, int pageNum, int pageSize, int navigatePages) {
        Page<T> page = new Page<>();

        page.setList(list);
        page.setTotal(total);
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setNavigatePages(navigatePages);

        page.setSize(list.size());
        page.setStartRow((page.getPageNum()-1)*page.getPageSize()+1);
        page.setEndRow(page.getStartRow()+page.getSize()-1);
        if (page.getTotal()%page.getPageSize()==0){
            page.setPages(page.getTotal()/page.getPageSize());
        }else {
            page.setPages(page.getTotal()/page.getPageSize()+1);
        }
        if (page.getPageNum()>1){
            page.setPrePage(page.getPageNum()-1);
        }else {
            page.setPrePage(0);
        }
        if (page.getPageNum()<page.getPages()){
            page.setNextPage(page.getPageNum()+1);
        }else {
            page.setNextPage(page.getPages());
        }
        if (page.getPageNum()==1){
            page.setFirstPage(true);
        }else {
            page.setFirstPage(false);
        }
        if (page.getPageNum()>=page.getPages()){
            page.setLastPage(true);
        }else {
            page.setLastPage(false);
        }
        page.setHasPreviousPage(!page.isFirstPage());
        page.setHasNextPage(!page.isLastPage());
        if (page.getPages()<page.getNavigatePages()) {
            page.setNavigatePages(page.getPages());
        }
        page.setNavigatepageNums(new int[page.getNavigatePages()]);
        int navigatepageNumsStart=page.getPageNum()-page.getNavigatePages()/2;
        while (true) {
            if (navigatepageNumsStart < 1) {
                navigatepageNumsStart++;
            }
            if (navigatepageNumsStart + page.getNavigatePages() - 1 > page.getPages()) {
                navigatepageNumsStart--;
            }
            if (navigatepageNumsStart >= 1 && (navigatepageNumsStart + page.getNavigatePages() - 1) <= page.getPages()) {
                break;
            }
        }
        for (int i = 0; i < page.getNavigatepageNums().length; i++) {
            page.getNavigatepageNums()[i]=navigatepageNumsStart+i;
        }
        page.setNavigateFirstPage(1);
        page.setNavigateLastPage(page.getPages());
        return page;
    }
}
