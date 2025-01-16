package com.charly.springbootconsumerpubsub.models;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserMessage {

    private String body;
    private String username;
    private LocalDateTime createdAt;

}

