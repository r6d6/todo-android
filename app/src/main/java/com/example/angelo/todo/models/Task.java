package com.example.angelo.todo.models;

public class Task {

    private int _id;

    private String name;

    public Task(String name) {
        this.setName(name);
    }

    public void setId(int _id) {
        this._id = _id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return _id;
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
