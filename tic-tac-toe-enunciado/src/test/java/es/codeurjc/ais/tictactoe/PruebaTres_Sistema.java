package es.codeurjc.ais.tictactoe;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBePresentInElementLocated;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

class PruebaTres_Sistema {
	//Dos pestañas dos driver
	protected WebDriver driver1,driver2;
	//Dado que es necesario cierto retardo para que las acciones no sean inmediatas hemos usado WebDriverWait
	protected WebDriverWait waitDriver1,waitDriver2;
	//Botones play al iniciar la app
	protected WebElement botonEntrada2;
	protected WebElement botonEntrada1;
	
	@BeforeClass
	public static void setUpDriver() {
		WebDriverManager.chromedriver().setup();
		//Al realizarlo en local
		WebApp.start();
	}
	
	@Before
	public void setUp() {
		driver1=new ChromeDriver();
		driver1.get("http://localhost:8083/");
		driver2=new ChromeDriver();	
		driver2.get("http://localhost:8083/");
		
		System.out.println(driver1.findElement(By.id("nickname")));
		driver1.findElement(By.id("nickname")).sendKeys("Sara");
		driver2.findElement(By.id("nickname")).sendKeys("Ioana");
		waitDriver1=new WebDriverWait(driver1,10);
		waitDriver2=new WebDriverWait(driver2,10);
		botonEntrada1=driver1.findElement(By.className("btn btn-lg"));
		botonEntrada2=driver2.findElement(By.id("startBtn"));
		botonEntrada1.click();
		botonEntrada2.click();
		
	}
	
	@After
	public void finish() {
		if(driver1!=null) {
			driver1.quit();
		}
		if(driver2!=null) {
			driver2.quit();
		}
	}
	
	@AfterClass
	public static void finishDriver() {
		WebApp.stop();
	}
	
	@Test
	public void haGanadoXTest() {	
		
		System.out.println(botonEntrada1);
		boolean selectBotonEntrada2=botonEntrada1.isSelected();
		boolean selectBotonEntrada1=botonEntrada2.isSelected();
		waitDriver1.until(ExpectedConditions.elementSelectionStateToBe(botonEntrada2, selectBotonEntrada1));
		waitDriver2.until(ExpectedConditions.elementSelectionStateToBe(botonEntrada1, selectBotonEntrada2));
		//Cuando ya los dos jugadores estan iniciados
		
		driver1.findElement(By.id("cell-0")).click();	
		driver2.findElement(By.id("cell-3")).click();
		driver1.findElement(By.id("cell-1")).click();	
		driver2.findElement(By.id("cell-4")).click();
		driver1.findElement(By.id("cell-2")).click();	
		
		waitDriver1.until(ExpectedConditions.alertIsPresent());
		waitDriver2.until(ExpectedConditions.alertIsPresent());
		//Mensaje del ganador y perdedor igual en ambas pantallas
		assertThat(driver1.switchTo().alert().getText()).isEqualTo(driver2.switchTo().alert().getText());		
		assertThat("sara wins").isSubstringOf(driver1.switchTo().alert().getText());
		assertThat("ioana looses").isSubstringOf(driver1.switchTo().alert().getText());
	}
	
	@Test
	void haPerdidoXTest() {
		
	}
	
	@Test
	void empateTest() {
		
	}

	
}
