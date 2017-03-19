/**
 * Created by fang on 17-3-17.
 */
class MyTest{
    static{
        System.out.println("静态初始化块....");
    }
    static final String compileConstant = System.currentTimeMillis()+"";
}
public class CompileConstantTest {
    public static void main(String[] args){
        System.out.println(MyTest.compileConstant);
    }
}
