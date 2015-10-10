package finep.inovatec.util;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import finep.inovatec.R;

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
