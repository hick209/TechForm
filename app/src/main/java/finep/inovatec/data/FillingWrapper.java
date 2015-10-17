package finep.inovatec.data;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

/**
 * @author Nivaldo Bondança
 */
public class FillingWrapper {

	@SerializedName("id")
	private long mGroupId;
	@SerializedName("fillings")
	private Set<Filling> mFillings;

	public long getGroupId() {
		return mGroupId;
	}

	public void setGroupId(long groupId) {
		this.mGroupId = groupId;
	}

	public Set<Filling> getFillings() {
		return mFillings;
	}

	public void setFillings(Set<Filling> fillings) {
		mFillings = fillings;
	}
}
