package hzyj.come.p2p.copy;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzyj.come.p2p.R;

public class SettingActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mToolbarTitle.setText("设置");
        mToolbar.setNavigationOnClickListener(v -> finish());

    }


    @OnClick({R.id.item_entrench, R.id.item_check_update, R.id.item_us, R.id.btn_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.item_entrench:
                break;
            case R.id.item_check_update:
                break;
            case R.id.item_us:
                break;
            case R.id.btn_logout:
                break;
        }
    }
}
