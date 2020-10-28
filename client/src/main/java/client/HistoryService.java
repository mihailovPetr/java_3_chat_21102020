package client;

public interface HistoryService {

    boolean writeMsg(String msg);

    String readMsgs(int n);

    void close();
}
