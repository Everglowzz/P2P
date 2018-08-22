package hzyj.come.p2p.copy.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

/**
 * 生成二维码的dialog
 */
public class PopImgDialog extends PopupWindow implements OnClickListener {
	private static final String TAG = "SelectPicPopupWindow";
	private Context mContext;
	private ImageView mMenuView;
	private TextView tv_1, tv_2, tv_3;

	public PopImgDialog(Activity context) {
		super(context);
		this.mContext = context;
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		mMenuView = new ImageView(context);
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getMatchParams();
		params.gravity = Gravity.CENTER;
		mMenuView.setLayoutParams(getMatchParams());
		mMenuView.setBackgroundColor(Color.WHITE);
		mMenuView.setOnClickListener(this);
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.MATCH_PARENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
	}

	public void TextView3Gone() {
		tv_3.setVisibility(View.GONE);
	}

	private LayoutParams getMatchParams() {
		// TODO Auto-generated method stub
		return new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		dismiss();
	}

	public void setBitmap(Bitmap bitmap) {
		// TODO Auto-generated method stub
		if (mMenuView != null) {
			mMenuView.setImageBitmap(bitmap);
		}
	}

}