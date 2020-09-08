package com.alp.familymart.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.alp.familymart.R;

public class NotificationsFragment extends Fragment {

    ListView listView;
    ListAdapter listAdapter = null;
    TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        listView = root.findViewById(R.id.lv_notification);
        textView = root.findViewById(R.id.tv_no_notification);

        if (listAdapter == null) {
            listView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        }

        else {
            listView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);
        }

        return root;
    }
}