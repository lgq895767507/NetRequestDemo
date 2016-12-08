package com.flw.netrequestdemo;

import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentsAct extends BaseActivity {

    @BindView(R.id.contents)
    TextView mContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents);
        ButterKnife.bind(this);

        mContents.setText("hello");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }
}
