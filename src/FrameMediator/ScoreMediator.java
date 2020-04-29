package FrameMediator;

import FrameState.Frame;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is responsible for managing scoring.
 */
public class ScoreMediator {
    private HashMap<String, Frame[]> frames;
    private ArrayList<String> bowlerNames;
    private int currentBowlerNum;
    private int frameNumber;

    /**
     * Constructor for the score mediator
     */
    public ScoreMediator(){
        this.bowlerNames = new ArrayList<>();
        this.frames = new HashMap<>();
        this.frameNumber = 1;
    }

    /**
     * Adds a player to the lane to start keeping track
     * of their bowling scores.
     *
     * @param bowlerName the bowler to be added
     */
    public void addPlayer(String bowlerName){
        Frame[] newScores = makeNewFrames();
        this.bowlerNames.add(bowlerName);
        this.frames.put(bowlerName, newScores);
    }

    /**
     * Returns if a player can throw again for a given frame.
     *
     * @param bowlerName Bowler to check
     * @param frame Frame to check
     * @return true if bowler can throw again false otherwise
     */
    public boolean canThrowAgain(String bowlerName, int frame){
        Frame currentFrame = this.frames.get(bowlerName)[frame];
        return !currentFrame.getFinished();
    }

    /**
     * Resets the scores for all the bowlers in the lane.
     */
    public void resetGame(){
        for(String bowlerName: frames.keySet()){
            this.frames.put(bowlerName, makeNewFrames());
        }
        frameNumber = 1;
    }

    /**
     * Handles a throw made by a bowler
     *
     * @param ballThrown Ball that they threw
     */
    public void addThrow(int ballThrown){
        String bowlerName = bowlerNames.get(currentBowlerNum);
        //get the frames for a specific bowler
        Frame[] throwerFrames = this.frames.get(bowlerName);
        for(int i = 0; i < 10; i++){
            //find the most recent unfinished frame for a bowler
            if(!throwerFrames[i].getFinished()){
                for(int j = 0; j <= i; j++){
                    //let all past frames know about the last ball (including
                    //the most recent one to be needed)
                    boolean nextBowler = throwerFrames[j].addBall(ballThrown);
                    if(j == i && nextBowler){
                        updateBowlerNum();
                    }
                }
                break;
            }
        }
    }

    /**
     * Gets an array of all the points for each frame.
     *
     * @param bowlerName Bowler to get frame points for
     * @return An array where each value is the score for that frame.
     */
    public int[] getFramePoints(String bowlerName){
        Frame[] bowlerFrames = frames.get(bowlerName);
        int[] framePoints = new int[10];
        int total = 0;
        for(int i = 0; i < frameNumber; i++){
            total += bowlerFrames[i].getScore();
            framePoints[i] = total;
        }
        return framePoints;
    }

    /**
     * This returns the score strings for 1 bowler
     * @param bowlerName: The bowler who's store string is being gotten
     * @return The score string
     */
    public String[] getScoreString(String bowlerName){
        Frame[] bowlerFrames = frames.get(bowlerName);
        String[] scoreString = new String[21];
        for(int i = 0; i < bowlerFrames.length; i++){
            String[] currentStrings = bowlerFrames[i].getStrings();
            scoreString[2 * i] = currentStrings[0];
            scoreString[(2 * i) + 1] = currentStrings[1];
            //Add the last value on the 10th frame
            if(i == bowlerFrames.length - 1){
                scoreString[20] = currentStrings[2];
            }
        }

        return scoreString;
    }

    /**
     * Function to update the current bowler number within the mediator
     */
    public void updateBowlerNum(){
        if(currentBowlerNum == bowlerNames.size() - 1){
            currentBowlerNum = 0;
            if(frameNumber < 10) {
                frameNumber++;
            }
        }else{
            currentBowlerNum++;
        }
    }

    /**
     * Makes new frames for a new game! (or reset game).
     * @return The new frame array
     */
    private Frame[] makeNewFrames(){
        Frame[] newScores = new Frame[10];
        for(int i = 0; i < 9; i++){
            newScores[i] = new Frame(false);
        }
        newScores[9] = new Frame(true);
        return newScores;
    }
}
