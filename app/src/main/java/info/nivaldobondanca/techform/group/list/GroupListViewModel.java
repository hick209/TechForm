package info.nivaldobondanca.techform.group.list;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AdapterView;

import info.nivaldobondanca.backend.techform.techFormAPI.model.Group;
import info.nivaldobondanca.techform.ListViewModel;
import info.nivaldobondanca.techform.widget.BasicListAdapter;

/**
 * @author Nivaldo Bondança
 */
public class GroupListViewModel extends ListViewModel {

	private GroupListActivity mActivity;

	public GroupListViewModel(GroupListActivity activity) {
		this.mActivity = activity;
	}

	@Override
	public SwipeRefreshLayout.OnRefreshListener getRefreshListener() {
		return mActivity;
	}

	@Override
	public BasicListAdapter<Group> getAdapter() {
		return mActivity.getAdapter();
	}

	@Override
	public AdapterView.OnItemClickListener getItemClickListener() {
		return mActivity;
	}

}
