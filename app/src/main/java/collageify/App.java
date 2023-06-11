package collageify;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;

import collageify.deprecated.api.TestClass;
import collageify.db.SQLTime;
import collageify.musicService.Playing;
import collageify.deprecated.api.CorsFilter;
import collageify.musicService.SpClientCredentials;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class App {
    public static void main(String[] args) throws Exception {
        /*SQLTime time = new SQLTime();
        System.out.println(time.getDate());
        System.out.println(time.getTime());
        Playing song = new Playing(0, "ibutts", 30000, "https://open.spotify.com/track/6lrDckuosGpwEHtm1hHBcf?si=c8286ba2785f4196", "Gorillaz", "Demon Days", "November Has Come",55, 165000);
        System.out.println(time.getTime());
        song.UpdateProgress(165000);
        song.UpdateDB();
        SpClientCredentials.clientCredentials_Sync("1DXD0wVXXHwUYo9AXbcMMI");*/

        SpringApplication.run(App.class,args);
        /* User user = new User("ldoggs","lawhitley@gmail.com", "somethingEmbarassing", "Laurel", "Whitley");
        user.UpdateDB();
         */
        /*JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();
        factoryBean.setResourceClasses(TestClass.class);


        factoryBean.setResourceProvider(TestClass.class, new SingletonResourceProvider(new TestClass()));
        factoryBean.setProvider(new CorsFilter());

        factoryBean.setAddress("http://localhost:8080/");

        factoryBean.create();
        System.out.println("Server started at http://localhost:80/");*/

    }

}
