package finep.inovatec.form;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.Map;

import finep.inovatec.FormFillingManager;
import finep.inovatec.R;
import finep.inovatec.common.BaseFragment;
import finep.inovatec.common.BasicListAdapter;
import finep.inovatec.data.FillingFormSection;
import finep.inovatec.databinding.FragmentFormSectionsBinding;
import finep.inovatec.databinding.ItemFormSectionBinding;
import finep.inovatec.section.SectionQuestionsActivity;
import finep.inovatec.util.Utils;
import info.nivaldobondanca.backend.techform.techFormAPI.model.Form;
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
	private int mFormPosition;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mFormPosition = getArguments().getInt(ARG_POSITION, -1);
		Form form = FormFillingManager.getInstance().getGroup().getForms().get(mFormPosition);
		mAdapter = new FormSectionsAdapter(getContext());
		mAdapter.changeData(form.getSections());
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		FragmentFormSectionsBinding binding = FragmentFormSectionsBinding.inflate(inflater, container, false);
		binding.setViewModel(this);

		return binding.getRoot();
	}

	@Override
	public void onResume() {
		super.onResume();
		// Recalculate completeness
		mAdapter.notifyDataSetChanged();
	}

	public FormSectionsAdapter getAdapter() {
		return mAdapter;
	}

	public boolean getEmpty() {
		return mAdapter.isEmpty();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if (view.isEnabled()) {
			startActivity(SectionQuestionsActivity.newInstance(getContext(), mFormPosition, position));
		}
	}

	public class FormSectionsAdapter extends BasicListAdapter<FormSection, ItemFormSectionBinding> {

		private Map<FormSection, SectionItemViewModel> mViewModels;

		public FormSectionsAdapter(Context context) {
			super(context);
			mViewModels = new ArrayMap<>();
		}

		@Override
		protected void bindView(ItemFormSectionBinding binding, FormSection item) {
			SectionItemViewModel viewModel = new SectionItemViewModel(item);

			viewModel.setComplete(isSectionComplete(item));

			mViewModels.put(item, viewModel);

			binding.setViewModel(viewModel);
		}

		@Override
		public boolean isEnabled(int position) {
			return !mViewModels.get(getItem(position)).getComplete();
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

	private boolean isSectionComplete(FormSection section) {
		FillingFormSection fillingSection = FormFillingManager.getInstance().getFilling().getForms()
				.get(mFormPosition).getSection(section.getCodeName());

		return Utils.isSectionComplete(section, fillingSection);
	}
}
