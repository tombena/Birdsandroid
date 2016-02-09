package fr.sio.ecp.federatedbirds.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.sio.ecp.federatedbirds.ApiClient;
import fr.sio.ecp.federatedbirds.R;
import fr.sio.ecp.federatedbirds.model.Message;
import fr.sio.ecp.federatedbirds.model.User;

/**
 * Created by tomb on 26/01/16.
 */
public class ProfileActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Message>> {

    private MessagesAdapter mMessagesAdapter;
    private static final int messagesLoader = 0;
    private static final int SELECT_PHOTO = 100;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 0;

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

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMessagesAdapter = new MessagesAdapter();
        mRecyclerView.setAdapter(mMessagesAdapter);
        registerForContextMenu(imageView);
    }

    //keep what's under???

    @Override
    protected void onStart() {
        super.onStart();
        getSupportLoaderManager().initLoader(
                messagesLoader,
                null,
                this
        );
    }

    @Override
    public Loader<List<Message>> onCreateLoader(int id, Bundle args) {
        return new MessagesLoader(this, null);
    }

    @Override
    public void onLoadFinished(Loader<List<Message>> loader, List<Message> messages) {
        mMessagesAdapter.setMessages(messages);
    }

    @Override
    public void onLoaderReset(Loader<List<Message>> loader) {

    }

    private void picChange(){
        Log.d("Profile image", "change");
        //TODO authenticate user -> check for match with current page




        //TODO upload picture from gallery
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);

        //permission for accessing gallery
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    //permission problem
                    Cursor cursor = getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();


                    Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                    //TODO handle the image input
                    Log.d("IMAGE", "got");

                    //traitement
                    //ApiClient.getInstance(getApplicationContext()).uploadPic(yourSelectedImage);
                }
        }
    }



}
