//Keeps track of the player's score
//Created by James Vanderhyde, 19 May 2021

package us.vanderhyde.wtfg;

public class ScoreComponent
{
    final int score;

    public ScoreComponent(int score)
    {
        this.score = score;
    }

    @Override
    public String toString()
    {
        return "score:"+score;
    }
    
}
