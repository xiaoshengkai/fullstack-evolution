package com.learner.Day12;

import com.baomidou.mybatisplus.annotation.TableId;

public class TodoTag {

    private  Long todo_item_id;
    private Long tag_id;

    // ========= 无参构造方法（MyBatis 需要） =========
    public TodoTag () {}

    // ========= 无参构造方法（MyBatis 需要） =========
    public TodoTag ( Long todo_item_id,  Long tag_id) {
        this.todo_item_id = todo_item_id;
        this.tag_id = tag_id;
    }

    public Long getTag_id() {
        return tag_id;
    }


    public Long getTodo_item_id() {
        return todo_item_id;
    }

    public void setTag_id(Long tag_id) {
        this.tag_id = tag_id;
    }

    public void setTodo_item_id(Long todo_item_id) {
        this.todo_item_id = todo_item_id;
    }
}
