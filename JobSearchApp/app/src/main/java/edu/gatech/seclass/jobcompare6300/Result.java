package edu.gatech.seclass.jobcompare6300;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

// Some or error return
public class Result<T> {
    private T result;
    private boolean isOk;
    private String errorMessage;
    private int errorCode;

    public static int ERR_NEG = 0x1000; // Negative number not allowed
    public static int ERR_EXCEED_MAX = 0x1001; // Exceeded some maximum
    public static int ERR_ZERO_LEN; // length of zero
    public static int ERR_ZERO; // zero value
    public static int ERR_NOT_A_INT; // not an integer
    public static int ERR_JSON; // error handling JSON
    public static int ERR_FILE_RW; // error with reading and writing files

    // Success
    public Result(T data) {
        this.result = data;
        this.isOk = true;
        this.errorMessage = "";
        this.errorCode = -1;
    }

    // Failure
    public Result(int errorCode, String errorMessage) {
        this.result = null;
        this.isOk = false;
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public boolean isOk() {
        return this.isOk;
    }

    public boolean isErr() {
        return !this.isOk();
    }

    public T get() {
        if (!this.isOk) {
            throw new RuntimeException("Result is not good. Need to handle this case in caller.");
        }
        return this.result;
    }

    public T val() {
        return this.get();
    }

    public T unwrap() {
        return this.get();
    }

    public String getError() {
        if (this.isOk) {
            throw new RuntimeException("Result is good. Need to handle this case in caller.");
        }
        return this.errorMessage;
    }

    public int getErrCode() {
        if (this.isOk) {
            throw new RuntimeException("Result is good. Need to handle this case in caller.");
        }
        return this.errorCode;
    }
}
