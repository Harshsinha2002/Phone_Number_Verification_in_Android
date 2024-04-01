package com.example.phone_number_verification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Enter_Mobile_Number extends AppCompatActivity {

    EditText input_mobile_number;
    Button get_otp_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mobile_number);

        getWindow().setStatusBarColor(ContextCompat.getColor(Enter_Mobile_Number.this, R.color.colourPrimary));

        input_mobile_number = findViewById(R.id.input_mobile_number);
        get_otp_btn = findViewById(R.id.get_otp_btn);

        get_otp_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String number = input_mobile_number.getText().toString().trim();

                if(number.trim().isEmpty())
                {
                    Toast.makeText(Enter_Mobile_Number.this, "Please enter your mobile number!!", Toast.LENGTH_SHORT).show();
                }
                else if (number.length() != 10)
                {
                    Toast.makeText(Enter_Mobile_Number.this, "Please enter correct mobile number!!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                   PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + input_mobile_number.getText().toString(),
                           60, TimeUnit.SECONDS,
                           Enter_Mobile_Number.this,
                           new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                           {
                               @Override
                               public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
                               {

                               }

                               @Override
                               public void onVerificationFailed(@NonNull FirebaseException e)
                               {
                                   Toast.makeText(Enter_Mobile_Number.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                               }

                               @Override
                               public void onCodeSent(@NonNull String backendOTP, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken)
                               {
                                   Intent intent = new Intent(Enter_Mobile_Number.this, Verify_OTP_2.class);
                                   intent.putExtra("mobile_number", input_mobile_number.getText().toString().trim());
                                   intent.putExtra("backendOTP", backendOTP);
                                   startActivity(intent);
                               }
                           }
                   );
                }
            }
        });

    }
}