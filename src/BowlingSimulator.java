import FrameMediator.ScoreMediator;

import java.util.Scanner;

/**
 * This class is intended to run a bowling simulation using either random
 * numbers or numbers put in by the user.
 * @author Trey Pachucki ttp2542@g.rit.edu
 */
public class BowlingSimulator {

    private String[] playerNames;
    private ScoreMediator scores;
    private int checkThrow;

    /**
     * Constructor for the bowlingSimulator object.
     */
    public BowlingSimulator(){
        this.scores = new ScoreMediator();
        this.checkThrow = -1;
    }

    /**
     * Sets the player names on the bowling sim and ScoreMediator
     * @param playerNames The player names as a String array
     */
    public void setPlayerNames(String[] playerNames){
        this.playerNames = playerNames;
        for(int i = 0; i < playerNames.length; i++){
            scores.addPlayer(playerNames[i]);
        }
    }

    /**
     * The main function which runs the bowling simulator.
     * @param args The string arguments, ignored
     */
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.println("How many players? (input number)");
        boolean sentinel = true;
        String[] playerNames;
        BowlingSimulator sim = new BowlingSimulator();
        while(sentinel) {
            String playerNum = in.nextLine();
            int players = 0;
            try{
                players = Integer.parseInt(playerNum);
                playerNames = new String[players];
                sentinel = false;
                for(int i = 0; i < players; i++){
                    int nameNum = i + 1;
                    String playerName = "Player " + nameNum;
                    playerNames[i] = playerName;
                }
                sim.setPlayerNames(playerNames);

            }catch(Exception e){
                System.out.println(e.toString());
            }

        }
        System.out.println("Would you like to input you're own values? (y/n)");
        String response = in.nextLine();
        response = response.toLowerCase();
        if(response.charAt(0) == 'y'){
            sim.numbersInput(in);
        }
    }

    /**
     * This method handles the throws
     * @param pinsDown the number of pins knocked down
     */
    public void handleThrow(int pinsDown){
        if(pinsDown > 10){
            System.out.println("Please input a proper throw value");
        }
        else if(checkThrow == -1){
            checkThrow = pinsDown;
            scores.addThrow(pinsDown);
        }else if(pinsDown > 10 - checkThrow) {
            System.out.println("Please input a proper throw value");
        }else{
            scores.addThrow(pinsDown);
            checkThrow = -1;
        }
    }

    /**
     * This handles the mode logic for a numbers input mode of bowlingSim
     * @param in The scanner for in
     */
    public void numbersInput(Scanner in){
        boolean sentinel = true;
        while(sentinel) {
            System.out.println("Input Pins Knocked Down or type 'quit': ");
            String pins = in.nextLine();
            pins = pins.toLowerCase();
            if(pins.charAt(0) == 'q'){
                sentinel = false;
            }else{
                try{
                    int pinsDown = Integer.parseInt(pins);
                    handleThrow(pinsDown);
                    System.out.println(this.toString());
                }catch(NumberFormatException ne){
                    System.out.println("Not a valid number or command");
                }
            }
        }
    }


}
