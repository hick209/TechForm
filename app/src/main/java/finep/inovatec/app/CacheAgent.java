package finep.inovatec.app;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import finep.inovatec.data.Filling;

/**
 * @author Nivaldo Bondança
 */
public class CacheAgent {

	private final Gson mGson;
	private final File mFillingsDir;

	public CacheAgent(Context context) {
		mGson = new Gson();
		mFillingsDir = new File(context.getFilesDir(), "fillings");
		mFillingsDir.mkdirs();
	}

	public @NonNull Set<Filling> loadFillings(long groupId) throws IOException {
		// Get the file
		File file = getFillingFile(groupId);
		FileReader reader = new FileReader(file);

		FillingWrapper fillingWrapper = mGson.fromJson(reader, FillingWrapper.class);
		Set<Filling> fillings = fillingWrapper.getFillings();
		if (fillings == null) {
			return new HashSet<>();
		}
		else {
			return fillings;
		}
	}

	public void saveFillings(long groupId, Set<Filling> fillings) throws IOException {
		// Create the wrapper
		FillingWrapper wrapper = new FillingWrapper();
		wrapper.setGroupId(groupId);
		wrapper.setFillings(fillings);

		// Save to the file
		String json = mGson.toJson(wrapper);
		Files.write(json.getBytes(), getFillingFile(groupId));
	}

	private File getFillingFile(long groupId) {
		return new File(mFillingsDir, String.format("group-%d.json", groupId));
	}


	private class FillingWrapper {
		@SerializedName("id")
		private long         mGroupId;
		@SerializedName("fillings")
		private Set<Filling> mFillings;

		public long getGroupId() {
			return mGroupId;
		}

		public void setGroupId(long groupId) {
			this.mGroupId = groupId;
		}

		public Set<Filling> getFillings() {
			return mFillings;
		}

		public void setFillings(Set<Filling> fillings) {
			mFillings = fillings;
		}
	}
}
