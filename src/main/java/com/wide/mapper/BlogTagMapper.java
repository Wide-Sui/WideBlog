package com.wide.mapper;

public interface BlogTagMapper {

    public void saveBlogTag(Long blogsId, Long tagsId);

    public void deleteOneTag(Long tagsId);

    public void deleteOneBlog(Long blogsId);
}
