package finep.inovatec.home;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import finep.inovatec.FormFillingManager;
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

	public static Intent newInstance(Context context) {
		return new Intent(context, HomeActivity.class);
	}


	private Group         mGroup;
	private HomeViewModel mViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mGroup = FormFillingManager.getInstance().getGroup();

		if (savedInstanceState == null) {
			mViewModel = new HomeViewModel(new FillingAdapter(this));
			put(KEY_VIEW_MODEL, mViewModel);
		}
		else {
			mViewModel = (HomeViewModel) get(KEY_VIEW_MODEL);
		}

		mViewModel.setCallbacks(this);

		ActivityHomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
		binding.setViewModel(mViewModel);

		setupToolbar();
		//noinspection ConstantConditions
		getSupportActionBar().setTitle(mGroup.getName());
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
		loadData();
	}

	@Override
	public void onRefresh() {
		loadData();
	}

	private void loadData() {
		mViewModel.setLoading(true);
		final Handler handler = new Handler();
		new Thread() {
			@Override
			public void run() {
				final List<Filling> data = new ArrayList<>();
				try {
					CacheAgent cacheAgent = getTechFormApplication().getCacheAgent();
					Set<Filling> collection = cacheAgent.loadFillings(mGroup.getId());
					data.addAll(collection);
				}
				catch (IOException ignored) {
				}

				handler.post(new Runnable() {
					@Override
					public void run() {
						mViewModel.getAdapter().changeData(data);
						mViewModel.setLoading(false);
					}
				});
			}
		}.start();
	}

	@Override
	public void newFilling() {
		FormFillingManager.getInstance().setFilling(null);
		startActivity(FillingInfoActivity.newInstance(this));
	}

	@Override
	public void onItemClick(FillingAdapter adapter, Filling item) {
		FormFillingManager.getInstance().setFilling(item);
		startActivity(FillingInfoActivity.newInstance(this));
	}

}
