package com.nanda.singlechecksectionheader.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.nanda.singlechecksectionheader.R;
import com.nanda.singlechecksectionheader.adapter.SingleSectionAdapter;
import com.nanda.singlechecksectionheader.model.ChildItem;
import com.nanda.singlechecksectionheader.model.HeaderItem;

import org.zakariya.stickyheaders.StickyHeaderLayoutManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SingleSelectStickyActivity extends AppCompatActivity implements SingleSectionAdapter.ClickListener {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;

    private List<HeaderItem> headerList;
    private SingleSectionAdapter adapter;
    private StickyHeaderLayoutManager stickyHeaderLayoutManager;

    public static Intent getIntent(Context context) {
        return new Intent(context, SingleSelectStickyActivity.class);
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
        adapter = new SingleSectionAdapter(this, this);
        recyclerview.setAdapter(adapter);

        setData();
        adapter.setStickyItemList(headerList);
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

    @Override
    public void onItemSelected(HeaderItem item, int headerPosition, int childPosition) {
        if (item == null) {
            return;
        }
        Log.e("Header", item.getTitle());
        List<ChildItem> childItemList = item.getChildItemList();
        if (childItemList != null && childItemList.size() > 0) {
            Log.e("Child", childItemList.get(childPosition).getMessage());
        }
    }
}
