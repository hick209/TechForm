package finep.inovatec.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;

/**
 * @author Nivaldo Bondança
 */
public class Utils {

	public static Drawable getDrawable(Context context, @DrawableRes int resource) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			return context.getDrawable(resource);
		}
		else {
			return context.getResources().getDrawable(resource);
		}
	}

}
