package fr.sio.ecp.federatedbirds.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import fr.sio.ecp.federatedbirds.R;
import fr.sio.ecp.federatedbirds.model.Message;
import fr.sio.ecp.federatedbirds.model.User;

/**
 * Created by tomb on 26/01/16.
 */
public class ProfileActivity extends AppCompatActivity {

    //TODO erase line
    private MessagesAdapter mMessagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("user");

        TextView textView= (TextView) findViewById(R.id.username);
        textView.setText(user.login);

        ImageView imageView=(ImageView) findViewById(R.id.avatar);
        Picasso
                .with(this)
                .load(user.avatar)
                .into(imageView);
        findViewById(R.id.avatar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picChange();
            }
        });


        //TODO display user's messages
        //it's a recycler view
//        RecyclerView listView = (RecyclerView) findViewById(R.id.list);
//        listView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        mMessagesAdapter = new MessagesAdapter();
//
//        Message message = new Message();





    }

    private void picChange(){
        Log.d("Profile image", "change");
        //TODO authenticate user -> check for match with current page

        //TODO upload picture from gallery

    }



}
