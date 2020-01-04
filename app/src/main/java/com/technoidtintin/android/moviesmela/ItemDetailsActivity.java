package com.technoidtintin.android.moviesmela;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.technoidtintin.android.moviesmela.Model.ListItem;

public class ItemDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        ImageView imageView = findViewById(R.id.item_imageView);
        //Getting Intent
        Intent intent = getIntent();
        if (intent.hasExtra(Constant.LIST_ITEM));
        ListItem listItem = intent.getParcelableExtra(Constant.LIST_ITEM);
        Picasso.get().load(listItem.getMoviePosterPath()).into(imageView);

    }
}
