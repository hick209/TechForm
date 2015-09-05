package info.nivaldobondanca.techform.util;

import android.databinding.BindingAdapter;
import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ViewAnimator;

/**
 * @author Nivaldo Bondança
 */
public class BindingUtils {

	@BindingAdapter("adapter")
	public void setAdapter(ListView view, ListAdapter adapter) {
		view.setAdapter(adapter);
	}

	@BindingAdapter("onItemClick")
	public <T extends Adapter> void setOnItemClickListener(AdapterView<T> view,
			AdapterView.OnItemClickListener l) {
		view.setOnItemClickListener(l);
	}

	@BindingAdapter("displayChild")
	public void setDisplayChild(ViewAnimator view, int child) {
		view.setDisplayedChild(child);
	}

	@BindingAdapter("refreshing")
	public void setRefreshing(SwipeRefreshLayout view, boolean refreshing) {
		view.setRefreshing(refreshing);
	}

	@BindingAdapter("onRefresh")
	public void setOnRefreshListener(SwipeRefreshLayout view,
			SwipeRefreshLayout.OnRefreshListener l) {
		view.setOnRefreshListener(l);
	}

	@BindingAdapter("colorScheme")
	public void setRefreshLayoutColorScheme(SwipeRefreshLayout view, @ColorRes int[] colors) {
		view.setColorSchemeResources(colors);
	}
}
