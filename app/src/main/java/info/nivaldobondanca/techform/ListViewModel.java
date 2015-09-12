package info.nivaldobondanca.techform;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AdapterView;
import android.widget.ListAdapter;

/**
 * @author Nivaldo Bondança
 */
public abstract class ListViewModel extends BaseObservable {

	private boolean           mLoading;
	private boolean           mFirstTimeLoading = true;
	private @ColorRes int[]   mRefreshLayoutColors = {
			R.color.app_primary,
			R.color.app_primary_light,
			R.color.app_accent,
			R.color.app_primary_dark
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

		notifyPropertyChanged(info.nivaldobondanca.techform.BR.empty);
		notifyPropertyChanged(info.nivaldobondanca.techform.BR.loading);
		notifyPropertyChanged(info.nivaldobondanca.techform.BR.firstTimeLoading);
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
