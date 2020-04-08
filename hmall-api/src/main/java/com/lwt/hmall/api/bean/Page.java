package com.lwt.hmall.api.bean;

import java.util.List;

/**
 * @author lwt
 * @date 2019/11/24 18:32
 */
public class Page<T> {

    /**
     * 总行数
     */
    private int total;
    /**
     * 页码
     */
    private int pageNum;
    /**
     * 每页多少行
     */
    private int pageSize;
    /**
     * 当前页行数
     */
    private int size;
    /**
     * 开始行
     */
    private int startRow;
    /**
     * 结束行
     */
    private int endRow;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 上一页页码
     */
    private int prePage;
    /**
     * 下一页页码
     */
    private int nextPage;
    /**
     * 当前页是否是第一页
     */
    private boolean isFirstPage;
    /**
     * 当前页是否是最后一页
     */
    private boolean isLastPage;
    /**
     * 是否存在上一页
     */
    private boolean hasPreviousPage;
    /**
     * 是否存在下一页
     */
    private boolean hasNextPage;
    /**
     * 导航页码数量
     */
    private int navigatePages;
    /**
     * 导航页码
     */
    private int[] navigatepageNums;
    /**
     * 导航第一页页码
     */
    private int navigateFirstPage;
    /**
     * 导航最后一页页码
     */
    private int navigateLastPage;
    /**
     * 当前页数据
     */
    private List<T> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int[] getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(int[] navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public int getNavigateFirstPage() {
        return navigateFirstPage;
    }

    public void setNavigateFirstPage(int navigateFirstPage) {
        this.navigateFirstPage = navigateFirstPage;
    }

    public int getNavigateLastPage() {
        return navigateLastPage;
    }

    public void setNavigateLastPage(int navigateLastPage) {
        this.navigateLastPage = navigateLastPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
