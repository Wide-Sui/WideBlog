package com.wide.Utils;

import com.wide.bean.Blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {

    @Autowired
    public RedisTemplate blogRedisTemplate;

    /*--以下是针对blog--*/
    public void delRedis(String key) {
        blogRedisTemplate.delete(key);
    }

    public void addAllSubmitBlog(String key, List<Blog> blogs) {
        delRedis(key);
        blogRedisTemplate.opsForList().rightPushAll(key, blogs);
        blogRedisTemplate.expire(key, 30, TimeUnit.DAYS);
    }

    public void addNewBlog(String key, Blog blog){
        Long size = blogRedisTemplate.opsForList().size(key);
        if(size != 0){
            System.out.println("执行了增加函数");
            blogRedisTemplate.opsForList().rightPush(key, blog);
        }

    }

    public List<Blog> getPartSubmitBlog(String key, int start, int end){
        List<Blog> lists = blogRedisTemplate.opsForList().range(key, start, end);
        return lists;
    }

    public List<Blog> getAllSubmitBlog(String key){
        List<Blog> lists = blogRedisTemplate.opsForList().range(key, 0, -1);
        return lists;
    }

}
