package example.com.zhouyi_20.object;

public class User {
    private static String name = "";
    private static String account = "";
    private static String id = "";
    private static String password = "";
    private static String true_name="";
    private static String birthday="";
    private static String telephone="";
    private static String token = "";

    private static String head = "";

    private static boolean state = false;

    public static void setName(final String name) {
        User.name = name;
    }
    public static String getName() {
        return User.name;
    }

    public static void setTrue_name(final String true_name){
        User.true_name=true_name;
    }
    public static String getTrue_name(){
        return User.true_name;
    }


    public static void setAccount(final String account) {
        User.account = account;
    }
    public static String getAccount() {
        return User.account;
    }

    public static void setPassword(final String password) {
        User.password = password;
    }
    public static String getPassword() {
        return User.password;
    }

    public static void setBirthday(final String birthday) {
        User.birthday = birthday;
    }
    public static String getBirthday() {
        return User.birthday;
    }


    public static void setState(final boolean state) {
        User.state = state;
    }
    public static boolean getState() {
        return User.state;
    }

    public static String getToken() {
        return token;
    }
    public static void setToken(String token) {
        User.token = token;
    }

    public static String getId() {
        return id;
    }
    public static void setId(String id) {
        User.id = id;
    }

    public static void changeState() {
        User.state = !User.state;
    }

    public static String getHead(){
        return head;
    }
    public static void setHead(String head){
        User.head = head;
    }

}
