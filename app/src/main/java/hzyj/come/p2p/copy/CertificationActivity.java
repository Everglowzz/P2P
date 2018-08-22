package hzyj.come.p2p.copy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.IDCardParams;
import com.baidu.ocr.sdk.model.IDCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzyj.come.p2p.R;
import hzyj.come.p2p.app.utils.FileUtil;
import hzyj.come.p2p.app.utils.ToastUtil;
import hzyj.come.p2p.app.utils.Tools;

public class CertificationActivity extends BaseActivity {
    private static final int REQUEST_CODE_CAMERA = 102;

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_name)
    EditText mEtCardName;
    @BindView(R.id.btn_ok)
    Button mBtnOk;
    @BindView(R.id.et_shenfenzhegn)
    EditText mEtShenfenzhegn;
    @BindView(R.id.shoot)
    ImageView mShoot;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.tv_send_code)
    TextView mTvSendCode;
    private boolean nameLegal, identityLegal, phoneLegal, codeLegal;

    private CountDownTimer mDownTimer = new CountDownTimer(60000,1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            mTvSendCode.setText("("+millisUntilFinished/1000+"S)重新获取");
        }

        @Override
        public void onFinish() {
            mTvSendCode.setText("重新获取");
            mTvSendCode.setClickable(true);
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.certification);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mToolbarTitle.setText("实名认证");
        mToolbar.setNavigationOnClickListener(v -> finish());
        mEtCardName.addTextChangedListener(editclick);
        mEtPhone.addTextChangedListener(editclick1);
        mEtShenfenzhegn.addTextChangedListener(editclick2);
        mEtCode.addTextChangedListener(editclick3);
        
    }


    @OnClick({R.id.shoot, R.id.btn_ok,R.id.tv_send_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shoot:
                scanID();
                
                break;
            case R.id.btn_ok:

                ToastUtil.showMessage("认证成功");
                break;
            case R.id.tv_send_code:
                if (!Tools.isMobile(mEtPhone.getText().toString().trim())) {
                    ToastUtil.showMessage("请输入正确的手机号码");
                    return;
                }
                mDownTimer.start();
                mTvSendCode.setClickable(false);
                break;
        }
    }

    public void scanID() {
        Intent intent = new Intent(CertificationActivity.this, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(getApplication()).getAbsolutePath());
//        intent.putExtra(CameraActivity.KEY_NATIVE_ENABLE,
//                true);
//        // KEY_NATIVE_MANUAL设置了之后CameraActivity中不再自动初始化和释放模型
//        // 请手动使用CameraNativeHelper初始化和释放模型
//        // 推荐这样做，可以避免一些activity切换导致的不必要的异常
//        intent.putExtra(CameraActivity.KEY_NATIVE_MANUAL,
//                true);
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
        startActivityForResult(intent, REQUEST_CODE_CAMERA);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String contentType = data.getStringExtra(CameraActivity.KEY_CONTENT_TYPE);
                String filePath = FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath();
                if (!TextUtils.isEmpty(contentType)) {
                    if (CameraActivity.CONTENT_TYPE_ID_CARD_FRONT.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_FRONT, filePath);
                    } else if (CameraActivity.CONTENT_TYPE_ID_CARD_BACK.equals(contentType)) {
                        recIDCard(IDCardParams.ID_CARD_SIDE_BACK, filePath);
                    }
                }
            }
        }
    }

    private void recIDCard(String idCardSide, String filePath) {
        IDCardParams param = new IDCardParams();
        param.setImageFile(new File(filePath));
        // 设置身份证正反面
        param.setIdCardSide(idCardSide);
        // 设置方向检测
        param.setDetectDirection(true);
        // 设置图像参数压缩质量0-100, 越大图像质量越好但是请求时间越长。 不设置则默认值为20
        param.setImageQuality(20);

        OCR.getInstance(this).recognizeIDCard(param, new OnResultListener<IDCardResult>() {
            @Override
            public void onResult(IDCardResult result) {
                if (result != null&&result.getIdNumber() != null&&result.getIdNumber().getWords() != null) {
                    mEtShenfenzhegn.setText(result.getIdNumber().getWords());
                }
            }

            @Override
            public void onError(OCRError error) {
                ToastUtil.showMessage( error.getMessage());
                Log.d(TAG, error.getMessage());
            }
        });
    }

    private TextWatcher editclick = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }


        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            nameLegal = (s.length() >= 1);
            mBtnOk.setEnabled(nameLegal && identityLegal&&phoneLegal&&codeLegal);
        }

        //一般我们都是在这个里面进行我们文本框的输入的判断，上面两个方法用到的很少
        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher editclick1 = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }


        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            phoneLegal = (s.length() >= 11);
            mBtnOk.setEnabled(nameLegal && identityLegal&&phoneLegal&&codeLegal);
        }

        //一般我们都是在这个里面进行我们文本框的输入的判断，上面两个方法用到的很少
        @Override
        public void afterTextChanged(Editable s) {
        
        }
    };
    private TextWatcher editclick2 = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }


        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            identityLegal = (s.length() >= 16);
            mBtnOk.setEnabled(nameLegal && identityLegal&&phoneLegal&&codeLegal);
        }

        //一般我们都是在这个里面进行我们文本框的输入的判断，上面两个方法用到的很少
        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher editclick3 = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }


        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            codeLegal = (s.length() >= 6);
            mBtnOk.setEnabled(nameLegal && identityLegal&&phoneLegal&&codeLegal);
        }

        //一般我们都是在这个里面进行我们文本框的输入的判断，上面两个方法用到的很少
        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
}
