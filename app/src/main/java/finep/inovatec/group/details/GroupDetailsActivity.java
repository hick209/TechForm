package finep.inovatec.group.details;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import info.nivaldobondanca.backend.techform.techFormAPI.TechFormAPI;
import info.nivaldobondanca.backend.techform.techFormAPI.model.Form;
import info.nivaldobondanca.backend.techform.techFormAPI.model.Group;
import finep.inovatec.R;
import finep.inovatec.content.ApiCall;
import finep.inovatec.content.ApiLoader;
import finep.inovatec.content.FormContentObserver;
import finep.inovatec.databinding.ActivityGroupDetailsBinding;
import finep.inovatec.form.list.FormListFragment;
import finep.inovatec.form.summary.FormSummaryFragment;
import finep.inovatec.util.Utils;

/**
 * @author Nivaldo Bondança
 */
public class GroupDetailsActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

	public static final String LOG_TAG = GroupDetailsActivity.class.getSimpleName();

	public static final String EXTRA_GROUP_ID   = "extra.GROUP_ID";
	public static final String EXTRA_GROUP_NAME = "extra.GROUP_NAME";

	public static final String STATE_SELECTED_FORM = "extra.SELECTED_FORM";

	public static Intent newInstance(Context c, long groupId, String groupName) {
		return new Intent(c, GroupDetailsActivity.class)
				.putExtra(EXTRA_GROUP_ID, groupId)
				.putExtra(EXTRA_GROUP_NAME, groupName);
	}

	private Group      mGroup;
	private List<Form> mData = Collections.emptyList();
	private int        mSelectedForm;

	private FormsLoaderCallback      mLoaderCallback;
	private Set<FormContentObserver> mContentObservers = new HashSet<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mGroup = new Group();
		mGroup.setId(getIntent().getLongExtra(EXTRA_GROUP_ID, -1));
		mGroup.setName(getIntent().getStringExtra(EXTRA_GROUP_NAME));

		ActivityGroupDetailsBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_group_details);
		binding.setViewModel(this);

		Utils.setupToolbar(this);

		mLoaderCallback = new FormsLoaderCallback();

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.fragment_container, FormListFragment.instantiate())
					.commit();
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
		getSupportLoaderManager().initLoader(0, null, mLoaderCallback);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt(STATE_SELECTED_FORM, mSelectedForm);
	}

	@Override
	protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mSelectedForm = savedInstanceState.getInt(STATE_SELECTED_FORM, 0);
	}

	public Group getGroup() {
		return mGroup;
	}

	public List<Form> getData() {
		return mData;
	}

	public Form getForm() {
		return mData.get(mSelectedForm);
	}

	public void onFormClick(int position) {
		mSelectedForm = position;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.fragment_container, FormSummaryFragment.instantiate())
				.addToBackStack(null)
				.commit();
	}

	@Override
	public void onRefresh() {
		getSupportLoaderManager().restartLoader(0, null, mLoaderCallback);
	}

	public void registerFormDataObserver(FormContentObserver observer) {
		mContentObservers.add(observer);
	}

	public void unregisterFormDataObserver(FormContentObserver observer) {
		mContentObservers.remove(observer);
	}

	private class FormsLoaderCallback implements LoaderManager.LoaderCallbacks<List<Form>>,
			ApiCall<List<Form>> {

		@Override
		public List<Form> call(TechFormAPI api) {
			final String METHOD_PREFIX = "[call]";
			List<Form> forms = new ArrayList<>();
			try {
				List<Form> items = api.form().list(mGroup.getId()).execute().getItems();
				Log.v(LOG_TAG, METHOD_PREFIX + " - Forms : " + items);
				if (items != null) {
					forms.addAll(items);
				}
			} catch (IOException e) {
				Log.w(LOG_TAG, METHOD_PREFIX + " - " + e.getMessage());
			}
			return forms;
		}

		@Override
		public Loader<List<Form>> onCreateLoader(int id, Bundle args) {
			return new ApiLoader<>(GroupDetailsActivity.this, this);
		}

		@Override
		public void onLoadFinished(Loader<List<Form>> loader, List<Form> data) {
			updateData(data);
		}

		@Override
		public void onLoaderReset(Loader<List<Form>> loader) {
			updateData(Collections.<Form>emptyList());
		}

		private void updateData(List<Form> data) {
			mData = data;
			for (FormContentObserver observer : mContentObservers) {
				observer.onChange(GroupDetailsActivity.this, mData);
			}
		}
	}
}
