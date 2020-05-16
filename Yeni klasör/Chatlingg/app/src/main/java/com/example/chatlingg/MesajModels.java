package com.example.chatlingg;

public class MesajModels {
    private String from,text;

    public MesajModels(){

    }
    public MesajModels(String from, String text) {
        this.from = from;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public String toString() {
        return "MesajModels{" +
                "from='" + from + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
