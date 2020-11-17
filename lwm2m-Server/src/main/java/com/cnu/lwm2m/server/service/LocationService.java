package com.cnu.lwm2m.server.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class LocationService {

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> getLocation(String ip) {
//		float lat = 0;
//		float lng = 0;
		Gson gson = new Gson();
		HashMap<String, Object> resultMap = null;
		try {
			URL url = new URL("http://ip-api.com/json/" + ip);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(5000);
			con.setReadTimeout(5000);
			int responseCode = con.getResponseCode();
			String inputLine;
			StringBuffer responseBuffer = new StringBuffer();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			while ((inputLine = in.readLine()) != null) {
				responseBuffer.append(inputLine);
			}
			in.close();
			if (200 == responseCode) {
				resultMap = gson.fromJson(responseBuffer.toString(), HashMap.class);
				/*
				 * if ("success".equals(resultMap.get("status"))) { double latDouble = (double)
				 * resultMap.get("lat"); lat = (float) latDouble;
				 * 
				 * double lngDouble = (double) resultMap.get("lon"); lng = (float) lngDouble; }
				 * else { lat = (float) 0.0; lng = (float) 0.0; }
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

//		log.info("{}", resultMap);
//		log.info("{},{}", lat, lng);

		return resultMap;
	}
}
