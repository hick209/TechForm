package finep.inovatec.app;

import android.app.Application;
import android.support.v4.util.ArrayMap;

import java.util.Map;

import finep.inovatec.common.ViewModel;

/**
 * @author Nivaldo Bondança
 */
public class TechFormApplication extends Application {

	private CacheAgent mCacheAgent;

	private Map<String, ViewModel> mViewModels;

	@Override
	public void onCreate() {
		super.onCreate();

		mViewModels = new ArrayMap<>();
		mCacheAgent = new CacheAgent(this);
	}

	public ViewModel get(String key) {
		return mViewModels.get(key);
	}

	public void put(String key, ViewModel viewModel) {
		mViewModels.put(key, viewModel);
	}
}
