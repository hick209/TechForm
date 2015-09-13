package finep.inovatec.group.list;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AdapterView;

import info.nivaldobondanca.backend.techform.techFormAPI.model.Group;
import finep.inovatec.common.ListViewModel;
import finep.inovatec.common.BasicListAdapter;

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
