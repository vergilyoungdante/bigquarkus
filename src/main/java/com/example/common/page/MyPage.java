package com.example.common.page;

import java.util.List;

/**
 * @BelongsProject: bigquarkus
 * @BelongsPackage: com.example.common.page
 * @Author: vergil young
 * @CreateTime: 2022-07-20  18:32
 * @Description: 自定义分页，quarkus自己的分页没有total
 */
public class MyPage<T> {
    private int page;
    private int size;

    private int total;
    private List<T> list;

    public MyPage(int page, int size, int total, List<T> list) {
        this.page = page;
        this.size = size;
        this.total = total;
        this.list = list;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
