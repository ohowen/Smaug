package com.jrh.smaug.app;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;

import com.jrh.smaug.util.Config;

public class SmgThread extends Thread {
	private String url;
	private int what;
	private Handler handler;
	private String type;

	public SmgThread(String url, int what, Handler handler, String type) {
		this.url = url;
		this.what = what;
		this.handler = handler;
		this.type = type;
	}

	@Override
	public void run() {
		if (type.equals(Config.TYPE_DEFAULT)) {
			try {
				String action = "/servlet/AnalysisAction?analysis_file=";
				Bitmap result = SmgApplication.getVoronoiImage(Config.SITE_TEST
				        + action + url);
				if (result != null && !result.equals("")) {
					// System.out.println("线程获取到的结果："+result);
					Message msg = new Message();
					msg.what = what;
					msg.obj = result;
					handler.sendMessage(msg);
				}
			} catch (Exception e) {
				System.out.println("线程获取数据异常：" + url);
				handler.sendMessage(handler.obtainMessage(-1, null));
			}
		}
		if (type.equals(Config.TYPE_TOUCH)) {
			String action = "/servlet/AnalysisAction?x=";
			Bitmap result = SmgApplication.getVoronoiImage(Config.SITE_TEST
			        + action + url);
			try {
				if (result != null && !result.equals("")) {
					Message msg = new Message();
					msg.what = what;
					msg.obj = result;
					handler.sendMessage(msg);
				}
			} catch (Exception e) {
				System.out.println("线程获取数据异常：" + url);
				handler.sendMessage(handler.obtainMessage(-1, null));
			}
		}

		if (type.equals(Config.TYPE_GET_INFO)) {
			String action = "/servlet/AnalysisAction?x=";
			String result = SmgApplication.getXmlByUrl(Config.SITE_TEST + action + url);
			try {
				if(result != null && !result.equals("")){
					Message msg = new Message();
					msg.what = what;
					msg.obj = result;
					handler.sendMessage(msg);
				}
            } catch (Exception e) {
            	System.out.println("线程获取数据异常：" + url);
            	handler.sendMessage(handler.obtainMessage(-1, null));            	
            }
		}

	}
}