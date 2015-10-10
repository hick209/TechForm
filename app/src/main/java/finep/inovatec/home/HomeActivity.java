package finep.inovatec.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import finep.inovatec.R;
import finep.inovatec.app.BaseActivity;
import finep.inovatec.data.Filling;
import finep.inovatec.databinding.ActivityHomeBinding;

/**
 * @author Nivaldo Bondança
 */
public class HomeActivity extends BaseActivity implements HomeViewModel.HomeCallbacks {

	private HomeViewModel mViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ActivityHomeBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
		binding.setViewModel(mViewModel);

		mViewModel = new HomeViewModel(new FillingAdapter(this));

		mViewModel.setCallbacks(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// Remove the callbacks
		mViewModel.setCallbacks(null);
	}

	@Override
	public void onRefresh() {
		// TODO
	}

	@Override
	public void newFilling() {
		//TODO
	}

	@Override
	public void onItemClick(FillingAdapter adapter, Filling item) {
		// TODO
	}
}
