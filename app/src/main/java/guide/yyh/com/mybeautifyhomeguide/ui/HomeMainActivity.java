package guide.yyh.com.mybeautifyhomeguide.ui;


import android.app.Activity;
import android.os.Bundle;

import guide.yyh.com.mybeautifyhomeguide.R;
import guide.yyh.com.mybeautifyhomeguide.util.GetToast;


/**
 * 类功能描述：</br>
 * 仿首页APP首页引导主界面
 * 博客地址：http://blog.csdn.net/androidstarjack
 * 公众号：终端研发部
 * @author yuyahao
 * @version 1.0 </p> 修改时间：</br> 修改备注：</br>
 */
public class HomeMainActivity extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		GetToast.useString(HomeMainActivity.this,"欢迎光临");
		//接下来开始处理自己的逻辑
	}

}
