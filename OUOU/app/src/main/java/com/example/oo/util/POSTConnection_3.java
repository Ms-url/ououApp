package com.example.oo.util;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class POSTConnection_3 {

    private String responseData;

    public String getResponseData() {
        return responseData;
    }

    public void setResponseData(String responseData) {
        this.responseData = responseData;
    }

    private HttpURLConnection connection;
    private String cook="";

    public List<String> sendGetNetRequest(String murl, HashMap<String, String> params) {
        POSTConnection_3 post_connection = new POSTConnection_3();
        try {
            URL  url = new URL(murl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(9000);
            connection.setReadTimeout(9000);
            connection.setDoOutput(true);
            connection.setDoInput(true);

            StringBuilder dataTowrite = new StringBuilder();
            for (String key : params.keySet()) {
                dataTowrite.append(key).append("=").append(params.get(key)).append("&");
            }
            connection.connect();

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(dataTowrite.substring(0, dataTowrite.length() - 1).getBytes());

            InputStream in = connection.getInputStream();
            Log.e("send", "ok");
            post_connection.setResponseData(StreamToString(in));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
            post_connection.setResponseData("1");
            Log.e("time1", "请求超时");
        } catch (IOException e) {
            e.printStackTrace();
            post_connection.setResponseData("1");
            Log.e("time2", "请求超时");
        }
        String finally_responseData = post_connection.getResponseData();

        List<String> list = new ArrayList<>();

       Map<String, List<String>> cookies_t2 = connection.getHeaderFields();
        List<String> cookie_t2 = cookies_t2.get("Set-Cookie");
        for (int i = 0; i < cookie_t2.size(); i++) {
            Log.e("cookie", cookie_t2.get(i));
            String[] k = cookie_t2.get(i).split(";");
            list.add(k[0]);
            Log.e("first",list.get(0));
            cook= cook+list.get(i) +";";
            Log.e("cook",cook);
        }

        List<String> list_re = new ArrayList<>();
        list_re.add(finally_responseData);
        list_re.add(cook);
        return list_re;
    }

    private String StreamToString(InputStream in) {
        StringBuilder sb = new StringBuilder();
        String oneLine;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        try {
            while ((oneLine = reader.readLine()) != null) {
                sb.append(oneLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

}
