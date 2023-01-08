package com.example.tictactoe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatisticsProvider {

    private static final StatisticsProvider INSTANCE = new StatisticsProvider();

    private static final String DB_URL = "jdbc:h2:./stats.h2";
    private static final String DB_USER = "user";
    private static final String DB_PASSWORD = "pass";
    private static final String SAVE_STATS_QUERY = "INSERT INTO stats (games_played, x_wins, o_wins, draws) VALUES (?, ?, ?, ?)";
    private static final String LOAD_STATS_QUERY = "SELECT SUM(games_played) AS games_played, SUM(x_wins) AS x_wins, SUM(o_wins) AS o_wins, SUM(draws) as draws FROM stats";
    private static final String CREATE_STATS_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS stats (games_played INT, x_wins INT, o_wins INT, draws INT)";

    private static int xWinsTotalSum;
    private static int oWinsTotalSum;
    private static int drawsTotalSum;
    private static int gamesPlayed;

    private Connection connection;

    private StatisticsProvider() {
        this(DB_URL, DB_USER, DB_PASSWORD);
    }

    StatisticsProvider(String dbUrl, String dbUser, String dbPassword) {
        try {
            this.connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            createStatsTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static StatisticsProvider getInstance() {
        return INSTANCE;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public int getXWinsTotalSum() {
        return xWinsTotalSum;
    }

    public int getOWinsTotalSum() {
        return oWinsTotalSum;
    }

    public  int getDrawsTotalSum() {
        return drawsTotalSum;
    }


    private void createStatsTable() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(CREATE_STATS_TABLE_QUERY);
        statement.executeUpdate();
    }

    public void saveStatistics(GameProcessor gameProcessor) {
        try {
            PreparedStatement statement = connection.prepareStatement(SAVE_STATS_QUERY);
            statement.setInt(1, (gameProcessor.getXWinningsCounter() + gameProcessor.getOWinningsCounter() + gameProcessor.getDrawsCounter()));
            statement.setInt(2, gameProcessor.getXWinningsCounter());
            statement.setInt(3, gameProcessor.getOWinningsCounter());
            statement.setInt(4, gameProcessor.getDrawsCounter());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadStatistics() {
        try {
            PreparedStatement statement = connection.prepareStatement(LOAD_STATS_QUERY);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                gamesPlayed = resultSet.getInt("games_played");
                xWinsTotalSum = resultSet.getInt("x_wins");
                oWinsTotalSum = resultSet.getInt("o_wins");
                drawsTotalSum = resultSet.getInt("draws");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}