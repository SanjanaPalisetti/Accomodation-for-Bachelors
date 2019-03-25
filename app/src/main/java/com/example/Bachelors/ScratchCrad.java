package com.example.Bachelors;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import in.myinnos.androidscratchcard.ScratchCard;

public class ScratchCrad extends AppCompatActivity {

    private ScratchCard mScratchCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scratch_crad);

        mScratchCard = (ScratchCard) findViewById(R.id.scratchCard);
        mScratchCard.setOnScratchListener(new ScratchCard.OnScratchListener() {
            @Override
            public void onScratch(ScratchCard scratchCard, float visiblePercent) {
                if (visiblePercent > 0.3) {
                    mScratchCard.setVisibility(View.GONE);
                    Toast.makeText(ScratchCrad.this, "Offer Applied!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
