package com.charly.springbootconsumerpubsub.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Body {

    private Message message;


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Message {

        private String messageId;
        private String publishTime;
        private String data;

    }

}
