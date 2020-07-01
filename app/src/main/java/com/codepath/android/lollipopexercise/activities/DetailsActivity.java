package com.codepath.android.lollipopexercise.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.codepath.android.lollipopexercise.R;
import com.codepath.android.lollipopexercise.models.Contact;

public class DetailsActivity extends AppCompatActivity {
    public static final String EXTRA_CONTACT = "EXTRA_CONTACT";
    private Contact mContact;
    private ImageView ivProfile;
    private TextView tvName;
    private TextView tvPhone;
    private View vPalette;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        tvName = (TextView) findViewById(R.id.tvName);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        vPalette = findViewById(R.id.vPalette);

        // Extract contact from bundle
        mContact = (Contact)getIntent().getExtras().getSerializable(EXTRA_CONTACT);

        // Fill views with data
        tvName.setText(mContact.getName());
        tvPhone.setText(mContact.getNumber());

        Glide.with(DetailsActivity.this).load(mContact.getThumbnailDrawable()).centerCrop().into(ivProfile);
        // Use Glide to get a callback with a Bitmap which can then
        // be used to extract a vibrant color from the Palette.

        // Define an asynchronous listener for image loading
        CustomTarget<Bitmap> target = new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                // Instruct Glide to load the bitmap into the `holder.ivProfile` profile image view
                Glide.with(DetailsActivity.this).load(resource).into(ivProfile);

                // Use generate() method from the Palette API to get the vibrant color from the bitmap
                Palette palette = Palette.from(resource).generate();
                // Set the result as the background color for `holder.vPalette` view containing the contact's name.
                vPalette.setBackgroundColor(palette.getLightVibrantSwatch().getRgb());
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {
                // can leave empty
            }
        };

        // Clear the bitmap and the background color in adapter
        ivProfile.setImageBitmap(null);
        ivProfile.setBackground(null);

        // Instruct Glide to load the bitmap into the asynchronous target defined above
        Glide.with(DetailsActivity.this).asBitmap().load(mContact.getThumbnailDrawable()).centerCrop().into(target);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
