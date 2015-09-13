package info.nivaldobondanca.techform.group.list;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.nivaldobondanca.backend.techform.techFormAPI.TechFormAPI;
import info.nivaldobondanca.backend.techform.techFormAPI.model.Group;
import info.nivaldobondanca.techform.R;
import info.nivaldobondanca.techform.content.ApiCall;
import info.nivaldobondanca.techform.content.ListLoaderCallback;
import info.nivaldobondanca.techform.databinding.ActivityGroupListBinding;
import info.nivaldobondanca.techform.group.details.GroupDetailsActivity;
import info.nivaldobondanca.techform.util.Utils;
import info.nivaldobondanca.techform.widget.BasicListAdapter;

/**
 * @author Nivaldo Bondança
 */
public class GroupListActivity extends AppCompatActivity
		implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

	private static final String LOG_TAG = GroupListActivity.class.getSimpleName();

	private GroupAdapter              mAdapter;
	private ListLoaderCallback<Group> mLoaderCallback;

	private GroupListViewModel mViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewModel = new GroupListViewModel(this);

		mAdapter = new GroupAdapter();
		mLoaderCallback = new GroupsLoaderCallback();

		ActivityGroupListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_group_list);
		binding.setViewModel(mViewModel);

		Utils.setupToolbar(this);
	}

	@Override
	protected void onStart() {
		super.onStart();
		getSupportLoaderManager().initLoader(0, null, mLoaderCallback);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_group_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_uploadData:
				// TODO
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onRefresh() {
		getSupportLoaderManager().restartLoader(0, null, mLoaderCallback);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Group group = mAdapter.getItem(position);
		startActivity(GroupDetailsActivity.newInstance(this, group.getId(), group.getName()));
	}

	public BasicListAdapter<Group> getAdapter() {
		return mAdapter;
	}

	private class GroupAdapter extends BasicListAdapter<Group> {

		public GroupAdapter() {
			super(GroupListActivity.this);
		}

		@Override
		public long getItemId(int position) {
			return getItem(position).getId();
		}

		@Override
		protected void bindView(View view, Group item) {
			((TextView) view.findViewById(android.R.id.text1))
					.setText(item.getName());
		}

		@Override
		protected @LayoutRes int getItemLayoutResource() {
			return android.R.layout.simple_list_item_1;
		}
	}

	private class GroupsLoaderCallback extends ListLoaderCallback<Group>
			implements ApiCall<List<Group>> {

		public GroupsLoaderCallback() {
			super(mAdapter, GroupListActivity.this);
		}

		@Override
		public Loader<List<Group>> onCreateLoader(int id, Bundle args) {
			mViewModel.setLoading(true);
			return super.onCreateLoader(id, args);
		}

		@Override
		public void onLoadFinished(Loader<List<Group>> loader, List<Group> data) {
			super.onLoadFinished(loader, data);
			mAdapter.changeData(data);
			mViewModel.setLoading(false);
		}

		@Override
		protected ApiCall<List<Group>> getCaller() {
			return this;
		}

		@Override
		public List<Group> call(TechFormAPI api) {
			final String METHOD_PREFIX = "[call]";
			List<Group> groups = new ArrayList<>();
			try {
				List<Group> items = api.group().list().execute().getItems();
				Log.v(LOG_TAG, METHOD_PREFIX + " - Groups : " + items);
				if (items != null) {
					groups.addAll(items);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return groups;
		}
	}
}
