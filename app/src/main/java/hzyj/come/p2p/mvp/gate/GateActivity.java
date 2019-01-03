package hzyj.come.p2p.mvp.gate;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;

import com.jess.arms.utils.ArmsUtils;

import hzyj.come.p2p.R;
import hzyj.come.p2p.app.https.CallBack;
import hzyj.come.p2p.copy.BaseActivity;
import hzyj.come.p2p.copy.WebActivity;
import hzyj.come.p2p.entity.Entity;
import hzyj.come.p2p.mvp.login.LoginActivity;
import hzyj.come.p2p.mvp.ui.activity.MainActivity;

public class GateActivity extends BaseActivity implements GateContract.view {

    private GatePresenter mPresenter;
    private static final String KEY_URL = "http://201888888888.com:8080/biz/getAppConfig?appid=2018830360jianzhi1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate);
        ConstraintLayout rootView = findViewById(R.id.rootView);
        rootView.postDelayed(new Runnable() {
            @Override
            public void run() {
 //               judgeH5();
                mPresenter.judge();
            }
        }, 2000);
        mPresenter = new GatePresenter(this);

    }

    public void judgeH5() {
        mGsonRequest.function(KEY_URL, null, new CallBack<Entity>() {
            @Override
            public void onResponse(Entity result) {
                if (result.isSuccess()) {
                    Entity.APPCONFIG config = result.getAppConfig();
                    String url = config.getUrl();
                    String del = config.getDel();
                    String isShow = config.getShowWeb();
                    if (isShow.equals("1")) {
                        Intent intent = new Intent();
                        intent.setClass(GateActivity.this, WebActivity.class);
                        intent.putExtra("Url", url);
                        intent.putExtra("showToast",!del.equals("1000"));
                        startActivity(intent);
                        finish();
                    } else {
                        mPresenter.judge();
                    }
                } else {
                    mPresenter.judge();
                }
            }

            @Override
            public void onFailure(String error) {
                mPresenter.judge();
            }
        });
    }

    @Override
    public void skipMain() {
        ArmsUtils.startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void skipLogin() {
        ArmsUtils.startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}
