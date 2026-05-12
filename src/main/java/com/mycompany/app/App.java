package com.mycompany.app;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class App {

	public static void main(String[] args) {

		System.setProperty(
				"webdriver.chrome.driver",
				"C:\\Users\\shken\\ST-7\\chromedriver-win64\\chromedriver.exe");

		WebDriver webDriver = new ChromeDriver();

		try {
			webDriver.get("https://www.calculator.net/password-generator.html");

			Thread.sleep(3000);

			WebElement passwordElement = webDriver.findElement(By.cssSelector("#resultid .verybigtext b"));

			System.out.println("Сгенерированный пароль: " + passwordElement.getText());

		} catch (Exception e) {
			System.out.println("Error");
			System.out.println(e.toString());
		} finally {
			webDriver.quit();
		}

		Task2.run();
		Task3.run();
	}
}
