package org.example.formatters;

import org.example.models.LogMessage;

public class JsonFormatter implements Formatter{
    @Override
    public String format(LogMessage message) {
        return message+"formatted as Json";
    }
}
