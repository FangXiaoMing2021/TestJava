import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by fang on 17-3-19.
 */
public class RingBuffer {

    private AtomicInteger productIndex = new AtomicInteger(0);

    private AtomicInteger consumeIndex = new AtomicInteger(0);

    private int MAX_LENGTH = 4;

    private String[] dataArr = new String[MAX_LENGTH];

    private static final int maxSpinNums = 1000;

    private static final ExecutorService executorService = new ThreadPoolExecutor(3, 10, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(5000), new RejectedExecutionHandler() {
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            // discard
        }
    });

    public boolean write(String content) {
        // 获取写入位置
        int oldWriteIndex = productIndex.get();

        // 追上一圈则写入失败
        if ((oldWriteIndex - consumeIndex.get()) >= MAX_LENGTH) {
            return false;
        }

        int indexAfterWrite = oldWriteIndex + 1;
        if (indexAfterWrite > Integer.MAX_VALUE) {
            indexAfterWrite = 0;
        }

        int spinNums = 0;
        for (; ; ) {
            if (spinNums++ >= maxSpinNums) {
                return false;
            }
            if (productIndex.compareAndSet(oldWriteIndex, indexAfterWrite)) {
                dataArr[oldWriteIndex & (MAX_LENGTH - 1)] = content;
                break;
            }
        }

        return true;
    }

    public String read() {
        // 获取读取位置
        int oldReadIndex = consumeIndex.get();

        if (productIndex.get() <= oldReadIndex) {
            return null;
        }

        int indexAfterRead = oldReadIndex + 1;
        if (indexAfterRead > Integer.MAX_VALUE) {
            indexAfterRead = 0;
        }

        for (; ; ) {
            if (consumeIndex.compareAndSet(oldReadIndex, indexAfterRead)) {
                return dataArr[oldReadIndex & (MAX_LENGTH - 1)];
            }
        }
    }


    public static void main(String[] args) {
        final RingBuffer noLockBuffer = new RingBuffer();

        executorService.execute(new Runnable() {

            public void run() {
                System.out.println(noLockBuffer.write("a"));
            }
        });

        executorService.execute(new Runnable() {

            public void run() {
                System.out.println(noLockBuffer.write("b"));
            }
        });

        executorService.execute(new Runnable() {

            public void run() {
                System.out.println(noLockBuffer.write("c"));
                ;
            }
        });

        executorService.execute(new Runnable() {

            public void run() {
                System.out.println(noLockBuffer.write("d"));
                ;
            }
        });

        executorService.execute(new Runnable() {

            public void run() {
                System.out.println(noLockBuffer.write("e"));
                ;
            }
        });

        executorService.execute(new Runnable() {

            public void run() {
                noLockBuffer.write("e");
            }
        });

        // consume
        while (true) {
            try {
                Thread.sleep(1000L);

                String res = noLockBuffer.read();
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