package for_project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Spaceship extends Game{
	
	//instance variables
	private String wordToGuess;
	private ArrayList<String> spaceship;
	private int attemptsLeft;
	private String userGuess;
	private ArrayList<String> Characters;
	private int index;
	private String setupMessage;
	private ArrayList<String> WordsGuessed;
	
	//constructor: read in your file of ASCII art using a Scanner 
	public Spaceship() {
		
		spaceship = new ArrayList<>();
		Characters = new ArrayList<>();
		WordsGuessed = new ArrayList<>();
		setupMessage = "";
		
		String[] words = {"planet", "asteroid", "galaxy", "comet", "spaceship"};
				
		try {
			File file = new File("rockets.txt");
			Scanner scanner = new Scanner(file);
			
			String spaceshipString = " ";
			while (scanner.hasNext()) {
				spaceshipString = spaceshipString +  scanner.nextLine() + ("\n");
                }
			scanner.close();
			
			String[] spaceshipList = spaceshipString.split(",");
			
			for(String s : spaceshipList) {
				spaceship.add(s);
			}
			
			attemptsLeft = 10;
			
			index = 0;
				
		}catch(FileNotFoundException e) {
			System.out.println("Error loading ASCII art file: " + e.getMessage());
			}
		
		Random random = new Random();
		int randomIndex = random.nextInt(words.length);
		
		wordToGuess = words[randomIndex];
		
		for(int i = 0; i < wordToGuess.length(); i++) {
			Characters.add(String.valueOf(wordToGuess.charAt(i)));
			WordsGuessed.add("_");
			
			}
		}
	//do not interact with user from child class
	
	@Override
	public String explainRules() {
		// TODO Auto-generated method stub
		return "Welcome to the Spaceship Game!\n\n"  +
		"In this game, you'll be trying to guess a secret word.\n" + 
		"But be careful, if you run out of attempts before guessing the word, you lose.\n\n" +
		"Let's get started! Good luck!\n";
	}

	@Override
	public String setup() {
		
		String setupMessage = "";
		for(String s : WordsGuessed) {
			setupMessage += s + " ";
		}
		
		if(attemptsLeft > 0) {
			return spaceship.get(index) + "\n\n\n" + setupMessage + "\n\n" + "Enter your guess\n";
		}
		else {
			return spaceship.get(index) + "\n\n\n" + setupMessage;
		}
		}
		

	@Override
	public boolean goodPlayerInput(String guess) {
		boolean goodPlayerInput = false;
		if(guess.length() == 1) {
			goodPlayerInput = true;
			userGuess = guess;
		}
		return goodPlayerInput;
	}

	@Override
	public String checkWinOrLose() { 
		
		boolean correctGuess = false;
		
		if(WordsGuessed.contains(userGuess)) {
            System.out.println("You already guessed this letter.");
            correctGuess = true;
            attemptsLeft--;
            index ++;
        }
		
	    for (int i = 0; i < wordToGuess.length(); i++) {
	            char currentChar = wordToGuess.charAt(i);
	            if (currentChar == userGuess.charAt(0) && WordsGuessed.get(i).equals("_")) {
	                correctGuess = true;
	                WordsGuessed.set(i, userGuess);
	                System.out.println(" ");
	            }
	    }   
	    
	    if(correctGuess & !WordsGuessed.contains(userGuess)) {
	    	System.out.println("Good guess!\n");
	    }
	    
	    if(!correctGuess) {
            System.out.println("Incorrect guess.");
            attemptsLeft--;
            index++;
	    	}
	    
	    if(Characters.equals(WordsGuessed)) {
			return "Congratulations! You've guessed the word: " + wordToGuess;
		}
	    else if(attemptsLeft == 0 & !Characters.equals(WordsGuessed)) {
	        return "Sorry, you've run out of attempts. The word was: " + wordToGuess;
	    }
	    else {
	    	return "";
	    }
	}	 

	@Override
	public boolean canPlayAgain() { 
		if(Characters.equals(WordsGuessed)) {
			return false;
		}
		else if(attemptsLeft == 0) {
	        return false;
	    }
		else {
			return true;
		}
	}

}
