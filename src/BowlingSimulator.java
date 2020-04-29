import FrameMediator.ScoreMediator;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is intended to run a bowling simulation using either random
 * numbers or numbers put in by the user.
 * @author Trey Pachucki ttp2542@g.rit.edu
 */
public class BowlingSimulator {

    private ArrayList<String> playerNames;
    private ScoreMediator scores;
    private int checkThrow;

    /**
     * Constructor for the bowlingSimulator object.
     */
    public BowlingSimulator(){
        this.scores = new ScoreMediator();
        this.checkThrow = -1;
        this.playerNames = new ArrayList<>();
    }

    /**
     * A function to add a player to the player arrayList.
     * @param playerName: The name of the player to be added.
     */
    public void addPlayer(String playerName){
        this.playerNames.add(playerName);
        scores.addPlayer(playerName);
    }

    /**
     * This method handles the throws
     * @param pinsDown the number of pins knocked down
     */
    public void handleThrow(int pinsDown){
        if(pinsDown > 10){
            System.out.println("Please input a proper throw value");
        }
        else if(checkThrow == -1 && pinsDown != 10){
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
            System.out.print("Input Pins Knocked Down or type 'quit': ");
            String pins = in.nextLine();
            pins = pins.toLowerCase();

            if(pins.length() > 0 && pins.charAt(0) == 'q'){
                sentinel = false;
            }else{
                try{
                    int pinsDown = Integer.parseInt(pins);
                    handleThrow(pinsDown);
                    printCurrentScores();
                    String lastBowler = playerNames.get(playerNames.size() - 1);
                    if(!scores.canThrowAgain(lastBowler, 9)){
                        System.out.println("Type 'restart' to reset the game" +
                                " or 'quit' to quit!");

                        while(true) {
                            String command = in.nextLine();
                            command = command.toLowerCase();
                            if (command.charAt(0) == 'q') {
                                sentinel = false;
                                break;
                            } else if (command.charAt(0) == 'r') {
                                scores.resetGame();
                                break;
                            }else{
                                System.out.println("Type 'restart' to reset the game" +
                                        " or 'quit' to quit!");
                            }
                        }
                    }
                }catch(NumberFormatException ne){
                    System.out.println("Not a valid number or command");
                }
            }
        }
    }

    /**
     * Prints the current standings in the game
     */
    public void printCurrentScores(){
        for(String playerName: playerNames){
            printOneScore(playerName);
        }
    }

    /**
     * Prints out the scores for one player
     * @param playerName: The name of the player who's scores are being printed
     */
    public void printOneScore(String playerName){
        printLine("_");
        String[] frameThrows = scores.getScoreString(playerName);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(playerName);
        for(int i = 0; i < 10 - playerName.length(); i++){
            stringBuilder.append(" ");
        }
        stringBuilder.append("|");
        for(int i = 0; i < frameThrows.length; i++){
            if(frameThrows[i].equals("")){
                stringBuilder.append("   |");
            }else {
                String score = " " + frameThrows[i] + " |";
                stringBuilder.append(score);
            }
        }
        System.out.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        //appending 10 spaces for consistency with the scores
        stringBuilder.append("          |");

        int[] frameScores = scores.getFramePoints(playerName);
        for(int i = 0; i < 9; i++){
            stringBuilder.append(makeScoreString(frameScores[i], false));
        }
        stringBuilder.append(makeScoreString(frameScores[9], true));
        System.out.println(stringBuilder.toString());
        printLine("‾");
    }

    /**
     * Makes a score string for one specific throw
     * @param score: The value of the throw
     * @param frameTen: Whether or not its the tenth frame
     * @return The score string for a single frame
     */
    public String makeScoreString(int score, boolean frameTen){
        String scoreString = "";
        if(score == 0){
            scoreString = "       ";
        }
        else if(score < 10) {
            scoreString = "   " + score + "   ";
        }else if(score < 100){
            scoreString = "  " + score + "   ";
        }else{
            scoreString = "  " + score + "  ";
        }

        if(frameTen){
            scoreString = "  " + scoreString + "  ";
        }

        scoreString = scoreString + "|";
        return scoreString;
    }

    /**
     * Prints a line for the top or bottom of the frames
     * @param character: The character to print a line of (where the frame
     *                 display is)
     */
    private void printLine(String character){
        System.out.print("          ");
        for(int i = 0; i < 85; i++){
            System.out.print(character);
        }
        System.out.println();
    }

    /**
     * The main function which runs the bowling simulator.
     * @param args The string arguments, ignored
     */
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);
        boolean sentinel = true;
        BowlingSimulator sim = new BowlingSimulator();
        while(sentinel) {
            System.out.println("Add players now or type " +
                    "'done' if all players are added (max 10 characters)");
            String playerName = in.nextLine();
            playerName = playerName.replaceAll("\n", "");

            if(playerName.toLowerCase().equals("done")){
                sentinel = false;
            }else if(playerName.length() > 10){
                System.out.println("The maximum length for a player name is 10" +
                        "characters");
            }else{
                sim.addPlayer(playerName);
            }

        }

        sim.numbersInput(in);
        //In the future I'd like to add simulated bowling games, code for then
//        System.out.println("Would you like to input you're own values? (y/n)");
//        String response = in.nextLine();
//        response = response.toLowerCase();
//        if(response.charAt(0) == 'y'){
//            sim.numbersInput(in);
//        }
    }

}
