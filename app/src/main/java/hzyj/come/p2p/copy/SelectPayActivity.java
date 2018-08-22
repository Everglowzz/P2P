package hzyj.come.p2p.copy;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.zxing.WriterException;
import com.yzq.zxinglibrary.encode.CodeCreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzyj.come.p2p.R;
import hzyj.come.p2p.app.utils.ToastUtil;

public class SelectPayActivity extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.ll_top)
    LinearLayout mLlTop;
    @BindView(R.id.iv_qr)
    ImageView mIvQr;
    @BindView(R.id.iv1)
    ImageView mIv1;
    @BindView(R.id.button_weixin)
    RelativeLayout mButtonWeixin;
    @BindView(R.id.iv2)
    ImageView mIv2;
    @BindView(R.id.button_zhifubao)
    RelativeLayout mButtonZhifubao;
    @BindView(R.id.iv3)
    ImageView mIv3;
    @BindView(R.id.button_shuaka)
    RelativeLayout mButtonShuaka;
    @BindView(R.id.iv4)
    ImageView mIv4;
    @BindView(R.id.button_yizhifu)
    RelativeLayout mButtonYizhifu;
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    @BindView(R.id.btn_submit)
    Button mBtnSubmit;


    String[] PERMISSIONS = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};

    //检测是否有写的权限
    public boolean testingAuthority() {
        int permission = ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE");
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // 没有写的权限，去申请写的权限，会弹出对话框
            ActivityCompat.requestPermissions(this, PERMISSIONS, 1);
            return false;
        } else {
            return true;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_select);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mToolbarTitle.setText("生成二维码");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mIvQr.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                SaveBitmapFromView(mIvQr);
                return true;//true代表消费了该事件，不在往下传递，如果返回false，会触发onclicklistener时间
            }
        });
    }

    @OnClick({R.id.button_weixin, R.id.button_zhifubao, R.id.button_shuaka, R.id.button_yizhifu, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.button_weixin:
                hide();
                mIv1.setVisibility(View.VISIBLE);
                break;
            case R.id.button_zhifubao:
                hide();
                mIv2.setVisibility(View.VISIBLE);
                break;
            case R.id.button_shuaka:
                hide();
                mIv3.setVisibility(View.VISIBLE);
                break;
            case R.id.button_yizhifu:
                hide();
                mIv4.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_submit:
                mBtnSubmit.setClickable(false);
                if (mBtnSubmit.getText().toString().equals("保存二维码") && testingAuthority()) {

                    SaveBitmapFromView(mIvQr);
                } else {
                    createQR("这是一个测试的二维码！！！");
                }

                break;
        }
    }

    public void hide() {
        mIv1.setVisibility(View.GONE);
        mIv2.setVisibility(View.GONE);
        mIv3.setVisibility(View.GONE);
        mIv4.setVisibility(View.GONE);

    }

    //生成二维码
    public void createQR(String contentEtString) {
        Bitmap bitmap = null;

        try {
            bitmap = CodeCreator.createQRCode(contentEtString, 400, 400, null);

        } catch (WriterException e) {
            e.printStackTrace();
        }
        if (bitmap != null) {
            mIvQr.setVisibility(View.VISIBLE);
            mLlContent.setVisibility(View.GONE);
            mIvQr.setImageBitmap(bitmap);
            mBtnSubmit.setText("保存二维码");
        }
        mBtnSubmit.setClickable(true);
    }

    //保存二维码
    public void SaveBitmapFromView(View view) {
        view.setDrawingCacheEnabled(true);
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
//        view.setDrawingCacheEnabled(false);

//        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
//        view.draw(new Canvas(bitmap));


        saveBitmap(bitmap, new Date() + ".JPEG");

    }


    public void saveBitmap(Bitmap bitmap, String bitName) {
        String fileName;
        File file;
        if (Build.BRAND.equals("Xiaomi")) { // 小米手机 
            fileName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/Camera/" + bitName;
        } else {  // Meizu 、Oppo
            fileName = Environment.getExternalStorageDirectory().getPath() + "/DCIM/" + bitName;
        }
        file = new File(fileName);

        if (file.exists()) {
            file.delete();
        }
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            // 格式为 JPEG，照相机拍出的图片为JPEG格式的，PNG格式的不能显示在相册中
            if (bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)) {
                out.flush();
                out.close();
// 插入图库   
                MediaStore.Images.Media.insertImage(this.getContentResolver(), file.getAbsolutePath(), bitName, null);
                ToastUtil.showMessage("保存成功");
            }
            mBtnSubmit.setClickable(true);
        } catch (FileNotFoundException e) {
            ToastUtil.showMessage("保存失败");
            mBtnSubmit.setClickable(true);
            e.printStackTrace();
        } catch (IOException e) {
            ToastUtil.showMessage("保存失败");
            mBtnSubmit.setClickable(true);
            e.printStackTrace();

        }
        // 发送广播，通知刷新图库的显示
        this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + fileName)));

    }

}
