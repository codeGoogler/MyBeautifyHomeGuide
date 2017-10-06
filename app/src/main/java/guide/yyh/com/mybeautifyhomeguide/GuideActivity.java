package guide.yyh.com.mybeautifyhomeguide;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import guide.yyh.com.mybeautifyhomeguide.ui.HomeMainActivity;
import guide.yyh.com.mybeautifyhomeguide.util.LogUtils;


/**
 * 类功能描述：</br>
 * 指示器为图片的CirclePageIndicator
 * 博客地址：http://blog.csdn.net/androidstarjack
 * 公众号：终端研发部
 * @author yuyahao
 * @version 1.0 </p> 修改时间：</br> 修改备注：</br>
 */
public class GuideActivity extends FragmentActivity implements OnPageChangeListener {
	private static final String TAG  = "GuideActivity";

	public static int currentItemId = 0;// 当前界面

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		initViewpageFragment();

	}

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

	public static class CirclePageFragmentAdapter extends FragmentPagerAdapter {

		private Context mContext;
		private final ArrayList<FragmentInfo> fragments = new ArrayList<FragmentInfo>();

		protected static final class FragmentInfo {
			@SuppressWarnings("unused")
			private final String tag;
			private final Class<?> clss;
			private final Bundle args;

			protected FragmentInfo(String _tag, Class<?> _class, Bundle _args) {
				tag = _tag;
				clss = _class;
				args = _args;
			}
		}

		public CirclePageFragmentAdapter(FragmentManager fm, Context context) {
			super(fm);
			this.mContext = context;
		}

		public void addFragment(String tag, Class<?> clss, Bundle args) {
			FragmentInfo fragmentInfo = new FragmentInfo(tag, clss, args);
			fragments.add(fragmentInfo);
		}

		@Override
		public Fragment getItem(int arg0) {
			FragmentInfo fragmentInfo = fragments.get(arg0);
			return Fragment.instantiate(mContext, fragmentInfo.clss.getName(), fragmentInfo.args);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

	}

	CirclePageFragmentAdapter mAdapter;
	ViewPager mPager;
	CirclePageIndicator mIndicator;

	private void initViewpageFragment() {
		mAdapter = new CirclePageFragmentAdapter(getSupportFragmentManager(), this);
		Bundle bundle = new Bundle();
		bundle.putInt("logo", 0);
		mAdapter.addFragment("", GuideFragment.class, bundle);

		Bundle bundle1 = new Bundle();
		bundle1.putInt("logo", 1);
		mAdapter.addFragment("", GuideFragment.class, bundle1);

		Bundle bundle2 = new Bundle();
		bundle2.putInt("logo", 2);
		mAdapter.addFragment("", GuideFragment.class, bundle2);

		Bundle bundle3 = new Bundle();
		bundle3.putInt("logo", 3);
		mAdapter.addFragment("", GuideFragment.class, bundle3);
		//
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		mPager.setOffscreenPageLimit(0);// 预加载页数

		mIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
		mIndicator.setOnPageChangeListener(this);
	}

	/**
	 * 返回页面导航器
	 * 
	 * @return
	 */
	public CirclePageIndicator getCirclePageIndicator() {
		return mIndicator;
	}

	/**
	 * 获取当前界面号
	 * 
	 * @return
	 */
	public int getCurrentItemId() {
		return currentItemId;
	}

	public void setCurrentItemId(int currentItemId) {
		this.currentItemId = currentItemId;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		currentItemId = arg0;
	}

	@Override
	public void onPageSelected(int arg0) {
		currentItemId = arg0;
	}

}
