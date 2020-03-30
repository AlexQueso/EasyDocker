package com.include.easydocker.rabbit;

import com.include.easydocker.utils.WebSocketHandler;

public class MessageHandlerImplementations{

    public static final int AFTER_BUILD = 0;
    public static final int AFTER_PUSH = 1;
    public static final int AFTER_LIST = 2;
    public static final int AFTER_CHECK_COMPOSE = 3;

    public static MessageHandler factory(int type){
        switch (type){
            case AFTER_BUILD:
                return new AfterBuildMessageHandler();
            case AFTER_PUSH:
                return new AfterPushMessageHandler();
            case AFTER_LIST:
                return new AfterListMessageHandler();
            case AFTER_CHECK_COMPOSE:
                return new AfterCheckMessageHandler();
        }
        return null;
    }
}

class AfterBuildMessageHandler extends MessageHandler{

    public void run() {
        WebSocketHandler.sendMessage(this.message.getResponse());
    }
}

class AfterPushMessageHandler extends MessageHandler{

    public void run() {
        WebSocketHandler.sendMessage(this.message.toString());
    }
}

class AfterListMessageHandler extends MessageHandler{

    public void run() {
        WebSocketHandler.sendMessage(this.message.toString());
    }
}

class AfterCheckMessageHandler extends MessageHandler{

    public void run() {
        WebSocketHandler.sendMessage(this.message.toString());
    }
}