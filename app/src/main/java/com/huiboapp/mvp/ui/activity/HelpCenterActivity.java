package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.component.DaggerHelpCenterComponent;
import com.huiboapp.di.module.HelpCenterModule;
import com.huiboapp.mvp.contract.HelpCenterContract;
import com.huiboapp.mvp.model.entity.ProblemEntity;
import com.huiboapp.mvp.presenter.HelpCenterPresenter;
import com.huiboapp.mvp.ui.adapter.ProblemListAdapter;
import com.jess.arms.di.component.AppComponent;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;

/**
 * 常见问题
 */
public class HelpCenterActivity extends MBaseActivity<HelpCenterPresenter> implements HelpCenterContract.View {


    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlayoutTitle)
    LinearLayout rlayoutTitle;
    @BindView(R.id.rvProblemList)
    RecyclerView rvProblemList;

    ProblemListAdapter adapter;
    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerHelpCenterComponent
                .builder()
                .appComponent(appComponent)
                .helpCenterModule(new HelpCenterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_help_center;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tvTitle.setText(getResources().getText(R.string.common_question));
//        rlayoutTitle.setBackgroundColor(ContextCompat.getColor(this, R.color.app_status_bar));
        adapter = new ProblemListAdapter();
        rvProblemList.setLayoutManager(new LinearLayoutManager(this));
        rvProblemList.setAdapter(adapter);

        //添加自定义分割线
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(Objects.requireNonNull(ContextCompat.getDrawable(this, R.drawable.item_divider2)));
        rvProblemList.addItemDecoration(divider);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            ProblemEntity problem = (ProblemEntity) adapter.getData().get(position);
            problem.setOpen(!problem.isOpen());
            adapter.notifyDataSetChanged();
        });


        ArrayList<ProblemEntity> data = new ArrayList<>();
        data.add(new ProblemEntity(R.string.problem01, R.string.problem_info00));
        data.add(new ProblemEntity(R.string.problem01, R.string.problem_info01));
        data.add(new ProblemEntity(R.string.problem02, R.string.problem_info02));
        data.add(new ProblemEntity(R.string.problem03, R.string.problem_info03));
        data.add(new ProblemEntity(R.string.problem04, R.string.problem_info04));
        data.add(new ProblemEntity(R.string.problem05, R.string.problem_info05));
        data.add(new ProblemEntity(R.string.problem06, R.string.problem_info06));
        data.add(new ProblemEntity(R.string.problem07, R.string.problem_info07));
        data.add(new ProblemEntity(R.string.problem08, R.string.problem_info08));

//        adapter.setNewData(data);

        ivBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivBack) {
            finish();
        }
    }
}
