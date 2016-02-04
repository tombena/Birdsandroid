package fr.sio.ecp.federatedbirds.app;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;

import java.util.List;

import fr.sio.ecp.federatedbirds.model.User;

/**
 * Created by tomb on 23/01/16.
 */
public class UserFollowedFragment extends UserFolFragment {
    @Override
    public Loader<List<User>> onCreateLoader(int id, Bundle args) {
        return new FollowedLoader(getContext(), null);

    }


}
