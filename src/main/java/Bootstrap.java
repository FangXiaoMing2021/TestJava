import java.net.URL;

/**
 * Created by fang on 17-3-17.
 */
public class Bootstrap {
    public static void main(String[]args){
        URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();
        for(int i=0;i<urls.length;i++){
            System.out.println(urls[i].toExternalForm());
        }
    }
}
