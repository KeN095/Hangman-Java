package hangman;

import java.util.*;

public class hangMan {

	private char[] guessWord; //The char array will be displayed to the player
	private String inputs = ""; //String variable consisting of guesses the player will make, empty by default
	private char userGuess; // The char variable the player's guesses will be stored in
	private int tries; // The amount of guesses made by user, 0 by default
	protected String chosenWord; //The word for the hangman game
	private int win = 0; //Number of wins the player has made per game,0 by default
	private int loss = 0; //Number of losses the player has made per game, 0 by default

	List<String> wordsList = new ArrayList<String>(Arrays.asList("ABBREVIATIONS","ABNORMALITIES","ABOMINATIONS", "BRANDISHING","BREADWINNERS","BREATHLESSNESS","COEFFICIENTS","COLLABORATIVELY","CROSSCHECKING","DIMINISHABLE","DAUGHTER", "DAZZLE","ELIGIBLE","EMACIATED","EMBODY","FEEDBACK","FELLOWSHIPS","FIDGETING","GIBBERISH","GIGABYTES","GHOSTS","HALLUCINATION","HAMSTRINGS","HANDKERCHIEF","IMPLODED","INACCURACY","INADMISSIBLE","JUSTIFICATION","JUDICIARIES","JUNKYARD","KNUCKLEDUSTER","KNOWLEDGEABLE","KNIGHTHOOD","LABORATORIES","LAMENTABLE","LAYABOUTS","MARKETABILITY","MARSUPIALS","MASQUERADING","NONCHALANCE","NORTHERNMOST","NUMEROLOGISTS","OBJECTIONABLE","OBSCURITIES","OPENMINDEDNESS","PARTICULARITY","PATERNALISM","PENTATHLON","QUADRANGULAR","QUANTITATIVELY","QUINQUENNIAL","QUICKWITTED","RACECOURSES","RADIOGALAXIES","RAGSTORICHES","SCALABILITY","SCHIZOPHRENICS","SEDIMENTATION","TERCENTENARY","THANKSGIVING","THEORETICALLY","UNFAIR","UNFATHOMABLE","UNHESITATINGLY","VACCINATING","VEGETATIONAL","VENTRILOQUISTS","WALKIETALKIE","WEIGHTLESSNESS","WELLINTENTIONED","XYLOPHONIST","XENOPHOBIA","XRAYED","YEARNINGLY","YIELDING", "YESTERDAY","ZIGZAG","ZOOLOGISTS","ZILLIONS")); 

	Scanner scan = new Scanner(System.in);

	public hangMan() {
		chooseWord();
	}
	
	
	private void chooseWord() {	
		//The chosen word for the game is obtained by generating a random number and then using that number as an index to obtain the word in the ArrayList object.
		//Once the chosen word is pulled, it is reduced to all lowercase and the 'guessWord' array has the size of the length of the chosen word.
		//'guessWord' is also filled with underscores and the chosenWord is removed from the object to eliminate repitition and the game will commence.
		
		int randomNumber = (int) (Math.random() * wordsList.size());

		chosenWord = wordsList.get(randomNumber).toLowerCase();

		guessWord = new char[chosenWord.length()];

		Arrays.fill(guessWord, '_');
		
		wordsList.remove(chosenWord);
		
		if(wordsList.size() == 1) {
			System.out.println("Last word!");
		}
		start();
	}

