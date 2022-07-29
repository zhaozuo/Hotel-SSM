package cn.sdut.util;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.util.Arrays;

/**
 * 1. 灰度化（减小图片大小）
 * 2. 人脸识别
 * 3. 人脸切割
 * 4. 规一化(人脸直方图)
 * 5. 直方图相似度匹配
 *
 * @Description: 比较两张图片人脸的匹配度
 */
public class FaceCompare {

    // 初始化人脸探测器
    static CascadeClassifier faceDetector;

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        faceDetector = new CascadeClassifier("E:/IdeaProjects/HotelSSM/WebContent/resources/haarcascade_frontalface_alt.xml");
    }

    // 灰度化人脸
    public static Mat conv_Mat(String img) {
        Mat image0 = Imgcodecs.imread(img);

        Mat image1 = new Mat();
        // 灰度化
        // 将图像从一种颜色空间转换为另一种颜色空间
        Imgproc.cvtColor(image0, image1, Imgproc.COLOR_BGR2GRAY);
        // 探测人脸
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image1, faceDetections);
        // rect中人脸图片的范围
        for (Rect rect : faceDetections.toArray()) {
            Mat face = new Mat(image1, rect);
            return face;
        }
        return null;
    }

    /**
     * 比对图片，必须是绝对路径
     * @param img_1 图片1
     * @param img_2 图片2
     * @return 相似度
     */
    public static double compare_image(String img_1, String img_2) {
        Mat mat_1 = conv_Mat(img_1);
        Mat mat_2 = conv_Mat(img_2);
        Mat hist_1 = new Mat();
        Mat hist_2 = new Mat();

        //颜色范围
        MatOfFloat ranges = new MatOfFloat(0f, 256f);
        //直方图大小， 越大匹配越精确 (越慢)
        MatOfInt histSize = new MatOfInt(10000);

        Imgproc.calcHist(Arrays.asList(mat_1), new MatOfInt(0), new Mat(), hist_1, histSize, ranges);
        Imgproc.calcHist(Arrays.asList(mat_2), new MatOfInt(0), new Mat(), hist_2, histSize, ranges);

        // CORREL 相关系数
        double res = Imgproc.compareHist(hist_1, hist_2, Imgproc.CV_COMP_CORREL);
        return res;
    }
}

