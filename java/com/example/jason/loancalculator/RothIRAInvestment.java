package com.example.jason.loancalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static java.sql.Types.NULL;

/**
 * Created by jason on 11/27/17.
 */

public class RothIRAInvestment <T extends Number> extends Activity {

    /**
     *
     * Roth IRA Investment Caluclator
     *
     * This calculator determines the future value of a retirement fund
     *
     * FV annuity due = PMT((((1+R)^n)-1)/R)*(1+R)
     * FV = Future Value
     * PMT = Yearly Payment - Maximum of 5500
     * R = Interest Rate
     * n = Years of the investment
     *
     * Taxable Savings is similar
     * TR = (R-(R-MTR))
     * TS = PMT((((1+TR)^n)-1)/TR)*(1+TR)
     * TR = Taxable Interest Rate
     * MTR = Marginal Tax Rate
     *
     */

    private Integer terms;
    private Double interestRate;
    private Double marginalInterestRate;
    private Double totalReturns;
    private Double taxableAmount;
    private Integer payment;
    private Double taxableInterest;
    private Integer startingBalance;
    private Double startPayment;

    private void setStartingBalance(Integer startBalance){
        startingBalance = startBalance;
    }

    private Integer getStartingBalance() {
        return startingBalance;
    }
    //set the loan term in years and then put it into months
    private void setTerms(Integer term){
        terms = term;
    }

    private Integer getTerms(){
        return terms;
    }

    private void setPayment(Integer monthlyPayment){
        payment = monthlyPayment;
    }

    private Integer getPayment(){
        return payment;
    }

    private void setMarginalInterestRate(Double marginalRate){
        marginalInterestRate = marginalRate;
    }

    private Double getMarginalInterestRate() {
        return marginalInterestRate/100;
    }

    //sets the APR for monthly interest rates instead for use in the monthly
    // payment calculation later on
    private void setInterestRate(Double rate){
        interestRate = rate/100;
    }

    private Double getInterestRate(){
        return interestRate;
    }

    private Double RothIRABalance() {
        if(getStartingBalance() > '0' && getStartingBalance() != null){
            startPayment = ((getStartingBalance() + getPayment()) * (((Math.pow(1 + getInterestRate(),1)) - 1) / getInterestRate()) * (1 + getInterestRate()));
            /*for(int i =0; i<terms -1;i++){
                totalReturns = ((startPayment + getPayment()) * (((Math.pow((1 + getInterestRate()), getTerms()-1)) - 1) / getInterestRate()) * (1 + getInterestRate()));
            }*/
            double tempReturns=startPayment;
            for(int i =0; i < terms-1;i++) {
                totalReturns = ((tempReturns + getPayment()) * (((Math.pow((1 + getInterestRate()), 1)) - 1) / getInterestRate()) * (1 + getInterestRate()));
                tempReturns = totalReturns;
            }
        } else {
            totalReturns = (getPayment() * (((Math.pow((1 + getInterestRate()), getTerms())) - 1) / getInterestRate()) * (1 + getInterestRate()));
        }
        return totalReturns;
    }

    private Double TaxableSavingsAccount(){
        taxableInterest = getInterestRate() - (getInterestRate()*getMarginalInterestRate());

        if(getStartingBalance() > '0' && getStartingBalance() != null){
            taxableAmount = ((getStartingBalance()+getPayment())*(((Math.pow((1+taxableInterest),1))-1)/taxableInterest)*(1+taxableInterest));

            double tempTaxAmount = taxableAmount;
            for(int i =0; i < terms-1;i++) {
                taxableAmount = ((tempTaxAmount + getPayment()) * (((Math.pow((1 + taxableInterest), 1)) - 1) / taxableInterest) * (1 + taxableInterest));
                tempTaxAmount=taxableAmount;
            }
        } else {
            taxableAmount = (getPayment() * (((Math.pow((1 + taxableInterest), getTerms())) - 1) / taxableInterest) * (1 + taxableInterest));
        }
        return taxableAmount;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.roth_ira_investment);

        final RothIRAInvestment<Number> rothIRAInvestment = new RothIRAInvestment<>();

        final EditText annualContribution = findViewById(R.id.payment);
        final EditText startingBalance = findViewById(R.id.balance);
        final EditText terms = findViewById(R.id.term);
        final EditText interestRate = findViewById(R.id.interestRate);
        final EditText marginalTaxRate = findViewById(R.id.marginal_tax_rate);
        final TextView rothIRA = findViewById(R.id.rothIRA);
        final TextView taxableSavingsAccount = findViewById(R.id.taxableSavings);

        Button calculateBtn = findViewById(R.id.calculate);
        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /**
                 * If you enter in a number with a comma
                 */
                if(annualContribution.getText().toString().contains(",")){
                    try {
                        rothIRAInvestment.setPayment(Integer.parseInt(annualContribution.getText().toString().replace(",","")));
                    } catch(NumberFormatException e){
                        Log.d("Error!","Wrong number format!");
                    }
                } else {
                    rothIRAInvestment.setPayment(Integer.parseInt(annualContribution.getText().toString()));
                }

                try{
                    rothIRAInvestment.setStartingBalance(Integer.parseInt(startingBalance.getText().toString()));
                    rothIRAInvestment.setInterestRate(Double.parseDouble(interestRate.getText().toString()));
                    rothIRAInvestment.setTerms(Integer.parseInt(terms.getText().toString()));
                    rothIRAInvestment.setMarginalInterestRate(Double.parseDouble(marginalTaxRate.getText().toString()));

                    NumberFormat numFormat = NumberFormat.getCurrencyInstance();

                    rothIRA.setText(numFormat.format(rothIRAInvestment.RothIRABalance()));
                    taxableSavingsAccount.setText(numFormat.format(rothIRAInvestment.TaxableSavingsAccount()));

                } catch (NumberFormatException e){
                    Toast toast = Toast.makeText(getApplicationContext(),"Please enter in numbers!", Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

        Button backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(RothIRAInvestment.this, MainActivity.class);
                startActivity(mainActivity);
                finish();
            }
        });
    }
}
