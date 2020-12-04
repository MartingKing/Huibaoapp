package com.huiboapp.mvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.huiboapp.R;
import com.huiboapp.app.base.MBaseFragment;
import com.huiboapp.app.utils.RegexUtils;
import com.huiboapp.di.component.DaggerDetectorComponent;
import com.huiboapp.di.module.DetectorModule;
import com.huiboapp.mvp.contract.DetectorContract;
import com.huiboapp.mvp.model.cache.UserInfoHelper;
import com.huiboapp.mvp.model.constant.MyConstant;
import com.huiboapp.mvp.model.entity.ProductListEntity;
import com.huiboapp.mvp.presenter.DetectorPresenter;
import com.huiboapp.mvp.ui.activity.LoginActivity;
import com.huiboapp.mvp.ui.adapter.DectactorAdapter;
import com.huiboapp.mvp.ui.widget.dialog.DialogUtil;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.PjbTextUtils;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 一定借到钱
 */
public class DetectorFragment extends MBaseFragment<DetectorPresenter> implements DetectorContract.View, RadioGroup.OnCheckedChangeListener {


    @BindView(R.id.banner)
    ImageView mBanner;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_id)
    EditText etId;
    @BindView(R.id.amout1)
    RadioButton amout1;
    @BindView(R.id.amout2)
    RadioButton amout2;
    @BindView(R.id.amout3)
    RadioButton amout3;
    @BindView(R.id.amout4)
    RadioButton amout4;
    @BindView(R.id.perior1)
    RadioButton perior1;
    @BindView(R.id.perior2)
    RadioButton perior2;
    @BindView(R.id.perior3)
    RadioButton perior3;
    @BindView(R.id.perior4)
    RadioButton perior4;
    @BindView(R.id.credit1)
    RadioButton credit1;
    @BindView(R.id.credit2)
    RadioButton credit2;
    @BindView(R.id.credit3)
    RadioButton credit3;
    @BindView(R.id.credit4)
    RadioButton credit4;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_id_no)
    TextView tvIdNo;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.tv_amount_group)
    RadioGroup tvAmountGroup;
    @BindView(R.id.tv_perior_group)
    RadioGroup tvPeriorGroup;
    @BindView(R.id.tv_credit_group)
    RadioGroup tvCreditGroup;
    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_submit)
    TextView tvSubmit;
    @BindView(R.id.tv_rematch)
    TextView tvRematch;
    @BindView(R.id.group1)
    Group group1;
