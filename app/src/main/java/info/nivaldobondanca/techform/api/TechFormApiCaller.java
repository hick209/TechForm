package info.nivaldobondanca.techform.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

import info.nivaldobondanca.backend.techform.techFormAPI.TechFormAPI;
import info.nivaldobondanca.backend.techform.techFormAPI.model.Form;

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
