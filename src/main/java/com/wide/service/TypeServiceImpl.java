package com.wide.service;

import com.github.pagehelper.PageHelper;
import com.wide.bean.Type;
import com.wide.mapper.BlogMapper;
import com.wide.mapper.TypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired(required = false)
    TypeMapper typeMapper;

    @Autowired(required = false)
    BlogMapper blogMapper;

    @Transactional
    @Override
    public Type saveType(Type type) {
        long id = typeMapper.saveType(type);
        type.setId(id);
        return type;
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        Type type = typeMapper.getType(id);
        return type;
    }

    @Transactional
    @Override
    public Type getTypeByName(String name) {
        Type typeByName = typeMapper.getTypeByName(name);
        return typeByName;
    }

    @Transactional
    @Override
    public List<Type> listType(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Type> types = typeMapper.listType();
        return types;
    }

    @Transactional
    @Override
    public List<Type> listTypeNoPage() {
        List<Type> types = typeMapper.listType();
        return types;
    }

    @Override
    public List<Type> listTypeTop(int num) {
        List<Type> types = typeMapper.listTypeAndBlogTop(num);
        return types;
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
         typeMapper.updateType(id, type);
         type.setId(id);
        return type;
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        blogMapper.deleteBlogType(id);
        typeMapper.deleteType(id);
    }
}
