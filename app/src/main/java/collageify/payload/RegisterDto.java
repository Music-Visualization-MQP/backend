package collageify.payload;
import lombok.Data;
import collageify.db.SQLTime;

@Data
public class RegisterDto {
    private String username;
    private String email;
    private String password;
}

