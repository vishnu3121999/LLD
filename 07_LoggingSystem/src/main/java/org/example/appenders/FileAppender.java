package org.example.appenders;

import org.example.formatters.Formatter;
import org.example.models.LogMessage;

public class FileAppender extends Appender{
    public FileAppender(Formatter f){this.formatter=f;}
    @Override
    public void append(LogMessage message) {
        System.out.println("Sending msg to File:"+formatter.format(message));
    }
}
