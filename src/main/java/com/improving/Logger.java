package com.improving;
import org.springframework.stereotype.Component;
@Component
public class Logger {

    public boolean enabled = true;
    public void println(String message) {
        if (enabled)
            System.out.println("> " + message);
    }
}