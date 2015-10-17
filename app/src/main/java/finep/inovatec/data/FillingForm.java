package finep.inovatec.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Nivaldo Bondança
 */
public class FillingForm {

	@SerializedName("codeName")
	private String mCodeName;

	@SerializedName("sections")
	private List<FillingFormSection> mSections = new ArrayList<>();

	public String getCodeName() {
		return mCodeName;
	}

	public void setCodeName(String codeName) {
		mCodeName = codeName;
	}

	public List<FillingFormSection> getSections() {
		return mSections;
	}

	public void setSections(List<FillingFormSection> list) {
		if (list == null) {
			list = Collections.emptyList();
		}
		mSections = list;
	}

	public FillingFormSection getSection(String codeName) {
		for (FillingFormSection item : mSections) {
			if (item.getCodeName().equals(codeName)) {
				return item;
			}
		}

		FillingFormSection section = new FillingFormSection();
		section.setCodeName(codeName);

		putSection(section);

		return section;
	}

	public void putSection(FillingFormSection item) {
		for (FillingFormSection section : mSections) {
			if (section.getCodeName().equals(item.getCodeName())) {
				mSections.remove(section);
				break;
			}
		}
		mSections.add(item);
	}
}
