package example.com.zhouyi_20.object;

public interface HttpsListener {
    void success(final String response);
    void failed(Exception exception);
}
