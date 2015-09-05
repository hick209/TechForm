package info.nivaldobondanca.techform.group;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Adapter;
import android.widget.AdapterView;

import info.nivaldobondanca.techform.BR;
import info.nivaldobondanca.techform.R;

/**
 * @author Nivaldo Bondança
 */
public class GroupListViewModel extends BaseObservable {

	private boolean           mLoading;
	private boolean           mFirstTimeLoading = true;
	private GroupListActivity mActivity;
	private @ColorRes int[]   mRefreshLayoutColors = {
			R.color.app_primary,
			R.color.app_primary_light,
			R.color.app_accent,
			R.color.app_primary_dark
	};

	public GroupListViewModel(GroupListActivity activity) {
		this.mActivity = activity;
	}

	@Bindable
	public boolean isLoading() {
		return mLoading;
	}

	@Bindable
	public boolean isFirstTimeLoading() {
		return mFirstTimeLoading;
	}

	public void setLoading(boolean loading) {
		this.mLoading = loading;
		this.mFirstTimeLoading = loading && mFirstTimeLoading;
		notifyPropertyChanged(BR.loading);
		notifyPropertyChanged(BR.firstTimeLoading);
	}

	public int[] getRefreshLayoutColors() {
		return mRefreshLayoutColors;
	}

	public SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
		return mActivity;
	}

	public Adapter getAdapter() {
		return mActivity.getAdapter();
	}

	public AdapterView.OnItemClickListener getItemClickListener() {
		return mActivity;
	}

}
