import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 方飞坤 on 2017/3/15.
 */
public class ExceptionThread implements Runnable {
    public void run(){
        throw new RuntimeException();
    }
    public static void main(String[]args){
        try{
            ExecutorService exec = Executors.newCachedThreadPool();
            exec.execute(new ExceptionThread());
        }catch(RuntimeException re){
            System.out.println("Exception has been handled!");
        }

    }
}
