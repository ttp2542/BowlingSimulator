package FrameState;

/**
 * The final state for a frame. If it gets here, the points are finalized for
 * that frame.
 * @author Trey Pachucki ttp2542@g.rit.edu
 */
public class PointsFinished implements FrameStatus {

    private final int[] scores;
    private final int totalScore;
    private final Frame frame;

    /**
     * Constructor for points finished, created by another state.
     * @param frame The frame who's status is updated
     * @param scores The finalized scores array
     */
    public PointsFinished(Frame frame, int[] scores, int totalScore){
        this.scores = scores;
        this.frame = frame;

        this.totalScore = totalScore;
    }

    /**
     * This returns the total score of the frame (as of time of calling)
     *
     * @return the score
     */
    @Override
    public int getScore() {
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
        return false;
    }

    /**
     * Returns the throws done in a frame.
     * @return Throws in int array form
     */
    @Override
    public int[] getThrows(){
        int[] ballThrows = new int[2];
        ballThrows[0] = scores[0];
        if(scores[0] == 10) {
            ballThrows[1] = -1;
        }else{
            ballThrows[1] = scores[1];
        }
        return ballThrows;
    }

    /**
     * This function returns a String array representation of the balls thrown
     * for a specific frame.
     * @return The String array
     */
    @Override
    public String[] getStrings(){
        String[] scoreString = new String[2];
        if(scores[0] == 10){
            scoreString[0] = "X";
            scoreString[1] = "";
        }else if(scores[0] + scores[1] == 10){
            scoreString[0] = Integer.toString(scores[0]);
            scoreString[1] = "/";
        }else{
            scoreString[0] = Integer.toString(scores[0]);
            scoreString[1] = Integer.toString(scores[1]);
        }
        return scoreString;
    }

}
