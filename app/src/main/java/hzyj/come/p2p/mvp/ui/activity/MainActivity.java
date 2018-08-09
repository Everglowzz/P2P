package hzyj.come.p2p.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hzyj.come.p2p.R;
import hzyj.come.p2p.app.utils.FragmentUtils;
import hzyj.come.p2p.di.component.DaggerMainComponent;
import hzyj.come.p2p.di.module.MainModule;
import hzyj.come.p2p.mvp.contract.MainContract;
import hzyj.come.p2p.mvp.presenter.MainPresenter;
import hzyj.come.p2p.mvp.ui.fragment.HomeFragment;
import hzyj.come.p2p.mvp.ui.fragment.MineFragment;
import hzyj.come.p2p.mvp.ui.fragment.ShopFragment;
import hzyj.come.p2p.mvp.ui.fragment.homefragment.MainHomeFragment;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static hzyj.come.p2p.app.EventBusTags.ACTIVITY_FRAGMENT_REPLACE;


public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_back)
    RelativeLayout toolbarBack;
    @BindView(R.id.main_frame)
    FrameLayout mMainFrame;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;
    private List<Integer> mTitles;   
    private List<Fragment> mFragments;
    private int mReplace = 1;

    private OnTabSelectListener mOnTabSelectListener = tabId -> {
        switch (tabId) {
            case R.id.tab_home:
                mReplace = 1;
                break;
            case R.id.tab_shop:
                mReplace = 0;
                break;
            case R.id.tab_mine:
                mReplace = 2;
                break;
        }
        mToolbarTitle.setText(mTitles.get(mReplace));
        FragmentUtils.hideAllShowFragment(mFragments.get(mReplace));
    };
    
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbarBack.setVisibility(View.GONE);
        if (mTitles == null) {
            mTitles = new ArrayList<>();
            mTitles.add(R.string.title_empty);
            mTitles.add(R.string.title_empty);
            mTitles.add(R.string.title_mine);
        }
        HomeFragment homeFragment;
        MineFragment mineFragment;
        MainHomeFragment mainHomeFragment;
        if (savedInstanceState == null) {
            homeFragment = HomeFragment.newInstance();
            mainHomeFragment = MainHomeFragment.newInstance();
            mineFragment = MineFragment.newInstance();
        }else{
            mReplace = savedInstanceState.getInt(ACTIVITY_FRAGMENT_REPLACE);
            android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
            homeFragment= (HomeFragment) FragmentUtils.findFragment(fm,HomeFragment.class);
            mainHomeFragment= (MainHomeFragment) FragmentUtils.findFragment(fm,MainHomeFragment.class);
            mineFragment= (MineFragment) FragmentUtils.findFragment(fm,MineFragment.class);

        }
        if (mFragments == null) {
            mFragments = new ArrayList<>();
            mFragments.add(mainHomeFragment);
            mFragments.add(homeFragment);
            mFragments.add(mineFragment);
        }
        FragmentUtils.addFragments(getSupportFragmentManager(), mFragments, R.id.main_frame, 0);
        mBottomBar.setOnTabSelectListener(mOnTabSelectListener);
        
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
