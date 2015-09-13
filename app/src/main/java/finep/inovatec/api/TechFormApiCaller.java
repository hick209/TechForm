package finep.inovatec.api;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import info.nivaldobondanca.backend.techform.techFormAPI.TechFormAPI;

/**
 * @author Nivaldo Bondança
 */
public class TechFormApiCaller {

	private TechFormAPI mFormApi;

	private static TechFormApiCaller apiCaller;

	public static TechFormApiCaller getInstance() {
		if (apiCaller == null) {
			apiCaller = new TechFormApiCaller();
		}
		return apiCaller;
	}

	private TechFormApiCaller() {
		TechFormAPI.Builder builder = new TechFormAPI.Builder(
				AndroidHttp.newCompatibleTransport(),
				new AndroidJsonFactory(), null)
				.setRootUrl("https://techform-998.appspot.com/_ah/api/");

		mFormApi = builder.build();
	}

	public TechFormAPI api() {
		return mFormApi;
	}
}
