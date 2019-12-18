package com.example.calculatorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements KeyPadFragment.KeypadFragmentListener, DisplayFragment.DisplayFragmentListener {


    private KeyPadFragment mKeyPadFragment;
    private DisplayFragment mDisplayFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mKeyPadFragment = new KeyPadFragment();
        mDisplayFragment = new DisplayFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.display_fragment, mDisplayFragment)
                .replace(R.id.keypad_fragment, mKeyPadFragment)
                .commit();
    }


    @Override
    public void sendKeypadValues(String msg) {
        FragmentManager manager = getSupportFragmentManager();
        DisplayFragment mReceiverFragment = (DisplayFragment)manager.findFragmentById(R.id.display_fragment);
        mReceiverFragment.displayKeypadValues(msg);
    }

    @Override
    public void clearDisplaytext(String msg) {
        FragmentManager manager = getSupportFragmentManager();
        DisplayFragment mReceiverFragment = (DisplayFragment)manager.findFragmentById(R.id.display_fragment);
        mReceiverFragment.clearDisplayValue(msg);
    }

    @Override
    public void sendCalculateResult(String msg) {
        FragmentManager manager = getSupportFragmentManager();
        DisplayFragment mReceiverFragment = (DisplayFragment)manager.findFragmentById(R.id.display_fragment);
        mReceiverFragment.getCalculateResult(msg);
    }

    @Override
    public void sendPlusOrMinus(String msg) {
        FragmentManager manager = getSupportFragmentManager();
        DisplayFragment mReceiverFragment = (DisplayFragment)manager.findFragmentById(R.id.display_fragment);
        mReceiverFragment.setPlusOrMinus(msg);
    }

    @Override
    public void sendStringExpression(String expression) {
        FragmentManager manager = getSupportFragmentManager();
        KeyPadFragment mSenderFragment = (KeyPadFragment) manager.findFragmentById(R.id.keypad_fragment);
        mSenderFragment.getStringExpression(expression);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
