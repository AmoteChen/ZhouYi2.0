package example.com.zhouyi_20.object;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpsConnect {
//
//    public static String token;
//
//    public static void setToken(String token) {
//        HttpsConnect.token = token;
//    }
//
//    public static String getToken() {
//        return HttpsConnect.token;
//    }

    public static void sendRequest(
            final String address,
            final String method,
            final JSONObject jsonData,
            final HttpsListener listener
            ) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod(method);
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-type", "application/json;charset=UTF-8");
                    connection.setRequestProperty("Accept", "application/json");
                    connection.setRequestProperty("token", User.getToken());

                    DataOutputStream ostream = new DataOutputStream(connection.getOutputStream());
                    ostream.write(jsonData.toString().getBytes());
                    InputStream istream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(istream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    if (listener != null) {
                        listener.success(response.toString());
                    }
                } catch (Exception exception) {
                    if (listener != null) {
                        listener.failed(exception);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}
