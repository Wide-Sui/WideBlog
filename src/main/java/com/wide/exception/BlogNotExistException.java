package com.wide.exception;

public class BlogNotExistException extends RuntimeException{
    public BlogNotExistException(){
        super("博客走丢，正在找回");
    }
}
