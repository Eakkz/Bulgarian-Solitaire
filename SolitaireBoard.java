// Name: Ekachai Suriyakriengkri
// USC NetID: suriyakr@usc.edu
// CS 455 PA2
// Spring 2018

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/*
   class SolitaireBoard
   The board for Bulgarian Solitaire.  You can change what the total number of cards is for the game
   by changing NUM_FINAL_PILES, below.  Don't change CARD_TOTAL directly, because there are only some values
   for CARD_TOTAL that result in a game that terminates.
   (See comments below next to named constant declarations for more details on this.)
 */


public class SolitaireBoard {

    public static final int NUM_FINAL_PILES = 9;
    // number of piles in a final configuration
    // (note: if NUM_FINAL_PILES is 9, then CARD_TOTAL below will be 45)

    public static final int CARD_TOTAL = NUM_FINAL_PILES * (NUM_FINAL_PILES + 1) / 2;

    public static final int MAX_CARDS = 45;
    // bulgarian solitaire only terminates if CARD_TOTAL is a triangular number.
    // see: http://en.wikipedia.org/wiki/Bulgarian_solitaire for more details
    // the above formula is the closed form for 1 + 2 + 3 + . . . + NUM_FINAL_PILES
    public static final int MIN_CARDS = 1; //minimum card in each pile must have at least 1

    public static final int MAX_FINAL_EACH_PILES = 9;
    public static final int MIN_FINAL_EACH_PILES = 1;
    // number of cards in each pile in a final configuration must be between 1 to 9
    // Note to students: you may not use an ArrayList -- see assgt description for details.


    /**
      Representation invariant:

      <put rep. invar. comment here>
      Number of piles must more than 0, but less than or equal to 45
      Sum of card in each piles must equal 45
      Each piles must have at least 1 card
     */

    // <add instance variables here>
    private int numPiles;
    private int[] piles;
    Random rand;

    /**
     Creates a solitaire board with the configuration specified in piles.
     piles has the number of cards in the first pile, then the number of cards in the second pile, etc.
     PRE: piles contains a sequence of positive numbers that sum to SolitaireBoard.CARD_TOTAL
     Take arraylist and filled in array until the end of arraylist
     */
    public SolitaireBoard(ArrayList<Integer> piles) {

        this.piles = new int[MAX_CARDS];
        this.numPiles = piles.size();

        for (int i = 0; i < piles.size(); i++)
        {
            this.piles[i] = piles.get(i) ;

        }
        assert isValidSolitaireBoard();
    }


    /**
      Creates a solitaire board with a random initial configuration.
      By calling generateInitial method
     */
    public SolitaireBoard() {

        generateInitial();
        assert isValidSolitaireBoard();
    }


    /**
      Plays one round of Bulgarian solitaire.  Updates the configuration according to the rules
      of Bulgarian solitaire: Takes one card from each pile, and puts them all together in a new pile.
      The old piles that are left will be in the same relative order as before, 
      and the new pile will be at the end. After that shifting non-zero piles to the left of array then replace
      that index with 0 by using countZero to track the starting location to replace 0 in array.
      Also, recalculate the size of pile again with reSizePiles (the value the count how many zero we replace after shifting)
     */
    public void playRound() {

        //Declaration local variables
        int countZero = 0;
        int reSizePiles = 0;

        // Take 1 card from each piles (Minus 1 in each array from first pile (index 0) to numPiles (index numPiles-1))
        for (int i = 0; i < this.numPiles; i++)
        {
            this.piles[i] = this.piles[i] - 1;
        }

        // Form a new pile after last numPiles (Amount of piles [numPiles] increase by 1)
        this.piles[this.numPiles] = this.numPiles;
        this.numPiles++;       

        // Move all 0 from index 0 to numPiles-1 to the back of array
        for (int i = 0; i < this.numPiles; i++)
        {
            if (this.piles[i] != 0)
            {
                this.piles[countZero] = this.piles[i];
                countZero++;
            }
        }

        // Replace zero to shifted card
        while (countZero < this.numPiles)
        {
            this.piles[countZero] = 0;
            countZero++;   
            reSizePiles++;
        }

        //Recalculate the size of piles after play one round
        this.numPiles = this.numPiles - reSizePiles;
        assert isValidSolitaireBoard();
    }

