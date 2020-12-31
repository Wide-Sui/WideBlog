package com.wide.mapper;

import com.wide.bean.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagMapper {

    public long saveTag(@Param("tag") Tag tag);

    public Tag getTag(Long id);

    public Tag getTagByName(String name);

    public List<Tag> listTag();

    public List<Tag> listTagAndBlogTop(int num);

    public List<Tag> listTagByIds(@Param("tagIds") String tagIds);

    public int updateTag(@Param("id") Long id, @Param("tag") Tag tag);

    public void deleteTag(Long id);
}
