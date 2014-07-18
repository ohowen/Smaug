package com.jrh.smaug.activity;

import com.jrh.smaug.R;
import com.jrh.smaug.app.SmgThread;
import com.jrh.smaug.util.Config;
import com.jrh.smaug.view.TouchImageView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener,
        OnTouchListener {
	private Button mBtnAnalysis;
	private EditText mEdtVoronoiUrl;
	private ProgressBar mBar;
	private TouchImageView mTouchImageView;
	private Bitmap mBitmap;
	private ImageView mImageView;
	private Context mContext;
	private String x, y, statement, params, paramsInfo;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				try {
					if (msg.obj != null) {// 判断网络服务器是否异常)
						mBitmap = (Bitmap) msg.obj;
						// mTouchImageView = new
						// TouchImageView(MainActivity.this);
						// mTouchImageView.setVoronoi(mBitmap);
						// MainActivity.this.setContentView(mTouchImageView);
						mImageView.setImageBitmap(mBitmap);
					} else {
						Toast.makeText(getApplicationContext(), "网络异常",
						        Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				mBar.setVisibility(View.GONE);
				break;
			case 1:
				// String a = msg.obj.toString();
				// Toast.makeText(mContext, "来自服务器返回的位置信息："+a,
				// Toast.LENGTH_SHORT).show();
				try {
					if (msg.obj != null) {// 判断网络服务器是否异常)
						mBitmap = (Bitmap) msg.obj;
						// mTouchImageView = new
						// TouchImageView(MainActivity.this);
						// mTouchImageView.setVoronoi(mBitmap);
						// MainActivity.this.setContentView(mTouchImageView);
						mImageView.setImageBitmap(mBitmap);
					} else {
						Toast.makeText(getApplicationContext(), "网络异常",
						        Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			// 获取所点击层级的信息
			case 2:
				try {
					if (msg.obj != null) {// 判断网络服务器是否异常)
						String result = msg.obj.toString();
						Toast.makeText(getApplicationContext(),
						        "该层级信息：" + result, Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getApplicationContext(), "网络异常",
						        Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.voronoi_view);
		mContext = this;

		initUI();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.analysis:
			String voronoiUrl = mEdtVoronoiUrl.getText().toString();
			new SmgThread(voronoiUrl, 0, handler, Config.TYPE_DEFAULT).start();
			break;
		case R.id.titletext:
			new SmgThread("refresh", 0, handler, Config.TYPE_DEFAULT).start();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageView:
			x = String.valueOf((int) event.getX());
			y = String.valueOf((int) event.getY());
			statement = "点击位置: X-" + x + " y-" + y;
			// Toast.makeText(mContext, statement, Toast.LENGTH_SHORT).show();
			params = x + "&y=" + y;
			paramsInfo = x + "&y=" + y + "&info=info";

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			final String[] items = { "解析下一层级", "获取该层信息" };
			builder.setItems(items, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0:
						new SmgThread(params, 1, handler, Config.TYPE_TOUCH)
						        .start();
						break;
					case 1:
						new SmgThread(paramsInfo, 2, handler,
						        Config.TYPE_GET_INFO).start();
						break;
					default:
						break;
					}
				}
			}).show();
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			setContentView(R.layout.voronoi_view);
		}
		return super.onKeyDown(keyCode, event);
	}

	public void initUI() {
		mImageView = (ImageView) findViewById(R.id.imageView);
		mEdtVoronoiUrl = (EditText) findViewById(R.id.voronoiurl);
		mBtnAnalysis = (Button) findViewById(R.id.analysis);
		mBar = (ProgressBar) findViewById(R.id.progressBar);
		findViewById(R.id.titletext).setOnClickListener(this);
		mBtnAnalysis.setOnClickListener(this);
		mImageView.setOnTouchListener(this);
	}

}