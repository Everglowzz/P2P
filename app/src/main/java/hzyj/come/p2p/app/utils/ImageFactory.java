package hzyj.come.p2p.app.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Youga on 2015/10/30.
 */
public class ImageFactory {

    public static byte[] compressionImageSize(File file, int maxImageSize) {
        maxImageSize = maxImageSize * 1024;
        Bitmap bmpPic = BitmapFactory.decodeFile(file.getPath());
        if ((bmpPic.getWidth() >= 1024) && (bmpPic.getHeight() >= 1024)) {
            BitmapFactory.Options bmpOptions = new BitmapFactory.Options();
            bmpOptions.inSampleSize = 1;
            while ((bmpPic.getWidth() >= 1024) && (bmpPic.getHeight() >= 1024)) {
                bmpOptions.inSampleSize++;
                bmpPic = BitmapFactory.decodeFile(file.getPath(), bmpOptions);
            }
        }
        int compressQuality = 104;
        int streamLength = maxImageSize;
        ByteArrayOutputStream bmpStream = null;
        while (streamLength >= maxImageSize) {
            bmpStream = new ByteArrayOutputStream();
            compressQuality -= 5;
            bmpPic.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream);
            byte[] bmpPicByteArray = bmpStream.toByteArray();
            streamLength = bmpPicByteArray.length;
        }
        return bmpStream.toByteArray();
    }


    private static final String TAG = "ImageFactory";

    public static Bitmap drawTextToBitmap(Resources resources, File file, String describe) {
        float scale = resources.getDisplayMetrics().density;
        Bitmap bitmap = getBitmap(file.getAbsolutePath());
        bitmap = scaleWithWH(bitmap, 300 * scale, 300 * scale);
        android.graphics.Bitmap.Config bitmapConfig =
                bitmap.getConfig();
        if (bitmapConfig == null) {
            bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;
        }
        bitmap = bitmap.copy(bitmapConfig, true);
        Canvas canvas = new Canvas(bitmap);
        TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setTextSize((int) (18 * scale));
        paint.setDither(true); //获取跟清晰的图像采样
        paint.setFilterBitmap(true);//过滤一些
        //这里是自动换行的
        canvas.translate(5*scale,5*scale);
        StaticLayout layout = new StaticLayout(describe, paint, (int) (bitmap.getWidth()-10*scale),
                Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
        layout.draw(canvas);
        return bitmap;
    }

    public static Bitmap scaleWithWH(Bitmap src, double w, double h) {
        if (w == 0 || h == 0 || src == null) {
            return src;
        } else {
            // 记录src的宽高
            int width = src.getWidth();
            int height = src.getHeight();
            // 创建一个matrix容器
            Matrix matrix = new Matrix();
            // 计算缩放比例
            float scaleWidth = (float) (w / width);
            float scaleHeight = (float) (h / height);
            // 开始缩放
            matrix.postScale(scaleWidth, scaleHeight);
            // 创建缩放后的图片
            return Bitmap.createBitmap(src, 0, 0, width, height, matrix, true);
        }
    }

    public static Bitmap getBitmap(String imgPath) {
        // Get bitmap through image path
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = false;
        newOpts.inPurgeable = true;
        newOpts.inInputShareable = true;
        // Do not compress
        newOpts.inSampleSize = 1;
        newOpts.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(imgPath, newOpts);
    }

    public static File writeBitmapFile(File file, Bitmap bitmap) throws Exception {
        FileOutputStream bmpFile = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bmpFile);
        bmpFile.flush();
        bmpFile.close();
        return file;
    }
}
