package info.nivaldobondanca.techform.form;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import info.nivaldobondanca.backend.techform.techFormAPI.model.Form;
import info.nivaldobondanca.techform.R;
import info.nivaldobondanca.techform.content.FormContentObserver;
import info.nivaldobondanca.techform.group.GroupDetailsActivity;
import info.nivaldobondanca.techform.widget.BasicListAdapter;

import static com.google.common.base.Objects.firstNonNull;

/**
 * @author Nivaldo Bondança
 */
public class FormListFragment extends Fragment implements FormContentObserver,
		AdapterView.OnItemClickListener {

	public static FormListFragment instantiate() {
		Bundle args = new Bundle();

		FormListFragment fragment = new FormListFragment();
		fragment.setArguments(args);

		return fragment;
	}

	private FormAdapter        mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GroupDetailsActivity activity = (GroupDetailsActivity) getActivity();
		activity.registerFormDataObserver(this);

		mAdapter = new FormAdapter();
		mAdapter.changeData(activity.getData());
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		((GroupDetailsActivity) getActivity()).unregisterFormDataObserver(this);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_form_list, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		ListView formList = (ListView) view.findViewById(android.R.id.list);
		formList.setAdapter(mAdapter);
		formList.setOnItemClickListener(this);
		formList.setItemsCanFocus(false);
	}

	@Override
	public void onChange(GroupDetailsActivity activity, List<Form> newData) {
		mAdapter.changeData(newData);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		((GroupDetailsActivity) getActivity()).onFormClick(position);
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
