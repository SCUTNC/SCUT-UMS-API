package com.demo.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import net.sf.json.JSONObject;

@Controller
@RequestMapping(value="/test")
public class TestUrlController {
	
	
	@RequestMapping(value = "/getAuthentication")
	public @ResponseBody JSONObject getAuthentication(HttpServletResponse res,
			@RequestBody JSONObject json) {

		try {
			URL url= new URL("http://ghxs.88u.cas.scut.edu.cn/Wisdom/qq/getAuthentication");
			HttpURLConnection conn = null;
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			 
			conn.setDoOutput(true);
			conn.setDoInput(true);

			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setRequestProperty("Content-Type","application/json");
		
			OutputStream out = conn.getOutputStream();
			
			out.write(json.toString().getBytes());
			out.close();
			
			InputStream in = conn.getInputStream();
			BufferedReader reader =
			          new BufferedReader(new InputStreamReader(in));

	        String line;
	        StringBuffer pageBuffer = new StringBuffer();
	        while ((line = reader.readLine()) != null) {
	          pageBuffer.append(line);
	        }
			
	        in.close();
	        reader.close();
	        
	        json = null;
	        json = JSONObject.fromObject(pageBuffer.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return json;
	}
}
