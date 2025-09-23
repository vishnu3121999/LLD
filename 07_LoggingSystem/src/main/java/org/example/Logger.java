package org.example;

import org.example.handlers.Handler;
import org.example.models.LogLevel;
import org.example.models.LogMessage;

public class Logger {

    private static Logger INSTANCE;
    private Handler head;
    private LogLevel minLevel;

    private Logger(Handler handler, LogLevel minLevel){head=handler;this.minLevel=minLevel;}

    static void init(Config config){
        INSTANCE = new Logger(ChainFactory.buildChain(config),config.minLevel);
    }

    static Logger getInstance(){
        return INSTANCE;
    }

    void log(LogLevel level, String msg){
        var logMsg = new LogMessage(level,msg);
        if(logMsg.getLevel().severity>= minLevel.severity){
            head.handle(logMsg);
        }
    }

    void info(String msg){ log(LogLevel.INFO,msg);}
    void debug(String msg){ log(LogLevel.DEBUG,msg);}
    void error(String msg){ log(LogLevel.ERROR,msg);}


}
