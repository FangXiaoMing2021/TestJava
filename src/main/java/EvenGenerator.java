/**
 * Created by 方飞坤 on 2017/3/19.
 */
public class EvenGenerator extends IntGenerator {
    private int currentEvenValue = 0;
    public int next(){
        ++currentEvenValue;
        ++currentEvenValue;
        return currentEvenValue;
    }
    public static void main(String[] args){
        EvenChecker.test(new EvenGenerator());
    }
}
