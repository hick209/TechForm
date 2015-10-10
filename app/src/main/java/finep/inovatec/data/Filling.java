package finep.inovatec.data;

/**
 * @author Nivaldo Bondança
 */
public class Filling {

	private long   mGroupId;
	private String mCode;
	private String mAddress;
	private long   mDeliverTimestamp;
	private String mInspectionResponsable;
	private long   mBeginningTimestamp;
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

	public String getInspectionResponsable() {
		return mInspectionResponsable;
	}

	public void setInspectionResponsable(String inspectionResponsable) {
		mInspectionResponsable = inspectionResponsable;
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
	public String toString() {
		return "Filling{" +
				"groupId=" + mGroupId +
				", code='" + mCode + '\'' +
				", address='" + mAddress + '\'' +
				", deliverTimestamp=" + mDeliverTimestamp +
				", inspectionResponsable='" + mInspectionResponsable + '\'' +
				", beginningTimestamp=" + mBeginningTimestamp +
				", endingTimestamp=" + mEndingTimestamp +
				'}';
	}
}
