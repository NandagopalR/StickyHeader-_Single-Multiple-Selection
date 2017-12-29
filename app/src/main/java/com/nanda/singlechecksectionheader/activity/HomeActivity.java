package com.nanda.singlechecksectionheader.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.nanda.singlechecksectionheader.R;
import com.nanda.singlechecksectionheader.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_multi_selection, R.id.btn_single_selection})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.btn_multi_selection:
                startActivity(MultiSelectStickyActivity.getIntent(this));
                break;
            case R.id.btn_single_selection:
                startActivity(SingleSelectStickyActivity.getIntent(this));
                break;
        }

    }
}
