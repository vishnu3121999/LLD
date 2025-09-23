package org.example.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class LogMessage {
    private LogLevel level;
    private String msg;
    private long ts = System.currentTimeMillis();

    public LogMessage(LogLevel level, String msg) {
        this.level = level;
        this.msg = msg;
    }
}
