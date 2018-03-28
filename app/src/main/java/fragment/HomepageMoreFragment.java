package fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import lottery.dwb.com.lottery.R;

/**更多
 * Created by dwb on 2018/3/26.
 */

public class HomepageMoreFragment extends Fragment implements View.OnClickListener{
    private WebView webview;
    private WebSettings webSettings;
    private ProgressBar progressbar;
    private ImageView img_back;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.webview_back_notitle_view,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initview();
    }
    public void initview(){
        progressbar= (ProgressBar)getView().findViewById(R.id.progressbar);
        webview=(WebView)getView().findViewById(R.id.webview);
        img_back=getView().findViewById(R.id.img_back);
        img_back.setOnClickListener(this);
        init();
    }
    private void init(){
        //WebView加载web资源
        webview.loadUrl("http://m.159cai.com/gong/news.html");
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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back:
                if(webview.canGoBack()){
                    webview.goBack();
                }
                break;
        }
    }
}
