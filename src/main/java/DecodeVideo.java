import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import static com.googlecode.javacv.cpp.opencv_highgui.CV_CAP_PROP_FPS;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_CAP_PROP_FRAME_COUNT;
import static com.googlecode.javacv.cpp.opencv_highgui.CV_CAP_PROP_POS_FRAMES;

/**
 * Created by fang on 17-4-6.
 */
public class DecodeVideo {
    private static final String VIDEO_FILE_NAME = "/videos/PetsD2TeC2.avi";

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        //VideoCapture capture("D:/videos/PetsD2TeC2.avi");
        System.loadLibrary("");
        VideoCapture videoCapture = new VideoCapture();
        videoCapture.open(VIDEO_FILE_NAME);
        if (!videoCapture.isOpened()) {
            System.out.println("打开视频失败,请检查文件的位置");
        }
        double totalFrameNumber = videoCapture.get(CV_CAP_PROP_FRAME_COUNT);
        System.out.println("整个视频总共帧数:" + totalFrameNumber);
        //设置开始帧()
        long frameToStart = 300;
        videoCapture.set(CV_CAP_PROP_POS_FRAMES, frameToStart);
        //设置结束帧
        int frameToStop = 400;
        if (frameToStop < frameToStart) {

        } else {

        }
        //获取帧率
        double rate = videoCapture.get(CV_CAP_PROP_FPS);
        //定义一个用来控制读取视频循环结束的变量
        boolean stop = false;
        //承载每一帧的图像
        Mat frame = new Mat();
        //两帧间的间隔时间:
        int delay = (int) (1000 / rate);

        //利用while循环读取帧
        //currentFrame是在循环体中控制读取到指定的帧后循环结束的变量
        long currentFrame = frameToStart;
        //滤波器的核
        int kernel_size = 3;
        //Mat kernel = Mat::ones(kernel_size,kernel_size,CV_32F)/(float)(kernel_size*kernel_size);
        while (!stop) {
            //读取下一帧
            if (!videoCapture.read(frame)) {
                System.out.println("读取视频失败");
                return;
            }
            //这里加滤波程序
            //imshow("Extracted frame",frame);
            //filter2D(frame,frame,-1,kernel);
            //imshow("after filter",frame);
            //cout<<"正在读取第"<<currentFrame<<"帧"<<endl;
                        //waitKey(int delay=0)当delay ≤ 0时会永远等待；当delay>0时会等待delay毫秒
                        //当时间结束前没有按键按下时，返回值为-1；否则返回按键

            //int c = waitKey(delay);
            //按下ESC或者到达指定的结束帧后退出读取视频
            //if((char) c == 27 || currentFrame > frameToStop)
            //{
            //  stop = true;
            //}
            //按下按键后会停留在当前帧，等待下一次按键
            // if( c >= 0)
            // {
            //  waitKey(0);
            //}
            currentFrame++;
        }
        //关闭视频文件
        videoCapture.release();
        // waitKey(0);
    }
}
