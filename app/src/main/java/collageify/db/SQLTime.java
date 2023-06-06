package collageify.db;
public class SQLTime {
    private long millis = System.currentTimeMillis();

    /**
     * The purpose of this method is to return the date at which the...
     * object was created in sql format
     * @return sql formatted date
     */
    public String getDate(){
        java.sql.Date date = new java.sql.Date(millis);
        return date.toString();
    }
    /**
     * The purpose of this method is to return the time at which the...
     * object was created in sql format
     * @return sql formatted time
     */
    public String getTime(){
        java.sql.Time time = new java.sql.Time(millis);
        return time.toString();
    }
    
}
