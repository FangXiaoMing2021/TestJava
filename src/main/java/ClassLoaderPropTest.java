import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * Created by fang on 17-3-17.
 */
public class ClassLoaderPropTest {
    public static void main(String args[]) throws IOException{
        ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemLoader);
        Enumeration<URL> em1 = systemLoader.getResources("");
        while(em1.hasMoreElements()){
            System.out.println(em1.nextElement());
        }
        ClassLoader extensionLoader = systemLoader.getParent();
        System.out.println("扩展类加载器: "+ extensionLoader );
        System.out.println("扩展类加载器的加载路径:" + System.getProperty("java.ext.dirs"));
        System.out.println("扩展类加载器的parent:"+extensionLoader.getParent());
    }
}
