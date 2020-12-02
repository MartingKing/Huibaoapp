package com.huiboapp.mvp.ui.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.contrarywind.adapter.WheelAdapter;
import com.contrarywind.view.WheelView;
import com.huiboapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取贷款期数
 *
 * Created by yaojian on 2018/11/23 11:11
 */
public class CalculatorTimeDialog extends Dialog {

    private View mView;

    private CalculatorTimeListener listener;
    private WheelViewOption calTime;

    public CalculatorTimeDialog(@NonNull Context context) {
        super(context, R.style.PromptDialogStyle);
        Context context1 = context;

        if(context instanceof CalculatorTimeListener)
            listener = (CalculatorTimeListener) context;

        initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mView);
        setCanceledOnTouchOutside(true);
        // 设置显示动画
        Window window = getWindow();
        window.setWindowAnimations(R.style.bottom_show_anim_style);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        wl.gravity = Gravity.BOTTOM;
        onWindowAttributesChanged(wl);
    }

    private void initView() {
        mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_calculator_time, null, false);

        LinearLayout llayoutBg = mView.findViewById(R.id.llayoutBg);
        TextView tvCancel = mView.findViewById(R.id.tvCancel);
        TextView tvConfirm = mView.findViewById(R.id.tvConfirm);

        WheelView wvTime = mView.findViewById(R.id.wvTime);

        llayoutBg.setOnClickListener(v -> dismiss());
        tvCancel.setOnClickListener(v -> dismiss());

        final List<WheelViewOption> options = new ArrayList<>();
        options.add(new WheelViewOption("1个月", 1));
        options.add(new WheelViewOption("2个月", 2));
        options.add(new WheelViewOption("3个月", 3));
        options.add(new WheelViewOption("6个月", 6));
//        options.add(new WheelViewOption("一年", 12));
//        options.add(new WheelViewOption("两年", 24));
//        options.add(new WheelViewOption("三年", 36));
//        options.add(new WheelViewOption("五年", 60));
//        options.add(new WheelViewOption("十年", 120));

        wvTime.setAdapter(new MyAdapter(options));
        wvTime.setOnItemSelectedListener(index -> calTime = options.get(index));
        wvTime.setCurrentItem(0);
        calTime = options.get(0);

        tvConfirm.setOnClickListener(v -> {
            if (listener != null)
                listener.getCalculatorTime(calTime);
        });
    }

    public interface CalculatorTimeListener{
        void getCalculatorTime(WheelViewOption time);
    }

    public class WheelViewOption {
        private String optionName;
        private int optionValue;

        public WheelViewOption(String optionName, int optionValue) {
            this.optionName = optionName;
            this.optionValue = optionValue;
        }

        public String getOptionName() {
            return optionName;
        }

        public void setOptionName(String optionName) {
            this.optionName = optionName;
        }

        public int getOptionValue() {
            return optionValue;
        }

        public void setOptionValue(int optionValue) {
            this.optionValue = optionValue;
        }
    }

    class MyAdapter implements WheelAdapter {
        // items
        private List<WheelViewOption> items;

        /**
         * Constructor
         * @param items the items
         */
        public MyAdapter(List<WheelViewOption> items) {
            this.items = items;

        }

        @Override
        public Object getItem(int index) {
            if (index >= 0 && index < items.size()) {
                return items.get(index).getOptionName();
            }
            return "";
        }

        @Override
        public int getItemsCount() {
            return items.size();
        }

        @Override
        public int indexOf(Object o){
            return items.indexOf(o);
        }
    }
}
