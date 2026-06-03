package Day9;

import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TodoItem {

    @TableId
    private Long id;
    private String title;
    private String priority;   // HIGH / MEDIUM / LOW
    private LocalDate deadline;
    private Boolean done;       // 0/1 自动映射为 false/true
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


    // ========= 无参构造方法（MyBatis 需要） =========
    public TodoItem() {}

    // ========= 带参构造方法（方便创建对象） =========
    public TodoItem(String title, String priority, LocalDate deadline, Boolean done) {
        this.title = title;
        this.priority = priority;
        this.deadline = deadline;
        this.done = done;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return String.format("[%d] %s | %s | 截止:%s | %s",
                id, title, priority, deadline, done ? "已完成" : "未完成");
    }
}


