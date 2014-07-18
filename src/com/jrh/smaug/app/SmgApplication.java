package com.jrh.smaug.app;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class SmgApplication {

	/**
	 * 根据URL从服务器得到xml的字符串
	 * @param url
	 * @return
	 */
	public static String getXmlByUrl(String url){
		String result = null;
		HttpGet request = new HttpGet(url);//实例化get请求
		DefaultHttpClient client = new DefaultHttpClient();//实例化客户端
		try {
			HttpResponse response = client.execute(request);//执行该请求,得到服务器端的响应内容
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = EntityUtils.toString(response.getEntity(),"GBK");//把响应结果转成String
			} else {
				result = response.getStatusLine().toString();
			}
		} catch (Exception e) {
			return e.getMessage();
		}
		return result;
	}
	
	/**
	 * 根据传进来的xml字符串来解析成Vorinoi树形图
	 * @param xml
	 */
	public static void analyzeXml(String xml){
		
	}
	
	
	/**
	 * 通过url获取服务器端的Voronoi图片
	 * @param url
	 */
	  //加载图片  
    public static Bitmap getVoronoiImage(String url) {  
        Bitmap bmp = null;  
        try {  
            URL myurl = new URL(url);  
            // 获得连接  
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();  
            conn.setConnectTimeout(6000);//设置超时  
            conn.setDoInput(true);  
            conn.setUseCaches(false);//不缓存  
            conn.connect();  
            InputStream is = conn.getInputStream();//获得图片的数据流  
            bmp = BitmapFactory.decodeStream(is);  
            is.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return bmp;  
    }  
}
