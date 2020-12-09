package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.component.DaggerFeedbackComponent;
import com.huiboapp.di.module.FeedbackModule;
import com.huiboapp.mvp.contract.FeedbackContract;
import com.huiboapp.mvp.presenter.FeedbackPresenter;
import com.jess.arms.di.component.AppComponent;

import butterknife.BindView;

/**
 * 今日有借 反馈
 */
public class FeedbackActivity extends MBaseActivity<FeedbackPresenter> implements FeedbackContract.View {


    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlayoutTitle)
    LinearLayout rlayoutTitle;
    @BindView(R.id.etContent)
    EditText etContent;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.tvCommit)
    TextView tvCommit;

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
        tvTitle.setText(getResources().getText(R.string.feed_back));
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
        }
    }


    @Override
    public void clearData() {
        etContent.setText("");
        etPhone.setText("");
    }

}
