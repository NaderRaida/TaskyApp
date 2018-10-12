package com.example.naderwalid.tasky;

public class Task {
    private String name;
    private int priority_number;
    private String date;

    public Task() {
    }

    public Task(String name,String date, int priority_number) {
        this.name = name;
        this.date=date;
        this.priority_number = priority_number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority_number() {
        return priority_number;
    }

    public void setPriority_number(int priority_number) {
        this.priority_number = priority_number;
    }
    public String getAll(){
        return "Title : "+ getName()+" date :"+getDate()+" priority : "+getPriority_number();
    }
}
