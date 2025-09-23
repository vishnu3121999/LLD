package org.example.handlers;

import org.example.appenders.Appender;
import org.example.models.LogLevel;
import org.example.models.LogMessage;

import java.util.List;

public class DebugHandler extends Handler{

    LogLevel logLevel = LogLevel.DEBUG;

    public DebugHandler(List<Appender> appenders){this.appenderList=appenders;}

    @Override
    public void handle(LogMessage message) {
        if(message.getLevel().severity>= logLevel.severity){
            for(var appender:appenderList)appender.append(message);
        }
        if(next!=null)
            next.handle(message);
    }
}
