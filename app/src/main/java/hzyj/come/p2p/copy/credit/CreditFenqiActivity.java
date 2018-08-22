package hzyj.come.p2p.copy.credit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzyj.come.p2p.R;
import hzyj.come.p2p.copy.BaseActivity;
import hzyj.come.p2p.copy.CardInfoActivity;
import me.shihao.library.XRadioGroup;

public class CreditFenqiActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.btn_ok)
    Button mBtnOk;
    @BindView(R.id.et_money)
    EditText mEtMoney;
    @BindView(R.id.tv_bank)
    TextView mTvBank;
    @BindView(R.id.rb1)
    RadioButton mRb1;
    @BindView(R.id.rb2)
    RadioButton mRb2;
    @BindView(R.id.rb3)
    RadioButton mRb3;
    @BindView(R.id.rb4)
    RadioButton mRb4;
    @BindView(R.id.radio_group)
    XRadioGroup mRadioGroup;
    @BindView(R.id.scrollView)
    ScrollView mScrollView;
    @BindView(R.id.tv_tips)
    TextView mTvTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit_fenqi);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mToolbarTitle.setText("信用卡分期");
        mToolbar.setNavigationOnClickListener(v -> finish());
        mEtMoney.addTextChangedListener(editclick);

        mRadioGroup.setOnCheckedChangeListener(new XRadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(XRadioGroup group, int checkedId) {


                if (checkedId == mRb1.getId()) {

                    if (!TextUtils.isEmpty(mEtMoney.getText().toString().trim())) {
                        mBtnOk.setEnabled(true);
                        mTvTips.setVisibility(View.GONE);
                    }
                } else if (checkedId == mRb2.getId()||checkedId == mRb3.getId()||checkedId == mRb4.getId()) {
                    if (!TextUtils.isEmpty(mEtMoney.getText().toString().trim())) {
                        if (Integer.parseInt(mEtMoney.getText().toString().trim()) >= 600) {
                            mBtnOk.setEnabled(true);
                        } else {
                            mBtnOk.setEnabled(false);
                            mTvTips.setVisibility(View.VISIBLE);
                        }
                    }
                } 

            }
        });
    }


    private TextWatcher editclick = new TextWatcher() {


        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {


        }


        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (TextUtils.isEmpty(s) || mRadioGroup.getCheckedRadioButtonId() < 0) {
                mTvTips.setVisibility(View.GONE);
                mBtnOk.setEnabled(false);
                mRadioGroup.clearCheck();
            } else if (mRadioGroup.getCheckedRadioButtonId() == mRb1.getId()) {
                mBtnOk.setEnabled(true);
                mTvTips.setVisibility(View.GONE);
            } else if (Integer.parseInt(String.valueOf(s)) >= 600) {
                mTvTips.setVisibility(View.GONE);
                mBtnOk.setEnabled(true);
            } else {
                mTvTips.setVisibility(View.VISIBLE);
                mBtnOk.setEnabled(false);
            }
        }

        //一般我们都是在这个里面进行我们文本框的输入的判断，上面两个方法用到的很少
        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @OnClick({R.id.btn_ok, R.id.tv_bank})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_ok:
                startActivity(new Intent(CreditFenqiActivity.this, FenqiInfoActivity.class));
                break;

            case R.id.tv_bank:

                break;
        }
    }
}
