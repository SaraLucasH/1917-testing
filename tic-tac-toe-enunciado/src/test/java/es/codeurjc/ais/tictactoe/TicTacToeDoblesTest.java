package es.codeurjc.ais.tictactoe;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import es.codeurjc.ais.tictactoe.Connection;
import es.codeurjc.ais.tictactoe.Player;
import es.codeurjc.ais.tictactoe.TicTacToeGame;
import es.codeurjc.ais.tictactoe.TicTacToeGame.EventType;
import es.codeurjc.ais.tictactoe.TicTacToeGame.WinnerResult;
import es.codeurjc.ais.tictactoe.TicTacToeGame.WinnerValue;

import static org.mockito.Mockito.*;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

import org.hamcrest.*;

public class TicTacToeDoblesTest {
	private TicTacToeGame game;
	private Connection connection1;
	private Connection connection2;
	private Player player1;
	private Player player2;
	
	@Before
	public void setUp() {
		game = new TicTacToeGame();		
		connection1 = mock(Connection.class);
		game.addConnection(connection1);
		connection2 = mock(Connection.class);
		game.addConnection(connection2);
		player1 = new Player(1, "X", "Pepe");
		game.addPlayer(player1);
		verify(connection1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(player1)));		
		player2 = new Player(2, "O", "Juan");
		game.addPlayer(player2);
		
		verify(connection1,times(2)).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(player1,player2)));
		verify(connection2,times(2)).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(player1,player2)));
	}
	@Test
	public void haGanadoXTest() {
		
	}
}
