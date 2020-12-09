package com.huiboapp.mvp.ui.adapter;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huiboapp.R;


public class SelectCityAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public interface OnCitySelectedListerner{
        void onCLick(String city);
    }
    private OnCitySelectedListerner citySelectedListerner;

    public void setCitySelectedListerner(OnCitySelectedListerner citySelectedListerner) {
        this.citySelectedListerner = citySelectedListerner;
    }

    public SelectCityAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.radio, item);
        RadioButton radioButton = helper.getView(R.id.radio);
        RelativeLayout container = helper.getView(R.id.container);
        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton.setChecked(true);
                citySelectedListerner.onCLick(item);
            }
        });
    }


}
