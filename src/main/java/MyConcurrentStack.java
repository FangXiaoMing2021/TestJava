import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by fang on 17-3-19.
 * 实现无锁Stack
 */
public class MyConcurrentStack {
    private AtomicInteger stackIndex = new AtomicInteger(-1);
    private int STACK_MAX_LENGTH = 4;
    private String[] dataArr = new String[STACK_MAX_LENGTH];
    private static final ExecutorService executorService = new ThreadPoolExecutor(3, 10, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(5000), new RejectedExecutionHandler() {
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            // discard
        }
    });

    /**
     * 压栈
     * @param content
     * @return boolean
     */
    public boolean push(String content) {
        // 获取写入位置
        int oldWriteIndex = stackIndex.get();
        //如果栈满,返回错误
        if(stackIndex.get()>= STACK_MAX_LENGTH -1){
            return false;
        }

        int indexAfterPush = oldWriteIndex + 1;
        //更新栈
        for (; ; ) {
            if (stackIndex.compareAndSet(oldWriteIndex, indexAfterPush)) {
                dataArr[indexAfterPush] = content;
                break;
            }
        }

        return true;
    }

    /**
     * 出栈
     * @return
     */
    public String pop() {
        // 获取读取位置
        int oldReadIndex = stackIndex.get();
        //如果栈空,返回null
        if (stackIndex.get() <= -1) {
            return null;
        }
        int indexAfterPop = oldReadIndex - 1;
        //更新栈
        for (; ; ) {
            if (stackIndex.compareAndSet(oldReadIndex, indexAfterPop)) {
                return dataArr[oldReadIndex];
            }
        }
    }

    public static void main(String[] args) {
        final MyConcurrentStack myConcurrentStack = new MyConcurrentStack();
        //进栈
        executorService.execute(new Runnable() {

            public void run() {
                System.out.println(myConcurrentStack.push("a"));
            }
        });

        executorService.execute(new Runnable() {

            public void run() {
                System.out.println(myConcurrentStack.push("b"));
            }
        });

        executorService.execute(new Runnable() {

            public void run() {
                System.out.println(myConcurrentStack.push("c"));
            }
        });

        executorService.execute(new Runnable() {

            public void run() {
                System.out.println(myConcurrentStack.push("d"));
            }
        });

        executorService.execute(new Runnable() {

            public void run() {
                System.out.println(myConcurrentStack.push("e"));
            }
        });

        executorService.execute(new Runnable() {

            public void run() {
                myConcurrentStack.push("e");
            }
        });

        // 出栈
        while (true) {
            try {
                Thread.sleep(1000L);

                String res = myConcurrentStack.pop();
                if (res != null) {
                    System.out.println(res);
                }
            } catch (InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }

    }
}
