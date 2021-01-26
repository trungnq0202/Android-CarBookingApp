package com.trungngo.carshareapp.ui.driver.checkout;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.trungngo.carshareapp.R;

public class CheckoutFragment extends DialogFragment {

    public static CheckoutFragment newInstance() {
        return new CheckoutFragment();
    }

    private TextView moneyText;
    private TextView moneyExtraText;
    private Button exitBtn;
    private Button processBtn;

    private void linkViewElements(View rootView) {
        moneyText = rootView.findViewById(R.id.text_money);
        moneyExtraText = rootView.findViewById(R.id.text_moneyExtra);
        exitBtn = rootView.findViewById(R.id.btn_exit);
        processBtn = rootView.findViewById(R.id.btn_process);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL,
                android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_driver_checkout, container, false);
        linkViewElements(root);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}