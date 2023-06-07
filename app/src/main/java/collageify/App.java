package collageify;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.SingletonResourceProvider;
import org.apache.cxf.transport.http.*;
import collageify.db.SQLTime;
import collageify.musicService.Playing;
import collageify.user.User;
import collageify.web.TestClass;




public class App {
    public static void main(String[] args) throws Exception {
        SQLTime time = new SQLTime();
        System.out.println(time.getDate());
        System.out.println(time.getTime());
        Playing song = new Playing(0, "ibutts", 30000, "https://open.spotify.com/track/6lrDckuosGpwEHtm1hHBcf?si=c8286ba2785f4196", "Gorillaz", "Demon Days", "November Has Come",55, 165000);
        song.UpdateProgress(165000);
        song.UpdateDB();
        /* User user = new User("ldoggs","lawhitley@gmail.com", "somethingEmbarassing", "Laurel", "Whitley");
        user.UpdateDB();
         */
        JAXRSServerFactoryBean factoryBean = new JAXRSServerFactoryBean();   
        try{
            factoryBean.setResourceClasses(collageify.web.TestClass.class);
            
            factoryBean.setResourceProvider(collageify.web.TestClass.class, new SingletonResourceProvider(new TestClass()));
            
            factoryBean.setAddress("http://localhost:80/");
            
            factoryBean.create();   
            System.out.println("Server started at http://localhost:80/"); 

        } catch (Exception e){
            throw e;
        }
    }

}
