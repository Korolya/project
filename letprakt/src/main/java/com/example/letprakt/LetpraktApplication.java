package com.example.letprakt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@Controller
public class LetpraktApplication {
	private static final int N = 5;

	public static void main(String[] args) {
		SpringApplication.run(LetpraktApplication.class, args);
	}

	@GetMapping("/hello")
	public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@GetMapping("/multiply")
	public String showMultiplyForm() {
		return "multiply";
	}

	@GetMapping("/showDetails")
	public String multiplyNumber(@RequestParam(value = "number", required = true) Integer number, Model model) {
		if (number == null) {
			throw new IllegalArgumentException("Параметр 'number' обязателен.");
		}
		int result = number * N;
		model.addAttribute("result", "Результат: " + result);
		return "multiply";
	}

	@ExceptionHandler(NumberFormatException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleNumberFormatException(NumberFormatException ex, Model model) {
		model.addAttribute("error", "Неверный формат числа. Пожалуйста, укажите целое число для параметра 'number'.");
		return "multiply";
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleIllegalArgumentException(IllegalArgumentException ex, Model model) {
		model.addAttribute("error", "Произошла ошибка: " + ex.getMessage());
		return "multiply";
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String handleException(Exception ex, Model model) {
		model.addAttribute("error", "Произошла ошибка: " + ex.getMessage());
		return "multiply";
	}

}
