package com.wuyiccc.mapper;

import com.wuyiccc.pojo.vo.CategoryVO;
import com.wuyiccc.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryMapperCustom {

    public List<CategoryVO> getSubCatList(Integer rootCatId);

    public List<NewItemsVO> getSixItemsLazy(@Param("paramsMap") Map<String,Object> map);//paramsMap必须与mapper.xml文件里使用的参数一致

}