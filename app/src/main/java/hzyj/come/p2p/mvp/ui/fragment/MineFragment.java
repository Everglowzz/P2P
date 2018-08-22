package hzyj.come.p2p.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.j256.ormlite.logger.Log;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import hzyj.come.p2p.R;
import hzyj.come.p2p.app.MyApplication;
import hzyj.come.p2p.app.https.config.CommonPreferences;
import hzyj.come.p2p.base.BaseFragment;
import hzyj.come.p2p.copy.AcUserInfo;
import hzyj.come.p2p.copy.CardManngeActivity;
import hzyj.come.p2p.copy.CertificationActivity;
import hzyj.come.p2p.copy.SettingActivity;
import hzyj.come.p2p.copy.dialog.AcAddressList;
import hzyj.come.p2p.entity.EntitiyUser;
import hzyj.come.p2p.mvp.ui.widget.GroupViewItem;

/**
 * Created by EverGlow on 2018/4/11 11:52
 */
public class MineFragment extends BaseFragment {


    @BindView(R.id.nick_name)
    TextView mNickName;
    @BindView(R.id.tv_money_balance)
    TextView mTvMoneyBalance;
    @BindView(R.id.tv_integral)
    TextView mTvIntegral;
    @BindView(R.id.profile_image)
    CircleImageView mProfileImage;
    @BindView(R.id.tv_recharge)
    TextView mTvRecharge;
    @BindView(R.id.tv_withdraw)
    TextView mTvWithdraw;
    @BindView(R.id.item_card)
    GroupViewItem mItemCard;
    @BindView(R.id.item_truth)
    GroupViewItem mItemTruth;
    @BindView(R.id.item_order)
    GroupViewItem mItemOrder;
    @BindView(R.id.item_shopping)
    GroupViewItem mItemShopping;
    @BindView(R.id.item_address)
    GroupViewItem mItemAddress;
    @BindView(R.id.item_setting)
    GroupViewItem mItemSetting;
    Unbinder unbinder;

    public static MineFragment newInstance() {
        MineFragment fragment = new MineFragment();
        return fragment;
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void refresh() {
        CommonPreferences preferences = new CommonPreferences(MyApplication.APP);
        EntitiyUser user = preferences.getModel(EntitiyUser.class);
        android.util.Log.d(TAG, "refresh: ");
        if (user != null) {
            mNickName.setText(user.getAppNickname());
            try {
                Glide.with(this).load(user.getImgURL()).into(mProfileImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick({R.id.profile_image, R.id.item_card, R.id.item_truth, R.id.item_order, R.id.item_shopping, R.id.item_address, R.id.item_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.profile_image:
                startActivity(new Intent(getActivity(), AcUserInfo.class));
                break;
            case R.id.item_card:
                startActivity(new Intent(getActivity(), CardManngeActivity.class));
                break;
            case R.id.item_truth:
                startActivity(new Intent(getActivity(), CertificationActivity.class));
                break;
            case R.id.item_order:
                break;
            case R.id.item_shopping:
                break;
            case R.id.item_address:
                startActivity(new Intent(getActivity(), AcAddressList.class));
                break;

            case R.id.item_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
        }
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            refresh();
        }
    }
}
