package com.example.calculatorapp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class KeyPadFragmentTest {

    KeyPadFragment keyPadFragment;

    @Before
    public void setUp() {
        keyPadFragment = new KeyPadFragment();
    }

    @Test
    public void checkBrackets() {
        Assert.assertEquals(true, keyPadFragment.checkBalancedParentesis("(9+1)+(8-1)"));
        Assert.assertEquals(false, keyPadFragment.checkBalancedParentesis("(9+1)+(8-1"));
        Assert.assertEquals(false, keyPadFragment.checkBalancedParentesis("((3*3+(8-1))"));
        Assert.assertEquals(true, keyPadFragment.checkBalancedParentesis(""));
        Assert.assertEquals(false, keyPadFragment.checkBalancedParentesis("((("));

    }

    @Test
    public void checkValidExpresion() {
        Assert.assertEquals(true, keyPadFragment.validateInput("9+1*3"));
        Assert.assertEquals(false, keyPadFragment.validateInput("9439182734444555+1*3"));
        Assert.assertEquals(false, keyPadFragment.checkValidOperator("234++355"));
        Assert.assertEquals(false, keyPadFragment.checkValidOperator("234+-355"));
        Assert.assertEquals(true, keyPadFragment.checkValidOperator("234*355"));
    }

    @Test
    public void evaluateExpression(){
        Assert.assertEquals("9 1 +3 8 4 /-*", keyPadFragment.findBraces("(9+1)*3-8/4"));
        Assert.assertEquals("9 1 +3 1 -*", keyPadFragment.findBraces("((9+1)*(3-1))"));
        Assert.assertEquals(10.0f,10.0f, keyPadFragment.evaluateExpression("9 1 +3 8 4 /-*"));
        Assert.assertEquals(20.0f,20.0f, keyPadFragment.evaluateExpression("9 1 +3 1 -*"));
    }


//    @Test(expected = ArithmeticException.class)
//    public void calculatorTestDivisionByZero() {
////        keyPadFragment.evaluateExpression("9 0 /");
////        keyPadFragment.evaluateExpression("(9+3)/0");
////        keyPadFragment.evaluateExpression("((9+3/0))");
//    }
//
//    @Test(expected = NumberFormatException.class)
//    public void calculatorTestNumberFormat() {
////        keyPadFragment.evaluate("jhgjhgjh");
////        keyPadFragment.evaluate("(9+1)+(9+bb)");
////        keyPadFragment.evaluate("(9+1)+[9+bb]");
////        keyPadFragment.evaluate("{9+19+0}");
////        keyPadFragment.evaluate("5746587461-13353453");
////        keyPadFragment.evaluate("(6+1)-(8-1)*10+");
////        keyPadFragment.evaluate("/(6+1)");
////        keyPadFragment.evaluate("(1+/2)+(8*-9)");
//
//    }
}
