package finep.inovatec.data;

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
