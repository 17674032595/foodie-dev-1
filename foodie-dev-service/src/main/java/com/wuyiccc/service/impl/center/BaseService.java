package com.wuyiccc.service.impl.center;

import com.github.pagehelper.PageInfo;
import com.wuyiccc.utils.PagedGridResult;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2020/1/18 15:27
 * 岂曰无衣，与子同袍~
 */
public class BaseService {

    //提供通用化的分页方法
    public PagedGridResult setterPagedGrid(List<?> list, Integer page) {

        PageInfo<?> pageList = new PageInfo<>(list);  // 将分页之后的数据交给com.github.pagehelper.PageInfo对象，用来获取当前页的信息

        // 为了和前端的数据名称对应，我们这里需要更改数据对应的key，进行如下变化
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);   // 将当前页index 交给grid对象
        grid.setRows(list);   // 将数据信息交给grid 对象
        grid.setTotal(pageList.getPages());  // 通过PageInfo 来获取页面的总页数，交给grid
        grid.setRecords(pageList.getTotal());  // 通过PageInfo 来获取当前页面的总记录条数，交给grid
        return grid;

    }
}