    /**
      Returns true iff the current board is at the end of the game.  That is, there are NUM_FINAL_PILES
      piles that are of sizes 1, 2, 3, . . . , NUM_FINAL_PILES, in any order. Also, each pile must has unique number between
      MIN_FINAL_EACH_PILES (1) to MAX_FINAL_EACH_PILES (9) Sums of card in final piles must equal CARD_TOTAL
     */

    public boolean isDone() {

        int  sumCard = 0;
        if (this.numPiles != NUM_FINAL_PILES)
            return false;

        for (int i = 0; i < this.numPiles; i++)
        {
            if (this.piles[i] > MAX_FINAL_EACH_PILES || this.piles[i] < MIN_FINAL_EACH_PILES)
                return false;
            sumCard = sumCard + this.piles[i];

            //Check duplicate number
            for (int j = i+1; j < this.numPiles; j++)
            {
                if (this.piles[i] == this.piles[j])
                    return false;
            }
        }
        if (sumCard != CARD_TOTAL)
            return false;

        assert isValidSolitaireBoard();
        return true;
    }


    /**
      Returns current board configuration as a string with the format of
      a space-separated list of numbers with no leading or trailing spaces.
      The numbers represent the number of cards in each non-empty pile.
     */
    public String configString() {

        // Declare empty string for  a space-separated list of numbers
        String configString = "";
        for (int i = 0; i < this.numPiles-1; i++)
        {
            // Concatenating string from first pile to second last pile with space between pile
            configString = configString + this.piles[i] + " ";
        }

        // Concatenating last string without space at the end
        configString = configString + this.piles[this.numPiles-1];
        assert isValidSolitaireBoard();
        return configString;
    }


    /**
      Returns true iff the solitaire board data is in a valid state
      (See representation invariant comment for more details.)
      Check CARD_TOTAL by using sum of every cards in each pile (sumCard variable)
      valid if sumCard equal CARD_TOTAL.
      For the assertion, call every public method for error checking.
     */
    private boolean isValidSolitaireBoard() {

        int sumCard = 0;

        if (this.numPiles >= MAX_CARDS && this.numPiles < MIN_CARDS)
            return false;

        for (int i = 0; i < this.numPiles; i++)
        {
            if (this.piles[i] < MIN_CARDS)
                return false;
            sumCard = sumCard + this.piles[i];
        }

        if (sumCard != CARD_TOTAL)
            return false;

        return true;

    }

    // <add any additional private methods here>
    
    /*
        Generating an initial configuration
        First, randomly generate a number of piles in the range of 1 to 45
        then randomly fill each pile with at least one card.
        The sum of each pile must equal to 45.
        Cards is random by subtract number of pile from total cards and to each pile.
        Repeat until run out of cards.
    */
     
    private void generateInitial() {
        
        int unusedCard = MAX_CARDS;

        rand = new Random();
        this.numPiles = rand.nextInt(MAX_CARDS - 1) + 1;

        this.piles = new int[MAX_CARDS+1];
        for (int i = 0; i < numPiles - 1; i++)
        {
            // Case that random number of pile to MAX_CARDS (45)
            // Each pile but get only 1 card
            if (this.numPiles == MAX_CARDS)
            {
                this.piles[i] = 1;
                unusedCard--;
            }
            else
            {
                this.piles[i] = rand.nextInt((unusedCard - numPiles + i)) + 1;
                unusedCard = unusedCard - this.piles[i];
            }
        }
        this.piles[numPiles - 1] = unusedCard;
    }
}
