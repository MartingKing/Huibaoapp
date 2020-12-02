package com.huiboapp.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.huiboapp.R;
import com.huiboapp.app.base.MBaseActivity;
import com.huiboapp.app.utils.MUtils;
import com.huiboapp.di.component.DaggerMainComponent;
import com.huiboapp.di.module.MainModule;
import com.huiboapp.event.CommonEvent;
import com.huiboapp.mvp.contract.MainContract;
import com.huiboapp.mvp.presenter.MainPresenter;
import com.huiboapp.mvp.ui.fragment.AllLoanProFragment;
import com.huiboapp.mvp.ui.fragment.DetectorFragment;
import com.huiboapp.mvp.ui.fragment.HomeFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.EventBusHelper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;

/**
 * 主页
 */
public class MainActivity extends MBaseActivity<MainPresenter> implements MainContract.View {

    @BindView(R.id.vpFragment)
    ViewPager mViewPager;
    @BindView(R.id.navigation)
    BottomNavigationView mBottomNavigationView;// 底部导航
    private MenuItem menuItem;
    Fragment[] fragments;
    private int mSize;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMainComponent
                .builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_home;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item_home:
                    mViewPager.setCurrentItem(0);

                    return true;
                case R.id.item_loan:
                    mViewPager.setCurrentItem(1);

                    return true;
                case R.id.item_mine:
                    mViewPager.setCurrentItem(2);
                    break;
            }
            return false;
        }
    };

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mSize = 3;
        fragments = new Fragment[mSize];
        mBottomNavigationView.inflateMenu(R.menu.menu_bottom);
        fragments[0] = HomeFragment.newInstance();
        fragments[1] = AllLoanProFragment.newInstance();
        fragments[2] = DetectorFragment.newInstance();

        mBottomNavigationView.setLabelVisibilityMode(1);
        adjustNavigationIcoSize(mBottomNavigationView);
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
                menuItem = mBottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }
        });
        mViewPager.setOffscreenPageLimit(mSize);
    }

    private void adjustNavigationIcoSize(BottomNavigationView navigation) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(android.support.design.R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, displayMetrics);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void scheduleEvent(CommonEvent event) {
        mViewPager.setCurrentItem(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusHelper.unRegister(this);
    }

    @Override
    public void onBackPressed() {
        if (!MUtils.isFastDoubleClick(1500)) {
            showMessage("再按一次退出程序");
        } else {
            super.onBackPressed();
        }
    }
}
