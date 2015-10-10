package finep.inovatec.cache;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

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

	public @NonNull List<Filling> loadFillings(long groupId) throws IOException {
		// Get the file
		File file = getFillingFile(groupId);
		FileReader reader = new FileReader(file);

		FillingWrapper fillingWrapper = mGson.fromJson(reader, FillingWrapper.class);
		return fillingWrapper.getFillings();
	}

	public void saveFillings(long groupId, List<Filling> fillings) throws IOException {
		// Create the wrapper
		FillingWrapper wrapper = new FillingWrapper();
		wrapper.setGroupId(groupId);
		wrapper.setFillings(fillings);

		// Save to the file
		String json = mGson.toJson(fillings);
		Files.write(json.getBytes(), getFillingFile(groupId));
	}

	private File getFillingFile(long groupId) {
		return new File(mFillingsDir, String.format("group-%d.json", groupId));
	}


	private class FillingWrapper {
		@SerializedName("id")
		private long          groupId;
		private List<Filling> mFillings;

		public long getGroupId() {
			return groupId;
		}

		public void setGroupId(long groupId) {
			this.groupId = groupId;
		}

		public List<Filling> getFillings() {
			return mFillings;
		}

		public void setFillings(List<Filling> fillings) {
			mFillings = fillings;
		}
	}
}
