package com.firatyazilim.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString

public class Message {
    private String senderName;
    private String receiverName;
    private String message;
    private String date;
    private Status status;
}
