package finep.inovatec.util;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import finep.inovatec.R;

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

	@BindingAdapter("error")
	public static void setError(TextInputLayout view, CharSequence error) {
		view.setError(error);
	}

	@BindingAdapter("textWatcher")
	public static void addTextChangedListener(EditText view, TextWatcher textWatcher) {
		view.addTextChangedListener(textWatcher);
		view.setText(view.getText()); // Call the watcher
	}

	@BindingAdapter("iconEnd")
	public static void setCompoundDrawableEnd(TextView view, Drawable drawable) {
		view.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null);
	}

	@BindingAdapter("iconStart")
	public static void setCompoundDrawableStart(TextView view, Drawable drawable) {
		view.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, null, null, null);
	}
}
