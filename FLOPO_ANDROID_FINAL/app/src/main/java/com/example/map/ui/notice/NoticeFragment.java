package com.example.map.ui.notice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.map.R;

public class NoticeFragment extends Fragment {

    private NoticeViewModel noticeViewModel;

    private TextView textView1, textView2, textView3;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        noticeViewModel = ViewModelProviders.of(this).get(NoticeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notice, container, false);
        /*final TextView textView = root.findViewById(R.id.text_notice);
        noticeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        textView1 = root.findViewById(R.id.textTime);
        textView2 = root.findViewById(R.id.textTime);
        textView3 = root.findViewById(R.id.textTime);

        return root;
    }
}