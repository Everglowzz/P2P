package hzyj.come.p2p.copy.address;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzyj.come.p2p.R;
import hzyj.come.p2p.app.https.CallBack;
import hzyj.come.p2p.app.https.config.NetWorkConstant;
import hzyj.come.p2p.app.utils.ToastUtil;
import hzyj.come.p2p.app.utils.Tools;
import hzyj.come.p2p.copy.BaseActivity;
import hzyj.come.p2p.entity.BaseBean;
import hzyj.come.p2p.entity.EntityBase;
import hzyj.come.p2p.entity.appShippingAddress;
import hzyj.come.p2p.global.Constants;

public class AcAddAddressActivity extends BaseActivity {


    CityPickerView mPicker = new CityPickerView();
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.edt_true_name)
    EditText mEdtTrueName;
    @BindView(R.id.edt_mob_phone)
    EditText mEdtMobPhone;
    @BindView(R.id.tv_area_info)
    TextView mTvAreaInfo;
    @BindView(R.id.rl_select_city)
    RelativeLayout mRlSelectCity;
    @BindView(R.id.tv_address)
    EditText mTvAddress;
    @BindView(R.id.tv_selected)
    ImageView mTvSelected;
    @BindView(R.id.rl_set_default)
    RelativeLayout mRlSetDefault;
    @BindView(R.id.tv_success)
    TextView mTvSuccess;
    private String defaultprovince = "陕西省";
    private String defaultcity = "西安市";
    private String defaultdistrict = "长安区";
    private Bundle mExtras;
    private String URL = NetWorkConstant.app_add_address;
    private appShippingAddress mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_add_address);
        mPicker.init(this);
        ButterKnife.bind(this);
        initData();
        initCitys();
    }

    private void initData() {
        mExtras = getIntent().getExtras();
        if (mExtras != null) {
            mData = (appShippingAddress) mExtras.getParcelable(Constants.DATA);

            mEdtTrueName.setText(mData.getAppConsignee());
            mEdtMobPhone.setText(mData.getAppPhone());
            mTvAddress.setText(mData.getAppAddressInfo());
            mTvSelected.setSelected(mData.getAppIsDefault() ==1);
            String[] split = mData.getAppAddress().split("-");
            if (split.length>=3) {
                defaultprovince = split[0];
                defaultcity = split[1];
                defaultdistrict = split[2];
            }
         
            mTvAreaInfo.setText(defaultprovince + "-" + defaultcity + "-" + defaultdistrict);
            mToolbarTitle.setText("编辑地址");
            URL = NetWorkConstant.app_edit_address;
        } else {
            mToolbarTitle.setText("新建地址");
        }
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRlSetDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTvSelected.setSelected(!mTvSelected.isSelected());
            }
        });
        mRlSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hideWindow(mRlSelectCity);
                mPicker.showCityPicker();
            }
        });
    }

    private void initCitys() {
        CityConfig cityConfig = new CityConfig.Builder().title("选择城市")//标题
                .titleTextSize(18)//标题文字大小
                .titleTextColor("#5c8af5")//标题文字颜  色
                .titleBackgroundColor("#E9E9E9")//标题栏背景色
                .confirTextColor("#5c8af5")//确认按钮文字颜色
                .confirmText("确定")//确认按钮文字
                .confirmTextSize(16)//确认按钮文字大小
                .cancelTextColor("#5c8af5")//取消按钮文字颜色
                .cancelText("取消")//取消按钮文字
                .cancelTextSize(16)//取消按钮文字大小
                .showBackground(true)//是否显示半透明背景
                .visibleItemsCount(7)//显示item的数量
                .province(defaultprovince)//默认显示的省份
                .city(defaultcity)//默认显示省份下面的城市
                .district(defaultdistrict)//默认显示省市下面的区县数据
                .provinceCyclic(true)//省份滚轮是否可以循环滚动
                .cityCyclic(true)//城市滚轮是否可以循环滚动
                .districtCyclic(true)//区县滚轮是否循环滚动
                .drawShadows(false)//滚轮不显示模糊效果
                .setLineColor("#5c8af5")//中间横线的颜色
                .setLineHeigh(2)//中间横线的高度
                .setShowGAT(true)//是否显示港澳台数据，默认不显示
                .build();

//设置自定义的属性配置
        mPicker.setConfig(cityConfig);
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {

                //省份
                if (province != null) {
                    defaultprovince = province.getName();
                }

                //城市
                if (city != null) {
                    defaultcity = city.getName();
                }

                //地区
                if (district != null) {
                    defaultdistrict = district.getName();

                }
                mTvAreaInfo.setText(defaultprovince + "-" + defaultcity + "-" + defaultdistrict);
            }

            @Override
            public void onCancel() {
            }
        });

    }

    public boolean textIsEmpty() {

        if (TextUtils.isEmpty(mEdtTrueName.getText().toString().trim())) {
            ToastUtil.showMessage("请填写收货人");
            return true;
        }
        if (TextUtils.isEmpty(mEdtMobPhone.getText().toString().trim())) {
            ToastUtil.showMessage("请填写手机号");
            return true;
        }
        if (!Tools.isMobile(mEdtMobPhone.getText().toString().trim())) {
            ToastUtil.showMessage("请填写正确的手机号");
            return true;
        }
        if (TextUtils.isEmpty(mTvAreaInfo.getText().toString().trim())) {
            ToastUtil.showMessage("请选择省市区");
            return true;
        }
        if (TextUtils.isEmpty(mTvAddress.getText().toString().trim())) {
            ToastUtil.showMessage("请填写详细地址");
            return true;
        }

        return false;
    }

    public void submitData() {

        HashMap<String, String> params = new HashMap<String, String>();
        if (URL.equals(NetWorkConstant.app_edit_address)) {
            params.put("appAddressId", mData.getAppAddressId());
        }
        params.put("appConsignee", mEdtTrueName.getText().toString().trim());
        params.put("appPhone", mEdtMobPhone.getText().toString().trim());
        params.put("appAddress", defaultprovince + "-" + defaultcity + "-" + defaultdistrict);
        params.put("appAddressInfo", mTvAddress.getText().toString().trim());
        params.put("appIsDefault", mTvSelected.isSelected() ? "1" : "0");
        mGsonRequest.function(URL, params, EntityBase.class, new CallBack<EntityBase>() {
            @Override
            public void onResponse(EntityBase userBean) {
                if (userBean.getResult() == Constants.SUCCESSCODE) {
                    finish();
                }
                ToastUtil.showMessage(userBean.getMsg());
            }

            @Override
            public void onFailure(String error) {
                ToastUtil.showMessage(error);
            }
        });
    }


    @OnClick(R.id.tv_success)
    public void onViewClicked() {
        if (!textIsEmpty()) {
            submitData();
        }


    }
}
