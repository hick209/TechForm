package finep.inovatec.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nivaldo Bondança
 */
public class FillingQuestion {

	@SerializedName("codeName")
	private String mCodeName;
	@SerializedName("notes")
	private String mNotes;

	@SerializedName("options")
	private List<FillingQuestionOption> mOptions = new ArrayList<>();
	@SerializedName("images")
	private List<String> mImages = new ArrayList<>();

	public String getCodeName() {
		return mCodeName;
	}

	public void setCodeName(String codeName) {
		mCodeName = codeName;
	}

	public String getNotes() {
		return mNotes;
	}

	public void setNotes(String notes) {
		mNotes = notes;
	}

	public List<FillingQuestionOption> getOptions() {
		return mOptions;
	}

	public void setOptions(List<FillingQuestionOption> list) {
		mOptions = list;
	}

	public List<String> getImages() {
		return mImages;
	}

	public void setImages(List<String> images) {
		mImages = images;
	}


	public FillingQuestion createCopy() {
		FillingQuestion clone = new FillingQuestion();
		clone.setCodeName(getCodeName());
		clone.setNotes(getNotes());

		clone.setImages(new ArrayList<>(getImages()));

		List<FillingQuestionOption> options = getOptions();
		List<FillingQuestionOption> optionFillings = new ArrayList<>(options.size());
		for (FillingQuestionOption o : options) {
			optionFillings.add(o.createCopy());
		}

		clone.setOptions(optionFillings);

		return clone;
	}
}
