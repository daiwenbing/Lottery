package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import activity.FeeBackActivity;
import application.DSLApplication;
import lottery.dwb.com.lottery.R;

/**更多
 * Created by dwb on 2018/3/26.
 */

public class HomepageMoreFragment extends Fragment implements View.OnClickListener{
    private RelativeLayout table2,table4,table5;
    private TextView tv;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set,null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initview();
    }
    public void initview(){
        table2=getView().findViewById(R.id.table2);
        table4=getView().findViewById(R.id.table4);
        table5=getView().findViewById(R.id.table5);
        tv=getView().findViewById(R.id.tv);
        table2.setOnClickListener(this);
        table4.setOnClickListener(this);
        table5.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.table2:
                tv.setText("0M");
                Toast.makeText(getActivity(),"清除缓存成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.table4:
                Intent intent=new Intent(getActivity(), FeeBackActivity.class);
                startActivity(intent);
                break;
            case R.id.table5:
                DSLApplication.getInstance().onTerminate();
                break;
        }
    }
}
