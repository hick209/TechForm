package finep.inovatec.data;

import android.support.v4.util.ArrayMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Nivaldo Bondança
 */
public class Filling {

	private long   mGroupId;
	private String mCode;
	private String mAddress;
	private long   mDeliverTimestamp = System.currentTimeMillis();
	private String mInspectionResponsible;
	private long   mBeginningTimestamp = System.currentTimeMillis();
	private long   mEndingTimestamp;

	private List<FillingForm> mForms = new ArrayList<>();

	private transient Map<String, FillingForm> mHelper = new ArrayMap<>();

	public long getGroupId() {
		return mGroupId;
	}

	public void setGroupId(long groupId) {
		mGroupId = groupId;
	}

	public String getCode() {
		return mCode;
	}

	public void setCode(String code) {
		mCode = code;
	}

	public String getAddress() {
		return mAddress;
	}

	public void setAddress(String address) {
		mAddress = address;
	}

	public long getDeliverTimestamp() {
		return mDeliverTimestamp;
	}

	public void setDeliverTimestamp(long deliverTimestamp) {
		mDeliverTimestamp = deliverTimestamp;
	}

	public String getInspectionResponsible() {
		return mInspectionResponsible;
	}

	public void setInspectionResponsible(String inspectionResponsible) {
		mInspectionResponsible = inspectionResponsible;
	}

	public long getBeginningTimestamp() {
		return mBeginningTimestamp;
	}

	public void setBeginningTimestamp(long beginningTimestamp) {
		mBeginningTimestamp = beginningTimestamp;
	}

	public long getEndingTimestamp() {
		return mEndingTimestamp;
	}

	public void setEndingTimestamp(long endingTimestamp) {
		mEndingTimestamp = endingTimestamp;
	}

	public List<FillingForm> getForms() {
		return mForms;
	}

	public void setForms(List<FillingForm> list) {
		if (list == null) {
			list = Collections.emptyList();
		}
		mForms = list;

		mHelper.clear();
		for (FillingForm f : list) {
			mHelper.put(f.getCodeName(), f);
		}
	}

	public FillingForm getForm(String codeName) {
		FillingForm form = mHelper.get(codeName);
		if (form == null) {
			form = new FillingForm();
			mForms.add(form);
		}
		return form;
	}

	public FillingForm putForm(FillingForm item) {
		return mHelper.put(item.getCodeName(), item);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Filling filling = (Filling) o;

		if (mBeginningTimestamp != filling.mBeginningTimestamp) return false;
		return !(mCode != null ? !mCode.equals(filling.mCode) : filling.mCode != null);
	}

	@Override
	public int hashCode() {
		int result = mCode != null ? mCode.hashCode() : 0;
		result = 31 * result + (int) (mBeginningTimestamp ^ (mBeginningTimestamp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "Filling{" +
				"groupId=" + mGroupId +
				", code='" + mCode + '\'' +
				", address='" + mAddress + '\'' +
				", deliverTimestamp=" + mDeliverTimestamp +
				", inspectionResponsible='" + mInspectionResponsible + '\'' +
				", beginningTimestamp=" + mBeginningTimestamp +
				", endingTimestamp=" + mEndingTimestamp +
				'}';
	}
}
