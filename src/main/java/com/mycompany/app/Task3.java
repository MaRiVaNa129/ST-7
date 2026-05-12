package com.mycompany.app;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Task3 {

	public static void run() {

		System.setProperty(
				"webdriver.chrome.driver",
				"C:\\Users\\shken\\ST-7\\chromedriver-win64\\chromedriver.exe");

		WebDriver webDriver = new ChromeDriver();

		try {

			String url = "https://api.open-meteo.com/v1/forecast?" +
					"latitude=56&longitude=44&" +
					"hourly=temperature_2m,rain&" +
					"timezone=Europe%2FMoscow&" +
					"forecast_days=1&" +
					"wind_speed_unit=ms";

			webDriver.get(url);

			WebElement elem = webDriver.findElement(By.tagName("pre"));

			String json_str = elem.getText();

			JSONParser parser = new JSONParser();

			JSONObject obj = (JSONObject) parser.parse(json_str);

			JSONObject hourly = (JSONObject) obj.get("hourly");

			JSONArray time = (JSONArray) hourly.get("time");

			JSONArray temp = (JSONArray) hourly.get("temperature_2m");

			JSONArray rain = (JSONArray) hourly.get("rain");

			File dir = new File("result");

			if (!dir.exists()) {
				dir.mkdir();
			}

			PrintWriter writer = new PrintWriter(
					new FileWriter("result/forecast.txt"));

			// Форматирование с выравниванием столбцов
			// %-4s - номер (4 символа, выравнивание влево)
			// %-20s - дата/время (20 символов, выравнивание влево)
			// %-12s - температура (12 символов, выравнивание влево)
			// %-8s - осадки (8 символов, выравнивание влево)

			writer.println(String.format("%-4s %-20s %-12s %-8s",
					"№", "Дата/время", "Температура", "Осадки"));

			writer.println(String.format("%-4s %-20s %-12s %-8s",
					"--", "--------------------", "------------", "--------"));

			System.out.println("\nПрогноз погоды:\n");
			System.out.println(String.format("%-4s %-20s %-12s %-8s",
					"№", "Дата/время", "Температура", "Осадки"));
			System.out.println(String.format("%-4s %-20s %-12s %-8s",
					"--", "--------------------", "------------", "--------"));

			for (int i = 0; i < time.size(); i++) {

				// Получаем только дату и время (убираем букву T, добавляем пробел)
				String dateTime = ((String) time.get(i)).replace("T", " ");

				// Форматируем строку
				String row = String.format("%-4d %-20s %3.1f°C        %-8s",
						(i + 1),
						dateTime,
						(double) temp.get(i),
						rain.get(i) + " мм");

				System.out.println(row);
				writer.println(row);
			}

			writer.close();

			System.out.println("\nФайл forecast.txt сохранен в папке result/");

		} catch (Exception e) {

			System.out.println("Error");
			System.out.println(e.toString());

		} finally {

			webDriver.quit();

		}
	}
}
