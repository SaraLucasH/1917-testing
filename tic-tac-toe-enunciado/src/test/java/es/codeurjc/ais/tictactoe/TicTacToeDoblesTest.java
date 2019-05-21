package es.codeurjc.ais.tictactoe;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

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
		player1 = new Player(1, "X", "Sara");
		game.addPlayer(player1);
		verify(connection1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(player1)));		
		player2 = new Player(2, "O", "Ioana");
		game.addPlayer(player2);
		
		verify(connection1,times(2)).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(player1,player2)));
		verify(connection2,times(2)).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(player1,player2)));
	}
	
	
	/*
	 * X |   | O
	 *   | X |
	 *   | O | X
	 */
	@Test
	public void haGanadoXTest() {
		
		//verificamos que los turnos iniciales son los correctos 
		verify(connection1,times(1)).sendEvent(EventType.SET_TURN, game.getPlayers().get(0));
		verify(connection2,times(0)).sendEvent(EventType.SET_TURN, game.getPlayers().get(1));
		
		//se va marcando la casilla correspondiete y se verifican los turnos 
		game.mark(0);
		verify(connection1, times(1)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(1)).sendEvent(EventType.SET_TURN, player2);
		game.mark(2);
		verify(connection1, times(2)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(1)).sendEvent(EventType.SET_TURN, player2);
		game.mark(4);
		verify(connection1, times(2)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(2)).sendEvent(EventType.SET_TURN, player2);
		game.mark(7);
		verify(connection1, times(3)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(2)).sendEvent(EventType.SET_TURN, player2);
		game.mark(8);
		verify(connection1, times(3)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(2)).sendEvent(EventType.SET_TURN, player2);
		
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
		
		//comprobamos que el array con las posiciones del tablero del jugador victoriosos son las mismas
		int[] tableroJugador1 = {0,4,8};
		assertArrayEquals(tableroJugador1, game.checkWinner().pos);
		
		verify(connection1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
		verify(connection2, times(1)).sendEvent(eq(EventType.GAME_OVER), argument.capture());
	}
	
	/*
	 * O |   | 
	 * O | X | X
	 * O |   | X
	 */ 
	@Test
	public void haPerdidoXTest() {
		//verificamos que los turnos iniciales son los correctos 
		verify(connection1,times(1)).sendEvent(EventType.SET_TURN, game.getPlayers().get(0));
		verify(connection2,times(0)).sendEvent(EventType.SET_TURN, game.getPlayers().get(1));
		
		//se va marcando la casilla correspondiete y se verifican los turnos 
		game.mark(4);
		verify(connection1, times(1)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(1)).sendEvent(EventType.SET_TURN, player2);
		game.mark(0);
		verify(connection1, times(2)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(1)).sendEvent(EventType.SET_TURN, player2);
		game.mark(5);
		verify(connection1, times(2)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(2)).sendEvent(EventType.SET_TURN, player2);
		game.mark(3);
		verify(connection1, times(3)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(2)).sendEvent(EventType.SET_TURN, player2);
		game.mark(8);
		verify(connection1, times(3)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(3)).sendEvent(EventType.SET_TURN, player2);
		game.mark(6);
		verify(connection1, times(3)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(3)).sendEvent(EventType.SET_TURN, player2);
		
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
		
		//comprobamos que el array con las posiciones del tablero del jugador victoriosos son las mismas
		int[] tableroJugador2 = {0,3,6};
		assertArrayEquals(tableroJugador2, game.checkWinner().pos);
		
		verify(connection1,times(1)).sendEvent(eq(EventType.GAME_OVER), argument.capture());
		verify(connection2).sendEvent(eq(EventType.GAME_OVER), argument.capture());
	}
	
	
	/* X | X | O
	 * O | X | X
	 * X | O | O
	 */
	@Test
	public void empateTest() {
		//verificamos que los turnos iniciales son los correctos 
		verify(connection1,times(1)).sendEvent(EventType.SET_TURN, game.getPlayers().get(0));
		verify(connection2,times(0)).sendEvent(EventType.SET_TURN, game.getPlayers().get(1));
		
		//se va marcando la casilla correspondiete y se verifican los turnos 
		game.mark(0);
		verify(connection1, times(1)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(1)).sendEvent(EventType.SET_TURN, player2);
		game.mark(2);
		verify(connection1, times(2)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(1)).sendEvent(EventType.SET_TURN, player2);
		game.mark(1);
		verify(connection1, times(2)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(2)).sendEvent(EventType.SET_TURN, player2);
		game.mark(3);
		verify(connection1, times(3)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(2)).sendEvent(EventType.SET_TURN, player2);
		game.mark(4);
		verify(connection1, times(3)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(3)).sendEvent(EventType.SET_TURN, player2);
		game.mark(7);
		verify(connection1, times(4)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(3)).sendEvent(EventType.SET_TURN, player2);
		game.mark(6);
		verify(connection1, times(4)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(4)).sendEvent(EventType.SET_TURN, player2);
		game.mark(8);
		verify(connection1, times(5)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(4)).sendEvent(EventType.SET_TURN, player2);
		game.mark(5);
		verify(connection1, times(5)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(4)).sendEvent(EventType.SET_TURN, player2);
		
		//comprobamos que se ha producido el empate
		assertTrue(game.checkDraw());
		assertFalse(game.checkWinner().win);
		
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
		verify(connection1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
		verify(connection2,times(1)).sendEvent(eq(EventType.GAME_OVER), argument.capture());
				
	}
	
	
}
