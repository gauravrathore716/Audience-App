package com.b2c.audience;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import java.lang.reflect.Field;


import com.b2c.audience.databinding.ActivityPaymentMeanBinding;
import com.b2c.audience.hidekeywboard.HideKeyboard;

import java.util.Calendar;

import io.card.payment.CardIOActivity;
import io.card.payment.CreditCard;

public class MeansPaymentActivity extends AppCompatActivity implements View.OnClickListener {

    ActivityPaymentMeanBinding binding;
    static final int DATE_DIALOG_ID = 1;
    private int mYear;
    private int mMonth;
    private int mDay;
    private static final int REQUEST_SCAN =101;
    private static final int REQUEST_AUTOTEST =200;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment_mean);
        binding.setActivity(this);

        HideKeyboard.setupUI(binding.layoutMain, this);
        binding.ivCardScan.setOnClickListener(this);

        setSupportActionBar(binding.toolbar);
        setfindviewid();

        binding.etCardNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                final char space = ' ';

                if (s.length() > 0 && (s.length() % 5) == 0) {
                    final char c = s.charAt(s.length() - 1);
                    if (space == c) {
                        s.delete(s.length() - 1, s.length());
                    }
                }
                if (s.length() > 0 && (s.length() % 5) == 0) {
                    char c = s.charAt(s.length() - 1);
                    // Only if its a digit where there should be a space we insert a space
                    if (Character.isDigit(c) && TextUtils.split(s.toString(), String.valueOf(space)).length <= 3) {
                        s.insert(s.length() - 1, String.valueOf(space));
                    }
                }
            }
        });
        binding.etExpDat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);


    }
    DatePickerDialog.OnDateSetListener mDateSetListner = new DatePickerDialog.OnDateSetListener(){

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDate();



        }
    };

    private void updateDate() {

        int localMonth = (mMonth + 1);
        String monthString = localMonth < 10 ? "0" + localMonth : Integer
                .toString(localMonth);
        String localYear = Integer.toString(mYear).substring(2);
        binding.etExpDat.setText(new StringBuilder()
                // Month is 0 based so add 1
                .append(monthString).append("/").append(localYear).append(" "));
        showDialog(DATE_DIALOG_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                /*
                 * return new DatePickerDialog(this, mDateSetListner, mYear, mMonth,
                 * mDay);
                 */
                DatePickerDialog datePickerDialog = this.customDatePicker();
                return datePickerDialog;
        }
        return null;
    }

    private DatePickerDialog customDatePicker() {
        DatePickerDialog dpd = new DatePickerDialog(this, mDateSetListner,
                mYear, mMonth, mDay);
        try {

            Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField
                            .get(dpd);
                    Field datePickerFields[] = datePickerDialogField.getType()
                            .getDeclaredFields();
                    for (Field datePickerField : datePickerFields) {
                        if ("mDayPicker".equals(datePickerField.getName())
                                || "mDaySpinner".equals(datePickerField
                                .getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = new Object();
                            dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }

            }
        } catch (Exception ex) {
        }
        return dpd;
    }



    private void setfindviewid() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            final Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_white_24dp);

            actionBar.setHomeAsUpIndicator(upArrow);
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.ivCardScan:
                Intent intent = new Intent(MeansPaymentActivity.this, CardIOActivity.class)
                        .putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true)
                        .putExtra(CardIOActivity.EXTRA_USE_CARDIO_LOGO,true)
                        .putExtra(CardIOActivity.EXTRA_USE_PAYPAL_ACTIONBAR_ICON,false)
                        .putExtra(CardIOActivity.EXTRA_SCAN_INSTRUCTIONS,true)
                        .putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY,true)
                        .putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE,false)
                        .putExtra(CardIOActivity.EXTRA_RETURN_CARD_IMAGE,false)
                        .putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true);
                startActivityForResult(intent, REQUEST_SCAN);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_SCAN || requestCode == REQUEST_AUTOTEST) && data != null )
        {
            String resultDisplayStr;
            String exprie ="";

            if (data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT))
            {
                Log.e("cardDetails",":: "+data);

                CreditCard scanResult = data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT);


// Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultDisplayStr = "" + scanResult.cardNumber+"";
                Log.e("cardNumber","cardNumber"+scanResult.cardNumber);
// Do something with the raw number, e.g.:
// myService.setCardNumber( scanResult.cardNumber );
                if (scanResult.isExpiryValid())
                {
                    //resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n";
                    exprie = ""+scanResult.expiryYear+"/"+scanResult.expiryMonth;
                    Log.e("Expiration","Expiration"+scanResult.expiryMonth+""+scanResult.expiryYear);

                }
                if (scanResult.cvv != null)
                {
// Never log or display a CVV
                    resultDisplayStr += "CVV has " + scanResult.cvv.length() + " digits.\n";
                    Log.e("CVV","CVV"+scanResult.cvv);

                }
                if (scanResult.postalCode != null)
                {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n";
                }
            }
            else
            {
                resultDisplayStr = "Scan was canceled.";
            }
            
            binding.etCardNo.setText(resultDisplayStr);
            binding.etExpDat.setText(exprie);
            //((TextView) findViewById(R.id.tvCardDetail)).setText(resultDisplayStr);
            Toast.makeText(getApplicationContext(),""+resultDisplayStr,Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case android.R.id.home:
                super.onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
