package UtilTool;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransitTime {

    public static String transitLocalDateTimeToString(LocalDateTime localDateTime) { // 如果傳入null 則會傳出 null
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if(localDateTime != null) {
            return dateTimeFormatter.format(localDateTime);
        } else {
            return null;
        }
    }

    public static LocalDateTime transitTimeStampFromGetAPI(String timeStampString) { // 如果傳入null 或 null字串 則會傳出 null
        if(timeStampString != null && !timeStampString.equals("null") && !timeStampString.equals("")) { // Todo 這裡有壞味道
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            return LocalDateTime.parse(timeStampString, dateTimeFormatter);
        } else {
            return null;
        }
    }

    // Todo 這函數跟上面的完全一樣, 所以之後需要換掉
    public static LocalDateTime transitTimeStamp(String timeStampString) { // 如果傳入null 或 null字串 則會傳出 null
        return transitTimeStampFromGetAPI(timeStampString);
    }

}
