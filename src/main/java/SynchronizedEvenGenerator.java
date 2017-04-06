/**
 * Created by 方飞坤 on 2017/3/19.
 */
public class SynchronizedEvenGenerator extends IntGenerator {
    private int currentEvenValue = 0;
    public synchronized int next(){
       // System.out.println("next()");
        ++currentEvenValue;
        Thread.yield();
        ++currentEvenValue;
        return currentEvenValue;
    }
    public static void main(String[]args){
        EvenChecker.test(new SynchronizedEvenGenerator());
    }
}
