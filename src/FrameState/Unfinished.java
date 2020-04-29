package FrameState;

/**
 * The class where a frame starts it's life. Nothing has been done (no ball thrown),
 * or only 1 ball has been thrown.
 * @author Trey Pachucki ttp2542@g.rit.edu
 */
public class Unfinished implements FrameStatus {

    private int[] scores;
    private final Frame frame;

    /**
     * Constructor for an unfinished frame (unstarted)
     * @param frame The frame who's status gets updated
     */
    public Unfinished(Frame frame){
        this.scores = new int[2];
        //Default values
        this.scores[0] = -1;
        this.scores[1] = -1;
        this.frame = frame;
    }

    /**
     * This returns the total score of the frame (as of time of calling)
     *
     * @return the score
     */
    @Override
    public int getScore() {
        int totalScore = 0;
        for(int i = 0; i < 2; i++){
            if(scores[i] != -1) {
                totalScore += scores[i];
            }
        }

        return totalScore;
    }

    /**
     * This adds a ball to the current score. Changes functionality based on
     * the current score.
     *
     * @param ballThrown The points scored by the ball thrown
     */
    @Override
    public boolean addThrow(int ballThrown) {
        if(scores[0] == -1){
            scores[0] = ballThrown;
            //if the ball hits 10 pins, it's a strike
            if(ballThrown == 10){
                frame.setStatus(new Strike(frame));
                frame.setFinished();
                return true;
            }
        }else{
            scores[1] = ballThrown;
            //checks for spare, otherwise frame is done
            if(this.getScore() == 10){
                frame.setStatus(new Spare(frame, scores[0], scores[1]));
                frame.setFinished();
                return true;
            }else{
                frame.setStatus(new PointsFinished(frame, scores, this.getScore()));
                frame.setFinished();
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the throws done in a frame.
     * @return Throws in int array form
     */
    @Override
    public int[] getThrows() {
        int[] score = new int[2];
        for (int i = 0; i < 2; i++) {
            score[i] = scores[i];
        }
        return score;
    }

    /**
     * This function returns a String array representation of the balls thrown
     * for a specific frame.
     * @return The String array
     */
    @Override
    public String[] getStrings(){
        String[] scoreString = new String[2];
        for(int i = 0; i < 2; i++){
            if(scores[i] != -1){
                scoreString[i] = Integer.toString(scores[i]);
            }else{
                scoreString[i] = "";
            }
        }
        return scoreString;
    }
}
