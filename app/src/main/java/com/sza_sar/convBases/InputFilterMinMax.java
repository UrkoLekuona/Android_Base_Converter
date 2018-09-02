package com.sza_sar.convBases;

import android.text.InputFilter;
import android.text.Spanned;

import java.math.BigInteger;

public class InputFilterMinMax implements InputFilter {
    private BigInteger min, max;

    public InputFilterMinMax(int min, int max) {
        this.min = new BigInteger(String.valueOf(min));
        this.max = new BigInteger(String.valueOf(max));
    }

    public InputFilterMinMax(String min, String max) {
        this.min = new BigInteger(min);
        this.max = new BigInteger(max);
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            BigInteger input = new BigInteger(dest.toString() + source.toString());
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) { }
        return "";
    }

    private boolean isInRange(BigInteger a, BigInteger b, BigInteger c) {
        return b.compareTo(a) > 0 ? c.compareTo(a) >= 0 && c.compareTo(b) <= 0 : c.compareTo(b) >= 0 && c.compareTo(a) <= 0;
    }
}
