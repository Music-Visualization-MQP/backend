package collageify;

import collageify.db.SQLAccess;
import collageify.exceptions.NoSPApiException;
import collageify.service.collageify.Player;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication(scanBasePackages = "collageify")
public class App {
    public static void main(String[] args) throws Exception, NoSPApiException {
        /*SQLTime time = new SQLTime();
        System.out.println(time.getDate());
        System.out.println(time.getTime());
        Playing song = new Playing(0, "ibutts", 30000, "https://open.spotify.com/track/6lrDckuosGpwEHtm1hHBcf?si=c8286ba2785f4196", "Gorillaz", "Demon Days", "November Has Come",55, 165000);
        System.out.println(time.getTime());
        song.UpdateProgress(165000);
        song.UpdateDB();
        SPAccess.clientCredentials_Sync("1DXD0wVXXHwUYo9AXbcMMI");*/
        Player play = new Player(8);

        BCryptPasswordEncoder pwencode = new BCryptPasswordEncoder();
        /*LoginDto dto = new LoginDto();
        dto.setPassword("f");
        dto.setUsernameOrEmail("ffsffff");
        System.out.println(new AuthController().authenticateUser(dto));
        System.out.println(pwencode.encode("euphoria"));
        user.UpdateDB();
         */
        //SpringApplication.run(App.class,args);
        /*JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        factoryBean.setResourceClasses(TestClass.class);


        factoryBean.setResourceProvider(TestClass.class, new SingletonResourceProvider(new TestClass()));
        factoryBean.setProvider(new CorsFilter());

        factoryBean.setAddress("http://localhost:8080/");

        factoryBean.create();
        System.out.println("Server started at http://localhost:80/");*/

    }

}
