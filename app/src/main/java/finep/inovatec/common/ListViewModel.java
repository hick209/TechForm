package finep.inovatec.common;

import android.databinding.Bindable;
import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import finep.inovatec.BR;
import finep.inovatec.R;

/**
 * @author Nivaldo Bondança
 */
public abstract class ListViewModel extends ViewModel {

	private boolean           mLoading;
	private boolean           mFirstTimeLoading = true;
	private @ColorRes int[]   mRefreshLayoutColors = {
			R.color.app_primary,
			R.color.app_accent,
			R.color.app_primary_dark,
			R.color.app_primary_light,
	};

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

		notifyPropertyChanged(BR.empty);
		notifyPropertyChanged(BR.loading);
		notifyPropertyChanged(BR.firstTimeLoading);
	}

	public int[] getRefreshLayoutColors() {
		return mRefreshLayoutColors;
	}

	public abstract SwipeRefreshLayout.OnRefreshListener getRefreshListener();

	@Bindable
	public boolean isEmpty() {
		return getAdapter().getCount() == 0;
	}

	public abstract ListAdapter getAdapter();

	public abstract AdapterView.OnItemClickListener getItemClickListener();
}
