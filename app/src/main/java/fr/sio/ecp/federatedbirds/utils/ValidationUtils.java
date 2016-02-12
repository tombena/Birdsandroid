package fr.sio.ecp.federatedbirds.utils;


import fr.sio.ecp.federatedbirds.ApiClient;

public class ValidationUtils {

    private static final String LOGIN_PATTERN = "^[A-Za-z0-9_-]{4,12}$";
    private static final String PASSWORD_PATTERN = "^\\w{4,12}$";
    private static final String EMAIL_PATTERN= "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public static boolean validateLogin(String login) {
        return login != null && login.matches(LOGIN_PATTERN);
    }

    public static boolean validatePassword(String password) {
        return password != null && password.matches(PASSWORD_PATTERN);
    }

    public static boolean validateEmail(String email) {
        return email!=null && email.matches(EMAIL_PATTERN);
    }



}
