package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.di.component.DaggerParkedHistoryComponent;
import com.huiboapp.di.module.ParkedHistoryModule;
import com.huiboapp.event.TabEvent;
import com.huiboapp.mvp.contract.ParkedHistoryContract;
import com.huiboapp.mvp.model.entity.HomeOrderEntity;
import com.huiboapp.mvp.presenter.ParkedHistoryPresenter;
import com.huiboapp.mvp.ui.fragment.ParkHistoryFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.EventBusHelper;

import butterknife.BindView;


public class ParkedHistoryActivity extends MBaseActivity<ParkedHistoryPresenter> implements ParkedHistoryContract.View {


    @BindView(R.id.clayoutBg)
    View clayoutBg;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rlayoutTitle)
    LinearLayout rlayoutTitle;
    @BindView(R.id.navigation)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.vpFragment)
    ViewPager mViewPager;
    private int mSize;
    private MenuItem menuItem;
    Fragment[] fragments;

    private String status;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerParkedHistoryComponent
                .builder()
                .appComponent(appComponent)
                .parkedHistoryModule(new ParkedHistoryModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_parkhistory;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        isBarDarkFont = false;
        tvTitle.setText("停车记录");
        ivBack.setOnClickListener(this);
        mSize = 3;
        fragments = new Fragment[mSize];
        mBottomNavigationView.inflateMenu(R.menu.menu_park);
        fragments[0] = ParkHistoryFragment.newInstance();
        fragments[1] = ParkHistoryFragment.newInstance();
        fragments[2] = ParkHistoryFragment.newInstance();

        mBottomNavigationView.setLabelVisibilityMode(1);
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return mSize;
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        status = "";
                        break;
                    case 1:
                        status = "unpaid";
                        break;
                    case 2:
                        status = "paid";
                        break;
                }
                EventBusHelper.postStickyEvent(new TabEvent(status));
                menuItem = mBottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }
        });
        mViewPager.setOffscreenPageLimit(mSize);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_home:
                    status = "";
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.item_loan:
                    status = "unpaid";
                    mViewPager.setCurrentItem(1);
                    return true;
                case R.id.item_mine:
                    status = "paid";
                    mViewPager.setCurrentItem(2);
                    break;
            }
            EventBusHelper.postStickyEvent(new TabEvent(status));
            return false;
        }
    };


    @Override
    protected void initViewClick(int id) {
        if (id == R.id.ivBack) {
            finish();
        }
    }

    @Override
    public void findOrderList(HomeOrderEntity orderEntity) {

    }
}
