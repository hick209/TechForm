package info.nivaldobondanca.techform.util;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import info.nivaldobondanca.techform.R;

/**
 * @author Nivaldo Bondança
 */
public class Utils {

	public static Toolbar setupToolbar(AppCompatActivity activity) {
		Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
		activity.setSupportActionBar(toolbar);

		return toolbar;
	}
}
