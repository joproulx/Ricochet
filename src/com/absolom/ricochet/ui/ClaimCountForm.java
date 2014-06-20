package com.absolom.ricochet.ui;

import com.absolom.utility.common.Event;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

public class ClaimCountForm extends Fragment {
	
	public Event<ClaimFormEventArgs> OnClaimedSolution = new Event<ClaimFormEventArgs>();
			
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		LinearLayout layoutForm = new LinearLayout(getActivity());
		layoutForm.setOrientation(LinearLayout.VERTICAL);
				
		LinearLayout layoutNumber = new LinearLayout(getActivity());
		layoutNumber.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView text = new TextView(getActivity());
		text.setText("Enter move count:");
		layoutNumber.addView(text);
		
		final NumberPicker picker = new NumberPicker(getActivity());
		picker.setMinValue(1);
		picker.setMaxValue(100);
		layoutNumber.addView(picker);
		
		LinearLayout layoutButtons = new LinearLayout(getActivity());
		layoutNumber.setOrientation(LinearLayout.HORIZONTAL);
		
		Button buttonOk = new Button(getActivity());
		buttonOk.setText("OK");
		Button buttonCancel = new Button(getActivity());
		buttonCancel.setText("Cancel");
		
		buttonOk.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentSwitcher.getInstance().gotoBack();
				
				OnClaimedSolution.triggers(this, new ClaimFormEventArgs(picker.getValue()));
			}
		});
		buttonCancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentSwitcher.getInstance().gotoBack();
				
			}
		});
		
		layoutButtons.addView(buttonOk);
		layoutButtons.addView(buttonCancel);
		
		layoutForm.addView(layoutNumber);
		layoutForm.addView(layoutButtons);
				
		return layoutForm;
	}
	
	 
}
