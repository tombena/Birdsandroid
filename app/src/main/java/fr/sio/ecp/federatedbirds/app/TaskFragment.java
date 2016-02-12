package fr.sio.ecp.federatedbirds.app;

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

import java.io.IOException;

import fr.sio.ecp.federatedbirds.ApiClient;
import fr.sio.ecp.federatedbirds.R;
import fr.sio.ecp.federatedbirds.auth.TokenManager;

public class TaskFragment extends DialogFragment {
    //class is named TaskFragment for convenience
    // could have been SignUpTaskFragment
    // or UploadTaskFragment
    // or Login TaskFragment
    private static final String ARG_LOGIN = "login";
    private static final String ARG_PASSWORD = "password";
    private static final String ARG_EMAIL = "email";
    private static final String ARG_FILEPATH= "filePath";

    //1 arg for Upload
    public void setArguments(String filePath) {
        Bundle args = new Bundle();
        args.putString(TaskFragment.ARG_FILEPATH, filePath);
        setArguments(args);
    }


    //2 args for Login
    public void setArguments(String login, String password) {
        Bundle args = new Bundle();
        args.putString(TaskFragment.ARG_LOGIN, login);
        args.putString(TaskFragment.ARG_PASSWORD, password);
        setArguments(args);
    }

    //3 args for Signup
    public void setArguments(String login, String password, String email) {
        Bundle args = new Bundle();
        args.putString(TaskFragment.ARG_LOGIN, login);
        args.putString(TaskFragment.ARG_PASSWORD, password);
        args.putString(TaskFragment.ARG_EMAIL, email);
        setArguments(args);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        AsyncTaskCompat.executeParallel(
                new TaskFragment.LoginTask()
        );
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressDialog dialog = new ProgressDialog(getContext());
        dialog.setIndeterminate(true);
        String email = getArguments().getString("email");
        String login = getArguments().getString("login");
        if(email!=null){
            dialog.setMessage(getString(R.string.signin_progress));
        } else if (login!=null) {
            dialog.setMessage(getString(R.string.login_progress));
        } else {
            dialog.setMessage(getString(R.string.upload_progress));
        }
        return dialog;
    }

    private class LoginTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            String login = getArguments().getString("login");
            String password = getArguments().getString("password");
            String email = getArguments().getString("email");
            String filePath = getArguments().getString("filePath");
            if (email== null && filePath ==null) { //login
                try {
                    return ApiClient.getInstance(getContext()).login(login, password);
                } catch (IOException e) {
                    Log.e(LoginActivity.class.getSimpleName(), "Login failed", e);
                    return null;
                }
            } else if(email!=null) { //signup
                try {
                    return ApiClient.getInstance(getContext()).signup(login, password, email);
                } catch (IOException e) {
                    Log.e(LoginActivity.class.getSimpleName(), "Signup failed", e);
                    return null;
                }

            } else { //upload
                return ApiClient.getInstance(getContext()).uploadPic(filePath);
            }
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
