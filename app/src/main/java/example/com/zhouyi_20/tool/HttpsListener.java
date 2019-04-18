package example.com.zhouyi_20.tool;

public interface HttpsListener {
    void success(final String response);
    void failed(Exception exception);
}
