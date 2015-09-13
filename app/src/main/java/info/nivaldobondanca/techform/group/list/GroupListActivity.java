package info.nivaldobondanca.techform.group.list;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import info.nivaldobondanca.backend.techform.techFormAPI.TechFormAPI;
import info.nivaldobondanca.backend.techform.techFormAPI.model.Group;
import info.nivaldobondanca.techform.R;
import info.nivaldobondanca.techform.content.ApiCall;
import info.nivaldobondanca.techform.content.ListLoaderCallback;
import info.nivaldobondanca.techform.databinding.ActivityGroupListBinding;
import info.nivaldobondanca.techform.group.ApiCaller;
import info.nivaldobondanca.techform.group.details.GroupDetailsActivity;
import info.nivaldobondanca.techform.util.ParseUtils;
import info.nivaldobondanca.techform.util.Utils;
import info.nivaldobondanca.techform.widget.BasicListAdapter;

/**
 * @author Nivaldo Bondança
 */
public class GroupListActivity extends AppCompatActivity
		implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener {

	private static final String LOG_TAG = GroupListActivity.class.getSimpleName();

	private static final int REQUEST_PERMISSION = 0;
	private static final int REQUEST_FILE       = 1;

	private GroupAdapter              mAdapter;
	private ListLoaderCallback<Group> mLoaderCallback;

	private GroupListViewModel       mViewModel;
	private ActivityGroupListBinding mBinding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewModel = new GroupListViewModel(this);

		mAdapter = new GroupAdapter();
		mLoaderCallback = new GroupsLoaderCallback();

		mBinding = DataBindingUtil.setContentView(this, R.layout.activity_group_list);
		mBinding.setViewModel(mViewModel);

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
				Log.v(LOG_TAG, "Requesting file to upload");
				showFileChooser();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == REQUEST_FILE && resultCode == RESULT_OK) {
			String path = data.getData().getPath();
			File file = new File(path);

			try {
				String fileContent = Files.toString(file, Charsets.UTF_8);

				JSONObject json = new JSONObject(fileContent);

				Log.i(LOG_TAG, "Checking JSON validity.");
				Log.v(LOG_TAG, json.toString(2));

				final String jsonData = json.toString();
				final Group g = ParseUtils.parseGroupJSON(jsonData);

				// JSON is valid!
				// Send it!
				Log.i(LOG_TAG, "Sending data");
				ApiCall<Boolean> apiCall = new ApiCall<Boolean>() {
					@Override
					public Boolean call(TechFormAPI api) {
						try {
							api.group().save(g.getId(), jsonData).execute();
							return true;
						} catch (IOException e) {
							Log.d(LOG_TAG, "Error while upload file", e);
						}
						return false;
					}
				};

				Uploader uploader = new Uploader();
				//noinspection unchecked
				uploader.execute(apiCall);
			}
			catch (JSONException e) {
				Toast.makeText(this, R.string.message_badFileFormat, Toast.LENGTH_LONG).show();
				Log.e(LOG_TAG, "JSON error", e);
			}
			catch (IOException e) {
				Toast.makeText(this, R.string.message_ioError, Toast.LENGTH_LONG).show();
				Log.e(LOG_TAG, "IO error", e);
			}
		}
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

	private void showFileChooser() {

		if (!getPermission()) return;

		// Create the intent
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("text/*");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent = Intent.createChooser(intent, getString(R.string.action_chooseUploadFile));

		try {
			startActivityForResult(intent, REQUEST_FILE);
		}
		catch (ActivityNotFoundException e) {
			Log.w(LOG_TAG, "No file chooser found", e);
			Toast.makeText(this, R.string.message_noFileChooser, Toast.LENGTH_LONG).show();
		}
	}

	public BasicListAdapter<Group> getAdapter() {
		return mAdapter;
	}

	@TargetApi(Build.VERSION_CODES.M)
	public boolean getPermission() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;

		if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
			return false;
		}
		return true;
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == REQUEST_PERMISSION) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				showFileChooser();
			}
		}
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

	private class Uploader extends ApiCaller<Boolean> {
		@Override
		protected void onPostExecute(Boolean result) {
			try {
				int message = result ? R.string.message_fileUploaded : R.string.message_errorOnFileUploaded;
				Snackbar.make(mBinding.coordinator, message, Snackbar.LENGTH_LONG).show();

				if (result) {
					// Load new data
					onRefresh();
				}
			}
			catch (Exception ignored) {
			}
		}
	}
}
