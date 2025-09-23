package org.example.appenders;

import org.example.formatters.Formatter;
import org.example.models.LogMessage;

public class ConsoleAppender extends Appender {

    public ConsoleAppender(Formatter f){this.formatter=f;}
    @Override
    public void append(LogMessage message) {
        System.out.println("Sending msg to Console:"+formatter.format(message));
    }
}
