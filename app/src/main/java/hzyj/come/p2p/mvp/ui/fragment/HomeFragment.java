package hzyj.come.p2p.mvp.ui.fragment;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.module.GlobalConfigModule;
import com.jess.arms.integration.cache.Cache;

import hzyj.come.p2p.R;
import hzyj.come.p2p.base.BaseFragment;

/**
 * Created by EverGlow on 2018/4/11 11:52
 */
public    class HomeFragment extends BaseFragment   {
    
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return  fragment;
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
}
