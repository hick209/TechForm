package finep.inovatec.form;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import finep.inovatec.R;
import finep.inovatec.common.BaseFragment;
import finep.inovatec.common.BasicListAdapter;
import finep.inovatec.databinding.FragmentFormSectionsBinding;
import finep.inovatec.databinding.ItemFormSectionBinding;
import info.nivaldobondanca.backend.techform.techFormAPI.model.FormSection;

/**
 * @author Nivaldo Bondança
 */
public class FormSectionsFragment extends BaseFragment implements AdapterView.OnItemClickListener {

	private static final String ARG_POSITION = "arg.POSITION";

	public static FormSectionsFragment instantiate(int position) {
		Bundle args = new Bundle();
		args.putInt(ARG_POSITION, position);

		FormSectionsFragment fragment = new FormSectionsFragment();
		fragment.setArguments(args);
		return fragment;
	}

	private FormSectionsAdapter mAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mAdapter = new FormSectionsAdapter(getContext());
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		FragmentFormSectionsBinding binding = FragmentFormSectionsBinding.inflate(inflater, container, false);
		binding.setViewModel(this);

		return binding.getRoot();
	}

	public FormSectionsAdapter getAdapter() {
		return mAdapter;
	}

	public boolean getEmpty() {
		return mAdapter.isEmpty();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// TODO
	}


	public class FormSectionsAdapter extends BasicListAdapter<FormSection, ItemFormSectionBinding> {

		public FormSectionsAdapter(Context context) {
			super(context);
		}

		@Override
		protected void bindView(ItemFormSectionBinding binding, FormSection item) {
			binding.setViewModel(new SectionItemViewModel(item));
		}

		@Override
		protected int getItemLayoutResource() {
			return R.layout.item_form_section;
		}

		@Override
		public long getItemId(int position) {
			return getItem(position).getId();
		}
	}
}
