package com.include.easydocker.rabbit;


public abstract class MessageHandler{
    DockerResponse message = null;

    void setMessage(DockerResponse message){
        this.message = message;
    }

    abstract void run();
}
