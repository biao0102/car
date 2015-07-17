package com.cars.manager.views.widgts.pullToRefresh;

import android.app.Activity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

import com.cars.manager.networks.AsyncHttpClient;
import com.cars.manager.views.fragments.BaseFragment;
import com.cars.manager.views.widgts.pullToRefresh.PullToRefreshBase.Mode;
import com.cars.manager.views.widgts.pullToRefresh.PullToRefreshBase.OnRefreshListener2;
import com.cars.managers.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

public class PullToRefreshFragmentListView extends BaseFragment implements OnRefreshListener2, OnScrollListener {
	protected PauseOnScrollListener mPauseOnScrollListener;
	protected ListView mListView;

	protected boolean isRefresh = false;
	protected boolean isLoadMore = false;
	protected boolean isCanLoadMore = true;

	protected boolean pauseOnScroll = true; // or true
	protected boolean pauseOnFling = true; // or false
	protected PullToRefreshListView mPullRefreshListView;
	protected LoadingLayout mLoadingLayout;

	public AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
	protected Activity mActivity;
	protected View mView;

	protected void initUI() {
		mPullRefreshListView = (PullToRefreshListView) mView.findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setMode(Mode.BOTH);
		mPullRefreshListView.setOnRefreshListener(this);
		mPullRefreshListView.setOnScrollListener(this);
		mPullRefreshListView.getHeaderLayout();
		mPullRefreshListView.setHeaderLoadingRefreshImg(getResources().getDrawable(R.drawable.default_ptr_drawable));

		mListView = mPullRefreshListView.getRefreshableView();
		mLoadingLayout = new LoadingLayout(mActivity, Mode.PULL_UP_TO_REFRESH, null);
		mLoadingLayout.setRefreshingLabel("加载中...");

		mPauseOnScrollListener = new PauseOnScrollListener(ImageLoader.getInstance(), pauseOnScroll, pauseOnFling, this);
		mListView.setOnScrollListener(mPauseOnScrollListener);
	}

	@Override
	public void onPullDownToRefresh() {
		refresh();
	}

	protected void refresh() {
		isRefresh = true;
		isCanLoadMore = true;
	}

	protected void onRefreshComplete() {
		isLoadMore = false;
		isRefresh = false;
		mPullRefreshListView.onRefreshComplete();
	}

	@Override
	public void onPullUpToRefresh() {
		// TODO Auto-generated method stub
		if (!isLoadMore && isCanLoadMore) {
			mLoadingLayout.refreshing();
			isLoadMore = true;
			loadMore();
		}
	}

	protected void loadMore() {

	}

	protected void setIsCanLoadMore(boolean flag) {
		this.isCanLoadMore = flag;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		if (firstVisibleItem + visibleItemCount == totalItemCount - 1) {
//			if (isCanLoadMore && !isRefresh)
//				this.onPullUpToRefresh();
		}
	}

}
