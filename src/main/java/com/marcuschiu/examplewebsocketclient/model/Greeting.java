package com.marcuschiu.examplewebsocketclient.model;

import lombok.Data;

@Data
public class Greeting {
    String content;

    /**
     * need to have default constructor for fasterxml.jackson.databind
     */
    public Greeting() {
    }

    public Greeting(String content) {
        this.content = content;
    }
}
