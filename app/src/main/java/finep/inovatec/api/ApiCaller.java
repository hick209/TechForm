package finep.inovatec.api;

import android.os.AsyncTask;
import android.util.Log;

import finep.inovatec.content.ApiCall;
import info.nivaldobondanca.backend.techform.techFormAPI.TechFormAPI;

/**
 * @author Nivaldo Bondança
 */
public class ApiCaller<T> extends AsyncTask<ApiCall<T>, Integer, T> {

	private static final String LOG_TAG = ApiCaller.class.getSimpleName();

	@SafeVarargs
	@Override
	protected final T doInBackground(ApiCall<T>... params) {
		final String METHOD_PREFIX = "[doInBackground]";
		Log.v(LOG_TAG, METHOD_PREFIX + " - Working...");

		ApiCall<T> caller = params[0];

		TechFormAPI api = TechFormApiCaller.getInstance().api();

		return caller.call(api);
	}
}
