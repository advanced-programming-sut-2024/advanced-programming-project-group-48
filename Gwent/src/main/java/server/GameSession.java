package server;

import model.GameEnvironment;
import model.GameHistory;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class GameSession implements Runnable {
    private final ClientHandler player1;
    private final ClientHandler player2;
    public CountDownLatch gameEndLatch;
    public static ArrayList<GameSession> allGameSessions = new ArrayList<>();
    public boolean isRunning = false;

    public GameSession(ClientHandler player1, ClientHandler player2, CountDownLatch gameEndLatch) {
        this.player1 = player1;
        this.player2 = player2;
        this.gameEndLatch = gameEndLatch;
        allGameSessions.add(this);
    }

    public synchronized boolean isRunning() {
        return isRunning;
    }

    @Override
    public void run() {
        this.isRunning=true;
        GameEnvironment game = new GameEnvironment(player1.getCurrentUser().getDeck(), player2.getCurrentUser().getDeck());
        String player1Json = game.toJson();
        player1.sendMessage(player1Json);
        game.swapGameEnvironmentFields();
        String player2Json = game.toJson();
        player2.sendMessage(player2Json);
        player1.sendMessage("start");
        String player1Move = player1.receiveMessage();
        game = GameEnvironment.fromJson(player1Move);
        game.swapGameEnvironmentFields();
        player2.sendMessage("your turn");
        player2.sendMessage(game.toJson());
        while (true){
            String player2Move = player2.receiveMessage();
            if(player2Move.equals("end")){
                player2Move = player2.receiveMessage();
                game = GameEnvironment.fromJson(player2Move);
                game.swapGameEnvironmentFields();
                player1.sendMessage("end");
                break;
            }
            game = GameEnvironment.fromJson(player2Move);
            game.swapGameEnvironmentFields();
            player1.sendMessage(game.toJson());
            player1Move=player1.receiveMessage();
            if(player1Move.equals("end")){
                player1Move = player1.receiveMessage();
                game = GameEnvironment.fromJson(player1Move);
                player2.sendMessage("end");
                break;
            }
            game = GameEnvironment.fromJson(player1Move);
            game.swapGameEnvironmentFields();
            player2.sendMessage(game.toJson());
        }
        // save Scores

        GameHistory gameHistory = new GameHistory(game.round1Score, game.round2Score, game.round3Score,
                game.enemyRound1Score, game.enemyRound2Score, game.enemyRound3Score, new Date(), player2.getCurrentUser().getUsername());
        player1.getCurrentUser().addGameHistory(gameHistory);

        GameHistory gameHistoryOpponent = new GameHistory(game.enemyRound1Score, game.enemyRound2Score, game.enemyRound3Score,
                game.round1Score, game.round2Score, game.round3Score, new Date(), player1.getCurrentUser().getUsername());
        player2.getCurrentUser().addGameHistory(gameHistoryOpponent);

        int status = game.getGameStatus();


        if (status == 0) {
            player1.getCurrentUser().increaseNumWins();
            player2.getCurrentUser().increaseNumLoses();
        }
        if (status == 1) {
            player1.getCurrentUser().increaseNumLoses();
            player2.getCurrentUser().increaseNumWins();
        }
        if (status == 2) {
            player1.getCurrentUser().increaseNumDraws();
            player2.getCurrentUser().increaseNumDraws();
        }
        player1.getCurrentUser().increaseNumTotalGames();
        player2.getCurrentUser().increaseNumTotalGames();


        // Game end - notify client handlers
        gameEndLatch.countDown(); // This signals the end of the game to both ClientHandler threads
    }

}
