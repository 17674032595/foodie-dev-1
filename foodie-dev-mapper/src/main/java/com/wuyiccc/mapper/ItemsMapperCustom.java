package com.wuyiccc.mapper;

import com.wuyiccc.pojo.vo.ItemCommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wuyiccc
 * @date 2020/1/10 9:51
 * 岂曰无衣，与子同袍~
 */
public interface ItemsMapperCustom {

    public List<ItemCommentVo> queryItemComments(@Param("paramsMap")Map<String,Object> map);
}
