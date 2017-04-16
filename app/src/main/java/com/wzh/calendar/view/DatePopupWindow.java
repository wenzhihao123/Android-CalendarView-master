package com.wzh.calendar.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wzh.calendar.R;
import com.wzh.calendar.adapter.DateMonthAdapter;
import com.wzh.calendar.utils.DataUtils;

/**
 * author：Administrator on 2017/4/11 15:43
 * description:文件说明
 * version:版本
 */
public class DatePopupWindow extends PopupWindow implements View.OnClickListener{
    private View conentView;
    private GridView gridView ;
    private TextView frontMonthTv ;
    private TextView nextMonthTv ;
    private TextView currentDateTv ;
    private DateMonthAdapter adapter ;
    private TextView ok ;
    public String date ;
    private int currentPosition =-1 ;
    final int RIGHT = 0;
    final int LEFT = 1;
    private GestureDetector gestureDetector;
    public DatePopupWindow(final Context context,String dateString) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.view_poup, null);
        int h = context.getResources().getDisplayMetrics().heightPixels;
        int w = context.getResources().getDisplayMetrics().widthPixels;
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
//         this.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
//        this.setAnimationStyle(R.style.Animation_Pop_style);
        gridView = (GridView) conentView.findViewById(R.id.list);
        frontMonthTv = (TextView)conentView.findViewById(R.id.front_month);
        frontMonthTv.setOnClickListener(this);
        nextMonthTv = (TextView)conentView.findViewById(R.id.next_month);
        nextMonthTv.setOnClickListener(this);
        ok = (TextView)conentView.findViewById(R.id.ok);
        ok.setOnClickListener(this);
        gestureDetector = new GestureDetector(context,onGestureListener);
        currentDateTv = (TextView)conentView.findViewById(R.id.now_month);
        this.date = dateString ;
        if (TextUtils.isEmpty(date)){
            this.date = DataUtils.getCurrDate("yyyy-MM-dd");
        }
        currentDateTv.setText("当前月份："+DataUtils.formatDate(date,"yyyy-MM"));
        adapter = new DateMonthAdapter(context);
        adapter.setData(DataUtils.getMonth(date));
        gridView.setAdapter(adapter);
        adapter.setDateString(date);
        adapter.setSelectedPosition(DataUtils.getSelectPosition());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onItemClick!=null && !TextUtils.isEmpty(adapter.getItem(position).date)){
                    adapter.setSelectedPosition(position);
                    currentDateTv.setText("当前月份："+DataUtils.formatDate(adapter.getItem(position).date,"yyyy-MM"));
                    date = adapter.getItem(position).date ;
                }
            }
        });
        gridView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });

    }

    /**
     * 手势监听是否是左右滑动
     */
    private GestureDetector.OnGestureListener onGestureListener =
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                       float velocityY) {
                    float x = e2.getX() - e1.getX();
                    float y = e2.getY() - e1.getY();

                    if (x > 100) {
                        doResult(RIGHT);
                    } else if (x < -100) {
                        doResult(LEFT);
                    }
                    return true;
                }
            };
    public void doResult(int action) {

        switch (action) {
            case RIGHT:
                date = DataUtils.getSomeMonthDay(date,-1);
                adapter.setData(DataUtils.getMonth(date));
                adapter.setDateString(date);
                adapter.setSelectedPosition(DataUtils.getSelectPosition());
                currentDateTv.setText("当前月份："+DataUtils.formatDate(date,"yyyy-MM"));
                Log.e("wenzihao","go right");
                break;
            case LEFT:
                date = DataUtils.getSomeMonthDay(date,+1);
                adapter.setData(DataUtils.getMonth(date));
                adapter.setDateString(date);
                adapter.setSelectedPosition(DataUtils.getSelectPosition());
                currentDateTv.setText("当前月份："+DataUtils.formatDate(date,"yyyy-MM"));
                Log.e("wenzihao","go left");
                break;

        }
    }
    public OnItemClick onItemClick ;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id==frontMonthTv.getId()){
            date = DataUtils.getSomeMonthDay(date,-1);
            adapter.setData(DataUtils.getMonth(date));
            adapter.setDateString(date);
            adapter.setSelectedPosition(DataUtils.getSelectPosition());
            currentDateTv.setText("当前月份："+DataUtils.formatDate(date,"yyyy-MM"));
        }else if (id==nextMonthTv.getId()){
            date = DataUtils.getSomeMonthDay(date,+1);
            adapter.setData(DataUtils.getMonth(date));
            adapter.setDateString(date);
            adapter.setSelectedPosition(DataUtils.getSelectPosition());
            currentDateTv.setText("当前月份："+DataUtils.formatDate(date,"yyyy-MM"));
        }else if (id==ok.getId()){
            if (onItemClick!=null){
                onItemClick.onItemClick(date);
            }
            dismiss();
        }
    }

    /**
     * 点击回调接口
     */
    public interface OnItemClick{
        void onItemClick(String date);
    }

    @Override
    public void dismiss() {
        super.dismiss();

    }
}
