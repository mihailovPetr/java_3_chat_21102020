package server;

import java.sql.*;

public class DBAuthService implements AuthService {

    private static Connection connection;
    private static PreparedStatement psAuth;
    private static PreparedStatement psReg;
    private static PreparedStatement psChNick;

    public DBAuthService(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getNicknameByLoginAndPassword(String login, String password) {

        try {
            connect();

            psAuth = connection.prepareStatement("SELECT nick FROM CLIENTS WHERE login=? AND password=?;");

            psAuth.setString(1, login);
            psAuth.setString(2, password);
            ResultSet rs = psAuth.executeQuery();

            String nick = null;

            if (rs.next()){
                nick = rs.getString(1);
            }
            rs.close();

            return nick;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disconnect();
        }


        return null;
    }

    @Override
    public boolean registration(String login, String password, String nickname) {
        try {
            connect();

            psReg = connection.prepareStatement("INSERT INTO clients (login, password, nick) VALUES (?, ?, ?);");

            psReg.setString(1, login);
            psReg.setString(2, password);
            psReg.setString(3, nickname);
            psReg.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            disconnect();
        }
    }

    @Override
    public boolean changeNick(String nick, String newNick) {
        try {
            connect();

            psChNick = connection.prepareStatement("UPDATE clients SET nick=? WHERE nick=?;");

            psChNick.setString(1, newNick);
            psChNick.setString(2, nick);

            psChNick.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            disconnect();
        }
    }

    private static void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:server/clients.db");
    }

    private static void disconnect() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
