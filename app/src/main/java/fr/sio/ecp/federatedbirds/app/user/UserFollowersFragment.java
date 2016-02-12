package fr.sio.ecp.federatedbirds.app.user;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;

import java.util.List;

import fr.sio.ecp.federatedbirds.app.user.FollowersLoader;
import fr.sio.ecp.federatedbirds.app.user.UserFolFragment;
import fr.sio.ecp.federatedbirds.model.User;

public class UserFollowersFragment extends UserFolFragment {

    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        Log.d("UserFollowersFragment", "ok");
        return new FollowersLoader(getContext(), null);
    }

}
