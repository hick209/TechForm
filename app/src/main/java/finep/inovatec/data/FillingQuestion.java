package finep.inovatec.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nivaldo Bondança
 */
public class FillingQuestion {

	private String mCodeName;
	private String mNotes;

	private List<FillingQuestionOption> mOptions = new ArrayList<>();

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
}
