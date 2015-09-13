package finep.inovatec.util;

import android.databinding.BindingAdapter;
import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ViewAnimator;

/**
 * @author Nivaldo Bondança
 */
public class BindingUtils {

	@BindingAdapter("adapter")
	public static void setAdapter(ListView view, ListAdapter adapter) {
		view.setAdapter(adapter);
	}

	@BindingAdapter("onItemClick")
	public static void setOnItemClickListener(ListView view,
			AdapterView.OnItemClickListener l) {
		view.setOnItemClickListener(l);
	}

	@BindingAdapter("displayChild")
	public static void setDisplayChild(ViewAnimator view, int child) {
		view.setDisplayedChild(child);
	}

	@BindingAdapter("refreshing")
	public static void setRefreshing(SwipeRefreshLayout view, boolean refreshing) {
		view.setRefreshing(refreshing);
	}

	@BindingAdapter("onRefresh")
	public static void setOnRefreshListener(SwipeRefreshLayout view,
			SwipeRefreshLayout.OnRefreshListener l) {
		view.setOnRefreshListener(l);
	}

	@BindingAdapter("colorScheme")
	public static void setRefreshLayoutColorScheme(SwipeRefreshLayout view, @ColorRes int[] colors) {
		view.setColorSchemeResources(colors);
	}
}
