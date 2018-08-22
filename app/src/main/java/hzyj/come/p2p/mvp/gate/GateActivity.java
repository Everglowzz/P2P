package hzyj.come.p2p.mvp.gate;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.jess.arms.utils.ArmsUtils;

import hzyj.come.p2p.R;
import hzyj.come.p2p.mvp.login.LoginActivity;
import hzyj.come.p2p.mvp.ui.activity.MainActivity;
import hzyj.come.p2p.mvp.ui.activity.MainActivity_MembersInjector;

public class GateActivity extends AppCompatActivity implements GateContract.view{

    private GatePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gate);
        ConstraintLayout rootView = findViewById(R.id.rootView);
        rootView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mPresenter.judge();
            }
        },2000);
        mPresenter = new GatePresenter(this);
        
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
