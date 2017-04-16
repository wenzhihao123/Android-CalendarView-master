package com.wzh.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.TextView;

import com.wzh.calendar.bean.DateEntity;
import com.wzh.calendar.view.DataView;

/**
 * author：Administrator on 2017/4/10 16:03
 * description:文件说明
 * version:版本
 */
public class MainActivity extends FragmentActivity {
    private DataView dataView ;
    private TextView info ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = (TextView) findViewById(R.id.info);
        dataView = (DataView) findViewById(R.id.week);
        dataView.setOnSelectListener(new DataView.OnSelectListener() {
            @Override
            public void onSelected(DateEntity date) {
                info.setText("日期："+ date.date+"\n"+
                             "周几："+ date.weekName+"\n"+
                             "今日："+ date.isToday+"\n"+
                             "时间戳："+ date.million+"\n");
                Log.e("wenzhiao--------------",date.toString());
            }
        });
        dataView.getData("2017-04-19");
    }

}
