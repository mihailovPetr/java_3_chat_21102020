package server;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.*;

public class Server {
    private List<ClientHandler> clients;
    private AuthService authService;
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    public Server() {
        LogManager manager = LogManager.getLogManager();
        try {
            manager.readConfiguration(new FileInputStream("server/logging.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        clients = new CopyOnWriteArrayList<>();
        authService = new DBAuthService();
        ServerSocket server = null;
        Socket socket = null;
        final int PORT = 8189;

        try {
            server = new ServerSocket(PORT);
            logger.log(Level.INFO,"Server started");
//            System.out.println("Server started");

            while (true) {
                socket = server.accept();
                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, e.toString());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            logger.log(Level.INFO,"Server stopped");
        }
    }

    public void broadcastMsg(ClientHandler sender, String msg) {
        String message = String.format("[ %s ]: %s", sender.getNickname(), msg);
        for (ClientHandler c : clients) {
            c.sendMsg(message);
        }
    }

    public void privateMsg(ClientHandler sender, String receiver, String msg) {
        String message = String.format("[ %s ] private [ %s ] : %s", sender.getNickname(), receiver, msg);
        for (ClientHandler c : clients) {
            if (c.getNickname().equals(receiver)) {
                c.sendMsg(message);
                if (!sender.getNickname().equals(receiver)) {
                    sender.sendMsg(message);
                }
                return;
            }
        }
        sender.sendMsg(String.format("Server: Client %s not found", receiver));
    }

    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        broadcastClientList();
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastClientList();
    }

    public AuthService getAuthService() {
        return authService;
    }

    public boolean isLoginAuthenticated(String login) {
        for (ClientHandler c : clients) {
            if (c.getLogin().equals(login)) {
                return true;
            }
        }
        return false;
    }

    public void broadcastClientList() {
        StringBuilder sb = new StringBuilder("/clientlist ");

        for (ClientHandler c : clients) {
            sb.append(c.getNickname()).append(" ");
        }
//        sb.setLength(sb.length() );
        String message = sb.toString();
        for (ClientHandler c : clients) {
            c.sendMsg(message);
        }
    }
}
