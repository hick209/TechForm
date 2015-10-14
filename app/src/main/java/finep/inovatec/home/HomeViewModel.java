package finep.inovatec.home;

import android.databinding.Bindable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;

import finep.inovatec.BR;
import finep.inovatec.common.ListViewModel;
import finep.inovatec.data.Filling;

/**
 * @author Nivaldo Bondança
 */
public class HomeViewModel extends ListViewModel implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {
	public interface HomeCallbacks {
		void onRefresh();
		void newFilling();
		void onItemClick(FillingAdapter adapter, Filling item);
	}

	private static final HomeCallbacks DUMMY_CALLBACK = new HomeCallbacks() {
		@Override public void onRefresh() {}
		@Override public void newFilling() {}
		@Override public void onItemClick(FillingAdapter adapter, Filling item) {}
	};

	private FillingAdapter mAdapter;
	private HomeCallbacks  mCallbacks = DUMMY_CALLBACK;
	private CharSequence   mToolbarTitle;

	private View.OnClickListener mNewFilling = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			mCallbacks.newFilling();
		}
	};

	public HomeViewModel(FillingAdapter adapter) {
		mAdapter = adapter;
	}

	public void setCallbacks(HomeCallbacks callbacks) {
		if (callbacks == null) {
			callbacks = DUMMY_CALLBACK;
		}
		mCallbacks = callbacks;
	}

	@Bindable
	public CharSequence getToolbarTitle() {
		return mToolbarTitle;
	}

	public void setToolbarTitle(CharSequence toolbarTitle) {
		mToolbarTitle = toolbarTitle;
		notifyPropertyChanged(BR.toolbarTitle);
	}

	@Override
	public FillingAdapter getAdapter() {
		return mAdapter;
	}

	@Override
	public SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
		return this;
	}

	@Override
	public AdapterView.OnItemClickListener getItemClickListener() {
		return this;
	}

	@Override
	public void onRefresh() {
		mCallbacks.onRefresh();
	}

	public View.OnClickListener getNewFilling() {
		return mNewFilling;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		mCallbacks.onItemClick(mAdapter, mAdapter.getItem(position));
	}
}
