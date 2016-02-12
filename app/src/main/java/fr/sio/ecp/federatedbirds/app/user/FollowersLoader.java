package fr.sio.ecp.federatedbirds.app.user;

import android.content.Context;

import java.io.IOException;
import java.util.List;

import fr.sio.ecp.federatedbirds.ApiClient;
import fr.sio.ecp.federatedbirds.model.User;


public class FollowersLoader extends UsersLoader {

    public FollowersLoader(Context context, Long userId) {
        super(context, userId);
    }

    @Override
    protected List<User> getUsers(Long userId) throws IOException {
        return ApiClient.getInstance(getContext()).getUserFollowers(userId);
    }
}