	private void start() {

		System.out.println("\n" + "Welcome to Hang man!");
		
		tries = 0;
		inputs = "";
		
		//tries and inputs variables are reset in case the player wants to play the game again.

		while (tries < 6) {
			
			//The main contents of the game take place in the while loop.

			System.out.println("\n" + new String(guessWord).replaceAll(".(?=.)", "$0 "));
			
			//The char array: "guessWord" is converted into a string and separated into spaces for displayability.

			if (checkStatus(guessWord) == true) {
				break;
			}
			//If the hangman game is won at anytime, then the loop is broken

			System.out.print("\n" + "Answer: ");

			userGuess = scan.next().charAt(0);
			
			while(!checkAnswer(userGuess)) {
				System.out.println("Invalid answer; Try again:");
				userGuess = scan.next().charAt(0);
			}
			
			//A check is performed on the players guess by passing it to a function: checkAnswer()

			if (inputs.indexOf(userGuess) == -1) {
				//The player's guesses are stored into a string variable. If the guess is not in the variable, we append it to the string.
				//If the guess was already made, then they are notified of it. 
				
				inputs = inputs + " " + userGuess;
			} else {
				System.out.println("You've already made that guess!");
			}

			if (findAllCharacters(userGuess)) {
				// The players guess is also passed into another function. If a match is found, we continue on.
				// If no match is found, the tries variable is incremented and drawMan() is called, which outputs portions of a stick man.
				
				continue;
			} else {
				
				tries++;
				drawMan();	
			}
		}
		
		System.out.println(generateMessage());
		
		//A message is displayed based on whether or not the player has won the game.
		
		try {
			Thread.sleep(1200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		System.out.print("\n" + "Would you like to continue? (Y/N): ");
		userGuess = scan.next().charAt(0);
		
		while(!checkAnswer(userGuess)) {
			System.out.println("Invalid answer; Try again:");
			userGuess = scan.next().charAt(0);
		}
		
		userGuess = Character.toUpperCase(userGuess);
		
		//The player is offered another chance to play hangman, and their input is checked to see if its valid. 
		
		if(userGuess == 'Y') {
		
			chooseWord();
			
		} else if(userGuess == 'N') {
			System.out.println("\n"+ "Thank you for playing!!");
			System.exit(1);
		}
		
		//If player answers Y, 'chooseWord()' is called to set another random word in the array list object
		
	}

	private boolean findAllCharacters(char userGuess) {
		
		//This function checks whether or not the players guess is in the word
		
		if (chosenWord.indexOf(userGuess) > -1 && chosenWord.indexOf(userGuess) == chosenWord.lastIndexOf(userGuess)) {

			//The functions 'indexOf()' and 'lastIndexOf()' are built in functions that returns the first and last numerical positions of the matching character in the string.
			//If a -1 is returned, that means it does not exist.
			//In this case, the if statement tests if 'indexOf(userGuess)' returns a positive value and that same value is equal to the number 'lastIndexOf()' returns
			
			guessWord[chosenWord.indexOf(userGuess)] = userGuess;

			return true;

		} else if (chosenWord.lastIndexOf(userGuess) > -1 && chosenWord.indexOf(userGuess) > -1
				&& chosenWord.indexOf(userGuess) != chosenWord.lastIndexOf(userGuess)) {

			// If both "lastIndexOf()" and "indexOf()" return a non-negative number and are not equal to each other,
			//then the possibility of 2 or more matching characters in the chosen word are considered.

			guessWord[chosenWord.indexOf(userGuess)] = userGuess;
			guessWord[chosenWord.lastIndexOf(userGuess)] = userGuess;

			for (int i = chosenWord.indexOf(userGuess) + 1; i < chosenWord.lastIndexOf(userGuess); i++) {
			//Finding more occurrences of the guess in the chosen word, requires a for loop.
			//In the for loop, i is equal to the value 'indexOf()' returns and adding one to it. i must also end at the value of the last occurrence of the guess in that word.
				
				if (userGuess == chosenWord.charAt(i)) {
					guessWord[i] = userGuess;
				}
			}
			return true;
		}
		return false;
	}

	private boolean checkStatus(char[] wordArr) {

		// This function checks whether or not an underscore exists in the char array.

		if (new String(wordArr).indexOf('_') > -1) {
			return false;
		}
		return true;
	}
	
	private boolean checkAnswer(char guess) {
		
		//This function checks the validity of the players answer by using regular expressions.
		
		if(checkStatus(guessWord) == true || (checkStatus(guessWord) == false && tries == 6)) {
	    	if(Character.toString(guess).matches("^[ynYN]*$")) { 
	    		
	    		//This portion of code can only be reached when the game is over and is only meant to see if the players response to another game of hangman 
	    		//is 'y' or 'n' in lower or uppercase.
	    		return true;
	    	}
		}
		
		else if (Character.toString(guess).matches("^[a-zA-Z]*$") && checkStatus(guessWord) == false) {
			//This block of code will check the players guess to see if it contains any alphabetical characters from a to z in lower or upper case.
	         return true;
	    }
		
		return false;
	}
		
	

	private void drawMan() {

		// Function utilizes a switch statement based on the "tries" variable. Each time tries is incremented, portions of the stick man is displayed.

		switch (tries) {

		case 1:
			System.out.println("\n"+ "    O");
			break;

		case 2:
			System.out.println("\n"+"    O" + "\n" + "    |");
			break;

		case 3:
			System.out.println("\n"+"    O" + "\n" + "   /|");
			break;

		case 4:
			System.out.println("\n"+"    O" + "\n" + "   /|\\");
			break;

		case 5:
			System.out.println("\n"+"    O" + "\n" + "   /|\\" + "\n" + "   /");
			break;

		case 6:
			System.out.println("\n"+"    O" + "\n" + "   /|\\" + "\n" + "   / \\" + "\n");
			break;

		default:
			System.out.println(" ");
		}
	}

	public String generateMessage() {
		
		//If the player has won, they get a positive message along with the guesses they made, games played, and how much of those games are wins and losses.
		if (checkStatus(guessWord) == true) {
			win++;
			
			return "\n" + "Congratulations, You've won!! " + "\n" + "Your guesses: " + inputs.trim() + 
					"\n\n" + "Games played: " + Integer.valueOf(win+loss) +  "\n" + "Wins: " + win + "\n"+ "Loss: " + loss;
		} else {
			//If the player has lost, the word will be displayed to them along with the guesses they made and how many games they played.
			loss++;
			
			return "\n" + "Sorry, you did not win. The word was " + chosenWord.toUpperCase() + ". " + "\n" + "Your guesses: " + inputs.trim() + "\n\n" + 
			"Games played: " + Integer.valueOf(win+loss) + "\n" + "Wins: " + win + "\n" + "Loss: " + loss;
		}
	}
}
