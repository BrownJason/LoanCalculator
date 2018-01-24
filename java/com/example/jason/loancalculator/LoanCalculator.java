package com.example.jason.loancalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.Scanner;

import static java.lang.Math.ceil;

import static java.lang.Math.*;

/**
 * Created by jason on 11/20/17.
 */
public class LoanCalculator<T extends Number>  extends Activity{

    // Declare the variables
    private Integer currentLoanAmount;
    private Integer terms;
    private Double interestRate;
    private Double monthlyPayment;

    /**
     *
     * Home and Auto Insurance
     * Loan Payment = Amount / Discount Factor
     * P = A / D
     * n = payment period (12 times per year)
     * i = (Annualy Percentage Rate divided into monthly payment, ie 6% - .06 /12 = .005
     * D = {[(1+i)^n]-1} / [i(1+i)^n]
     *
     */

    //set the loan amount for later use in the get method
    private void setLoanAmount(Integer loanAmount){
        currentLoanAmount = loanAmount;
    }

    private Integer getLoanAmount(){
        return currentLoanAmount;
    }

    //set the loan term in years and then put it into months
    private void setTerms(Integer term){
        terms = term * 12;
    }

    private Integer getTerms(){
        return terms;
    }

    //sets the APR for monthly interest rates instead for use in the monthly
    // payment calculation later on
    private void setInterestRate(Double rate){
        interestRate = ((rate/100) /12);
    }

    private Double getInterestRate(){
        return (interestRate*100)/100;
    }

    //Computes the monthly discount for amortizing loands (standard home and auto loans)
    private Double getDiscount(){
        Double discount = ((((1+getInterestRate())*getTerms())-1) / (((1+ getInterestRate())* getTerms())*getInterestRate()));

        return (ceil(discount*100)/100);
    }

    private Double Calculate(){
        monthlyPayment = getLoanAmount() / getDiscount();

        return monthlyPayment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_auto_insurance);

        final LoanCalculator<Number> carLoan = new LoanCalculator<>();

        final EditText loanAmount = findViewById(R.id.loanAmount);
        final EditText termLength = findViewById(R.id.term);
        final EditText interst = findViewById(R.id.interestRate);
        final TextView discountTV = findViewById(R.id.monthly_discount);
        final TextView paymentTv = findViewById(R.id.monthly_payment);

        Button calculateBtn = findViewById(R.id.calculate);
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * If you enter in a number with a comma
                 */
                if(loanAmount.getText().toString().contains(",")){
                    try {
                        carLoan.setLoanAmount(Integer.parseInt(loanAmount.getText().toString().replace(",","")));
                    } catch(NumberFormatException e){
                        Log.d("Error!","Wrong number format!");
                    }
                } else {
                    carLoan.setLoanAmount(Integer.parseInt(loanAmount.getText().toString()));
                }
                //carLoan.setTerms(Integer.parseInt(termLength.getText().toString()));
                carLoan.setInterestRate(Double.parseDouble(interst.getText().toString()));
                carLoan.setTerms(Integer.parseInt(termLength.getText().toString()));


                NumberFormat numFormat = NumberFormat.getCurrencyInstance();

                paymentTv.setText(numFormat.format(carLoan.Calculate()));
                discountTV.setText(String.format("$%.2f", carLoan.getDiscount()));
            }
        });

        Button backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(LoanCalculator.this, MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        });
    }
}