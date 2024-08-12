package ru.artemlychko.spring.rest.exceptions;

public class ElementIncorrectData {
    private String info;

    public ElementIncorrectData(){

    }

    public String getInfo(){
        return info;
    }

    public void setInfo(String info){
        this.info = info;
    }
}
