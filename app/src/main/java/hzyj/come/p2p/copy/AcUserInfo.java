package hzyj.come.p2p.copy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.j256.ormlite.logger.Log;
import com.sxjs.common.util.NetworkUtil;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hzyj.come.p2p.R;
import hzyj.come.p2p.app.MyApplication;
import hzyj.come.p2p.app.https.CallBack;
import hzyj.come.p2p.app.https.config.CommonPreferences;
import hzyj.come.p2p.app.https.config.LogInterceptor;
import hzyj.come.p2p.app.https.config.NetWorkConstant;
import hzyj.come.p2p.app.utils.ImageFactory;
import hzyj.come.p2p.app.utils.ToastUtil;
import hzyj.come.p2p.copy.dialog.CustomOnClick;
import hzyj.come.p2p.copy.dialog.LiveSelectDialog;
import hzyj.come.p2p.entity.EntitiyUser;
import hzyj.come.p2p.entity.EntityBase;
import hzyj.come.p2p.global.Constants;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AcUserInfo extends BaseActivity {
    private static final int REQUEST_CODE = 377;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.information_image)
    CircleImageView mInformationImage;
    @BindView(R.id.ll_image)
    LinearLayout mLlImage;
    @BindView(R.id.information_nickname)
    TextView mInformationNickname;
    @BindView(R.id.ll_nickname)
    LinearLayout mLlNickname;
    @BindView(R.id.information_sex)
    TextView mInformationSex;
    @BindView(R.id.ll_sex)
    LinearLayout mLlSex;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.ll_phone)
    LinearLayout mLlPhone;
    private LiveSelectDialog liveSelectDialog;
    private String[] sexlist = new String[]{
        "保密","男","女"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac_user_info);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {

        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });
        mToolbarTitle.setText("个人资料");
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        liveSelectDialog = new LiveSelectDialog(this, null, 0, new CustomOnClick() {
            @Override
            public void click(View v, int date) {
                CommonPreferences preferences = new CommonPreferences(MyApplication.APP);
                EntitiyUser user = preferences.getModel(EntitiyUser.class);
                if (user.getAppSex() != date) {
                    changeGender(date);
                }

            }
        });
        liveSelectDialog.setTitle("请选择性别");
    }

    public void refresh() {
        CommonPreferences preferences = new CommonPreferences(MyApplication.APP);
        EntitiyUser user = preferences.getModel(EntitiyUser.class);
        android.util.Log.d(TAG, "refresh: ");
        if (user != null) {
            mInformationSex.setText(sexlist[user.getAppSex()]);
            mInformationNickname.setText(user.getAppNickname());
            try {
                Glide.with(this).load(user.getImgURL()).into(mInformationImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    @OnClick({R.id.ll_image, R.id.ll_nickname, R.id.ll_sex})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_image:
                ISListConfig config = new ISListConfig.Builder()
                        // 是否多选
                        .multiSelect(false).btnText("确定")
                        // 确定按钮背景色
                        //.btnBgColor(Color.parseColor(""))
                        // 确定按钮文字颜色
                        .btnTextColor(Color.WHITE)
                        // 使用沉浸式状态栏
                        .statusBarColor(R.color.colorPrimary)
                        // 返回图标ResId
                        .backResId(R.mipmap.back_write).title("选择图片").titleColor(Color.WHITE).titleBgColor(R.color.colorPrimary).needCrop(true).cropSize(1, 1, 200, 200)
                        // 第一个是否显示相机
                        .needCamera(true)
                        // 最大选择图片数量
                        .maxNum(1).build();
                ISNav.getInstance().toListActivity(this, config, REQUEST_CODE);

                break;
            case R.id.ll_nickname:

                startActivity(new Intent(this, AcChangenickName.class));
                break;

            case R.id.ll_sex:
                liveSelectDialog.show();
                //  mGenderDialog.show();
                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            for (String path : pathList) {

                uploadFile(path);
            }
        }
    }

    private void uploadFile(final String path) {
        HashMap<String, String> params = new HashMap<>();
        CommonPreferences preferences = new CommonPreferences(MyApplication.APP);
        EntitiyUser user = preferences.getModel(EntitiyUser.class);
        if (user != null) {
            params.put(NetWorkConstant.appUserId, user.getAppUserId());
        }
      

        OkHttpClient client = new OkHttpClient();
// form 表单形式上传
        MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
        if (path != null) {
            File file = new File(path);

            if (file != null) {
                // MediaType.parse() 里面是上传的文件类型。
//                byte[] data = ImageFactory.compressionImageSize(file, 50);
                RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
                String filename = file.getName();
                // 参数分别为， 请求key ，文件名称 ， RequestBody
                requestBody.addFormDataPart("imgFile", filename, body);

                for (String key : params.keySet()) {
                    requestBody.addFormDataPart(key, params.get(key));
                }


            }
        }

        Request request = new Request.Builder().url(NetWorkConstant.app_upload_img).post(requestBody.build()).build();
// readTimeout("请求超时时间" , 时间单位);
        client.newBuilder().readTimeout(5000, TimeUnit.MILLISECONDS).addInterceptor(new LogInterceptor(this)).build().newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                if (response.code() == 200) {

                    EntityBase entity = new Gson().fromJson(json, EntityBase.class);
                    CommonPreferences preferences = new CommonPreferences(MyApplication.APP);
                    preferences.setModel(entity.getAppUsers());
                    
                    ToastUtil.showMessage(entity.getMsg());
                }

            }
        });

    }

    private void changeGender(final int position) {
        HashMap<String, String> params = new HashMap<>();
        params.put("appSex", String.valueOf(position));
        mGsonRequest.function(NetWorkConstant.app_edit_info, params, EntityBase.class, new CallBack<EntityBase>() {
            @Override
            public void onResponse(EntityBase result) {
                if (result.getResult() == Constants.SUCCESSCODE) {
                    EntitiyUser users =  result.getAppUsers();
                    CommonPreferences preferences = new CommonPreferences(MyApplication.APP);
                    preferences.setModel(users);
                    mInformationSex.setText(sexlist[users.getAppSex()]);
                    
                }
                ToastUtil.showMessage(result.getMsg());
            }

            @Override
            public void onFailure(String error) {
                ToastUtil.showMessage(error);
            }
        });

    }


}
