package com.yu.mybatis.simple.plugin;

import org.apache.ibatis.session.RowBounds;

/**
 * Created By Yu On 2018/8/13
 * Description：
 **/
public class PageRowBounds extends RowBounds {
    private long total;

    public PageRowBounds() {
        super();
    }

    public PageRowBounds(int offset, int limit) {
        super(offset, limit);
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
