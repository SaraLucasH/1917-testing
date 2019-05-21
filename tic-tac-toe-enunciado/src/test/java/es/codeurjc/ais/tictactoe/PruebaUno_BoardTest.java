package es.codeurjc.ais.tictactoe;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import es.codeurjc.ais.tictactoe.Board;

//Necesario que esten en el mismo package puesto que la clase celda no es publica
//Por tanto para acceder a sus metodos los test deben estar
//En el mismo paquete
public class PruebaUno_BoardTest {
	//Uso de biblioteca assertj
	private Board tablero;
	
	//Para comprobaciones al iniciar el tablero
	private void ComprobacionInicio() {
		//El tablero esta lleno?
		assertThat(tablero.checkDraw()).isFalse();
		//Tenemos algun ganador?
		assertThat(tablero.getCellsIfWinner("X")).isNull();
		assertThat(tablero.getCellsIfWinner("O")).isNull();
	}
	
	//En lugar del metodo mark de la clase TicTacToeGame, cambio los valores de las celdas con la etiqueta de jugador
	//y deshabilitandola
	private void MarcoCelda(int numeroCelda, String jugador) {
		tablero.getCell(numeroCelda).value=jugador;
		tablero.getCell(numeroCelda).active=false;
	}
	
	@Before
	public void setUp() {
		this.tablero = new Board();
		//Habilito las celdas
		this.tablero.enableAll();
	}
	
	@Test
	public void haGanadoXTest() {
		ComprobacionInicio();
		MarcoCelda(0,"X");
		MarcoCelda(3,"O");
		//Para comprobar que apesar de marcar alguna casilla los metodos siguen funcionando
		assertThat(tablero.checkDraw()).isFalse();
		assertThat(tablero.getCellsIfWinner("X")).isNull();
		assertThat(tablero.getCellsIfWinner("O")).isNull();
		
		MarcoCelda(1,"X");
		MarcoCelda(4,"O");
		MarcoCelda(2,"X");						
		assertThat(tablero.checkDraw()).isFalse();
		assertThat(tablero.getCellsIfWinner("X")).isNotNull();
		assertThat(tablero.getCellsIfWinner("O")).isNull();
		assertThat(tablero.getCellsIfWinner("X")).isEqualTo(new int[] {0,1,2});
	}
	//Simulacion:
	// X X X
	// O O 
	//
	
	@Test
	public void pierdeXTest() {
		ComprobacionInicio();		
		MarcoCelda(0,"X");
		MarcoCelda(2,"O");
		MarcoCelda(1,"X");
		MarcoCelda(4,"O");
		MarcoCelda(3,"X");
		ComprobacionInicio();		
		
		MarcoCelda(6,"O");		
		assertThat(tablero.checkDraw()).isFalse();
		assertThat(tablero.getCellsIfWinner("O")).isNotNull();
		assertThat(tablero.getCellsIfWinner("X")).isNull();		
		assertThat(tablero.getCellsIfWinner("O")).isEqualTo( new int[] {6,4,2});
	}
	
	//X X O
	//X O 
	//O  
	 
	@Test
	public void empateTest() {
		ComprobacionInicio();
		MarcoCelda(0,"X");
		MarcoCelda(2,"O");
		MarcoCelda(1,"X");
		MarcoCelda(3,"O");
		MarcoCelda(4,"X");
		MarcoCelda(7,"O");
		MarcoCelda(5,"X");
		MarcoCelda(8,"O");
		MarcoCelda(6,"X");
		
		assertThat(tablero.checkDraw()).isTrue();
		assertThat(tablero.getCellsIfWinner("O")).isNull();
		assertThat(tablero.getCellsIfWinner("X")).isNull();
	}
	//X X O
	//O X X
	//X O O

}
