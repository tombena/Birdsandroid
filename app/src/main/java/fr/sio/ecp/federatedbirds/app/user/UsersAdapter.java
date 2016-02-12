package fr.sio.ecp.federatedbirds.app.user;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.sio.ecp.federatedbirds.ApiClient;
import fr.sio.ecp.federatedbirds.R;
import fr.sio.ecp.federatedbirds.model.User;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MessageViewHolder> {

    private List<User> mUsers;

    public void setUsers(List<User> users) {
        mUsers = users;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mUsers != null ? mUsers.size() : 0;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new MessageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        final User user = mUsers.get(position);

        Picasso.with(holder.mAvatarView.getContext())
                .load(user.avatar)
                .into(holder.mAvatarView);

        holder.mUsernameView.setText(user.login);


        //click on a user avatar redirects to his profile
        holder.mAvatarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Redirect to ProfileActivity
                Intent intent = new Intent(v.getContext(),ProfileActivity.class);
                intent.putExtra("user", user);
                v.getContext().startActivity(intent);
            }
        });

        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Click", "follow : " + user.login);
                //doesn't work properly
                FolTaskFragment folTaskFragment = new FolTaskFragment();
                folTaskFragment.setArguments(user.id, "");
                folTaskFragment.show(folTaskFragment.getFragmentManager(), "fol_task");
            }
        });

        holder.unfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Click", "unfollow : " + user.login);
                //doesn't work properly
                FolTaskFragment folTaskFragment = new FolTaskFragment();
                folTaskFragment.setArguments(user.id, "un");
                //folTaskFragment.show(folTaskFragment.getFragmentManager(), "fol_task");
            }
        });






    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {

        private ImageView mAvatarView;
        private TextView mUsernameView;
        private Button follow;
        private Button unfollow;

        public MessageViewHolder(View itemView) {
            super(itemView);
            mAvatarView = (ImageView) itemView.findViewById(R.id.avatar);
            mUsernameView = (TextView) itemView.findViewById(R.id.username);
            follow = (Button) itemView.findViewById(R.id.follow);
            unfollow = (Button) itemView.findViewById(R.id.unfollow);
        }

    }

}