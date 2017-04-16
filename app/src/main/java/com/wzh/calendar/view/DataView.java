package com.wzh.calendar.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzh.calendar.bean.DateEntity;
import com.wzh.calendar.R;
import com.wzh.calendar.adapter.DateAdapter;
import com.wzh.calendar.utils.DataUtils;

import java.util.ArrayList;

/**
 * author：Administrator on 2017/4/10 16:04
 * description:文件说明
 * version:版本
 */
public class DataView extends LinearLayout implements View.OnClickListener, AdapterView.OnItemClickListener {
    private ArrayList<DateEntity> millisList ;
    private TextView frontWeekTv ;
    private TextView nextWeekTv ;
    private TextView currentDateTv ;
    private GridView list ;
    private DateAdapter mAdapter ;
    private ArrayList<DateEntity> datas ;
    private String dataFormate = "yyyy-MM-dd" ;
    private String currentData ;
    private DatePopupWindow popupWindow ;

    public DataView(Context context) {
        this(context,null);
    }

    public DataView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public DataView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        datas = new ArrayList<>();
        millisList = new ArrayList<>();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.view_data,null,false);
        frontWeekTv = (TextView)view.findViewById(R.id.front_week);
        frontWeekTv.setOnClickListener(this);
        nextWeekTv = (TextView)view.findViewById(R.id.next_week);
        nextWeekTv.setOnClickListener(this);
        currentDateTv = (TextView)view.findViewById(R.id.now_day);
        currentDateTv.setOnClickListener(this);
        list = (GridView) view.findViewById(R.id.list);
        list.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mAdapter = new DateAdapter(getContext(),datas);
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(this);
        addView(view);
    }

    public void getData(String dateNumber){
        datas.clear();
        millisList.clear();
        currentDateTv.setText(dateNumber);
        if (TextUtils.isEmpty(dateNumber)){
            dateNumber = DataUtils.getCurrDate(dataFormate);
        }
        millisList = DataUtils.getWeek(dateNumber);
        if (millisList==null || millisList.size()<=0){
            return;
        }
        datas.addAll(millisList);
        for (int i=0;i<millisList.size();i++){
            if (dateNumber.equals(millisList.get(i).date)){
                mAdapter.setSelectedPosition(i);
                currentData = millisList.get(i).date;
                currentDateTv.setText(currentData);
                if (onSelectListener!=null){
                    onSelectListener.onSelected(millisList.get(i));
                }
            }
        }
        if (TextUtils.isEmpty(currentData)){
            mAdapter.setSelectedPosition(0);
            currentData = millisList.get(0).date ;
            currentDateTv.setText(millisList.get(0).date);
            if (onSelectListener!=null){
                onSelectListener.onSelected(millisList.get(0));
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id==frontWeekTv.getId()){
            getData(DataUtils.getSomeDays(currentData,-7));
        }else if (id==nextWeekTv.getId()){
            getData(DataUtils.getSomeDays(currentData,+7));
        }else if (id==currentDateTv.getId()){
            showPopupWindow(this);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DateEntity entity = datas.get(position);
        currentData = entity.date ;
        currentDateTv.setText(currentData);
        mAdapter.setSelectedPosition(position);
        if (onSelectListener!=null){
            onSelectListener.onSelected(millisList.get(position));
        }
    }

    private void showPopupWindow(View v) {
        popupWindow = new DatePopupWindow(getContext(),currentData);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(v);
        popupWindow.setOnItemClick(new DatePopupWindow.OnItemClick() {
            @Override
            public void onItemClick(String date) {
                getData(date);
            }
        });
    }

    /**
     * 选中日期之后的回调
     */
    private OnSelectListener onSelectListener ;
    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }
    public interface OnSelectListener{
        void onSelected(DateEntity date);
    }
}
