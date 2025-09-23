package org.example;

import org.example.appenders.Appender;
import org.example.appenders.ConsoleAppender;
import org.example.appenders.FileAppender;
import org.example.formatters.Formatter;
import org.example.formatters.JsonFormatter;
import org.example.formatters.TextFormatter;
import org.example.handlers.DebugHandler;
import org.example.handlers.ErrorHandler;
import org.example.handlers.Handler;
import org.example.handlers.InfoHandler;
import org.example.models.LogLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class ChainFactory {
    static Formatter mkFormatter(String name) {
        return switch (name) {
            case "json" -> new JsonFormatter();
            case "text" -> new TextFormatter();
            default -> null;
        };
    }
    static Appender mkAppender(String name, String formatterName) {
        Formatter f = mkFormatter(formatterName);
        return switch (name) {
            case "console" -> new ConsoleAppender(f);
            case "file"    -> new FileAppender(f);
            default -> null;
        };
    }
    static Handler mkHandler(LogLevel level,List<Appender> appenders){

        return switch (level){
            case INFO -> new InfoHandler(appenders);
            case DEBUG-> new DebugHandler(appenders);
            case ERROR-> new ErrorHandler(appenders);
        };


    }
    static Handler buildChain(Config cfg){

        Map<String,Appender> reg = new HashMap<>();
        for(var s: cfg.appenders) reg.put(s.name, mkAppender(s.name,s.formatterType));
        Handler head=null, prev=null;
        for(Config.HandlerRef hr: cfg.chain){
            ArrayList<Appender> appenders = new ArrayList<>();
            for(var x:hr.appenderRefs)appenders.add(reg.get(x));

            Handler h = mkHandler(hr.logLevel,appenders);
            if(head==null) head=h; else prev.next=h;
            prev=h;
        }
        return head;
    }
}