//    @BindView(R.id.smartRefresh)
//    SmartRefreshLayout mSmartrefreshlayout;

    //偏移量
    private int pageSize = 5;
    //每页加载数据
    private int pageNo = 1;
    private DectactorAdapter mDectactorAdapter;
    private Map<String, String> mSelectionsID;
    private String mAfterMatchBanner;
    private String headUrl = "";
    private String headTitle = "";
    private int isRedirect = -1;
    private RecyclerView mFooterRecyclerView;
    private View footerView;

    public static DetectorFragment newInstance() {
        return new DetectorFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerDetectorComponent
                .builder()
                .appComponent(appComponent)
                .detectorModule(new DetectorModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_detector, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initMap();
        initEvent();
//        mSmartrefreshlayout.setEnableLoadmore(false);
//        mSmartrefreshlayout.setOnRefreshListener(refreshlayout -> {
//            mDectactorAdapter.setEnableLoadMore(true);
//            pageNo = 1;
//            mPresenter.findRecommendList(mSelectionsID, false);
//        });
    }

    /**
     * 初始化默认请求数据
     */
    private void initMap() {
        mSelectionsID = new ArrayMap<>();
        mSelectionsID.put("loanMin", "0");
        mSelectionsID.put("resourceId", "-1");
        mSelectionsID.put("loanMax", "2999");
        mSelectionsID.put("termStart", "1");
        mSelectionsID.put("termEnd", "3");
        mSelectionsID.put("zhimaScoreStart", "0");
        mSelectionsID.put("zhimaScoreEnd", "499");
        mSelectionsID.put("pageNo", String.valueOf(pageNo));
        mSelectionsID.put("pageSize", String.valueOf(pageSize));
    }

    private void initEvent() {
        tvAmountGroup.setOnCheckedChangeListener(this);
        tvPeriorGroup.setOnCheckedChangeListener(this);
        tvCreditGroup.setOnCheckedChangeListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mDectactorAdapter = new DectactorAdapter();
        mDectactorAdapter.setEmptyView(R.layout.layout_empty_view, (ViewGroup) mRecyclerView.getParent());
        mRecyclerView.setAdapter(mDectactorAdapter);
        //初始化底部三个icon的view
        footerView = View.inflate(mContext, R.layout.dectator_footerview, null);
        mFooterRecyclerView = footerView.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(0);
        mFooterRecyclerView.setLayoutManager(linearLayoutManager);

        mDectactorAdapter.setOnItemClickListener((adapter, view, position) -> {
            ProductListEntity product = (ProductListEntity) adapter.getData().get(position);
            String url = product.getProductUrl();
            String name = product.getProductName();
            int proId = mDectactorAdapter.getData().get(position).getId();
            if (UserInfoHelper.getInstance().isLogin()) {

            } else {
//                new DialogUtil(getContext(), true, R.style.dialog, "您还未登录，请您先完成登录！", (dialog, confirm) -> {
//                    if (confirm) {
//                        dialog.dismiss();
//                        setIntent(LoginActivity.class);
//                    } else {
//                        dialog.dismiss();
//                    }
//                }).show();
            }
        });
        tvSubmit.setOnClickListener(this);
        tvRematch.setOnClickListener(this);
        mBanner.setOnClickListener(this);
    }

    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    protected void initViewClick(int id) {
        switch (id) {
            case R.id.tv_submit:
                if (etName.getText().toString().isEmpty()) {
                    showMessage("请输入姓名!");
                    return;
                }
                if (etId.getText().toString().isEmpty()) {
                    showMessage("请输入身份证号码!");
                    return;
                }
                if (!RegexUtils.isIDCard(etId.getText().toString().trim())) {
                    showMessage("请输入正确的身份证号");
                    return;
                }

                Picasso.get().load(PjbTextUtils.getDefaultText(mAfterMatchBanner, "http://m3.yinpiaobao.cn/daichao/dev/201905/20190509/133857804.png")).into(mBanner);
                mPresenter.findRecommendList(mSelectionsID, false);
                break;
            case R.id.tv_rematch:
                group1.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
                tvRematch.setVisibility(View.GONE);
                break;
            case R.id.banner:
                if (isRedirect == 2) {
                    if (UserInfoHelper.getInstance().isLogin()) {
                        if (!headUrl.isEmpty())
                            toWeb(headUrl, PjbTextUtils.getDefaultText(headTitle, "今日有借"), true);
                    } else {
                        new DialogUtil(getContext(), true, R.style.dialog, "您还未登录，请您先完成登录！", (dialog, confirm) -> {
                            if (confirm) {
                                dialog.dismiss();
                                setIntent(LoginActivity.class);
                            } else {
                                dialog.dismiss();
                            }
                        }).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int id) {
        switch (id) {
            case R.id.amout1:
                mSelectionsID.put("loanMin", "0");
                mSelectionsID.put("loanMax", "2999");
                break;
            case R.id.amout2:
                mSelectionsID.put("loanMin", "3000");
                mSelectionsID.put("loanMax", "4999");
                break;
            case R.id.amout3:
                mSelectionsID.put("loanMin", "10000");
                mSelectionsID.put("loanMax", "19999");
                break;
            case R.id.amout4:
                mSelectionsID.put("loanMin", "20000");
                mSelectionsID.put("loanMax", "100000");
                break;
            case R.id.perior1:
                mSelectionsID.put("termStart", "1");
                mSelectionsID.put("termEnd", "3");
                break;
            case R.id.perior2:
                mSelectionsID.put("termStart", "4");
                mSelectionsID.put("termEnd", "6");
                break;
            case R.id.perior3:
                mSelectionsID.put("termStart", "7");
                mSelectionsID.put("termEnd", "12");
                break;
            case R.id.perior4:
                mSelectionsID.put("termStart", "13");
                mSelectionsID.put("termEnd", "120");
                break;
            case R.id.credit1:
                mSelectionsID.put("zhimaScoreStart", "0");
                mSelectionsID.put("zhimaScoreEnd", "499");
                break;
            case R.id.credit2:
                mSelectionsID.put("zhimaScoreStart", "500");
                mSelectionsID.put("zhimaScoreEnd", "579");
                break;
            case R.id.credit3:
                mSelectionsID.put("zhimaScoreStart", "580");
                mSelectionsID.put("zhimaScoreEnd", "649");
                break;
            case R.id.credit4:
                mSelectionsID.put("zhimaScoreStart", "650");
                mSelectionsID.put("zhimaScoreEnd", "850");
                break;
        }
    }

    @Override
    public void setRecProduct(List<ProductListEntity> products, boolean isLoadmore) {
//        mSmartrefreshlayout.finishRefresh();
        group1.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
        tvRematch.setVisibility(View.VISIBLE);
        if (products == null || products.size() == 0)
            return;
        mDectactorAdapter.setNewData(products);
    }

    private Bundle setBundleToEmptyJump(ProductListEntity product) {
        Bundle bundle = new Bundle();
        if (product != null) {
            bundle.putSerializable(MyConstant.HOME_EMPTY_JUMP, product);
        }
        return bundle;
    }

    @Override
    public void onError() {
//        mSmartrefreshlayout.finishRefresh(false);
    }
}
