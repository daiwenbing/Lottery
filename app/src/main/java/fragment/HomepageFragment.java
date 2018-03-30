package fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.bumptech.glide.Glide;
import com.sunfusheng.marqueeview.MarqueeView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import activity.HomePageListDetail;
import activity.WebviewActivity;
import activity.XListView;
import application.DSLApplication;
import javabean.HonePageListModel;
import lottery.dwb.com.lottery.R;
import recycler_listview_adapter.HomeAdapter;
import utils.DSLConnections;
import utils.DateChange;
import viewpager_adaper.AdvAdapter;
import volley.GsonRequest;
import volley.VolleyErrorHelper;

/**首页
 * Created by dwb on 2018/3/26.
 */

public class HomepageFragment extends Fragment implements View.OnClickListener, XListView.IXListViewListener{
    private Intent mintent;
    private HomeAdapter adapter;
    private HonePageListModel honePageListModel;
    private XListView xListView;
    private MarqueeView marqueeView;
    private RelativeLayout homepage_layout_kj,homepage_layout_gg,layout_img;
    private RelativeLayout homepage_layout_3,homepage_layout_4,homepage_layout_5,homepage_layout_6,homepage_layout_7,homepage_layout_8,homepage_layout_9,homepage_layout_10;
    private ArrayList<HonePageListModel.showapi_res_body.result> list;
    private View view;
    private String[] imageUrls;
    private List<String> banner_list;
    //////////////////////////viewpager//////////////////
    private View view_banner ;
    private int width,height;
    private ImageView img1;
    private List<View> advPics;
    private int item;
    private ImageView[] imageViews = null;
    private ImageView imageView = null;
    private AdvAdapter advAdapter;
    private ViewPager advPager = null;
    private ViewGroup group;
    private AtomicInteger what = new AtomicInteger(0);
    private boolean isContinue = true;
    private ProgressDialog dialog=null;
    //////////////////////////////////////////////////////
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.homepagefragment,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initview();
        dialog_shows();
        request();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        DSLApplication.getHttpQueue().cancelAll("tag");
    }

    public void initview(){
        list=new ArrayList<HonePageListModel.showapi_res_body.result>();
        adapter=new HomeAdapter(list,getActivity());
        xListView=(XListView) getView().findViewById(R.id.xListView);
        xListView.setAdapter(adapter);
        xListView.setPullLoadEnable(false);
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this);
        xListView.setFocusable(true);
        xListView.setOnItemClickListener(new itemclink());
        listhead();
        xListView.addHeaderView(view, null, false);
        initViewPager();
    }
    /**
     * listview头文件初始化控件
     */
    public void listhead() {
        view=LayoutInflater.from(getActivity()).inflate(R.layout.homepage_list_headview, null);
        advPager = (ViewPager)view.findViewById(R.id.adv_pager);
        group = (ViewGroup)view.findViewById(R.id.viewGroup);
        homepage_layout_kj=view.findViewById(R.id.homepage_layout_kj);
        homepage_layout_gg=view.findViewById(R.id.homepage_layout_gg);
        layout_img=view.findViewById(R.id.layout_img);
        homepage_layout_3=view.findViewById(R.id.homepage_layout_3);
        homepage_layout_4=view.findViewById(R.id.homepage_layout_4);
        homepage_layout_5=view.findViewById(R.id.homepage_layout_5);
        homepage_layout_6=view.findViewById(R.id.homepage_layout_6);
        homepage_layout_7=view.findViewById(R.id.homepage_layout_7);
        homepage_layout_8=view.findViewById(R.id.homepage_layout_8);
        homepage_layout_9=view.findViewById(R.id.homepage_layout_9);
        homepage_layout_10=view.findViewById(R.id.homepage_layout_10);
        marqueeView=view.findViewById(R.id.marqueeView);
        homepage_layout_kj.setOnClickListener(this);
        homepage_layout_gg.setOnClickListener(this);
        layout_img.setOnClickListener(this);
        homepage_layout_3.setOnClickListener(this);
        homepage_layout_4.setOnClickListener(this);
        homepage_layout_5.setOnClickListener(this);
        homepage_layout_6.setOnClickListener(this);
        homepage_layout_7.setOnClickListener(this);
        homepage_layout_8.setOnClickListener(this);
        homepage_layout_9.setOnClickListener(this);
        homepage_layout_10.setOnClickListener(this);
        marqueeView.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                mintent=new Intent(getActivity(), WebviewActivity.class);
                mintent.putExtra("title",textView.getText().toString());
                mintent.putExtra("url","http://mapi.159cai.com/discovery/notice/2018/0320/32942.html");
                startActivity(mintent);
            }
        });
        bindmequrelist();
        bind_banner();
    }
    public class itemclink implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int a = position - 2;
            mintent=new Intent(getActivity(), HomePageListDetail.class);
            mintent.putExtra("code",list.get(a).getCode());
            mintent.putExtra("title",list.get(a).getDescr());
            startActivity(mintent);
        }
    }
    /**
     * 数据请求
     */
    public void request(){
        Map<String,String> param=new HashMap<String, String>();
        param.put("showapi_appid","49035");
        param.put("showapi_sign","6f6b85bce5e347139a9fc1affb25abd1");
        GsonRequest<HonePageListModel> request=new GsonRequest<HonePageListModel>(Request.Method.POST,DSLConnections.home_list_url, HonePageListModel.class,param,
                new Response.Listener<HonePageListModel>() {
                    @Override
                    public void onResponse(HonePageListModel model) {
                        dialog_dismess();
                        onLoad();
                        honePageListModel=model;
                        return_list();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
                Toast.makeText(getActivity(), VolleyErrorHelper.getMessage(arg0,getActivity()),Toast.LENGTH_SHORT).show();
                dialog_dismess();
                onLoad();
            }
        });
        request.setTag("tag");
        DSLApplication.getHttpQueue().add(request);
    }
    public void return_list(){
        try {
            if("0".equals(honePageListModel.getShowapi_res_code())){
                list.clear();
                list.addAll(honePageListModel.getShowapi_res_body().getResult());
                adapter.notifyDataSetChanged();
            }
        }catch (NullPointerException e){
        }
    }
    public void bindmequrelist(){
        List<String> info = new ArrayList<>();
        info.add("关于发单【跟单异常】调整公告");
        info.add("关于【预约发单调整】公告");
        marqueeView.startWithList(info);

// 在代码里设置自己的动画
        marqueeView.startWithList(info, R.anim.anim_bottom_in, R.anim.anim_top_out);
    }
    /**
     * 初始化banner数据
     */
    public void bind_banner(){
           /*    this.imgurllist.add("http://img.500.com/upimages/wap/img/20171215102026226.jpg?!1349.webp");
        this.imgurllist.add("http://img.500.com/upimages/wap/img/20171201174313643.jpg?!1349.webp");
        this.imgurllist.add("http://m.159cai.com/uploads/171210/2-1G2101H522149.jpg");
        this.imgurllist.add("http://m.159cai.com/uploads/171104/2-1G1041602113P.png");

        http://m.159cai.com/uploads/180328/2-1P32P92AL45.jpg http://m.159cai.com/sjbguanyajun/guanjun.html
        */
        imageUrls = new String[]{
            "http://m.159cai.com/uploads/180328/2-1P32P92AL45.jpg",
            "http://m.dididapiao.com/upload/cms/news/images/2018/3/27/tn_up_201803271034000037.png",
            "http://m.159cai.com/uploads/180302/2-1P302114923240.jpg",
            "http://m.159cai.com/uploads/180313/2-1P313105F0448.png",
            "http://m.dididapiao.com/upload/cms/news/images/2017/12/20/tn_up_201712201706000007.png"};
    banner_list = new ArrayList();
    banner_list.add("http://m.159cai.com/sjbguanyajun/guanjun.html");
    banner_list.add("http://mapi.159cai.com/discovery/news/football/2018/0330/33094.html");
    banner_list.add("http://m.159cai.com/discovery/news/football/2018/0302/32651.html");
    banner_list.add("http://mapi.159cai.com/discovery/news/basketball/2018/0328/33054.html");
    banner_list.add("http://m.lottech.cn/vue/views/didi/betRedPlan.html#/redPlan/1/D33269DE20A5278C6517B89FC1940850-384234?hiddenHead=true");
}
    private void initViewPager() {
//	      这里存放的是四张广告背景
        //http://m.lifan99.com/static/images/home/banner-1.jpg
        advPics = new ArrayList<View>();
        for(int i=0;i<banner_list.size();i++){
            img1 = new ImageView(getActivity().getApplicationContext());
            img1.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(this)
                    .load(imageUrls[i])
                    .placeholder(R.mipmap.loag_station_banner)
                    .into(img1);
            advPics.add(img1);
        }
        //对imageviews进行填充
        imageViews = new ImageView[advPics.size()];
        /**
         * 有几张图片 下面就显示几个小圆点
         */
        if(group!=null){
            group.removeAllViews();
        }
        for (int i = 0;i < advPics.size(); i++) {
            LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            //设置每个小圆点距离左边的间距
            margin.setMargins(width/10, 0, 0, 0);
            imageView = new ImageView(getActivity());
            //设置每个小圆点的宽高
            imageView.setLayoutParams(new LinearLayout.LayoutParams(20, 20));
            imageViews[i] = imageView;
            if (i == 0) {
                // 默认选中第一张图片
                imageViews[i]
                        .setBackgroundResource(R.mipmap.circle_blude_select);
            } else {
                //其他图片都设置未选中状态
                imageViews[i]
                        .setBackgroundResource(R.mipmap.circle_blue);
            }
            group.addView(imageViews[i] , margin);
        }

        advAdapter=new AdvAdapter(advPics);
        advPager.setAdapter(advAdapter);
        advAdapter.notifyDataSetChanged();
        advPager.setOnPageChangeListener(new GuidePageChangeListener());
        advPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        isContinue = false;
                        break;
                    case MotionEvent.ACTION_UP:
                        isContinue = true;
                        break;
                    default:
                        isContinue = true;
                        break;
                }
                return false;
            }
        });
        new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    if (isContinue) {
                        viewHandler.sendEmptyMessage(what.get());
                        whatOption();
                    }
                }
            }

        }).start();

    }
    private void whatOption() {
        what.incrementAndGet();
        if (what.get() > imageViews.length - 1) {
            what.getAndAdd(-imageViews.length);
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            return;
        }
    }
    private final Handler viewHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            advPager.setCurrentItem(msg.what);
            super.handleMessage(msg);

        }

    };
    private final class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }
        @Override
        public void onPageSelected(int arg0) {

            item=arg0;
            view_banner= advPics.get(arg0);
            view_banner.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    try {
                        String url=banner_list.get(item);
                        mintent=new Intent(getActivity(), WebviewActivity.class);
                        mintent.putExtra("url", url);
                        mintent.putExtra("title","详情");
                        startActivity(mintent);
                    }catch (NullPointerException e){
                    }
                }
            });
            what.getAndSet(arg0);
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[arg0]
                        .setBackgroundResource(R.mipmap.circle_blude_select);
                if (arg0!= i) {
                    imageViews[i].setBackgroundResource(R.mipmap.circle_blue);
                }
            }
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.homepage_layout_kj:
                mintent=new Intent(getActivity(),WebviewActivity.class);
                mintent.putExtra("url","http://m.dididapiao.com/bet/ssq/index;jsessionid=A69D197C8A190FBA482D85A53F6BC92B.w3?clear=1&agentId=100107");
                startActivity(mintent);
                break;
            case R.id.homepage_layout_gg:
                mintent=new Intent(getActivity(),WebviewActivity.class);
                mintent.putExtra("url","http://m.dididapiao.com/bet/tcdlt/index;jsessionid=A69D197C8A190FBA482D85A53F6BC92B.w3?clear=1&agentId=100107");
                startActivity(mintent);
                break;
            case R.id.layout_img:
                //第一资讯
                mintent=new Intent(getActivity(),WebviewActivity.class);
                mintent.putExtra("url","http://m.159cai.com/gong/news.html");
                startActivity(mintent);
                break;
            case R.id.homepage_layout_3:
                mintent=new Intent(getActivity(),WebviewActivity.class);
                mintent.putExtra("url","http://m.dididapiao.com/bet/fc3d/index;jsessionid=A69D197C8A190FBA482D85A53F6BC92B.w3?clear=1&agentId=100107");
                startActivity(mintent);
                break;
            case R.id.homepage_layout_4:
                mintent=new Intent(getActivity(),WebviewActivity.class);
                mintent.putExtra("url","http://m.dididapiao.com/bet/y11x5/index;jsessionid=A69D197C8A190FBA482D85A53F6BC92B.w3?clear=1&agentId=100107");
                startActivity(mintent);
                break;
            case R.id.homepage_layout_5:
                mintent=new Intent(getActivity(),WebviewActivity.class);
                mintent.putExtra("url","http://m.dididapiao.com/bet/jxk3/index;jsessionid=A69D197C8A190FBA482D85A53F6BC92B.w3?clear=1&agentId=100107");
                startActivity(mintent);
                break;
            case R.id.homepage_layout_6:
                mintent=new Intent(getActivity(),WebviewActivity.class);
                mintent.putExtra("url","http://m.dididapiao.com/bet/jsk3/index;jsessionid=A69D197C8A190FBA482D85A53F6BC92B.w3?clear=1&agentId=100107");
                startActivity(mintent);
                break;
            case R.id.homepage_layout_7:
            case R.id.homepage_layout_8:
            case R.id.homepage_layout_9:
                mintent=new Intent(getActivity(),WebviewActivity.class);
                mintent.putExtra("url","http://m.dididapiao.com/bet/tcpl3/index;jsessionid=925822A1DDCEBE6924C1D2CE8B630820.w3?clear=1&agentId=100107");
                startActivity(mintent);
                break;
            case R.id.homepage_layout_10:
                mintent=new Intent(getActivity(),WebviewActivity.class);
                mintent.putExtra("url","http://m.dididapiao.com/bet/tcpl5/index;jsessionid=925822A1DDCEBE6924C1D2CE8B630820.w3?clear=1&agentId=100107");
                startActivity(mintent);
                break;
        }
    }

    @Override
    public void onRefresh() {
    request();
    }

    @Override
    public void onLoadMore() {
    onLoad();
    }
    private void onLoad() {
        xListView.stopRefresh();
        xListView.stopLoadMore();
        DateChange change=new DateChange();
        xListView.setRefreshTime(change.getTime1());
    }
    public void dialog_shows() {
        if (dialog == null) {
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("加载中...");
            dialog.setCancelable(true);
        }
        dialog.show();

    }
    public void dialog_dismess(){
        if (dialog!=null&&dialog.isShowing()){dialog.dismiss();}return;

    }
}
