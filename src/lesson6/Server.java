package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;


public class Server {
    private static final Logger logger = Logger.getLogger(Server.class.getName());

    private Vector<ClientHandler> clients;
    private AuthService authService;
    private ExecutorService service;

    public ExecutorService getService() {
        return service;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public Server() {
        clients = new Vector<>();
        service = Executors.newCachedThreadPool();
        if (!SQLHandler.connect()) {
            throw new RuntimeException("Не удалось подключиться к БД!");
        }
        authService = new DBAuthService();

        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(8189);
            // System.out.println("Сервер запущен");
            logger.info("Сервер запущен");

            while (true) {
                socket = server.accept();
                // System.out.println("Клиент подключился");
                logger.info("Клиент подключился");

                new ClientHandler(socket, this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            SQLHandler.disconnect();

            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void broadcastMsg(String nick, String msg) {
        for (ClientHandler c : clients) {
            c.sendMsg(nick + ": " + msg);
        }
    }

    public void privateMsg(ClientHandler sender, String receiver, String msg) {
        String message = String.format("[ %s ] private [ %s ] : %s",
                sender.getNick(), receiver, msg);

        if (sender.getNick().equals(receiver)) {
            sender.sendMsg(message);
            return;
        }

        for (ClientHandler c : clients) {
            if (c.getNick().equals(receiver)) {
                c.sendMsg(message);
                sender.sendMsg(message);
                return;
            }
        }

        sender.sendMsg("not found user: " + receiver);
    }


    public void subscribe(ClientHandler clientHandler) {
        clients.add(clientHandler);
        broadcastClientList();
    }

    public void unsubscribe(ClientHandler clientHandler) {
        clients.remove(clientHandler);
        broadcastClientList();
    }

    public boolean isLoginAuthorized(String login) {
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
            sb.append(c.getNick() + " ");
        }

        String msg = sb.toString();
        for (ClientHandler c : clients) {
            c.sendMsg(msg);
        }
    }

}
