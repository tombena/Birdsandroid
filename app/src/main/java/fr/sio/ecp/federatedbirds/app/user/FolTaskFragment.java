package fr.sio.ecp.federatedbirds.app.user;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.os.AsyncTaskCompat;
import android.util.Log;
import android.widget.Toast;

import fr.sio.ecp.federatedbirds.ApiClient;
import fr.sio.ecp.federatedbirds.R;
import fr.sio.ecp.federatedbirds.app.MainActivity;
import fr.sio.ecp.federatedbirds.auth.TokenManager;


public class FolTaskFragment extends DialogFragment {

    private static final String ARG_USERID = "userID";
    private static final String ARG_UN = "un";
    public void setArguments(Long userId, String un) {
        Bundle args = new Bundle();
        args.putLong(FolTaskFragment.ARG_USERID, userId);
        args.putString(FolTaskFragment.ARG_UN, un);
        Log.d("args", "set");
        setArguments(args);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        AsyncTaskCompat.executeParallel(
                new FolTaskFragment.FolTask()
        );
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setIndeterminate(true);
        dialog.setMessage(getString(R.string.login_progress));
        return dialog;
    }

    private class FolTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            Long userId = getArguments().getLong("userId");
            String un = getArguments().getString("un");
            Log.d("doInBackground", "running");
            return ApiClient.getInstance(getContext()).fol(userId, un);
        }

        @Override
        protected void onPostExecute(String token) {
            if (token != null) {
                TokenManager.setUserToken(getContext(), token);
                getActivity().finish();
                startActivity(MainActivity.newIntent(getContext()));
            } else {
                Toast.makeText(getContext(), R.string.login_failed, Toast.LENGTH_SHORT).show();
            }
            dismiss();
        }
    }
}
