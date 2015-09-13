package finep.inovatec.content;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import info.nivaldobondanca.backend.techform.techFormAPI.TechFormAPI;
import finep.inovatec.api.TechFormApiCaller;

/**
 * @author Nivaldo Bondança
 */
public class ApiLoader<T> extends AsyncTaskLoader<T> {
	private static final String LOG_TAG = ApiLoader.class.getSimpleName();

	private ApiCall<T> mCaller;
	private T          mData;

	public ApiLoader(Context context, ApiCall<T> caller) {
		super(context);
		mCaller = caller;
	}

	@Override
	protected void onStartLoading() {
		final String METHOD_PREFIX = "[onStartLoading]";
		super.onStartLoading();
		Log.v(LOG_TAG, METHOD_PREFIX + " - Start Loading...");

		if (mData != null) {
			Log.v(LOG_TAG, METHOD_PREFIX + " - Using cached data");
			deliverResult(mData);
		}
		else {
			forceLoad();
		}
	}

	@Override
	public T loadInBackground() {
		final String METHOD_PREFIX = "[loadInBackground]";
		Log.v(LOG_TAG, METHOD_PREFIX + " - Downloading...");

		TechFormAPI api = TechFormApiCaller.getInstance().api();

		return mCaller.call(api);
	}

	@Override
	public void deliverResult(T data) {
		final String METHOD_PREFIX = "[deliverResult]";
		mData = data;
		Log.v(LOG_TAG, METHOD_PREFIX + " - Downloaded!");
		super.deliverResult(data);
	}
}
