package es.codeurjc.ais.tictactoe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItems;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import es.codeurjc.ais.tictactoe.Connection;
import es.codeurjc.ais.tictactoe.Player;
import es.codeurjc.ais.tictactoe.TicTacToeGame;
import es.codeurjc.ais.tictactoe.TicTacToeGame.EventType;
import es.codeurjc.ais.tictactoe.TicTacToeGame.WinnerValue;
import static org.mockito.Mockito.*;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

public class TicTacToeDoblesTest {
	private TicTacToeGame game;
	private Connection connection1;
	private Connection connection2;
	private Player player1;
	private Player player2;
	
	private void verificacionTurnoInicial() {
		//verificamos que los turnos iniciales son los correctos 
		verify(connection1,times(1)).sendEvent(EventType.SET_TURN, game.getPlayers().get(0));
		verify(connection2,times(0)).sendEvent(EventType.SET_TURN, game.getPlayers().get(1));
	}
	
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
	
	@Test
	public void haGanadoXTest() {
		verificacionTurnoInicial();
		//Se va marcando la casilla correspondiete y se verifican los turnos 
		game.mark(0);
		verify(connection1, times(1)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(1)).sendEvent(EventType.SET_TURN, player2);
		game.mark(3);
		verify(connection1, times(2)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(1)).sendEvent(EventType.SET_TURN, player2);
		game.mark(1);
		verify(connection1, times(2)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(2)).sendEvent(EventType.SET_TURN, player2);
		game.mark(4);
		verify(connection1, times(3)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(2)).sendEvent(EventType.SET_TURN, player2);
		game.mark(2);
		verify(connection1, times(3)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(2)).sendEvent(EventType.SET_TURN, player2);
		
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
		
		//comprobamos que el array con las posiciones del tablero del jugador victoriosos son las mismas
		
		assertThat(new int[] {0,1,2}).isEqualTo( game.checkWinner().pos);		
		verify(connection1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
		verify(connection2, times(1)).sendEvent(eq(EventType.GAME_OVER), argument.capture());
	}	
	//Simulacion:
	// X X X
	// O O 
	//
	
	@Test
	public void haPerdidoXTest() {
		verificacionTurnoInicial();
		
		game.mark(0);
		verify(connection1, times(1)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(1)).sendEvent(EventType.SET_TURN, player2);
		game.mark(2);
		verify(connection1, times(2)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(1)).sendEvent(EventType.SET_TURN, player2);
		game.mark(1);
		verify(connection1, times(2)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(2)).sendEvent(EventType.SET_TURN, player2);
		game.mark(4);
		verify(connection1, times(3)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(2)).sendEvent(EventType.SET_TURN, player2);
		game.mark(3);
		verify(connection1, times(3)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(3)).sendEvent(EventType.SET_TURN, player2);
		game.mark(6);
		verify(connection1, times(3)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(3)).sendEvent(EventType.SET_TURN, player2);
		
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
		
		//comprobamos que el array con las posiciones del tablero del jugador victoriosos son las mismas
		
		assertThat(new int[] {6,4,2}).isEqualTo( game.checkWinner().pos);	
		verify(connection1,times(1)).sendEvent(eq(EventType.GAME_OVER), argument.capture());
		verify(connection2).sendEvent(eq(EventType.GAME_OVER), argument.capture());
	}	
	//X X O
	//X O 
	//O 
	
	@Test
	public void empateTest() {
		verificacionTurnoInicial();
		
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
		game.mark(5);
		verify(connection1, times(4)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(4)).sendEvent(EventType.SET_TURN, player2);
		game.mark(8);
		verify(connection1, times(5)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(4)).sendEvent(EventType.SET_TURN, player2);
		game.mark(6);
		verify(connection1, times(5)).sendEvent(EventType.SET_TURN, player1);
		verify(connection2, times(4)).sendEvent(EventType.SET_TURN, player2);
		
		//Comprobamos que se ha producido el empate
		assertThat(game.checkDraw()).isTrue();
		assertThat(game.checkWinner().pos).isNull();
		
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
		verify(connection1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
		verify(connection2,times(1)).sendEvent(eq(EventType.GAME_OVER), argument.capture());				
	}
	//X X O
	//O X X
	//X O O
	
}
