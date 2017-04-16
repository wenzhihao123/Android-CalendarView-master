package com.wzh.calendar.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    protected Context mContext;
    public ArrayList<T> mBeanList;

    public BaseAdapter(Context c) {
        mContext = c;
    }

    public BaseAdapter(Context c, ArrayList<T> beanList) {
        mContext = c;
        mBeanList = beanList;
    }

    public void setData(ArrayList<T> beanList) {
        if (mBeanList == null)
            mBeanList = new ArrayList<T>();
        else
            mBeanList.clear();
        if (beanList == null) {
            mBeanList.clear();
            notifyDataSetChanged();
            return;
        }
        mBeanList.addAll(beanList);
        notifyDataSetChanged();
    }



    public void removeData(int position) {
        mBeanList.remove(position);
        notifyDataSetChanged();
    }

    public void removeData(T data) {
        mBeanList.remove(data);
        notifyDataSetChanged();
    }

    public void addListData(ArrayList<T> beanList) {
        if (mBeanList == null) {
            mBeanList = new ArrayList<T>();
        }
        if (beanList == null) {
            return;
        }
        mBeanList.addAll(beanList);

        notifyDataSetChanged();
    }

    public void addData(T bean) {
        if (mBeanList == null)
            mBeanList = new ArrayList<T>();
        if (bean == null)
            return;
        mBeanList.add(bean);

        notifyDataSetChanged();
    }

    public void addData(int index,T bean) {
        if (mBeanList == null)
            mBeanList = new ArrayList<T>();
        if (bean == null)
            return;
        mBeanList.add(index,bean);

        notifyDataSetChanged();
    }

    public ArrayList<T> getData() {
        if (mBeanList == null)
            mBeanList = new ArrayList<T>();
        return mBeanList;
    }

    @Override
    public int getCount() {
        return mBeanList.size();
    }

    public ArrayList<T> getBeanList() {
        if (mBeanList == null)
            mBeanList = new ArrayList<>();
        return mBeanList;
    }

    @Override
    public T getItem(int position) {
        return mBeanList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}