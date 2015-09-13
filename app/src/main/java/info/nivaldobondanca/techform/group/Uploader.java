package info.nivaldobondanca.techform.group;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;

import info.nivaldobondanca.backend.techform.techFormAPI.TechFormAPI;
import info.nivaldobondanca.techform.api.TechFormApiCaller;

/**
 * @author Nivaldo Bondança
 */
public class Uploader extends AsyncTaskLoader<Boolean> {

	private static final String LOG_TAG = Uploader.class.getSimpleName();
	
	private final long   mGroupId;
	private final String mData;

	public Uploader(Context context, long groupId, String data) {
		super(context);
		mGroupId = groupId;
		mData = data;
	}

	@Override
	protected void onStartLoading() {
		forceLoad();
	}

	@Override
	public Boolean loadInBackground() {
		final String METHOD_PREFIX = "[loadInBackground]";
		Log.v(LOG_TAG, METHOD_PREFIX + " - Uploading...");

		TechFormAPI api = TechFormApiCaller.getInstance().api();
		try {
			api.group().save(mGroupId, mData);
			return true;
		}
		catch (IOException e) {
			Log.v(LOG_TAG, METHOD_PREFIX + " - Error!", e);
		}

		return false;
	}

}
