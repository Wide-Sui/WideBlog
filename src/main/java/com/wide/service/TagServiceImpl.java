package com.wide.service;

import com.github.pagehelper.PageHelper;
import com.wide.bean.Tag;
import com.wide.mapper.BlogTagMapper;
import com.wide.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired(required = false)
    TagMapper tagMapper;
    @Autowired(required = false)
    BlogTagMapper blogTagMapper;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        long id = tagMapper.saveTag(tag);
        tag.setId(id);
        return tag;
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        Tag tag = tagMapper.getTag(id);
        return tag;
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        Tag tagByName = tagMapper.getTagByName(name);
        return tagByName;
    }

    @Transactional
    @Override
    public List<Tag> listTag(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Tag> tags = tagMapper.listTag();
        return tags;
    }

    @Override
    public List<Tag> listTagNoPage() {
        List<Tag> tags = tagMapper.listTag();
        return tags;
    }

    @Override
    public List<Tag> listTagTop(int num) {
        List<Tag> tags = tagMapper.listTagAndBlogTop(num);
        return tags;
    }

    @Override
    public List<Tag> listTag(String tagIds) {
        List<Tag> tags = tagMapper.listTagByIds(tagIds);
        return tags;
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        tagMapper.updateTag(id, tag);
        tag.setId(id);
        return tag;
    }


    @Transactional
    @Override
    public void deleteTag(Long id) {
        blogTagMapper.deleteOneTag(id);
        tagMapper.deleteTag(id);
    }
}
