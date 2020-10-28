package client;

import java.io.*;

public class FileHistoryService implements HistoryService {

    private File file;
    private FileWriter out;
    private static final String SEP = "\n"; //разделитель сообщений в файле

    public FileHistoryService(String login) throws IOException {

        file = new File("client/src/main/java/client/history/history_" + login + ".txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        out = new FileWriter(file, true);
    }

    @Override
    public boolean writeMsg(String msg) {
        try {
            out.write(msg + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public String readMsgs(int n) {

        char[] buf = new char[256];
        int x;
        StringBuilder sb = new StringBuilder();

        try (FileReader in = new FileReader(file)) {
            while ((x = in.read(buf)) == 256) {
                sb.append(buf);
            }

            if (x > 0) {
                sb.append(buf, 0, x);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (n == 0) {
            return sb.toString();
        }

        int sepPos = sb.length() - 1;
        int i = 0;

        while (sepPos != -1 && i < n) {
            sepPos = sb.lastIndexOf(SEP, sepPos - 1);
            i++;
        }

        if (sepPos == -1) {
            return sb.toString();
        }

        return sb.substring(sepPos + 1, sb.length());
    }

    @Override
    public void close() {
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}