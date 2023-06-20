package collageify.service.collageify;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

public class Credentials {
    Integer id;
    String refreshToken;
    String accessToken;
    Integer userID;
    Date accessTokenExpDate;
    Time accessTokenExpTime;

    public Credentials(Integer id, String refreshToken, String accessToken, Integer userID, Date accessTokenExpDate, Time accessTokenExpTime){
        this.id = id;
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.userID = userID;
        this.accessTokenExpDate = accessTokenExpDate;
        this.accessTokenExpTime = accessTokenExpTime;
    }
    public boolean isTokenValid(){
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        Calendar expirationCalendar = Calendar.getInstance();
        expirationCalendar.setTime(this.accessTokenExpDate);
        expirationCalendar.set(Calendar.HOUR_OF_DAY, this.accessTokenExpTime.getHours());
        expirationCalendar.set(Calendar.MINUTE, this.accessTokenExpTime.getMinutes());
        expirationCalendar.set(Calendar.SECOND, this.accessTokenExpTime.getSeconds());

        System.out.println(expirationCalendar.getTime().after(currentDate));

        return expirationCalendar.getTime().after(currentDate);

    }

}
