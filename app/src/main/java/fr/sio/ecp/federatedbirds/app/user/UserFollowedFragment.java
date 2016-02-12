package fr.sio.ecp.federatedbirds.app.user;

import android.os.Bundle;
import android.support.v4.content.Loader;

import java.util.List;

import fr.sio.ecp.federatedbirds.app.user.FollowedLoader;
import fr.sio.ecp.federatedbirds.app.user.UserFolFragment;
import fr.sio.ecp.federatedbirds.model.User;

public class UserFollowedFragment extends UserFolFragment {
    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        return new FollowedLoader(getContext(), null);

    }


}
