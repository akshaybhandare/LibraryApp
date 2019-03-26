package com.example.libraryapp;

import android.hardware.fingerprint.FingerprintManager;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;


@RequiresApi(api = Build.VERSION_CODES.M)
public class AuthenticationHandler extends FingerprintManager.AuthenticationCallback {
    private Fingureprint fingureprint;
    public AuthenticationHandler(Fingureprint fingureprint){
        this.fingureprint = fingureprint;
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        Toast.makeText(fingureprint,"Auth Error: "+ errString,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);
        Toast.makeText(fingureprint,"Auth Help:"+ helpString,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        Toast.makeText(fingureprint,"Auth Successful",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        Toast.makeText(fingureprint,"Auth Failed",Toast.LENGTH_LONG).show();

    }
}
