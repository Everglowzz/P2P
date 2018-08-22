package hzyj.come.p2p.copy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.BankCardParams;
import com.baidu.ocr.sdk.model.BankCardResult;
import com.baidu.ocr.ui.camera.CameraActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzyj.come.p2p.R;
import hzyj.come.p2p.app.utils.FileUtil;

public class AddCardActivity extends BaseActivity {

    private static final int REQUEST_CODE_BANKCARD = 111;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_code)
    EditText mEtCardId;
    @BindView(R.id.btn_ok)
    Button mBtnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mToolbarTitle.setText("添加信用卡");
        mToolbar.setNavigationOnClickListener(v -> finish());
        mEtCardId.addTextChangedListener(editclick);
    }


    @OnClick({R.id.shoot, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shoot:
                scanCard();
                break;
            case R.id.btn_ok:
                startActivity(new Intent(AddCardActivity.this,CardInfoActivity.class));
                break;
        }
    }

    private TextWatcher editclick = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }


        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mBtnOk.setEnabled(s.length() >= 16);
        }

        //一般我们都是在这个里面进行我们文本框的输入的判断，上面两个方法用到的很少
        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    
    public void scanCard(){
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                FileUtil.getSaveFile(getApplication()).getAbsolutePath());
        intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                CameraActivity.CONTENT_TYPE_BANK_CARD);
        startActivityForResult(intent, REQUEST_CODE_BANKCARD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_BANKCARD && resultCode == Activity.RESULT_OK) {
            BankCardParams param = new BankCardParams();
            param.setImageFile(new File( FileUtil.getSaveFile(getApplicationContext()).getAbsolutePath()));
            OCR.getInstance(this).recognizeBankCard(param, new OnResultListener<BankCardResult>() {
                @Override
                public void onResult(BankCardResult result) {
                    String res = String.format("卡号：%s\n类型：%s\n发卡行：%s",
                            result.getBankCardNumber(),
                            result.getBankCardType().name(),
                            result.getBankName());
                    Log.d(TAG, res);
                    if (!TextUtils.isEmpty( result.getBankCardNumber())) {
                        mEtCardId.setText(result.getBankCardNumber());
                    }
                   
                }

                @Override
                public void onError(OCRError error) {
                 
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
      
    }
}
