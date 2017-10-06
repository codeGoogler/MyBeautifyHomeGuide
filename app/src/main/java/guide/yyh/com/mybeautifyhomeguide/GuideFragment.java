package guide.yyh.com.mybeautifyhomeguide;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import guide.yyh.com.mybeautifyhomeguide.util.LogUtils;


/**
 * 类功能描述：</br>
 * 指引导页面之fragment
 * 博客地址：http://blog.csdn.net/androidstarjack
 * 公众号：终端研发部
 * @author yuyahao
 * @version 1.0 </p> 修改时间：</br> 修改备注：</br>
 */
public final class GuideFragment extends Fragment implements OnClickListener {
	private static final String TAG = "LogoFragment";

	int currentItem;
	ImageView logo,  skip;
	TextView into;
	Activity activity = null;

	@Override
	public void onCreate(Bundle saveBundle) {
		super.onCreate(saveBundle);
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}
	
	@Override
	public void onDetach() {
		super.onDetach();
		activity = null;
	}

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

	/**
	 * 第二个引导页面
	 */
	private void guide_two() {
		skip.setVisibility(View.VISIBLE);
		into.setVisibility(View.GONE);
		logo.setImageResource(R.mipmap.start_02);
	}
	
	/**
	 * 第三个引导页面
	 */
	private void guide_third(){
		skip.setVisibility(View.VISIBLE);
		into.setVisibility(View.GONE);
		logo.setImageResource(R.mipmap.start_03);
	}

	/**
	 * 最后一个引导页面
	 */
	private void guide_last() {
		skip.setVisibility(View.GONE);
		into.setVisibility(View.VISIBLE);
		into.setEnabled(true);
		logo.setImageResource(R.mipmap.start_04);//TODO
	}

	@Override
	public void onClick(View v) {
		LogUtils.d(TAG, "onClick id:"+v.getId());
		switch (v.getId()) {
		case R.id.into:// 当前页面最后一个页面，跳到首页
			LogUtils.d(TAG, "onClick into");
			((GuideActivity)activity).skipToMain();
			activity.overridePendingTransition(R.anim.translate_right_in, R.anim.translate_left_in);
			break;
		case R.id.skip:// 第一二个页面，跳到首页
			LogUtils.d(TAG, "onClick skip");
			((GuideActivity)activity).skipToMain();
			activity.overridePendingTransition(R.anim.translate_right_in, R.anim.translate_left_in);
			break;
		default:
			break;
		}

	}

}
