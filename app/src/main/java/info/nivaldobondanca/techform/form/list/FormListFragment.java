package info.nivaldobondanca.techform.form.list;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.List;

import info.nivaldobondanca.backend.techform.techFormAPI.model.Form;
import info.nivaldobondanca.techform.R;
import info.nivaldobondanca.techform.content.FormContentObserver;
import info.nivaldobondanca.techform.databinding.FragmentFormListBinding;
import info.nivaldobondanca.techform.group.details.GroupDetailsActivity;
import info.nivaldobondanca.techform.widget.BasicListAdapter;

import static com.google.common.base.Objects.firstNonNull;

/**
 * @author Nivaldo Bondança
 */
public class FormListFragment extends Fragment implements FormContentObserver,
		AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

	public static FormListFragment instantiate() {
		Bundle args = new Bundle();

		FormListFragment fragment = new FormListFragment();
		fragment.setArguments(args);

		return fragment;
	}

	private GroupDetailsActivity mActivity;

	private FormListViewModel mViewModel;
	private FormAdapter       mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mActivity = (GroupDetailsActivity) getActivity();
		mActivity.registerFormDataObserver(this);

		mAdapter = new FormAdapter();
		mAdapter.changeData(mActivity.getData());

		mViewModel = new FormListViewModel(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mActivity.unregisterFormDataObserver(this);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		FragmentFormListBinding binding = FragmentFormListBinding.inflate(inflater, container, false);
		binding.setViewModel(mViewModel);

		return binding.getRoot();
	}

	@Override
	public void onChange(GroupDetailsActivity activity, List<Form> newData) {
		mAdapter.changeData(newData);
		mViewModel.setLoading(false);
	}

	@Override
	public void onRefresh() {
		mViewModel.setLoading(true);
		mActivity.onRefresh();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		((GroupDetailsActivity) getActivity()).onFormClick(position);
	}

	public FormAdapter getAdapter() {
		return mAdapter;
	}

	private class FormAdapter extends BasicListAdapter<Form> {
		public FormAdapter() {
			super(getActivity());
		}

		@Override
		public long getItemId(int position) {
			return getItem(position).getId();
		}

		@Override
		protected @LayoutRes int getItemLayoutResource() {
			return R.layout.item_form_card;
		}

		@Override
		protected void bindView(View view, Form item) {
			String code = firstNonNull(item.getCodeName(), String.valueOf(item.getCode()));
			((TextView) view.findViewById(R.id.form_code)).setText(code);

			((TextView) view.findViewById(R.id.form_name)).setText(item.getName());
		}
	}
}
