package com.example.oo.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oo.R;
import com.example.oo.util.GlobalData;


public class SelfFragment extends Fragment {
    View view;
    private TextView textView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_self, container, false);
        textView = view.findViewById(R.id.self_s);

        textView.setText(GlobalData.sessionId);

        return view;
    }
}