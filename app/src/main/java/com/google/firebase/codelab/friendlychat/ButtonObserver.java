package com.google.firebase.codelab.friendlychat;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;

public class ButtonObserver implements TextWatcher {

    private final Button mButton;

    public ButtonObserver(Button button) {
        mButton = button;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
        // No-op
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        mButton.setEnabled(!TextUtils.isEmpty(charSequence.toString().trim()));
    }

    @Override
    public void afterTextChanged(Editable editable) {
        // No-op
    }

}
