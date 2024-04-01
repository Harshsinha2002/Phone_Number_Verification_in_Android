package com.example.phone_number_verification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Verify_OTP_2 extends AppCompatActivity {

    TextView show_mobile_number, resend_OTP;
    EditText input_otp1, input_otp2, input_otp3, input_otp4, input_otp5, input_otp6;
    Button submit_otp_btn;
    String getOtpBackend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp2);

        getWindow().setStatusBarColor(ContextCompat.getColor(Verify_OTP_2.this, R.color.colourPrimary));

        show_mobile_number = findViewById(R.id.show_mobile_number);
        resend_OTP = findViewById(R.id.resend_OTP);
        input_otp1 = findViewById(R.id.input_otp1);
        input_otp2 = findViewById(R.id.input_otp2);
        input_otp3 = findViewById(R.id.input_otp3);
        input_otp4 = findViewById(R.id.input_otp4);
        input_otp5 = findViewById(R.id.input_otp5);
        input_otp6 = findViewById(R.id.input_otp6);
        submit_otp_btn = findViewById(R.id.submit_otp_btn);


        String mobile_number = String.valueOf(getIntent().getStringExtra("mobile_number"));
        show_mobile_number.setText("+91" + mobile_number);

        getOtpBackend = String.valueOf(getIntent().getStringExtra("backendOTP"));

        numberOtpMove();

        submit_otp_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(input_otp1.getText().toString().trim().isEmpty() || input_otp2.getText().toString().trim().isEmpty() ||
                        input_otp3.getText().toString().trim().isEmpty() || input_otp4.getText().toString().trim().isEmpty() ||
                        input_otp5.getText().toString().trim().isEmpty() || input_otp6.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(Verify_OTP_2.this, "Please enter the OTP", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    String enteredOTP = input_otp1.getText().toString() + input_otp2.getText().toString() + input_otp3.getText().toString() +
                            input_otp4.getText().toString() + input_otp5.getText().toString() + input_otp6.getText().toString();

                    if(getOtpBackend != null)
                    {
                        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getOtpBackend, enteredOTP);

                        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>()
                        {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task)
                            {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(Verify_OTP_2.this, "OTP verified!!", Toast.LENGTH_SHORT).show();
                                }

                                else
                                {
                                    Toast.makeText(Verify_OTP_2.this, "Enter the correct OTP!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    else
                    {
                        Toast.makeText(Verify_OTP_2.this, "Please check your internet connection!!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        resend_OTP.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(show_mobile_number.getText().toString(),
                        30, TimeUnit.SECONDS,
                        Verify_OTP_2.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                        {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
                            {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e)
                            {
                                Toast.makeText(Verify_OTP_2.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                if (e instanceof FirebaseAuthInvalidCredentialsException)
                                {
                                    // Invalid request
                                }
                                else if (e instanceof FirebaseTooManyRequestsException)
                                {
                                    // The SMS quota for the project has been exceeded
                                }
                                else if (e instanceof FirebaseAuthMissingActivityForRecaptchaException)
                                {
                                    // reCAPTCHA verification attempted with null Activity
                                }
                            }

                            @Override
                            public void onCodeSent(@NonNull String new_backendOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
                            {
                                getOtpBackend = new_backendOTP;
                                Toast.makeText(Verify_OTP_2.this, "OTP Sent!!", Toast.LENGTH_SHORT).show();
                                input_otp1.setText("");
                                input_otp2.setText("");
                                input_otp3.setText("");
                                input_otp4.setText("");
                                input_otp5.setText("");
                                input_otp6.setText("");
                                input_otp1.requestFocus();
                            }
                        }
                );
            }
        });

    }

    private void numberOtpMove()
    {
        input_otp1.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(!s.toString().trim().isEmpty())
                {
                    input_otp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        input_otp2.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(!s.toString().trim().isEmpty())
                {
                    input_otp3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        input_otp3.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(!s.toString().trim().isEmpty())
                {
                    input_otp4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        input_otp4.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(!s.toString().trim().isEmpty())
                {
                    input_otp5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        input_otp5.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(!s.toString().trim().isEmpty())
                {
                    input_otp6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });

        input_otp6.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(!s.toString().trim().isEmpty())
                {
                    submit_otp_btn.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }
}