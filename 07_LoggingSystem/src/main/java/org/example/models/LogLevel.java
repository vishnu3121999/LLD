package org.example.models;

//  Severity: TRACE < DEBUG < INFO < WARN < ERROR < FATAL

public enum LogLevel {
    DEBUG(20),
    INFO(30),
    ERROR(50);

    public int severity;

    LogLevel(int severity){this.severity=severity;}
}
