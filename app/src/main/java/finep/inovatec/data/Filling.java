package finep.inovatec.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Nivaldo Bondança
 */
public class Filling {

	@SerializedName("groupId")
	private long   mGroupId;
	@SerializedName("code")
	private String mCode;
	@SerializedName("address")
	private String mAddress;
	@SerializedName("deliverTimestamp")
	private long   mDeliverTimestamp = System.currentTimeMillis();
	@SerializedName("inspectionResponsible")
	private String mInspectionResponsible;
	@SerializedName("beginningTimestamp")
	private long   mBeginningTimestamp = System.currentTimeMillis();
	@SerializedName("endingTimestamp")
	private long   mEndingTimestamp;

	@SerializedName("forms")
	private List<FillingForm> mForms = new ArrayList<>();

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
	}

	public FillingForm getForm(String codeName) {
		for (FillingForm item : mForms) {
			if (item.getCodeName().equals(codeName)) {
				return item;
			}
		}

		FillingForm form = new FillingForm();
		form.setCodeName(codeName);

		putForm(form);

		return form;
	}

	public void putForm(FillingForm item) {
		for (FillingForm form : mForms) {
			if (form.getCodeName().equals(item.getCodeName())) {
				mForms.remove(form);
				break;
			}
		}
		mForms.add(item);
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
