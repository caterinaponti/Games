package for_project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Trivia extends Game{

	private ArrayList<String> trivia;
	private int score;
	private String playerGuess;
	private String currentQuestion;
	private String currentAnswer;
	private ArrayList<Integer> usedIndices;
	
	public Trivia() {
		trivia = new ArrayList<>();
		score = 2;
		currentQuestion = "";
		currentAnswer = "";
		usedIndices = new ArrayList<>();
		try {
			File file = new File("Trivia.txt");
			Scanner scanner = new Scanner(file);
			while(scanner.hasNextLine()) {
				trivia.add(scanner.nextLine());
			}
			scanner.close();
			
		}catch(FileNotFoundException e) {
			System.out.println("Error loading ASCII art file: " + e.getMessage());
		}
	}
	
	@Override
	public String explainRules() {
		return "Welcome to the Trivia Challenge!\n\n" +
		"You start with 2 point.\n" +
		"Every time you answer a question correctly, you gain 1 point. You can only accumulate a maximum of 3 points.\n" +
		"Every time you answer a question incorrectly, you lose 1 point.\n" +
		"Be careful! If your score reaches 0, you lose the game.\n\n" +
		"Enter just one word as answer. For names enter just last name.";
	}

	@Override
	public String setup() {
		int randomIndex;
		randomIndex = new Random().nextInt(trivia.size()-1);
		
		if(randomIndex % 2 == 1) {
			randomIndex += 1;
		}
		
		currentQuestion = trivia.get(randomIndex);
		currentAnswer = trivia.get(randomIndex + 1);
		
		trivia.remove(randomIndex + 1);
		trivia.remove(randomIndex);
		
		return currentQuestion;
	}

	@Override
	public boolean goodPlayerInput(String guess) {
		playerGuess = guess.replaceAll("\\s", "").toLowerCase();
		boolean goodInput = true;
		for (int i = 0; i < playerGuess.length(); i++) {
	        char c = playerGuess.charAt(i);
	        if (!Character.isLetter(c)) {
	            goodInput = false;
	        }
		}
		return goodInput;
	}

	@Override
	public String checkWinOrLose() {
		String correctAnswer = currentQuestion.replace(",", "");
		correctAnswer = currentAnswer.replace(",", "");
		
		if(playerGuess.equalsIgnoreCase(correctAnswer)) {
			if(score < 3) {
				score ++;
				return "Correct answer! You won 1 point!"+ "\n" + "You have " + score + " points.";
				}
			else {
            return "Correct answer! Keep going! "+ "\n" + "You have " + score + " points.";
			}
        } else {
            score--;
            return "Incorrect Answer! You lost 1 point" + "\n" + "You have " + score + " points.";
		}
	}

	@Override
	public boolean canPlayAgain() {
		if(score <= 0) {
			System.out.println("You lost! Your score reached 0.");
			return false;
		}
		if(trivia.isEmpty()) {
			System.out.println("\n\nCongratulations! You won!");
			return false;
		}
		return true;
	}

}
