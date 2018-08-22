package hzyj.come.p2p.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hzyj.come.p2p.R;
import hzyj.come.p2p.base.BaseFragment;
import hzyj.come.p2p.copy.SelectPayActivity;
import hzyj.come.p2p.copy.credit.CreditFenqiActivity;
import hzyj.come.p2p.copy.credit.KuaijiePayActivity;

/**
 * Created by EverGlow on 2018/4/11 11:52
 */
public class HomeFragment extends BaseFragment {

    Unbinder unbinder;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
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

    @OnClick({R.id.tv_shaoma, R.id.tv_kuaijie, R.id.tv_daihuan, R.id.fenqi, R.id.tv_wangyin, R.id.tv_shengqing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_shaoma:
                startActivity(new Intent(getContext(),SelectPayActivity.class));
                break;
            case R.id.tv_kuaijie:
                startActivity(new Intent(getContext(),KuaijiePayActivity.class));
                break;
            case R.id.tv_daihuan:
                break;
            case R.id.fenqi:
                startActivity(new Intent(getContext(),CreditFenqiActivity.class));
                break;
            case R.id.tv_wangyin:
                break;
            case R.id.tv_shengqing:
                break;
        }
    }
}
