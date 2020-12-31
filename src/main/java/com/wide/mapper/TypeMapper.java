package com.wide.mapper;

import com.wide.bean.Type;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TypeMapper {


    public long saveType(@Param("type") Type type);

    public Type getType(Long id);

    public Type getTypeByName(String name);

    public List<Type> listType();

    public List<Type> listTypeAndBlogTop(int num);

    public int updateType(@Param("id") Long id, @Param("type") Type type);

    public void deleteType(Long id);
}
