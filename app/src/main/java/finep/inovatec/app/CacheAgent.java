package finep.inovatec.app;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.common.io.Files;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import finep.inovatec.data.Filling;
import finep.inovatec.data.FillingWrapper;

/**
 * @author Nivaldo Bondança
 */
public class CacheAgent {

	private final Gson mGson;
	private final File mFillingsDir;

	public CacheAgent(Context context) {
		mGson = new Gson();
		mFillingsDir = new File(context.getFilesDir(), "fillings");
		//noinspection ResultOfMethodCallIgnored
		mFillingsDir.mkdirs();
	}

	public @NonNull Set<Filling> loadFillings(long groupId) throws IOException {
		// Get the file
		File file = getFillingFile(groupId);
		FileReader reader = new FileReader(file);

		FillingWrapper fillingWrapper = mGson.fromJson(reader, FillingWrapper.class);
		Set<Filling> fillings = fillingWrapper.getFillings();

		if (fillings == null) {
			fillings = new HashSet<>();
		}

		return fillings;
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

	public void deleteFilling(long groupId, Filling filling) throws IOException {
		Set<Filling> fillings = loadFillings(groupId);
		fillings.remove(filling);
		saveFillings(groupId, fillings);
	}

	private File getFillingFile(long groupId) {
		return new File(mFillingsDir, String.format("group-%d.json", groupId));
	}
}
