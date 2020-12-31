package com.wide.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type {
    private Long id;

    private String name;

    private List<Blog> blogs = new ArrayList<>();
}
