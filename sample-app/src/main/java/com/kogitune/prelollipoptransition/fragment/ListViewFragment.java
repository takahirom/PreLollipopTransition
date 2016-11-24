package com.kogitune.prelollipoptransition.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.kogitune.prelollipoptransition.ListViewActivity;
import com.kogitune.prelollipoptransition.R;
import com.kogitune.prelollipoptransition.SubActivity;

/**
 * Created by Joe on 2016/11/24.
 * Email lovejjfg@gmail.com
 */

public class ListViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_list_view, container, false);



        final ListView listView = (ListView) inflate.findViewById(R.id.list);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 10;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return inflater.inflate(R.layout.list_row, parent, false);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Intent intent = new Intent(getContext(), SubActivity.class);
                ActivityTransitionLauncher.with(getActivity()).from(view.findViewById(R.id.image)).launch(intent);
            }
        });
        return inflate;
    }

}
