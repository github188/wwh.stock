package com.bm.wanma.view;

import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;

public class FocusChange implements OnFocusChangeListener {

	private String text;
	private EditText editText;
	private ImageButton button;
	
	public FocusChange(EditText editText, String text,ImageButton button){
		this.editText = editText;
		this.text = text;
		this.button = button;
		
	}
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		if(hasFocus){
//			editText.setHint("");
			
			String phone = editText.getText().toString().trim();
			if (phone.length() == 0) {
				if (button != null && button.getVisibility() == 0) {
					button.setVisibility(View.INVISIBLE);
				}
			}else{
				if (button != null && button.getVisibility() == 4) {
					button.setVisibility(View.VISIBLE);
				}
			}
			
			
			return;
		}
		if (button != null && button.getVisibility() == 0) {
			button.setVisibility(View.INVISIBLE);
		}
//		editText.setHint(text);
	}

}
