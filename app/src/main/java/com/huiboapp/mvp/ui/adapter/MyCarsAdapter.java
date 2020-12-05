package com.huiboapp.mvp.ui.adapter;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huiboapp.R;
import com.huiboapp.mvp.model.entity.UserInfoEntity;


public class MyCarsAdapter extends BaseQuickAdapter<UserInfoEntity.CarList, BaseViewHolder> {

    public MyCarsAdapter() {
        super(R.layout.item_my_cars, null);
    }

    public interface DeleteBindedCarInterface {
        void delete(String id, String plate, String color);

        void onAutoPay(String id, String plate, String color, boolean auto);
    }

    private DeleteBindedCarInterface deleteBindedCarInterface;

    public void setDeleteBindedCarInterface(DeleteBindedCarInterface deleteBindedCarInterface) {
        this.deleteBindedCarInterface = deleteBindedCarInterface;
    }

    @Override
    protected void convert(BaseViewHolder helper, UserInfoEntity.CarList item) {
        helper.setText(R.id.tv_check_time, "距离车检还有" + item.getInspectionexpire() + "天");
        helper.setText(R.id.tv_carnumber, item.getPlate());
        helper.setText(R.id.iv_car_status, getStatus(item.getApprovalstatus()));
        TextView textViewv = helper.getView(R.id.tv_delete);
        textViewv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteBindedCarInterface.delete(item.getId(), item.getPlate(), item.getPlatecolor());
            }
        });
        Switch s = helper.getView(R.id.autopay);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                deleteBindedCarInterface.onAutoPay(item.getId(), item.getPlate(), item.getPlatecolor(), isChecked);
            }
        });
    }

    private String getStatus(String s) {
        String status = "审核中";
        if (s.equals("tobeapproved")) {
            status = "审核中";
        }
        if (s.equals("approved")) {
            status = "审核通过";
        }
        if (s.equals("rejected")) {
            status = "审核未通过";
        }
        return status;
    }

}
