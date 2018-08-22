package hzyj.come.p2p.copy;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzyj.come.p2p.R;
import hzyj.come.p2p.app.utils.ToastUtil;
import hzyj.come.p2p.app.utils.Tools;

public class InputCodeActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.btn_ok)
    Button mBtnOk;
    @BindView(R.id.tv_send_code)
    TextView mTvSendCode;

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
        setContentView(R.layout.input_code);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mToolbarTitle.setText("填写短信验证码");
        mToolbar.setNavigationOnClickListener(v -> finish());
        mEtCode.addTextChangedListener(editclick);
    }


    @OnClick({ R.id.btn_ok,R.id.tv_send_code, R.id.tv_not_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:

                ToastUtil.showMessage("添加成功");
                break;
            case R.id.tv_send_code:
          
                mDownTimer.start();
                mTvSendCode.setClickable(false);
                break;
            case R.id.tv_not_code:
                break;
        }
    }

    private TextWatcher editclick = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }


        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mBtnOk.setEnabled(s.length() >= 6);
        }

        //一般我们都是在这个里面进行我们文本框的输入的判断，上面两个方法用到的很少
        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    
}
