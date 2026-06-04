package com.learner.Day12;

import com.baomidou.mybatisplus.annotation.TableId;

public class Tag {

    @TableId
    private  Long id;
    private String name;

    // ========= 无参构造方法（MyBatis 需要） =========
    public Tag () {}

    // ========= 无参构造方法（MyBatis 需要） =========
    public Tag ( Long id,  String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
