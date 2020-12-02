package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.huiboapp.di.component.DaggerFeedbackComponent;
import com.jess.arms.di.component.AppComponent;
import com.huiboapp.BuildConfig;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.module.FeedbackModule;
import com.huiboapp.mvp.common.H5Urls;
import com.huiboapp.mvp.contract.FeedbackContract;
import com.huiboapp.mvp.presenter.FeedbackPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * 今日有借 反馈
 */
public class FeedbackActivity extends MBaseActivity<FeedbackPresenter> implements FeedbackContract.View {

    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.etContent)
    EditText etContent;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.tvCommit)
    TextView tvCommit;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.changeIp)
    TextView mChangeIp;
    @BindView(R.id.rlayoutTitle)
    RelativeLayout rlayoutTitle;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerFeedbackComponent
                .builder()
                .appComponent(appComponent)
                .feedbackModule(new FeedbackModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_feedback;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        ivBack.setOnClickListener(this);
        tvCommit.setOnClickListener(this);
        mChangeIp.setOnClickListener(this);
        rlayoutTitle.setBackgroundColor(ContextCompat.getColor(this, R.color.app_bg));
        tvTitle.setText(getResources().getText(R.string.feed_back));
        if (BuildConfig.LOG_DEBUG) {
            mChangeIp.setVisibility(View.VISIBLE);
        } else {
            mChangeIp.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initViewClick(int id) {
        switch (id) {
            case R.id.ivBack:
                finish();
                break;
            case R.id.tvCommit:
                mPresenter.addFeedback(etContent.getText().toString().trim(), etPhone.getText().toString().trim());
                break;
            case R.id.changeIp:
                showChoiceDomainDialog();
                break;
        }
    }

    private void showChoiceDomainDialog() {
        List<String> data = new ArrayList<>();
        String[] servers = this.getResources().getStringArray(R.array.server_array);
        for (int i = 0; i < servers.length; i++) {
            data.add(i, servers[i]);
        }

        View view = getLayoutInflater().inflate(R.layout.controller_volume, null);
        AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle("Change Domain")
                .setIcon(R.mipmap.ic_launcher)
                .setView(view)
                .setPositiveButton("return", (paramAnonymousDialogInterface, paramAnonymousInt) -> {

                }).create();
        ListView listView = view.findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FeedbackActivity.this, android.R.layout.simple_list_item_1, data);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RetrofitUrlManager.getInstance().putDomain("kdj", H5Urls.getH5Url(adapter.getItem(position)));
                alertDialog.dismiss();
            }
        });

        alertDialog.show();
    }

    @Override
    public void clearData() {
        etContent.setText("");
        etPhone.setText("");
    }

}
