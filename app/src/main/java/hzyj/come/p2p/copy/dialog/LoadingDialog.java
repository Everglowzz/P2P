package hzyj.come.p2p.copy.dialog;


import android.R.color;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import hzyj.come.p2p.R;


/**
 * dialog 建造者模式
 * 
 * @author Administrator
 * 
 */
public class LoadingDialog extends Dialog {

	public LoadingDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public static class Builder {
		private Context context;

		public Builder(Context context) {
			// TODO Auto-generated constructor stub
			this.context = context;
		}

		public LoadingDialog creates() {
			// TODO Auto-generated method stub
			LoadingDialog dialog = new LoadingDialog(context);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.getWindow().setBackgroundDrawableResource(color.transparent);
			dialog.show();
			LinearLayout layout = new LinearLayout(context);
			// LinearLayout.LayoutParams params = getParams();
			layout.setLayoutParams(getParams());
			layout.setOrientation(LinearLayout.VERTICAL);
			layout.setBackgroundResource(R.drawable.load_dialog_bg);
			ProgressBar pBar = new ProgressBar(context);
			pBar.setPadding(0, 20, 0, 0);
			// ImageView view = new ImageView(app);
			// params.gravity = Gravity.CENTER;
			// params.setMargins(80, 80, 80, 80);
			// params.height = 180;
			// params.width =180;
			// setRotateAnimation(view);
			layout.addView(pBar);
			TextView tv = new TextView(context);
			tv.setText("数据加载中...");
			tv.setPadding(20, 20, 20, 20);
			tv.setTextColor(context.getResources().getColor(R.color.white));
			layout.addView(tv);
			dialog.setContentView(layout);
			// 外界因素无法影响其消失
			// dialog.setCancelable(true);
			// 点击屏幕不会消失 可以返回可以退出dialog
			dialog.setCanceledOnTouchOutside(false);
			dialog.dismiss();
			return dialog;
		}

		private LinearLayout.LayoutParams getParams() {
			// TODO Auto-generated method stub
			return new LinearLayout.LayoutParams(50, 50);
		}

		/**
		 * 旋转动画
		 *
		 * @param view
		 */
		private void setRotateAnimation(View view) {
			AnimationSet animationSet = new AnimationSet(false);
			// 参数1：从哪个旋转角度开始
			// 参数2：转到什么角度
			// 后4个参数用于设置围绕着旋转的圆的圆心在哪里
			// 参数3：确定x轴坐标的类型，有ABSOLUT绝对坐标、RELATIVE_TO_SELF相对于自身坐标、RELATIVE_TO_PARENT相对于父控件的坐标
			// 参数4：x轴的值，0.5f表明是以自身这个控件的一半长度为x轴
			// 参数5：确定y轴坐标的类型
			// 参数6：y轴的值，0.5f表明是以自身这个控件的一半长度为x轴
			RotateAnimation rotateAnimation = new RotateAnimation(0, 358,
					Animation.RELATIVE_TO_SELF, 0.5f,
					Animation.RELATIVE_TO_SELF, 0.5f);
			rotateAnimation.setDuration(1000);
			rotateAnimation.setRepeatCount(-1);// 设置重复次数 -1 代表无限循环
			rotateAnimation.setFillAfter(true);// 动画执行完后是否停留在执行完的状态
			rotateAnimation.setRepeatMode(Animation.RESTART);// 设置循环模式
			rotateAnimation.setInterpolator(new LinearInterpolator()); // 动画运行模式
																		// 加速 匀速
																		// 等等...
			animationSet.addAnimation(rotateAnimation);

			view.startAnimation(animationSet);
		}
	}
}
