package com.example.tictactoe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatisticsProviderTest {

    @Mock
    private GameProcessor gameProcessor;

    private StatisticsProvider statisticsProvider;

    @BeforeEach
    public void setUp() {
        String dbUrl = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
        String dbUser = "sa";
        String dbPassword = "";
        statisticsProvider = new StatisticsProvider(dbUrl, dbUser, dbPassword);
    }

    @Test
    void testSaveStatistics() {
        when(gameProcessor.getXWinningsCounter()).thenReturn(2);
        when(gameProcessor.getOWinningsCounter()).thenReturn(1);
        when(gameProcessor.getDrawsCounter()).thenReturn(3);

        statisticsProvider.saveStatistics(gameProcessor);
        statisticsProvider.loadStatistics();

        assertEquals(6, statisticsProvider.getGamesPlayed());
        assertEquals(2, statisticsProvider.getXWinsTotalSum());
        assertEquals(1, statisticsProvider.getOWinsTotalSum());
        assertEquals(3, statisticsProvider.getDrawsTotalSum());
    }

    @Test
    void testLoadStatistics() {
        statisticsProvider.loadStatistics();

        assertEquals(0, statisticsProvider.getGamesPlayed());
        assertEquals(0, statisticsProvider.getXWinsTotalSum());
        assertEquals(0, statisticsProvider.getOWinsTotalSum());
        assertEquals(0, statisticsProvider.getDrawsTotalSum());
    }

    @Test
    void testGetInstance() {
        assertNotNull(StatisticsProvider.getInstance());
    }

}
