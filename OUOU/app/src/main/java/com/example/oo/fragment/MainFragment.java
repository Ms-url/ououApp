package com.example.oo.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.oo.R;
import com.example.oo.adapter.MainAdapter;
import com.example.oo.util.GlobalData;
import com.example.oo.util.HttpUtil;
import com.example.oo.util.JsonAnalyze;
import com.example.oo.util.MySQLiteOpenHelper;
import com.example.oo.util.ThreadPoolManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class MainFragment extends Fragment {
    View view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    Banner banner;
    List<GlobalData> list = new ArrayList<>();
    List<String> list_path = new ArrayList<>();
    JsonAnalyze jsonAnalyze = new JsonAnalyze();
    MainAdapter mainAdapter = new MainAdapter(list, list_path);
    HttpUtil getConnection = new HttpUtil();
    private String responseData;
    private MySQLiteOpenHelper mySQLiteOpenHelper;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(mainAdapter);
                    swipeRefreshLayout.setRefreshing(false);
                    Log.e("UIchange", "ui");
                    break;
                case 2:
                    Toast.makeText(getActivity(), "请求超时", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                case 3:
                    Toast toast = Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
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
        view = inflater.inflate(R.layout.fragment_main, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_main);
        recyclerView = view.findViewById(R.id.recycler_main);
        fab = view.findViewById(R.id.fab_add);
        banner = view.findViewById(R.id.main_banner);

        mySQLiteOpenHelper = new MySQLiteOpenHelper(getContext(),"Cities.db",null,1);
        mySQLiteOpenHelper.getWritableDatabase();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "add", Toast.LENGTH_SHORT).show();
            }
        });

        list.add(new GlobalData());
        list.add(new GlobalData());
        list.add(new GlobalData());
        list_path.add("https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png");
        showResponse(1);
        
       Runnable runnable = new Runnable() {
           @Override
           public void run() {
          /*     responseData = getConnection.sendGetRequest("  ", cook);
            if (responseData.equals("1")) {
                showResponse(2);
            } else {
                jsonAnalyze.JsonDataGet_(responseData, list);
                showResponse(1);
            } */
           }
       };
       ThreadPoolManager.getInstance().execute(runnable);
        return view;
    }

    private void refresh() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                /*    responseData = getConnection.sendGetNetRequest("  ", cook);
            if (responseData.equals("1")) {
                showResponse(2);
            } else {
                jsonAnalyze.JsonDataGet_(responseData, list);
                showResponse(1);
            }*/
                list.add(new GlobalData());
                list.add(new GlobalData());
                list_path.add("https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png");
                list_path.add("https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png");
                showResponse(1);
            }
        };
        ThreadPoolManager.getInstance().execute(runnable);

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