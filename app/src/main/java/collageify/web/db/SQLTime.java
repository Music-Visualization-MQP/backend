package collageify.web.db;

import java.sql.Time;

public class SQLTime {
    private final long millis = System.currentTimeMillis();
    /**
     * The purpose of this method is to return the date at which the...
     * object was created in sql format
     * @return sql formatted date
     */
    public java.sql.Date getDate(){
        return new java.sql.Date(millis);
    }
    /**
     * The purpose of this method is to return the time at which the...
     * object was created in sql format
     * @return sql formatted time
     */
    public Time getTime(){
        return new java.sql.Time(millis);
    }
}
