package org.example;

import org.example.models.LogLevel;

import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        Config config = Config.builder().minLevel(LogLevel.INFO)
                .appenders(List.of(
                        Config.AppenderRef.builder().name("console").formatterType("text").build(),
                        Config.AppenderRef.builder().name("file").formatterType("json").params(new HashMap<>()).build()))
                .chain(List.of(
                        Config.HandlerRef.builder().logLevel(LogLevel.INFO).appenderRefs(List.of("console")).build(),
                        Config.HandlerRef.builder().logLevel(LogLevel.ERROR).appenderRefs(List.of("file")).build()
                )).build();


       Logger.init(config);
       Logger log = Logger.getInstance();

        log.info("I am INFO log");
        log.debug("I am DEBUG log");
        log.error("I am ERROR log");
    }
}