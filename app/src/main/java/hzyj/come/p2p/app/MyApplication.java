package hzyj.come.p2p.app;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.jess.arms.base.BaseApplication;
import com.squareup.leakcanary.LeakCanary;
import com.sxjs.common.CommonConfig;
import com.sxjs.common.GlobalAppComponent;

/**
 * @author admin
 */
public class MyApplication extends BaseApplication {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base); MultiDex.install(this);
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        APP = this;
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(this);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        if(CommonConfig.DEBUG){
//            LeakCanary.install(this);
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
    
        ARouter.init(this);
        //CrashReport.initCrashReport(getApplicationContext(), "93f0e37549", CommonConfig.DEBUG);
        Fresco.initialize(this);
        GlobalAppComponent.init(this);
        initAccessTokenWithAkSk();
    }

    private void initAccessTokenWithAkSk() {
        OCR.getInstance(this).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
            
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
            }
        }, getApplicationContext(),  "Lrahj1sd9PnAsR37Hu1CxmIw", "rE7CYYfYH32EzneIEzVe3P928or7wMea");
    }
    
    public static  Application APP;
  

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearMemoryCaches();
    }

}
