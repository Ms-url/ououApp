package com.example.oo.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.oo.R;
import com.example.oo.util.GlobalData;
import com.example.oo.util.HttpUtil;
import com.example.oo.util.JsonAnalyze;

import java.util.ArrayList;
import java.util.List;

public class WeatherFragment extends Fragment {
    View view;
    List<GlobalData> list = new ArrayList<>();
    JsonAnalyze jsonAnalyze = new JsonAnalyze();
    HttpUtil getConnection = new HttpUtil();
    private String responseData;
    String location=null;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    break;
                case 2:

                case 3:

            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_weather, container, false);

        new Thread(() -> {
            responseData = getConnection.sendGetRequest("https://devapi.qweather.com/v7/weather/now"+location+"&key=2c3d6a1953cd43ccb16e6f72ef24c6c2");
            if (responseData.equals("1")) {
                showResponse(2);
            } else {
              //jsonAnalyze.JsonDataGet_(responseData, list);
                showResponse(1);
            }
        }).start();

        return view;
    }

    private void showResponse(int num) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = new Message();
                message.what = num;
                handler.sendMessage(message);
            }
        }).start();
    }
}