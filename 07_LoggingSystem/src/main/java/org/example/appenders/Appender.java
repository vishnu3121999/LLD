package org.example.appenders;

import org.example.formatters.Formatter;
import org.example.models.LogMessage;

public abstract class Appender {
    Formatter formatter;
    public abstract void append(LogMessage message);
}
