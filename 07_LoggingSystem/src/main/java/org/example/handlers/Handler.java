package org.example.handlers;

import org.example.appenders.Appender;
import org.example.models.LogLevel;
import org.example.models.LogMessage;

import java.util.List;

public abstract class Handler {
    List<Appender> appenderList;
    public Handler next;
    public abstract void handle(LogMessage message);
}
