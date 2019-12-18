package com.example.calculatorapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class KeyPadFragment extends Fragment {

    private GridView mGridView;

    ArrayList<KeyJDO> mKeyPadList = new ArrayList<>();

    private KeypadFragmentListener mKeypadFragmentListener;

    private String mFinalString = null;
    private String mKeyPadValue;

    private ArrayList<Character> mOperators;
    StringBuffer myString = new StringBuffer();

    Boolean token = true;

    KeyPadFragment() {
        mOperators = new ArrayList<>();
        mOperators.add('/');
        mOperators.add('*');
        mOperators.add('+');
        mOperators.add('-');
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mKeypadFragmentListener = (KeypadFragmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_key_pad, container, false);

        mGridView = view.findViewById(R.id.gridView1);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                KeyJDO lKeyJDO = mKeyPadList.get(position);
                mKeyPadValue = lKeyJDO.getText();
                myString.append(mKeyPadValue);
                String stringFirstIndex = String.valueOf(myString.charAt(0));

                try {
                    if (mKeyPadValue.equals("C")) {
                        mKeypadFragmentListener.clearDisplaytext("");
                        myString.delete(0, myString.length());
                    } else if (mKeyPadValue.equals("+/-")) {
                        mKeypadFragmentListener.sendPlusOrMinus("-");
                    } else if (mKeyPadValue.equals("=")) {

                        if (!checkBalancedParentesis(mFinalString)) {
                            Toast.makeText(getActivity(), "Invalid Format", Toast.LENGTH_LONG).show();
                        }
                        if (!checkValidOperator(mFinalString)) {
                            Toast.makeText(getActivity(), "Invalid Format", Toast.LENGTH_LONG).show();
                        } else {
                            doIfValidExpression();
                        }

                    } else if (stringFirstIndex.contains("+") || stringFirstIndex.contains("-") || stringFirstIndex.contains("/") || stringFirstIndex.contains("*") || stringFirstIndex.contains(")")) {
                        myString.delete(0, myString.length());

                    } else if (token){
                        mKeypadFragmentListener.sendKeypadValues(mKeyPadValue);
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Invalid Format", Toast.LENGTH_SHORT).show();
                }


            }
        });


        mKeyPadList = loadData();


        mGridView.setAdapter(new KeypadViewAdapter(getActivity(), mKeyPadList));

        return view;
    }

    public ArrayList<KeyJDO> loadData() {

        int greenColor = getResources().getColor(R.color.green);
        int whiteColor = getResources().getColor(R.color.white);
        int redColor = getResources().getColor(R.color.colorAccent);
        int blackColor = getResources().getColor(R.color.black);

        ArrayList<KeyJDO> data = new ArrayList<>();
        data.add(new KeyJDO("C", redColor, R.drawable.button_background));
        data.add(new KeyJDO("(", greenColor, R.drawable.button_background));
        data.add(new KeyJDO(")", greenColor, R.drawable.button_background));
        data.add(new KeyJDO("/", greenColor, R.drawable.button_background));
        data.add(new KeyJDO("7", blackColor, R.drawable.button_background));
        data.add(new KeyJDO("8", blackColor, R.drawable.button_background));
        data.add(new KeyJDO("9", blackColor, R.drawable.button_background));
        data.add(new KeyJDO("*", greenColor, R.drawable.button_background));
        data.add(new KeyJDO("4", blackColor, R.drawable.button_background));
        data.add(new KeyJDO("5", blackColor, R.drawable.button_background));
        data.add(new KeyJDO("6", blackColor, R.drawable.button_background));
        data.add(new KeyJDO("-", greenColor, R.drawable.button_background));
        data.add(new KeyJDO("1", blackColor, R.drawable.button_background));
        data.add(new KeyJDO("2", blackColor, R.drawable.button_background));
        data.add(new KeyJDO("3", blackColor, R.drawable.button_background));
        data.add(new KeyJDO("+", greenColor, R.drawable.button_background));
        data.add(new KeyJDO("+/-", blackColor, R.drawable.button_background));
        data.add(new KeyJDO("0", blackColor, R.drawable.button_background));
        data.add(new KeyJDO(".", blackColor, R.drawable.button_background));
        data.add(new KeyJDO("=", whiteColor, R.drawable.button_background_green));
        return data;
    }


    public boolean validateInput(String msg) {
        int count = 0;
        for (int i = 0; i < msg.length(); i++) {
            if (msg.charAt(i) >= '0' && msg.charAt(i) <= '9') {
                count++;

            } else {
                count = 0;
                token=true;
            }
            if (count > 15) {
                return false;
            }
        }
        token =true;
        return true;
    }

    public void getStringExpression(String expression) {
        if (!expression.isEmpty()) {

            if (!validateInput(expression)) {
                token=false;
                Toast.makeText(getActivity(), "Can't enter more than 15 digits", Toast.LENGTH_LONG).show();
            }
            mFinalString = expression;
        }
    }


    //Interface for communication
    public interface KeypadFragmentListener {
        void sendKeypadValues(String msg);

        void clearDisplaytext(String msg);

        void sendCalculateResult(String msg);

        void sendPlusOrMinus(String msg);
    }

    private void doIfValidExpression() {
        String result = findBraces(mFinalString);
        try {
            float finalResult = evaluateExpression(result);

            if (String.valueOf(finalResult).endsWith(".0")) {
                Integer val = Math.round(finalResult);
                mKeypadFragmentListener.sendCalculateResult(String.valueOf(val));
            } else {
                mKeypadFragmentListener.sendCalculateResult(String.valueOf(finalResult));
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Invalid Format", Toast.LENGTH_SHORT).show();
        }

    }


    public Boolean checkBalancedParentesis(String pExpression) {

        Stack<Character> lStack = new Stack<>();
        for (int i = 0; i < pExpression.length(); i++) {
            char current = pExpression.charAt(i);
            if (current == '(') {
                lStack.push(current);
            }
            if (current == ')') {
                char last = lStack.peek();
                if (current == ')' && last == '(') {
                    lStack.pop();
                }
            }
        }
        return lStack.isEmpty() ? true : false;
    }

    public Boolean checkValidOperator(String pFinalString) {

        String[] opr = new String[]{"+", "-", "/", "*"};

        List<String> list = Arrays.asList(opr);
        for (int i = 1; i < pFinalString.length(); i++) {
            String next = String.valueOf(pFinalString.charAt(i));
            String previous = String.valueOf(pFinalString.charAt(i - 1));
            System.out.println(next + " " + previous);
            if (list.contains(next) && list.contains(previous)) {
                return false;
            }
        }
        return true;

    }


    public String findBraces(String pInput) {
        String lTemp = "";
        Stack<Character> lCharStack = new Stack<>();
        lCharStack.add('(');
        char lOpr;
        pInput = pInput + ")";

        for (int i = 0; i < pInput.length(); i++) {
            lOpr = pInput.charAt(i);

            if (lOpr == '(') {
                lCharStack.add('(');

            } else if (mOperators.contains(lOpr)) {
                lCharStack.add(lOpr);
            } else if (lOpr == ')') {
                lTemp = lTemp + pop(lCharStack);

            } else {
                while (lOpr != '(' && lOpr != ')' && !mOperators.contains(lOpr)) {
                    lTemp = lTemp + lOpr;
                    i = i + 1;
                    lOpr = pInput.charAt(i);
                }
                lTemp = lTemp + " ";
                i = i - 1;
            }

        }
        return lTemp;
    }


    private String pop(Stack<Character> al) {
        String temp = "";
        for (int i = al.size() - 1; al.get(i) != '(' && i >= 0; i--) {
            temp = temp + al.get(i);
            al.remove(i);
        }
        al.remove(al.size() - 1);
        return temp;
    }


    public float evaluateExpression(String inputP) {
        Stack<Float> lFinalStack = new Stack<>();
        char lOpr;
        float lValueA;
        float lValueB;
        for (int i = 0; i < inputP.length(); i++) {
            lOpr = inputP.charAt(i);
            if (mOperators.contains(lOpr)) {
                lValueB = lFinalStack.pop();
                lValueA = lFinalStack.pop();
                switch (lOpr) {
                    case '/':
                        lFinalStack.push(lValueA / lValueB);
                        break;
                    case '*':
                        lFinalStack.push(lValueA * lValueB);
                        break;
                    case '+':
                        lFinalStack.push(lValueA + lValueB);
                        break;
                    case '-':
                        lFinalStack.push(lValueA - lValueB);
                        break;
                }
            } else {
                String temp = "";
                while (!mOperators.contains(lOpr) && i < inputP.length() && lOpr != ' ') {
                    temp = temp + lOpr;
                    i = i + 1;
                    lOpr = inputP.charAt(i);
                }
                lFinalStack.push((Float.parseFloat(temp)));
            }
        }
        return lFinalStack.pop();
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mKeypadFragmentListener = null;
    }
}
