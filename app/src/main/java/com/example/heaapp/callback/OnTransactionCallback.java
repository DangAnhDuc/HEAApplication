package com.example.heaapp.callback;

public interface OnTransactionCallback {
    void onTransactionSuccess();

    void onTransactionError(Exception e);
}
