package com.mic.entity;

import java.util.Date;

/**
 * @author liushuang
 * @create 2019-08-30 13:18
 */
public class User {
    private String name;
    private int age;
    private long id;
    private Date data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                ", data=" + data +
                '}';
    }
}
