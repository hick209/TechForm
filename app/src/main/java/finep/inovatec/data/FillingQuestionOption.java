package finep.inovatec.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nivaldo Bondança
 */
public class FillingQuestionOption {

	@SerializedName("items")
	private List<String> mItems = new ArrayList<>();

	public List<String> getItems() {
		return mItems;
	}

	public void setItems(List<String> items) {
		mItems = items;
	}

	public FillingQuestionOption createCopy() {
		FillingQuestionOption clone = new FillingQuestionOption();
		clone.setItems(new ArrayList<>(getItems()));

		return clone;
	}
}
