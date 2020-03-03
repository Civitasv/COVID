package com.gis.application.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 使用高德解析地名
 */
public class GaodeHttp {
	// 高德api
	public static String MAP_AK = "17b3ad7ccaafe0b0fd1041ce89d20024";
	public static String MAP_URL = "http://restapi.amap.com/v3/geocode/geo?output=JSON&key=" + MAP_AK;


	/**
	 * 将地址解析成经纬度
	 *
	 * @param address
	 *            地址，例：浙江省杭州市西湖区
	 * @return 返回经纬度数据。例：{"lng":120.08899292561351,"lat":30.207036169515438}
	 */
	public static JSONObject getPosition(String address) {
		HttpURLConnection connection = null;
		InputStream is = null;
		BufferedReader br = null;
		String result = null;// 返回结果字符串
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("lat",0);
		jsonObject.put("lng",0);
		try {
			// 创建远程url连接对象
			URL url = new URL(MAP_URL + "&address=" + URLEncoder.encode(address,"UTF-8"));
			// 通过远程url连接对象打开一个连接，强转成httpURLConnection类
			connection = (HttpURLConnection) url.openConnection();
			// 设置连接方式：get
			connection.setRequestMethod("GET");
			// 设置连接主机服务器的超时时间：15000毫秒
			connection.setConnectTimeout(15000);
			// 设置读取远程返回的数据时间：60000毫秒
			connection.setReadTimeout(60000);
			// 发送请求
			connection.connect();
			// 通过connection连接，获取输入流
			if (connection.getResponseCode() == 200) {
				is = connection.getInputStream();
				// 封装输入流is，并指定字符集
				br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				// 存放数据
				StringBuffer sbf = new StringBuffer();
				String temp = null;
				while ((temp = br.readLine()) != null) {
					sbf.append(temp);
					sbf.append("\r\n");
				}
				result = sbf.toString();
			}
			JSONObject jsonObject1 = new JSONObject(result);
			JSONArray jsonArray = jsonObject1.getJSONArray("geocodes");
			JSONObject jsonObject2 = jsonArray.getJSONObject(0);
			String lnglat = jsonObject2.getString("location");
			String[] location = lnglat.split(",");
			JSONObject jsonObject3 = new JSONObject();
			jsonObject3.put("lng",Double.parseDouble(location[0]));
			jsonObject3.put("lat",Double.parseDouble(location[1]));
			System.out.println(jsonObject3);
			return jsonObject3;
		} catch (Exception e) {
			return jsonObject;
		}
	}

}
