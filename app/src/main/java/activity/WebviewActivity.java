package activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import application.DSLApplication;
import lottery.dwb.com.lottery.R;

/**
 * Created by dwb on 2018/3/27.
 */

public class WebviewActivity extends AppCompatActivity {
    private Intent mintent;
    private TextView toorbar_txt_main_title;
    private LinearLayout toorbar_layout_main_back;
    private WebView webview;
    private WebSettings webSettings;
    private String url,title;
    private ProgressBar progressbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DSLApplication.getInstance().addActivity(this);
        setContentView(R.layout.webview_title_view);
        initview();
    }
public void initview(){
    mintent=getIntent();
    url=mintent.getStringExtra("url");
    title=mintent.getStringExtra("title");
    toorbar_txt_main_title=(TextView) findViewById(R.id.toorbar_txt_main_title);
    toorbar_layout_main_back=(LinearLayout) findViewById(R.id.toorbar_layout_main_back);
    progressbar= (ProgressBar) findViewById(R.id.progressbar);
    webview=(WebView) findViewById(R.id.webview);
    toorbar_txt_main_title.setText(title);
    toorbar_layout_main_back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
    init();
}
    private void init(){
        //WebView加载web资源
        webview.loadUrl(url);
        webview.setLayerType(View.LAYER_TYPE_HARDWARE, null);//启用加速，否则滑动界面不流畅
        webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);//关键点
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
//        webSettings.setAppCacheEnabled(true);//是否使用缓存
        webSettings.setSupportZoom(true); // 支持缩放
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressbar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressbar.setVisibility(View.INVISIBLE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                view.loadUrl("file:///android_asset/error.html");//加载本地网页的路径
            }
        });
//        webView.addJavascriptInterface(new JsCallJava() {
//            @JavascriptInterface
//            @Override   window.android_daipai.androidMethod()
//            public void onCallback() {
//
//                Toast.makeText(getApplicationContext(),"JavaScript调用的java代码",Toast.LENGTH_SHORT).show();
//
//            }
//        }, "demo");
    }
    /**
     * 返回键
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(webview.canGoBack()){
                webview.goBack();
                return true;
            }
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
