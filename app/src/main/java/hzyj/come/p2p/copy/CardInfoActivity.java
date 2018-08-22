package hzyj.come.p2p.copy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzyj.come.p2p.R;

public class CardInfoActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_code)
    EditText mEtCardName;
    @BindView(R.id.btn_ok)
    Button mBtnOk;
    @BindView(R.id.tv_card_name)
    TextView mTvCardName;
    @BindView(R.id.tv_card_weihao)
    TextView mTvCardWeihao;
    @BindView(R.id.tv_card_type)
    TextView mTvCardType;
    @BindView(R.id.et_shenfenzhegn)
    EditText mEtShenfenzhegn;
    @BindView(R.id.et_iphone)
    EditText mEtIphone;
    @BindView(R.id.iv_tip_phone)
    ImageView mIvTipPhone;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    private  boolean nameLegal,identityLegal,phoneNamberLegal;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_info);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mToolbarTitle.setText("填写银行卡信息");
        mToolbar.setNavigationOnClickListener(v -> finish());
        mEtCardName.addTextChangedListener(editclick);
        mEtShenfenzhegn.addTextChangedListener(editclick1);
        mEtIphone.addTextChangedListener(editclick2);
    }




    private TextWatcher editclick = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }


        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            nameLegal = s.length() >= 2;
            mBtnOk.setEnabled(nameLegal &&identityLegal&&phoneNamberLegal);

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
            identityLegal = s.length() >= 15;
            mBtnOk.setEnabled(nameLegal &&identityLegal&&phoneNamberLegal);

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
            phoneNamberLegal = s.length() >= 11;
            mBtnOk.setEnabled(nameLegal &&identityLegal&&phoneNamberLegal);

        }

        //一般我们都是在这个里面进行我们文本框的输入的判断，上面两个方法用到的很少
        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @OnClick({R.id.iv_tip_name, R.id.iv_tip_phone,R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_tip_name:
                break;
            case R.id.iv_tip_phone:
                break;
            case R.id.btn_ok:
                startActivity(new Intent(CardInfoActivity.this,InputCodeActivity.class));
                break;
        }
    }
}
