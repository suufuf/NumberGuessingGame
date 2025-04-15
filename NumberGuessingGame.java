package numberguessinggame;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Random;
import java.util.Scanner;

/**
 * This game provides the user with the task of guessing a random number between 1 and 100. 
 * Depending on the selected difficulty the number of guesses will vary. 
 * The high scores will be saved.
 * 
 * @author Fabian
 **/

public class NumberGuessingGame {

	public static void main(String[] args) {
		welcomeMessage();
		
		final Scanner SCANNER = new Scanner(System.in);
		playGame(SCANNER);
		SCANNER.close();
		System.out.println("Thank you for playing!");
	}
	
	private static void welcomeMessage() {
		System.out.println("Welcome to the Number Guessing Game!\r\n"
				+ "I'm thinking of a number between 1 and 100.\r\n"
				+ "-------------------------------------------");
	}
	
	private static void playGame(Scanner SCANNER) {
		final int MAX_GUESSES = selectDifficulty(SCANNER);
		final Random RANDOM = new Random();
		final int NUMBER_TO_GUESS = RANDOM.nextInt(99) + 1;
		int userGuess;
		boolean guessedCorrectly = false;
		int guesses;
		
		for (guesses = 1; guesses <= MAX_GUESSES; guesses++) {
				System.out.print("Please enter your guess: ");
			
				userGuess = getUserGuess(SCANNER);
			
				guessedCorrectly = checkUserGuess(NUMBER_TO_GUESS, userGuess, guesses, MAX_GUESSES);
				
				if (guessedCorrectly == true) {
					break;
				}
		}
		
		saveHighscore(MAX_GUESSES, guesses, guessedCorrectly);
		
		playAgain(SCANNER);
	}

	private static void playAgain(Scanner SCANNER) {
		System.out.println("Do you want to play again?");
		
		while (true) {
			switch (SCANNER.next()) {
			case "Yes": playGame(SCANNER);
			case "No": return;
			default: System.out.println("Please enter 'Yes' or 'No'!");
			}			
		}		
	}

	private static boolean checkUserGuess(int NUMBER_TO_GUESS, int userGuess, int guesses, int MAX_GUESSES) {
		if (userGuess == NUMBER_TO_GUESS) {
			System.out.println("Congratulations, you guessed the number in " + guesses + " moves!");
			return true;
		} else if (userGuess != NUMBER_TO_GUESS && guesses == MAX_GUESSES) {
			System.out.println("Mission failed!");
		} else if (userGuess > NUMBER_TO_GUESS) {
			if ((userGuess - 10) < NUMBER_TO_GUESS && guesses >= MAX_GUESSES/2) {
				System.out.println("The correct number is really close, although " + userGuess + " is too big!");
			} else {
				System.out.println("Incorrect! The number is less than " + userGuess + "!");
			}
		} else if (userGuess < NUMBER_TO_GUESS) {
			if ((userGuess + 10) > NUMBER_TO_GUESS && guesses >= MAX_GUESSES/2) {
				System.out.println("The correct number is really close, although " + userGuess + " is too low!");
			} else {
				System.out.println("Incorrect! The number is greater than " + userGuess + "!");
			}
		}
		return false;
	}

	private static void saveHighscore(int MAX_GUESSES, int guesses, boolean guessedCorrectly) {
		if (guessedCorrectly == true && (guesses < getHighscore(MAX_GUESSES) || getHighscore(MAX_GUESSES) == -1)) {
			String filename = null;
			switch (MAX_GUESSES) {
			case 10: filename = "easy.ser"; break;
			case 5: filename = "medium.ser"; break;
			case 3: filename = "hard.ser"; break;
			default: ;
			}
			if (filename != null) {
				try {
					FileOutputStream fileOut = new FileOutputStream(filename);
					ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
					objectOut.writeObject(guesses);
					fileOut.close();
					objectOut.close();
				} catch (Exception e) {
				    e.printStackTrace();
				}
			} else {
				System.out.println("An error occured while saving your score!");
			}
		}
	}

	private static int getHighscore(int MAX_GUESSES) {
		int highscore = -1;
		String filename = null;
		switch (MAX_GUESSES) {
		case 10: filename = "easy.ser"; break;
		case 5: filename = "medium.ser"; break;
		case 3: filename = "hard.ser"; break;
		default: ;
		}
		if (filename != null) {
			try {
				FileInputStream fileIn = new FileInputStream(filename);
				ObjectInputStream objectIn = new ObjectInputStream(fileIn);
				highscore = (int) objectIn.readObject();
				fileIn.close();
				objectIn.close();
			} catch (FileNotFoundException e) {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return highscore;
	}

	private static int selectDifficulty(Scanner SCANNER) {
		System.out.println("Please select the difficulty level:\r\n"
				+ "1. Easy (10 chances)\r\n"
				+ "2. Medium (5 chances)\r\n"
				+ "3. Hard (3 chances)\r\n"
				+ "-------------------------------------------");
		
		int difficulty;
		
		while (true) {
			switch (SCANNER.next()) {
			case "Easy": {
				System.out.println("Good luck!\r\n"); 
				difficulty = 10;
				break;
			}
			case "Medium": {
				System.out.println("Good luck!\r\n");
				difficulty = 5;
				break;
			}
			case "Hard": {
				System.out.println("Good luck!\r\n");
				difficulty = 3;
				break;
			}
			default: System.out.println("Please enter a valid difficulty (Easy, Medium, Hard)!"); continue;
			}
			break;
		}
		printHighscore(difficulty);
		return difficulty;
	}
	
	private static int getUserGuess(Scanner SCANNER) {
		int userInput;
		while (true) {
			try {
				userInput = SCANNER.nextInt();
				if (userInput <= 100 && userInput >= 1) {
					return userInput;
				} else {
					System.out.println("Please enter a valid number between 1 and 100!");
				}
			} catch (Exception e) {
				System.out.println("Please enter a valid number between 1 and 100!");
				SCANNER.nextLine();
			}
		}
	}
	
	
	private static void printHighscore(int MAX_GUESSES) {
		if (getHighscore(MAX_GUESSES) != -1) {
			System.out.println("The highscore for this difficulty is " + getHighscore(MAX_GUESSES) + " guesses!");
		} else {
			System.out.println("There has been no highscore set for this difficulty!");
		}
		System.out.println("-------------------------------------------");
	}

}
