//Name: Ekachai Suriyakriengkri
//USC NetID: suriyakr@usc.edu
//CS 455 PA2
//Spring 2018
import java.util.ArrayList;
import java.util.Scanner;
/**
   The program has 3 mode: 
   default (randomly generating), randomly generate initial configuration by running empty SolitaireBoard constructor
   -u: take input from user and validate the input in validateInput method
   return false and prompt the user to input the correct input again when validation fail
   return true, then running SolitaireBoard object with input in arrraylist.
   -s which show output step by step, continue when user press return.
   Loop playing the game until meet the final configuration requirement and print "Done!" when the game finish.
 */

public class BulgarianSolitaireSimulator {

    public static void main(String[] args) {

        Scanner in = new Scanner (System.in);
        SolitaireBoard game;
        int numPlay = 0;
        ArrayList<Integer> piles = new ArrayList<Integer>();
        boolean singleStep = false;
        boolean userConfig = false;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-u")) {
                userConfig = true;
            }
            else if (args[i].equals("-s")) {
                singleStep = true;
            }
        }

        // -u is enabled, Program get input from user instead of random generating
        if (userConfig)
        {
            System.out.println("Number of total cards is 45");
            System.out.println("You will be entering the initial configuration of the cards (i.e., how many in each pile).");
            System.out.println("Please enter a space-separated list of positive integers followed by newline:");
            String userInput = in.nextLine();
            Scanner sc = new Scanner(userInput);

            // Prompt user an error and re-ask the user to prompt the input again, Do until input meet the
            // requirement in validateInput method
            while (!validateInput(userInput))
            {
                System.out.println("ERROR: Each pile must have at least one card and the total number of cards must be 45");
                System.out.println("Please enter a space-separated list of positive integers followed by newline:");
                userInput = in.nextLine();
                sc = new Scanner(userInput);
            }
            // Put the validated input in Arraylist
            while (sc.hasNextInt())
            {
                int input = sc.nextInt();
                piles.add(input);
            }
            game = new SolitaireBoard(piles);
        }
        // Call solitaire with randomly generate initial configuration
        else
            game = new SolitaireBoard();

        System.out.println("Initial configuration: " + game.configString());
        // Play until game is met with final configuration
        // See isDone method in SolitaireBoard for more information
        while (!game.isDone())
        {
            game.playRound();
            System.out.println("["+ ++numPlay + "] Current configuration: " + game.configString());

            // -s is enabled, Program will ask user to press return before start next round
            if (singleStep)
            {
                System.out.print("<Type return to continue>");
                in.nextLine();
            }
        }
        System.out.println("Done!");
    }

    /* User input validation
       When receive input from user, validating input by
       - Input must contain only positive integer (no string, character, negative integer, float, double)
       - The sum of input (separated by space) must equal to 45
     */
    private static boolean validateInput(String stringInput) {

        int validateSumCard = 0;
        Scanner scValidation = new Scanner(stringInput);
        // Regular expression true when string(remove front and end space) is contains number 0...9 
        // and white space between string
        if (stringInput.trim().matches("[0-9 ]+"))
        {
            while (scValidation.hasNextInt())
            {
                int input = scValidation.nextInt();
                if (input < SolitaireBoard.MIN_CARDS)
                    return false;
                validateSumCard = validateSumCard + input;
            }
            if (validateSumCard != SolitaireBoard.CARD_TOTAL)
                return false;
            return true;
        }

        return false;
    }
}
