package info.nivaldobondanca.backend.techform.model;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.repackaged.com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.google.api.client.repackaged.com.google.common.base.Objects.firstNonNull;

/**
 * @author Nivaldo Bondança
 */
public class FormQuestionOption {
	public enum Selection {
		@SerializedName("single")
		SINGLE,
		@SerializedName("multiple")
		MULTIPLE,
	}

	private long         mId;
	private String       mTitle;
	private Selection    mSelection;
	private List<String> mItems;

	public long getId() {
		return mId;
	}

	public void setId(long id) {
		mId = id;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public Selection getSelection() {
		return mSelection;
	}

	public void setSelection(Selection selection) {
		mSelection = selection;
	}

	public List<String> getItems() {
		return mItems;
	}

	public void setItems(List<String> items) {
		mItems = items;
	}

	public static FormQuestionOption fromEntity(DatastoreService dataStore, Entity entity) throws EntityNotFoundException {
		FormQuestionOption option = new FormQuestionOption();

		long id = entity.getKey().getId();
		option.setId(id);

		String title = (String) entity.getProperty("title");
		option.setTitle(title);

		String selection = (String) entity.getProperty("selection");
		option.setSelection(Selection.valueOf(selection.toUpperCase()));

		//noinspection unchecked
		List<Key> keys = (List<Key>) entity.getProperty("options");
		keys = firstNonNull(keys, Collections.<Key>emptyList());

		List<String> items = new ArrayList<>();
		for (Key k : keys) {
			String q = (String) dataStore.get(k).getProperty("value");
			items.add(q);
		}

		option.setItems(items);

		return option;
	}
}
