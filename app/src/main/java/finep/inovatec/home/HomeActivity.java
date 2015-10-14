package finep.inovatec.home;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import java.io.IOException;
import java.util.List;

import finep.inovatec.R;
import finep.inovatec.app.BaseActivity;
import finep.inovatec.app.CacheAgent;
import finep.inovatec.data.Filling;
import finep.inovatec.databinding.ActivityHomeBinding;
import finep.inovatec.filling.FillingInfoActivity;
import info.nivaldobondanca.backend.techform.techFormAPI.model.Group;

/**
 * @author Nivaldo Bondança
 */
public class HomeActivity extends BaseActivity implements HomeViewModel.HomeCallbacks {

	public static final String KEY_VIEW_MODEL = "key.VIEW_MODEL";

	public static final String EXTRA_ID   = "extra.ID";
	public static final String EXTRA_NAME = "extra.NAME";

	public static Intent newInstance(Context context, long id, String name) {
		return new Intent(context, HomeActivity.class)
				.putExtra(EXTRA_ID, id)
				.putExtra(EXTRA_NAME, name);
	}


	private Group         mGroup;
	private HomeViewModel mViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mGroup = new Group();
		mGroup.setId(getIntent().getLongExtra(EXTRA_ID, -1));
		mGroup.setName(getIntent().getStringExtra(EXTRA_NAME));

		if (savedInstanceState == null) {
			mViewModel = new HomeViewModel(new FillingAdapter(this));
			put(KEY_VIEW_MODEL, mViewModel);
		}
		else {
			mViewModel = (HomeViewModel) get(KEY_VIEW_MODEL);
		}

		mViewModel.setCallbacks(this);
		mViewModel.setToolbarTitle(mGroup.getName());

		ActivityHomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
		binding.setViewModel(mViewModel);

		setupToolbar();
		//noinspection ConstantConditions
		getSupportActionBar().setTitle(mViewModel.getToolbarTitle());
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Remove the callbacks
		mViewModel.setCallbacks(null);
	}

	@Override
	protected void onResume() {
		super.onResume();
		onRefresh();
	}

	@Override
	public void onRefresh() {
		mViewModel.setLoading(true);
		new Thread() {
			@Override
			public void run() {
				try {
					CacheAgent cacheAgent = getTechFormApplication().getCacheAgent();
					List<Filling> data = cacheAgent.loadFillings(mGroup.getId());
					mViewModel.getAdapter().changeData(data);
				}
				catch (IOException ignored) {
				}

				mViewModel.setLoading(false);
			}
		}.start();
	}

	@Override
	public void newFilling() {
		startActivity(FillingInfoActivity.newInstance(this, mGroup.getId()));
	}

	@Override
	public void onItemClick(FillingAdapter adapter, Filling item) {
		startActivity(FillingInfoActivity.newInstance(this, item));
	}

}
