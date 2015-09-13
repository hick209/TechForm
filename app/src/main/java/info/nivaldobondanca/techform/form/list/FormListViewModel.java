package info.nivaldobondanca.techform.form.list;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import info.nivaldobondanca.techform.ListViewModel;

/**
 * @author Nivaldo Bondança
 */
public class FormListViewModel extends ListViewModel {

	private FormListFragment mFragment;

	public FormListViewModel(FormListFragment fragment) {
		this.mFragment = fragment;
	}

	@Override
	public SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
		return mFragment;
	}

	@Override
	public ListAdapter getAdapter() {
		return mFragment.getAdapter();
	}

	@Override
	public AdapterView.OnItemClickListener getItemClickListener() {
		return mFragment;
	}
}
