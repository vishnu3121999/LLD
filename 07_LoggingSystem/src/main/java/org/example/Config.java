package org.example;

import lombok.Builder;
import org.example.models.LogLevel;

import java.util.List;
import java.util.Map;

@Builder
public class Config {

    LogLevel minLevel;

    List<AppenderRef> appenders;
    List<HandlerRef> chain;

    @Builder
    static class HandlerRef{
        LogLevel logLevel;
        List<String> appenderRefs;
    }

    @Builder
    static class AppenderRef{
        String name;
        String formatterType;
        Map<String,String> params;
    }

}
