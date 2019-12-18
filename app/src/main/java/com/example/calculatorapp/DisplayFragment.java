package com.example.calculatorapp;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class DisplayFragment extends Fragment {
    EditText mExpressionTextView;
    String mExpression;
    ImageView mBackSpace;
    DisplayFragmentListener mDisplayFragmentListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDisplayFragmentListener = (DisplayFragmentListener) context;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_display, container, false);
        mExpressionTextView = view.findViewById(R.id.expression_text);
        mBackSpace = view.findViewById(R.id.delete);
        mBackSpace.setAlpha(0.5f);

//        mExpressionTextView.setFilters(new InputFilter[] {new InputFilter.LengthFilter(15)});
        mExpressionTextView.setShowSoftInputOnFocus(false);

        mExpressionTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mExpression = mExpressionTextView.getText().toString();

                mDisplayFragmentListener.sendStringExpression(mExpression);
                int  expLength = mExpression.length();

                if (expLength>16 && expLength <35){
                    mExpressionTextView.setTextSize(35);
                }else if (expLength>36 && expLength <100){
                    mExpressionTextView.setTextSize(30);
                }

                if (mExpression.length() > 0) {
                    mBackSpace.setAlpha(1f);
                } else {
                    mBackSpace.setAlpha(0.5f);
                }
            }
        });

        mBackSpace.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mExpressionTextView.getText().clear();
                return false;
            }
        });

        mBackSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cursorPosition = mExpressionTextView.getSelectionStart();

                if (cursorPosition > 0) {
                    mExpressionTextView.setText(mExpressionTextView.getText().delete(cursorPosition - 1, cursorPosition));
                    mExpressionTextView.setSelection(cursorPosition - 1);
                }
            }
        });

        return view;
    }

    //Receive message
    public void displayKeypadValues(String msg) {
//        Log.d("Last String ", String.valueOf(msg.charAt(msg.length() - 1)));

        int start = mExpressionTextView.getSelectionStart();
        mExpressionTextView.getText().insert(start, msg);
    }

    public void clearDisplayValue(String msg) {
        mExpressionTextView.setText(msg);
    }


    public void getCalculateResult(String result) {
        mExpressionTextView.setText(result);
    }

    public void setPlusOrMinus(String msg){
        mExpressionTextView.getText().insert(0,msg);
    }


    public interface DisplayFragmentListener {
        void sendStringExpression(String msg);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mDisplayFragmentListener = null;
    }
}
