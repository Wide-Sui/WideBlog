package com.wide.service;

import com.wide.bean.Type;
import java.util.List;

public interface TypeService {

    Type saveType(Type type);

    Type getType(Long id);

    Type getTypeByName(String name);

    List<Type> listType(int pageNum, int pageSize);

    List<Type> listTypeNoPage();

    List<Type> listTypeTop(int num);

    Type updateType(Long id, Type type);

    void deleteType(Long id);
}
