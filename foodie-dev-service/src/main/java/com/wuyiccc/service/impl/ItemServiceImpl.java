package com.wuyiccc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuyiccc.enums.ItemCommentLevel;
import com.wuyiccc.mapper.*;
import com.wuyiccc.pojo.*;
import com.wuyiccc.pojo.vo.ItemCommentVo;
import com.wuyiccc.pojo.vo.ItemCommentsLevelCountsVO;
import com.wuyiccc.service.ItemService;
import com.wuyiccc.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @author wuyiccc
 * @date 2020/1/6 20:15
 * 岂曰无衣，与子同袍~
 */
@Service
public class ItemServiceImpl implements ItemService {


    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsParamMapper itemsParamMapper;

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Autowired
    private ItemsMapperCustom itemsMapperCustom;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items queryItemById(String itemId) {

        Items item = itemsMapper.selectByPrimaryKey(itemId);
        return item;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {

        Example itemsImgExp = new Example(ItemsImg.class);

        Example.Criteria criteria = itemsImgExp.createCriteria();
        criteria.andEqualTo("itemId",itemId);

        List<ItemsImg> results = itemsImgMapper.selectByExample(itemsImgExp);

        return results;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {

        Example itemsSpecExp = new Example(ItemsSpec.class);
        Example.Criteria criteria = itemsSpecExp.createCriteria();
        criteria.andEqualTo("itemId",itemId);

        List<ItemsSpec> results = itemsSpecMapper.selectByExample(itemsSpecExp);
        return results;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemParam(String itemId) {

        Example itemParamExp = new Example(ItemsParam.class);
        Example.Criteria criteria = itemParamExp.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        ItemsParam result = itemsParamMapper.selectOneByExample(itemParamExp);
        return result;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemCommentsLevelCountsVO queryItemCommentLevelCounts(String itemId) {


        Integer goodCounts = getCommentsCounts(itemId, ItemCommentLevel.GOOD.type);
        Integer normalCounts = getCommentsCounts(itemId, ItemCommentLevel.NORMAL.type);
        Integer badCounts = getCommentsCounts(itemId, ItemCommentLevel.BAD.type);
        Integer totalCounts = goodCounts + normalCounts + badCounts;


        ItemCommentsLevelCountsVO countsVO = new ItemCommentsLevelCountsVO();
        countsVO.setTotalCounts(totalCounts);
        countsVO.setGoodCounts(goodCounts);
        countsVO.setNormalCounts(normalCounts);
        countsVO.setBadCounts(badCounts);
        return countsVO;
    }



    @Transactional(propagation = Propagation.SUPPORTS)
    Integer getCommentsCounts(String itemId,Integer level){//只有动态绑定的方法才能添加事务

        ItemsComments comment = new ItemsComments();
        comment.setItemId(itemId);
        comment.setCommentLevel(level);
        int counts = itemsCommentsMapper.selectCount(comment);
        return counts;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryPagedComments(String itemId,
                                                  Integer level,
                                                  Integer page,
                                                  Integer pageSize) {

        Map<String,Object> map = new HashMap<>();
        map.put("itemId",itemId);
        map.put("level",level);

        //使用pagehelper : page  查询第几页  pageSize
        PageHelper.startPage(page,pageSize);

        List<ItemCommentVo> list = itemsMapperCustom.queryItemComments(map);  //list 就是分页之后的数据

        PagedGridResult grid = setterPagedGrid(list, page);


        return grid;
    }

    //提供通用化的分页方法
    private PagedGridResult setterPagedGrid(List<?> list,Integer page){

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
