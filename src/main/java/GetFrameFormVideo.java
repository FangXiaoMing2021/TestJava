import com.googlecode.javacv.cpp.opencv_highgui;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;

/**
 * Created by fang on 17-4-6.
 * 实现每播放一秒视频就截取一张图片保存到本地的操作
 * 没有使用ffmpeg,则只能读取avi格式视频
 * 下载google javcv.jar
 * sudo add-apt-repository ppa:kirillshkrogalev/ffmpeg-next
 * sudo apt-get update
 * sudo apt-get install ffmpeg
 * /home/fang/BigDataSoft/opencv-2.4.13/3rdparty/ffmpeg中有opencv_ffmpeg_64.dll文件
 * 不能解析视频,提示moov atom not found 是文件有错
 */
public class GetFrameFormVideo {
    public static void main(String[] args) {
//         System.out.println("Welcome to OpenCV " + Core.VERSION);
//         System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//         Mat m = Mat.eye(3, 3, CvType.CV_8UC1);
//         System.out.println("m = " + m.dump());
        //加载本地的OpenCV库，这样就可以用它来调用Java API
         System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
         run2();
    }

    public static void run2() {
        //读取视频文件
        VideoCapture cap = new VideoCapture("/home/fang/Downloads/src_11.mp4");
        System.out.println(cap.isOpened());

        //判断视频是否打开
        if (cap.isOpened()) {
            //总帧数
            double frameCount = cap.get(opencv_highgui.CV_CAP_PROP_FRAME_COUNT);
            System.out.println("视频总帧数:"+frameCount);
            //帧率
            double fps = cap.get(opencv_highgui.CV_CAP_PROP_FPS);
            System.out.println("视频帧率"+fps);
            //时间长度
            double len = frameCount / fps;
            System.out.println("视频总时长:"+len);
            Double d_s = new Double(len);
            System.out.println(d_s.intValue());
            Mat frame = new Mat();
            for (int i = 0; i < d_s.intValue(); i++) {
                //设置视频的位置(单位:毫秒)
                cap.set(opencv_highgui.CV_CAP_PROP_POS_MSEC, i * 1000);
                //读取下一帧画面
                if (cap.read(frame)) {
                    System.out.println("正在保存");
                    //保存画面到本地目录
                    Highgui.imwrite("/home/fang/images/" + i + ".jpg", frame);
                }
            }
            //关闭视频文件
            cap.release();
        }
    }
}
