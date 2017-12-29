package com.nanda.singlechecksectionheader.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.nanda.singlechecksectionheader.R;
import com.nanda.singlechecksectionheader.adapter.MultiSelectionAdapter;
import com.nanda.singlechecksectionheader.base.BaseActivity;
import com.nanda.singlechecksectionheader.model.ChildItem;
import com.nanda.singlechecksectionheader.model.HeaderItem;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MultiSelectStickyActivity extends BaseActivity {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private List<HeaderItem> headerList;
    private StickyHeaderLayoutManager stickyHeaderLayoutManager;
    private MultiSelectionAdapter adapter;

    public static Intent getIntent(Context context) {
        return new Intent(context, MultiSelectStickyActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        ButterKnife.bind(this);

        stickyHeaderLayoutManager = new StickyHeaderLayoutManager();
        stickyHeaderLayoutManager.setItemPrefetchEnabled(false);
        recyclerview.setLayoutManager(stickyHeaderLayoutManager);
        recyclerview.setItemAnimator(null);
        adapter = new MultiSelectionAdapter(this);
        recyclerview.setAdapter(adapter);

        setData();
        adapter.setStickyItemList(headerList);
    }

    @OnClick(R.id.btn_get_selected)
    void onClicked() {
        List<HeaderItem> selectedItemList = adapter.fetchSelectedItemsInList();
        if (selectedItemList != null && selectedItemList.size() > 0) {
            Toast.makeText(this, "" + selectedItemList.size(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setData() {
        headerList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            List<ChildItem> stickyItemList = new ArrayList<>();
            Random random = new Random();
            for (int j = 0; j < random.nextInt(20); j++) {
                stickyItemList.add(new ChildItem("Child Item - " + j));
            }
            headerList.add(new HeaderItem("Header Item - " + i, stickyItemList));
        }
    }

}
