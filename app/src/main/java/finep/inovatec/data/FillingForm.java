package finep.inovatec.data;

import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Nivaldo Bondança
 */
public class FillingForm {

	private String mCodeName;

	private List<FillingFormSection> mSections = new ArrayList<>();

	private transient Map<String, FillingFormSection> mHelper = new ArrayMap<>();

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

		mHelper.clear();
		for (FillingFormSection f : list) {
			mHelper.put(f.getCodeName(), f);
		}
	}

	public FillingFormSection getSection(String codeName) {
		FillingFormSection section = mHelper.get(codeName);
		if (section == null) {
			section = new FillingFormSection();
			mSections.add(section);
		}

		return section;
	}

	public void putSection(FillingFormSection item) {
		mHelper.put(item.getCodeName(), item);
	}
}
