package com.example.QuoraApp.utils;

import java.awt.*;
import java.time.LocalDateTime;

public class CursorUtils {
    public static boolean isValidCursor(String cursor) {
        // this func checks if the given cursor string is valid cursor or not
        if(cursor==null || cursor.isEmpty()){
            return false;
        }
        try{
            LocalDateTime.parse(cursor);
            /* if the string given to us is parsable in localdatetime, thenwe can say its a valid cursor */
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    public static LocalDateTime parseCursor(String cursor){
        // this func first checks if the given string cursor valid or not using isValid cursor func
        // it then return the cursor in timeStamp
        if(!isValidCursor(cursor)){
            throw new IllegalArgumentException("Invalid cursor");
        }
        return LocalDateTime.parse(cursor);
    }
}
