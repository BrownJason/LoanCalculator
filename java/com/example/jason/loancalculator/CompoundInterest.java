package com.example.jason.loancalculator;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by jason on 11/23/17.
 *
 * This class will be created you the user to calculate compound interest
 * on their savings account. How much they will make with doing monthly
 * deposits along with their initial deposit.
 *
 *
 * Compound Interest Formula
 * A = P(1 + (r/n))^(nt)
 * A = Amount
 * P = Principal
 * r = Interest Rate
 * n = Number of time interest is compounded per year
 * t = Time in years
 *
 * A = 5,000(1 + (0.05/12))^(12*6)
 *
 */

public class CompoundInterest <T extends Number> extends Activity {

    private Integer principle;
    private Integer terms;
    private Double interestRate;
    private Double savingsAmount;
    private Byte n = 12;

    //set the principle for later use in the get method
    private void setPrinciple(Integer principleAmount){
        principle = principleAmount;
    }

    private Integer getPrinciple(){
        return principle;
    }

    //set the loan term in years and then put it into months
    private void setTerms(Integer term){
        terms = term;
    }

    private Integer getTerms(){
        return terms;
    }

    //sets the APR for monthly interest rates instead for use in the monthly
    // payment calculation later on
    private void setInterestRate(Double rate){
        interestRate = rate/100;
    }

    private Double getInterestRate(){
        return interestRate;
    }

    private Double Calculate(){
        savingsAmount = (getPrinciple() * Math.pow((1+(getInterestRate()/n)),(n*(getTerms()))));

        return savingsAmount;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


    }

}
