package hzyj.come.p2p.copy.credit;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzyj.come.p2p.R;
import hzyj.come.p2p.app.utils.ToastUtil;
import hzyj.come.p2p.app.utils.Tools;
import hzyj.come.p2p.copy.BaseActivity;

public class FenqiInfoActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.tv_card_name)
    TextView mTvCardName;
    @BindView(R.id.et_card_id)
    EditText mEtCardId;
    @BindView(R.id.et_card_youxiaoqi)
    EditText mEtCardYouxiaoqi;
    @BindView(R.id.et_card_anquanma)
    EditText mEtCardAnquanma;
    @BindView(R.id.et_user_name)
    EditText mEtUserName;
    @BindView(R.id.et_user_shenfenzhegn)
    EditText mEtUserShenfenzhegn;
    @BindView(R.id.et_user_phone)
    EditText mEtUserPhone;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.tv_send_code)
    TextView mTvSendCode;
    @BindView(R.id.tv_tips)
    TextView mTvTips;
    @BindView(R.id.btn_ok)
    Button mBtnOk;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
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
        setContentView(R.layout.fenqi_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mToolbarTitle.setText("分期付款信息");
        mToolbar.setNavigationOnClickListener(v -> finish());
       
    }

    

    @OnClick({R.id.tv_send_code, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send_code:
                if (!Tools.isMobile(mEtUserPhone.getText().toString().trim())) {
                    ToastUtil.showMessage("请输入正确的手机号码");
                    return;
                }
                mDownTimer.start();
                mTvSendCode.setClickable(false);
                break;
            case R.id.btn_ok:
                break;
        }
    }
}
