package info.nivaldobondanca.techform.content;

import info.nivaldobondanca.backend.techform.techFormAPI.TechFormAPI;

/**
 * @author Nivaldo Bondança
 */
public interface ApiCall<T> {
	T call(TechFormAPI api);
}
