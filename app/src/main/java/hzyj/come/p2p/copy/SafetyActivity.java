package hzyj.come.p2p.copy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import hzyj.come.p2p.R;
import hzyj.come.p2p.copy.dialog.CustomDialog;
import hzyj.come.p2p.copy.dialog.FingerprintDialog;
import hzyj.come.p2p.mvp.ui.widget.GroupViewItem;

public class SafetyActivity extends BaseActivity {
    private static final int GESTURE_LOCK = 0X999;

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.item_shoushi)
    GroupViewItem mItemShoushi;
    @BindView(R.id.item_zhiwen)
    GroupViewItem mItemZhiwen;
    @BindView(R.id.item_renlian)
    GroupViewItem mItemRenlian;
    private boolean IS_GESTURE_LOCK,IS_FINGERPRINT_LOCK,IS_FACE_DETECTION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safety);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mToolbarTitle.setText("安全防护");
        mToolbar.setNavigationOnClickListener(v -> finish());
        mItemShoushi.setRightImage(getResources().getDrawable(IS_GESTURE_LOCK?R.mipmap.turn_off:R.mipmap.turn_on));
        mItemZhiwen.setRightImage(getResources().getDrawable(IS_FINGERPRINT_LOCK?R.mipmap.turn_off:R.mipmap.turn_on));
        mItemRenlian.setRightImage(getResources().getDrawable(IS_FACE_DETECTION?R.mipmap.turn_off:R.mipmap.turn_on));
        mItemShoushi.setRightImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SafetyActivity.this, GestureLockActivity.class);
                intent.putExtra(GestureLockActivity.LOCK_MODE, IS_GESTURE_LOCK ? 1 : 0);
                startActivityForResult(intent, GESTURE_LOCK);

            }
        });
        mItemZhiwen.setRightImageClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FingerprintDialog dialog = new FingerprintDialog(SafetyActivity.this);
                dialog.show();
            }
        });
    }
    

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       if(resultCode ==RESULT_OK) switch (requestCode) {
            case GESTURE_LOCK:
                int mode = data.getIntExtra(GestureLockActivity.LOCK_MODE, -1);
                if (mode == 0) {
                    mItemShoushi.setRightImage(getResources().getDrawable(R.mipmap.turn_off));
                    IS_GESTURE_LOCK = true;
                } else if (mode == 1) {
                    mItemShoushi.setRightImage(getResources().getDrawable(R.mipmap.turn_on));
                    IS_GESTURE_LOCK = false;
                }

                break;

        }
    }
}
