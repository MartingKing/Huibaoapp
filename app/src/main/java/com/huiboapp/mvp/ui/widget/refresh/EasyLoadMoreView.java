package com.huiboapp.mvp.ui.widget.refresh;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.huiboapp.R;


public class EasyLoadMoreView extends LoadMoreView {
    @Override
    public int getLayoutId() {
        return R.layout.layout_base_load_more;
    }

    @Override
    protected int getLoadingViewId() {return R.id.load_more_loading_view;}

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }

}
