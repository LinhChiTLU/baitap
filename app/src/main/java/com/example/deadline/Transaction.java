package com.example.deadline;

public class Transaction {

    private String id;       // 🔑 Firebase key (quan trọng để xoá)
    private String name;
    private String icon;
    private double amount;
    private String date;
    private String note;
    private String type;     // "income" hoặc "expense"

    // Constructor đầy đủ
    public Transaction(String id, String name, String icon, double amount, String date, String note, String type) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.amount = amount;
        this.date = date;
        this.note = note;
        this.type = type;
    }

    // Constructor không có id (có thể dùng khi thêm mới, rồi set id sau)
    public Transaction(String name, String icon, double amount, String date, String note, String type) {
        this.name = name;
        this.icon = icon;
        this.amount = amount;
        this.date = date;
        this.note = note;
        this.type = type;
    }

    public Transaction() {
        // Bắt buộc với Firebase
    }

    // Getters và Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
