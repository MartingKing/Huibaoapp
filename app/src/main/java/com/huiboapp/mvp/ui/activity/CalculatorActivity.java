package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiboapp.di.component.DaggerCalculatorComponent;
import com.jess.arms.di.component.AppComponent;
import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.app.utils.NumberUtils;
import com.huiboapp.di.module.CalculatorModule;
import com.huiboapp.mvp.contract.CalculatorContract;
import com.huiboapp.mvp.model.entity.InterestEntity;
import com.huiboapp.mvp.presenter.CalculatorPresenter;
import com.huiboapp.mvp.ui.adapter.InterestListAdapter;
import com.huiboapp.mvp.ui.widget.dialog.CalculatorTimeDialog;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 贷款计算器 todo 暂时无用的页面
 */
public class CalculatorActivity extends MBaseActivity<CalculatorPresenter> implements CalculatorContract.View, CalculatorTimeDialog.CalculatorTimeListener {

    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.rvInterestList)
    RecyclerView rvInterestList;

    @BindView(R.id.tvCapital)
    TextView tvCapital;
    @BindView(R.id.tvInterest)
    TextView tvInterest;
    @BindView(R.id.tvTotalSum)
    TextView tvTotalSum;

    @BindView(R.id.etAdvanceAmount)
    EditText etAdvanceAmount;
    @BindView(R.id.llayoutAdvanceTime)
    LinearLayout llayoutAdvanceTime;
    @BindView(R.id.tvAdvanceTime)
    TextView tvAdvanceTime;
    @BindView(R.id.etAdvanceInterest)
    EditText etAdvanceInterest;

    @BindView(R.id.tvAverageCapital)
    TextView tvAverageCapital;
    @BindView(R.id.tvAverageCapitalPlusInterest)
    TextView tvAverageCapitalPlusInterest;

    InterestListAdapter adapter;

    boolean isAverageCapital = true;
    CalculatorTimeDialog calculatorTimeDialog;
    int advanceTime = 0;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCalculatorComponent
                .builder()
                .appComponent(appComponent)
                .calculatorModule(new CalculatorModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_calculator;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isBarDarkFont = false;

        adapter = new InterestListAdapter();
        adapter.setEmptyView(R.layout.layout_empty_view, (ViewGroup) rvInterestList.getParent());
        rvInterestList.setLayoutManager(new LinearLayoutManager(this));
        rvInterestList.setAdapter(adapter);

        tvAverageCapital.setOnClickListener(this);
        tvAverageCapitalPlusInterest.setOnClickListener(this);

        etAdvanceAmount.addTextChangedListener(textWatcher);
        etAdvanceInterest.addTextChangedListener(textWatcher);

        ivBack.setOnClickListener(this);
        llayoutAdvanceTime.setOnClickListener(this);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (isAverageCapital)
                toAverageCapital();
            else
                toAverageCapitalPlusInterest();
        }
    };

    @Override
    protected void initViewClick(int id) {
        if(id == R.id.ivBack){
            finish();
        }
        if(id == R.id.llayoutAdvanceTime){
            if (calculatorTimeDialog == null)
                calculatorTimeDialog = new CalculatorTimeDialog(this);
            calculatorTimeDialog.show();
        }
        if (id == R.id.tvAverageCapital) {
            isAverageCapital = true;

            tvAverageCapital.setBackgroundResource(R.drawable.bg_fill_blue_60);
            tvAverageCapitalPlusInterest.setBackgroundResource(R.drawable.bg_border_blue_60);
            tvAverageCapital.setTextColor(getResources().getColor(R.color.white));
            tvAverageCapitalPlusInterest.setTextColor(getResources().getColor(R.color.app_blue));

            toAverageCapital();
        }
        if (id == R.id.tvAverageCapitalPlusInterest) {
            isAverageCapital = false;

            tvAverageCapital.setBackgroundResource(R.drawable.bg_border_blue_60);
            tvAverageCapitalPlusInterest.setBackgroundResource(R.drawable.bg_fill_blue_60);
            tvAverageCapital.setTextColor(getResources().getColor(R.color.app_blue));
            tvAverageCapitalPlusInterest.setTextColor(getResources().getColor(R.color.white));

            toAverageCapitalPlusInterest();
        }
    }

    private void toAverageCapital(){
        double advanceAmount = NumberUtils.stringToDouble(etAdvanceAmount.getText().toString().trim());
        double advanceInterest = NumberUtils.stringToDouble(etAdvanceInterest.getText().toString().trim()) / 100;

        setTextBySize(tvCapital, NumberUtils.roundDownStr(2, advanceAmount + ""));

        if (advanceAmount == 0 || advanceTime == 0 || advanceInterest == 0) return;

        ArrayList<InterestEntity> datas = new ArrayList<>();
        double principal = advanceAmount / advanceTime;
        double interestsD = 0;
        for (int i = 1; i <= advanceTime; i++) {
            // 最后一期为剩余金额
            // if (i == advanceTime) principal = (double)advanceAmount - principal * (advanceTime - 1);
            double interestD = NumberUtils.roundHalfUp(2, (advanceAmount - (i - 1) * principal) * advanceInterest);
            interestsD += interestD;
            InterestEntity interest = new InterestEntity();
            interest.setNum(i);
            interest.setPrincipal(NumberUtils.roundHalfUp(2, principal));// 本金
            interest.setInterest(interestD);// 利息
            interest.setAmount(NumberUtils.roundHalfUp(2, principal + interestD));// 应还

            datas.add(interest);
        }

        adapter.setNewData(datas);

        setTextBySize(tvInterest, NumberUtils.roundDownStr(2, interestsD + ""));
        setTextBySize(tvTotalSum, NumberUtils.roundDownStr(2, advanceAmount + interestsD + ""));
    }

    private void toAverageCapitalPlusInterest(){
        double advanceAmount = NumberUtils.stringToDouble(etAdvanceAmount.getText().toString().trim());
        double advanceInterest = NumberUtils.stringToDouble(etAdvanceInterest.getText().toString().trim()) / 100;

        tvCapital.setText(NumberUtils.roundDownStr(2, advanceAmount + ""));

        if (advanceAmount == 0 || advanceTime == 0 || advanceInterest == 0) return;

        ArrayList<InterestEntity> datas = new ArrayList<>();
        double amount = advanceAmount * advanceInterest * Math.pow((1 + advanceInterest), advanceTime)
                / (Math.pow((1 + advanceInterest), advanceTime) - 1);
        amount = NumberUtils.roundHalfUp(2, amount);
        double principalY = 0;

        for (int i = 1; i <= advanceTime; i++) {
            double interestD = NumberUtils.roundHalfUp(2, (advanceAmount - principalY) * advanceInterest);
            double principal = amount - interestD;
            principalY += principal;
            InterestEntity interest = new InterestEntity();
            interest.setNum(i);
            interest.setPrincipal(NumberUtils.roundHalfUp(2, principal));// 本金
            interest.setInterest(interestD);// 利息
            interest.setAmount(amount);// 应还

            datas.add(interest);
        }

        adapter.setNewData(datas);

        double totalSum = amount * advanceTime;
        tvInterest.setText(NumberUtils.roundDownStr(2,  NumberUtils.roundHalfUp(2, totalSum - advanceAmount)+ ""));
        tvTotalSum.setText(NumberUtils.roundDownStr(2, totalSum + ""));
    }

    private void setTextBySize(TextView textView, String text){
        if (text.length() <= 7) {

        } else if (text.length() <= 8) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        } else if (text.length() <= 10) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        } else if (text.length() <= 12) {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        } else {
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        }
        textView.setText(text);
    }

    @Override
    public void getCalculatorTime(CalculatorTimeDialog.WheelViewOption time) {
        advanceTime = time.getOptionValue();
        tvAdvanceTime.setText(time.getOptionName());
        if (calculatorTimeDialog != null && calculatorTimeDialog.isShowing())
            calculatorTimeDialog.dismiss();

        if (isAverageCapital)
            toAverageCapital();
        else
            toAverageCapitalPlusInterest();
    }
}
