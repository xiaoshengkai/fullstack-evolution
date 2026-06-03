package com.learner.Day10;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.learner.Day8.TodoItem;
import com.learner.Day8.TodoItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 事务管理:
 * 原子性 (Atomicity)	操作不可分割，要么全成功，要么全回滚	转账扣款和加款必须同时成功或失败
 * 一致性 (Consistency)	事务前后数据完整性不变	转账前后总金额不变
 * 隔离性 (Isolation)	并发事务之间互不干扰	多人同时转账不冲突
 * 持久性 (Durability)	事务提交后数据永久保存	断电也不丢
 */

/**
 * 先关注原子性:
 * 想象你正在实现一个转账功能：A 扣 100 元，B 加 100 元。
 * 如果 A 扣款成功但 B 加款失败，钱就凭空消失了。事务就是保证这两步要么全成功，要么全失败。
 */


//在你的 Service 层方法上加 @Transactional，Spring 会自动开启事务。
// 如果方法执行过程中抛出非受检异常（RuntimeException 及其子类），事务会自动回滚。
@Service
public class Handle {
    @Autowired
    private TodoItemMapper todoItemMapper;

    /**
     * 标记完成并修改优先级（模拟需要事务的操作）
     */
    @Transactional
    public void markDoneAndChangePriority (Long id, boolean done, String priority, boolean isError) {
        // 1. 更新完成状态
        LambdaUpdateWrapper<TodoItem> queryWrapper = new LambdaUpdateWrapper<>();
        queryWrapper.eq(TodoItem::getId, id).set(TodoItem::getDone, done);
        todoItemMapper.update(null, queryWrapper);


        if (isError) {
            // 2.故意报错
            int a = 1 / 0;
        }

        LambdaUpdateWrapper<TodoItem> queryWrapper1 = new LambdaUpdateWrapper<>();
        queryWrapper1.eq(TodoItem::getId, 1).set(TodoItem::getPriority, priority);
        todoItemMapper.update(null, queryWrapper1);
    }

    @Transactional
    public void insertBatchWithRollback () {
        TodoItem item1 = new TodoItem("事务测试1", "HIGH", null, false);
        TodoItem item2 = new TodoItem("事务测试2", "HIGH", null, false);
        todoItemMapper.insert(item1);
        todoItemMapper.insert(item2);

        // 故意抛出异常，观察前面插入的两条是否被回滚
        throw new RuntimeException("'估计报错一下'");
        // !!!!!
        // @Transactional 默认只对 RuntimeException 及其子类 回滚，对 checked 异常不回滚（可配置 rollbackFor 属性修改）。
    }

}


