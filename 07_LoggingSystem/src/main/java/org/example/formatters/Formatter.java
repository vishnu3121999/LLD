package org.example.formatters;

import org.example.models.LogMessage;

public interface Formatter {
    String format(LogMessage message);
}
