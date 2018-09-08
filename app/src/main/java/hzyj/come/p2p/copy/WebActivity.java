package hzyj.come.p2p.copy;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

import hzyj.come.p2p.R;
import hzyj.come.p2p.app.utils.ToastUtil;

public class WebActivity extends BaseActivity {

    private WebView mWeb;


    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mWeb = findViewById(R.id.webView);
        bar = findViewById(R.id.myProgressBar);
        initData();

    }
    
    Timer mTimer = new Timer();
    

    private void initData() {

        String url = getIntent().getStringExtra("Url");
        boolean showToast = getIntent().getBooleanExtra("showToast",false);
        if (url != null) {
            initWebView(url);
        }
        if (showToast) {
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    ToastUtil.showMessage("测试！！！");
                }
            },2000,5000); 
        }
      

    }

    private void initWebView(String url) {


        //启用支持javascript
        WebSettings settings = mWeb.getSettings();
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);

        // 设置允许加载混合内容
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWeb.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        // 允许所有SSL证书
        mWeb.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return super.shouldOverrideUrlLoading(view, url);
                return false;
            }
        });
        mWeb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    bar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == bar.getVisibility()) {
                        bar.setVisibility(View.VISIBLE);
                    }
                    bar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

        });
        //WebView加载web资源
        mWeb.loadUrl(url);


//        
//        WebSettings settings = mWeb.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setAppCacheEnabled(false);
//
//// 解决对某些标签的不支持出现白屏
//        settings.setDomStorageEnabled(true);
//        // 设置允许加载混合内容
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            mWeb.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//
//        // 允许所有SSL证书
//        mWeb.setWebViewClient(new WebViewClient() {
//
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                view.loadUrl(url);//在2.3上面不加这句话，可以加载出页面，在4.0上面必须要加入，不然出现白屏
//                return true;
//            }
//
//            @Override
//            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                handler.proceed();
//            }
//        });
//        mWeb.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress == 100) {
//                    bar.setVisibility(View.INVISIBLE);
//                } else {
//                    if (View.INVISIBLE == bar.getVisibility()) {
//                        bar.setVisibility(View.VISIBLE);
//                    }
//                    bar.setProgress(newProgress);
//                }
//                super.onProgressChanged(view, newProgress);
//            }
//
//        });
//
//        mWeb.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
   
        if (mWeb.canGoBack()) {
            mWeb.goBack();
        } else {
            exitBy2Click();

        }
    }

    private static Boolean isExit = false;

    private void exitBy2Click() {

        if (!isExit) {
            isExit = true;
            ToastUtil.showMessage(getString(R.string.please_press_to_exit));
            Timer tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        } else {
            finish();
        }

    }
}
