# Android-CalendarView-master
Android开发实现自定义日历、日期选择控件
最近项目需要日历效果，考虑用第三方的反而不太适合设计需求，修改复杂，与其这样不入自己重新写一个干净的控件。虽不是什么牛逼控件，但是也需要我们能按照设计自己写出来。在此记录一下实现思路。

效果图：

![切换周](http://upload-images.jianshu.io/upload_images/2018489-4717cf33106e3544.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![切换月份](http://upload-images.jianshu.io/upload_images/2018489-eba2c986665394ac.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 实现思路
* 头部是一个自定义组合控件；
* 显示一周的日期部分用GridView 更加方便更新；
* 切换月的部分是一个自定义PopupWindow；
* GridView选中效果；
* GridView根据手势GestureDetector监听左右滑动；
* 核心其实还是Calendar类，根据这个类我们可以获取制定日期一周的日期集合、可以获取制定日期一月的日期集合等等；
* 根据阳历日期获取阴历日期

#### 使用
```
// xml布局引用
<com.wzh.calendar.view.DataView
        android:id="@+id/week"
        android:layout_width="match_parent"
        android:background="@color/color_ffffff"
        android:layout_height="wrap_content">
</com.wzh.calendar.view.DataView>

// 代码中，自定义回调监听选中的日期
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
//需要传递此种格式的日期，不传默认是获取今日的日期
dataView.getData("2017-04-19");
```
[博客详解地址](http://www.jianshu.com/p/a2f102c728ce)

邮箱：821027320@qq.com

QQ:821027320