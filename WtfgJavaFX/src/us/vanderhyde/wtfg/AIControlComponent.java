//Component for AI control
//Created by James Vanderhyde, 24 July 2019

package us.vanderhyde.wtfg;

public class AIControlComponent
{
    private final int moveTime = 10, stopTime = 15;
    public static enum State { movingLeft, movingRight, stopped };
    private State state;
    private int timeLeftInState;

    public AIControlComponent()
    {
        this.state = State.stopped;
        this.timeLeftInState = stopTime;
    }
    
    public State getState()
    {
        return this.state;
    }
    
    public void update()
    {
        if (timeLeftInState > 0)
            timeLeftInState--;
        else
        {
            switch (state)
            {
                case movingLeft:
                    state = State.movingRight;
                    timeLeftInState = moveTime;
                    break;
                case movingRight:
                    state = State.stopped;
                    timeLeftInState = stopTime;
                    break;
                case stopped:
                    state = State.movingLeft;
                    timeLeftInState = moveTime;
                    break;
                default:
            }
        }
    }
    
    @Override
    public String toString()
    {
        return "AIState:"+state+":"+timeLeftInState;
    }
}
