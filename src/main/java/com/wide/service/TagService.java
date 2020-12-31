package com.wide.service;

import com.wide.bean.Tag;
import com.wide.bean.Type;

import java.util.List;

public interface TagService {

    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Tag getTagByName(String name);

    List<Tag> listTag(int pageNum, int pageSize);

    List<Tag> listTagNoPage();

    List<Tag> listTagTop(int num);

    List<Tag> listTag(String tagIds);

    Tag updateTag(Long id, Tag tag);

    void deleteTag(Long id);
}
