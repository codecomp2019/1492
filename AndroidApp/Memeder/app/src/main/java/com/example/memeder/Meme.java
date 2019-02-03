package com.example.memeder;

public class Meme {
    private String file;
    private String description;
    private String text;

    public String getFile(){return file;}
    public String getDescription(){return description;}
    public String getText(){return text;}

    public void setFile(String file){
        this.file = file;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString(){
        return String.format("Filename: %s\nDescription: %s\nText: %s",
                file,description,text);
    }
}
