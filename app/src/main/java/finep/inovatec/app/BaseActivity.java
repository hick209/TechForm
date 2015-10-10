package finep.inovatec.app;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import finep.inovatec.R;

/**
 * @author Nivaldo Bondança
 */
public class BaseActivity extends AppCompatActivity {

	public Toolbar setupToolbar() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		return toolbar;
	}



	private TechFormApplication getTechFormApplication() {
		return (TechFormApplication) getApplication();
	}

}
