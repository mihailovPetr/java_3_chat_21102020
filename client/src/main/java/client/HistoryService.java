package client;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public interface HistoryService {

    public boolean writeMsg(String msg);

    public String readMsgs(int n);

    public void close();
}
