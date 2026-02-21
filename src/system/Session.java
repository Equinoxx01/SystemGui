package system;


public class Session {

    private static int userId;
    private static String userType;
    private static boolean active;

    public static void setSession(int id, String type) {
        userId = id;
        userType = type != null ? type : "";
        active = true;
    }

    public static void clearSession() {
        active = false;
        userId = 0;
        userType = null;
    }

    public static boolean isLoggedIn() {
        return active;
    }

    public static int getUserId() {
        return userId;
    }

    public static String getUserType() {
        return userType;
    }

    public static boolean isAdmin() {
        return "admin".equalsIgnoreCase(userType);
    }
}
