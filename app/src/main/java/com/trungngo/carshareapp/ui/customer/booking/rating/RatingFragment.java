package com.trungngo.carshareapp.ui.customer.booking.rating;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.trungngo.carshareapp.R;

public class RatingFragment extends Fragment {

    private RatingViewModel mViewModel;
    private RatingBar mRatingBar;
    private TextView mRatingScale;
    private Button mSendFeedback;
    private EditText mFeedback;

    public static RatingFragment newInstance() {
        return new RatingFragment();
    }

    private void linkViewElements(View view) {
        mRatingBar = view.findViewById(R.id.rating_bar);
        mRatingScale = view.findViewById(R.id.rating_scale_text_view);
        mFeedback = view.findViewById(R.id.feed_back_edit_text);
        mSendFeedback = view.findViewById(R.id.send_feedback_btn);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_rating, container, false);
        linkViewElements(view);

        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mRatingScale.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        mRatingScale.setText("Very bad");
                        break;
                    case 2:
                        mRatingScale.setText("Need some improvement");
                        break;
                    case 3:
                        mRatingScale.setText("Good");
                        break;
                    case 4:
                        mRatingScale.setText("Great");
                        break;
                    case 5:
                        mRatingScale.setText("Awesome");
                        break;
                    default:
                        mRatingScale.setText("");
                }
            }
        });

        mSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFeedback.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please fill in feedback text box", Toast.LENGTH_LONG).show();
                }
                else {
                    mFeedback.setText("");
                    mRatingBar.setRating(0);
                    Toast.makeText(getActivity(), "Thank you for sharing your feedback", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RatingViewModel.class);
        // TODO: Use the ViewModel
    }

}
