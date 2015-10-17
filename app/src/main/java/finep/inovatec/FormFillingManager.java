package finep.inovatec;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import finep.inovatec.app.CacheAgent;
import finep.inovatec.data.Filling;
import info.nivaldobondanca.backend.techform.techFormAPI.model.Group;

/**
 * @author Nivaldo Bondança
 */
public class FormFillingManager {

	private static FormFillingManager instance;

	public static void init(CacheAgent cacheAgent) {
		instance = new FormFillingManager(cacheAgent);
	}

	public static FormFillingManager getInstance() {
		return instance;
	}


	private CacheAgent mCacheAgent;
	private Filling    mFilling;
	private Group      mGroup;

	private FormFillingManager(CacheAgent cacheAgent) {
		mCacheAgent = cacheAgent;
	}

	public Group getGroup() {
		return mGroup;
	}

	public void setGroup(Group group) {
		mGroup = group;
	}

	public Filling getFilling() {
		return mFilling;
	}

	public void setFilling(Filling filling) {
		mFilling = filling;
	}

	public boolean save() {
		long groupId = mFilling.getGroupId();
		Set<Filling> fillings;
		try {
			// Load
			fillings = mCacheAgent.loadFillings(groupId);
			fillings.remove(mFilling);
		}
		catch (IOException e) {
			fillings = new HashSet<>();
		}

		fillings.add(mFilling);

		try {
			// Save
			mCacheAgent.saveFillings(groupId, fillings);
			return true;
		}
		catch (IOException ignored) {
		}

		return false;
	}
}
