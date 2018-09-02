package com.sza_sar.convBases;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigInteger;


public class MainActivity extends AppCompatActivity {

    private TextView tx_h, tx_d, tx_b;
    private EditText hex, bin, dec;
    private TextWatcher bin_W = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            removeTextListeners();

            if (after == 0 && s.length() == 1) {
                dec.setText("");
                hex.setText("");
                bin.setText("");
            }

            addTextListeners();
        }

        @Override
        public void afterTextChanged(Editable s) {
            BigInteger b = BigInteger.valueOf(-1);
            int length = bin.getText().length();

            removeTextListeners();

            if (length > 0) {
                b = new BigInteger(String.valueOf(bin.getText()));
            }
            if (b.compareTo(BigInteger.ZERO) < 0) {
                hex.setText("", TextView.BufferType.EDITABLE);
                dec.setText("", TextView.BufferType.EDITABLE);
            } else {
                BigInteger d = binaryToDecimal(b);
                String h = Long.toHexString(d.longValue());

                hex.setText(h.toUpperCase(), TextView.BufferType.EDITABLE);
                dec.setText(String.valueOf(d), TextView.BufferType.EDITABLE);
            }

            addTextListeners();
        }
    };
    private TextWatcher dec_W = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            removeTextListeners();

            if (after == 0 && s.length() == 1) {
                dec.setText("");
                hex.setText("");
                bin.setText("");
            }

            addTextListeners();
        }

        @Override
        public void afterTextChanged(Editable s) {
            BigInteger d = BigInteger.valueOf(-1);
            int length = dec.getText().length();

            removeTextListeners();

            if (length > 0) {
                d = new BigInteger(String.valueOf(dec.getText()));
            }
            if (d.compareTo(BigInteger.ZERO) < 0) {
                hex.setText("", TextView.BufferType.EDITABLE);
                bin.setText("", TextView.BufferType.EDITABLE);
            } else {

                BigInteger b = convert(d, BigInteger.valueOf(2));
                String h = Long.toHexString(d.longValue());

                hex.setText(h.toUpperCase(), TextView.BufferType.EDITABLE);
                bin.setText(String.valueOf(b), TextView.BufferType.EDITABLE);
            }

            addTextListeners();
        }
    };
    private TextWatcher hex_W = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) { }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            removeTextListeners();

            if (after == 0 && s.length() == 1) {
                dec.setText("");
                hex.setText("");
                bin.setText("");
            }

            addTextListeners();
        }

        @Override
        public void afterTextChanged(Editable s) {
            String h;
            int length = hex.getText().length();
            BigInteger d = BigInteger.ZERO;

            removeTextListeners();

            if (length > 0) {
                h = String.valueOf(hex.getText());
                d = new BigInteger(h, 16);
            }

            if (d.compareTo(BigInteger.ZERO) <= 0 ) {
                dec.setText("", TextView.BufferType.EDITABLE);
                bin.setText("", TextView.BufferType.EDITABLE);
            } else {
                BigInteger b = convert(d, BigInteger.valueOf(2));

                dec.setText(String.valueOf(d), TextView.BufferType.EDITABLE);
                bin.setText(String.valueOf(b), TextView.BufferType.EDITABLE);
            }

            addTextListeners();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Inicializar las views*/
        tx_h = (TextView) findViewById(R.id.tx_hex);
        tx_b = (TextView) findViewById(R.id.tx_bin);
        tx_d = (TextView) findViewById(R.id.tx_dec);
        hex = (EditText) findViewById(R.id.ed_hex);
        bin = (EditText) findViewById(R.id.ed_bin);
        dec = (EditText) findViewById(R.id.ed_dec);

        /*Añadir filtro de maximo y minimo numero al campo de entrada*/
        dec.setFilters(new InputFilter[]{new InputFilterMinMax("0", "1152921504606846975")});
        bin.setFilters(new InputFilter[]{new InputFilterMinMax("0", "111111111111111111111111111111111111111111111111111111111111")});

        /*Añadir los listeners a los campos*/
        addTextListeners();

    }

    public BigInteger convert(BigInteger decimal, BigInteger base) {
        BigInteger result = BigInteger.ZERO;
        BigInteger multiplier = BigInteger.ONE;

        while (decimal.compareTo(BigInteger.ZERO) > 0) {
            BigInteger residue = decimal.mod(base);
            decimal = decimal.divide(base);
            result = result.add(residue.multiply(multiplier));
            multiplier = multiplier.multiply(BigInteger.valueOf(10));
        }

        //System.out.println("DtB binary: " + result);

        return result;
    }

    public BigInteger binaryToDecimal(BigInteger binaryNumber) {

        BigInteger decimal = BigInteger.ZERO;
        BigInteger p = BigInteger.ZERO;

        while (true) {
            if (binaryNumber.compareTo(BigInteger.ZERO) == 0) {
                break;
            } else {
                BigInteger temp = binaryNumber.mod(BigInteger.TEN);
                long x = (long) Math.pow(2, p.doubleValue());
                decimal = decimal.add(temp.multiply(BigInteger.valueOf(x)));
                binaryNumber = binaryNumber.divide(BigInteger.TEN);
                p = p.add(BigInteger.ONE);
            }
        }
        //System.out.println("BtD decimal: " + decimal);

        return decimal;
    }

    public void removeTextListeners() {
        dec.removeTextChangedListener(dec_W);
        bin.removeTextChangedListener(bin_W);
        hex.removeTextChangedListener(hex_W);
    }

    public void addTextListeners() {
        hex.addTextChangedListener(hex_W);
        bin.addTextChangedListener(bin_W);
        dec.addTextChangedListener(dec_W);
    }
}
