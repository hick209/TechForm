package finep.inovatec.app;

import android.app.Application;
import android.support.v4.util.ArrayMap;

import java.util.Map;

import finep.inovatec.FormFillingManager;
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

		FormFillingManager.init(mCacheAgent);
	}

	public CacheAgent getCacheAgent() {
		return mCacheAgent;
	}

	public ViewModel get(String key) {
		return mViewModels.get(key);
	}

	public void put(String key, ViewModel viewModel) {
		mViewModels.put(key, viewModel);
	}
}
