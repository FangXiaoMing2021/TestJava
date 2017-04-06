/**
 * Created by fang on 17-4-6.
 */

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class VideoPlay {

    static{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private JFrame frame;
    private static JLabel lblNewLabel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VideoPlay window = new VideoPlay();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        //我们的操作
        VideoCapture capture=new VideoCapture("F://2.avi");//读取视频
        if (!capture.isOpened()) {
            System.out.println("Error");
        } else {
            Mat webcam_image = new Mat();
            capture.read(webcam_image);
            while (true) {
                capture.read(webcam_image);
                if(!webcam_image.empty() ){
                    //TODO
                    BufferedImage b = null;
                    //BufferedImage b=mat2BufferedImage.matToBufferedImage(webcam_image);
                    lblNewLabel.setIcon(new ImageIcon(b));
                }else{
                    System.out.println("视频已结束!");
                    capture.release();
                    break;
                }

            }
        }
    }

    /**
     * Create the application.
     */
    public VideoPlay() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(0, 0, 1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        lblNewLabel = new JLabel("");
        lblNewLabel.setBounds(0, 0, 1000, 800);
        frame.getContentPane().add(lblNewLabel);
    }

}
