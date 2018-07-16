package com.example.user.timeplanner;

import java.sql.Time;
import java.util.Date;
import com.example.user.timeplanner.Category;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

import static com.example.user.timeplanner.Category.housework;
import static com.example.user.timeplanner.Category.learning;
import static com.example.user.timeplanner.Category.meal;
import static com.example.user.timeplanner.Category.phystraining;
import static com.example.user.timeplanner.Category.rest;
import static com.example.user.timeplanner.Category.transport;
import static com.example.user.timeplanner.Category.work;

/**
 * Created by User on 11.04.2018.
 */

public class Task extends RealmObject{
    //@Required
    @PrimaryKey
    private int taskId;
    private int taskCategoryId;
    @Required
    private String taskName;
    private long taskStartTime;
    private long taskEndTime;
    private String taskDescription;
    private boolean taskNotify;
    private long taskDate;
    private int taskImportanceId;
    private boolean isExecuted;

    public Task(int taskId, int taskCategoryId, String taskName, long taskStartTime, long taskEndTime, String taskDescription,
                boolean taskNotify, long taskDate, int taskImportanceId, boolean isExecuted) {
        this.taskId = taskId;
        this.taskCategoryId = taskCategoryId;
        this.taskName = taskName;
        this.taskStartTime = taskStartTime;
        this.taskEndTime = taskEndTime;
        this.taskDescription = taskDescription;
        this.taskNotify = taskNotify;
        this.taskDate = taskDate;
        this.taskImportanceId = taskImportanceId;
        this.isExecuted = isExecuted;
    }

    public Task(){}

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public Category getTaskCategory() {
        return Category.values()[taskCategoryId];
    }

    public void setTaskCategory(Category taskCategory) {
        this.taskCategoryId = taskCategory.ordinal();
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public long getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(long taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public long     getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(long taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public boolean isTaskNotify() {
        return taskNotify;
    }

    public void setTaskNotify(boolean taskNotify) {
        this.taskNotify = taskNotify;
    }

    public long getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(long taskDate) {
        this.taskDate = taskDate;
    }

    public Importance getTaskImportance() {
        return Importance.values()[taskImportanceId];
    }

    public void setTaskImportance(Importance taskImportance) {
        this.taskImportanceId = taskImportance.ordinal();
    }

    public boolean isExecuted() {
        return isExecuted;
    }

    public void setExecuted(boolean executed) {
        isExecuted = executed;
    }
}
