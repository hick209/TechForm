package finep.inovatec.app;

import android.app.Application;

/**
 * @author Nivaldo Bondança
 */
public class TechFormApplication extends Application {

	private CacheAgent mCacheAgent;

	@Override
	public void onCreate() {
		super.onCreate();

		mCacheAgent = new CacheAgent(this);
	}


}
