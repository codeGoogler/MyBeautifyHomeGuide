一个优美的APP界面引导效果。

![项目效果图](http://upload-images.jianshu.io/upload_images/4614633-86dc137a3197e237.gif?imageMogr2/auto-orient/strip)


![](http://upload-images.jianshu.io/upload_images/4614633-8f5e258b19b52fc2.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

最近在优化项目的时候，提炼出来一个APP界面启动引导效果，之前有童鞋们在技术公众号里留言过。这里给需要的同童鞋们参考一下。

其实这种效很多方式去实现。方法不只这一种。

- 自定义ViewGroup--->稍微比较复杂
- ViewPager+n个Fragment


这个效果网上不大部分都有，还是打算自己出一篇。

底部的切换小圆点是自定义View实现PageIndicator接口。

基本布局如下：

```
   <android.support.v4.view.ViewPager
        android:id="@+id/pager"
        android:layout_width="fill_parent"
        android:layout_height="fill_parent" />

    <guide.yyh.com.mybeautifyhomeguide.CirclePageIndicator
        android:id="@+id/indicator"
        android:layout_width="fill_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:background="#00000000"
        android:padding="20dip"
        app:centered="true"
        app:currentBg="@mipmap/wifi_on"
        app:defaultBg="@mipmap/wifi_off"
        app:fillColor="#FF888888"
        app:pageColor="#EEEEEE"
        app:radius="10dp" />

```

绘制底部底部小圆点：

```
......

        Rect dest_current = new Rect();
        dest_current.left = (int)(dX-mRadius);
        dest_current.top = (int)(dY-mRadius);
        dest_current.right = (int)(dX+mRadius);
        dest_current.bottom = (int)(dY+mRadius);
        try {//画当前页面的LOGO图标
        	canvas.drawBitmap(ImageUtil.drawableToBitmap(currentBg), null, dest_current, mPaintCurrent);
		} catch (Exception e) {//异常用回原代码
			e.printStackTrace();
            canvas.drawCircle(dX, dY, mRadius, mPaintCurrent);
		}

......
```

首页跳转：

```
/**
 * 跳到首页
 */
public void skipToMain() {
    LogUtils.d(TAG, "skipToMain");
    Intent i = new Intent(GuideActivity.this, HomeMainActivity.class);
    if (null != GuideActivity.this.getIntent().getExtras()) {
        i.putExtras(GuideActivity.this.getIntent().getExtras());
    }
    if (null != GuideActivity.this.getIntent().getData()) {
        i.setData(GuideActivity.this.getIntent().getData());
    }
    startActivity(i);
    finish();
}

```
页面切换或者是滑动的时候是使用的Viewpager+动态实例化Fragment来实现的。这样防止实例化多个fragment浪费资源
```
    ······
public void addFragment(String tag, Class<?> clss, Bundle args) {
			FragmentInfo fragmentInfo = new FragmentInfo(tag, clss, args);
			fragments.add(fragmentInfo);
		}

		@Override
		public Fragment getItem(int arg0) {
			FragmentInfo fragmentInfo = fragments.get(arg0);
			return Fragment.instantiate(mContext, fragmentInfo.clss.getName(), fragmentInfo.args);
		}

    ······
```
fragment中简单做特殊处理
```
@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_guide, null);
		logo = (ImageView) view.findViewById(R.id.logo);
		into = (TextView) view.findViewById(R.id.into);
		into.setOnClickListener(this);
		skip = (ImageView) view.findViewById(R.id.skip);
		skip.setOnClickListener(this);
		Bundle bundle = getArguments();
		if (bundle != null) {
			currentItem = bundle.getInt("logo");
			switch (currentItem) {
			case 0://第一个页面
				logo.setImageResource(R.mipmap.start_01);
				skip.setVisibility(View.VISIBLE);
				into.setVisibility(View.GONE);
				break;
			case 1://第二个页面
				guide_two();
				break;
			case 2://第三个页面
				guide_third();
				break;
			case 3://最后一个页面
				guide_last();
				break;
			default:
				break;
			}
		}
		return view;
	}
```

![点击跳过的效果](http://upload-images.jianshu.io/upload_images/4614633-9cb44af7e6e0c5cf.gif?imageMogr2/auto-orient/strip)


> 项目地址：
>
> https://github.com/androidstarjack

### 更多文章

[ 2017上半年技术文章集合—184篇文章分类汇总](http://blog.csdn.net/androidstarjack/article/details/77923753)

[那些年不容错过的智能刷新加载框](http://mp.weixin.qq.com/s?__biz=MzI3OTU0MzI4MQ==&mid=100001024&idx=1&sn=2a01ffd977ca426ae2f91c5db626f4d5&chksm=6b47699e5c30e088c4c3bd7202e33f789307b1c4eea7effdf1872bbb43be952af27e12697eec#rd)

[Android中自定义View坐标系那些事](http://mp.weixin.qq.com/s?__biz=MzI3OTU0MzI4MQ==&mid=100001024&idx=1&sn=2a01ffd977ca426ae2f91c5db626f4d5&chksm=6b47699e5c30e088c4c3bd7202e33f789307b1c4eea7effdf1872bbb43be952af27e12697eec#rd)

[NDK项目实战—高仿360手机助手之卸载监听](http://blog.csdn.net/androidstarjack/article/details/77984865)

[MediaPlayer实现金额的语音播报功能](http://mp.weixin.qq.com/s/EEXYy5_MRiTKuAKb9pX3Fw)

[高级UI特效仿直播点赞效果—一个优美炫酷的点赞动画](http://mp.weixin.qq.com/s?__biz=MzI3OTU0MzI4MQ==&mid=100000969&idx=1&sn=626d821d16346764fdce33e65f372031&chksm=6b4768575c30e14163ae8fb9f0406db0b3295ce47c4bc27b1df7a3abee1fa0bb71ef27b4e959#rd)

[一个实现录音和播放的小案例](http://mp.weixin.qq.com/s?__biz=MzI3OTU0MzI4MQ==&mid=100000959&idx=1&sn=a5acb0f44fbadeaa9351df067438922c&chksm=6b4768215c30e1371a3c750f2b826f38b3a263c937272ae208717f73f92ed3e8fd8b6a674686#rd)



#### 相信自己，没有做不到的，只有想不到的

![加入大牛圈](http://img.blog.csdn.net/20170910215455020?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvYW5kcm9pZHN0YXJqYWNr/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

 如果你觉得此文对您有所帮助，欢迎入群 QQ交流群 ：644196190
微信公众号：终端研发部

![技术+职场](https://user-gold-cdn.xitu.io/2017/8/1/d354d51a5c58fb8a5ba576f2d9ea7a8e)

