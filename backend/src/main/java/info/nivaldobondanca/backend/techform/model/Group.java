package info.nivaldobondanca.backend.techform.model;

import com.google.appengine.api.datastore.Entity;

/**
 * @author Nivaldo Bondança
 */
public class Group {

	private long   mId;
	private String mName;

	public long getId() {
		return mId;
	}

	public void setId(long id) {
		this.mId = id;
	}

	public String getName() {
		return mName;
	}

	public void setName(String name) {
		this.mName = name;
	}

	public static Group fromEntity(Entity entity) {
		Group group = new Group();

		String name = (String) entity.getProperty("name");
		group.setName(name);

		long id = entity.getKey().getId();
		group.setId(id);

		return group;
	}
}
