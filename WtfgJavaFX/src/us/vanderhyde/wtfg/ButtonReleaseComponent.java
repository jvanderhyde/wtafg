//Button release component for keeping track of input
//Created by James Vanderhyde, 30 August 2019

package us.vanderhyde.wtfg;

public class ButtonReleaseComponent
{
    final boolean attackReleased, flipReleased;

    public ButtonReleaseComponent(boolean attackReleased, boolean flipReleased)
    {
        this.attackReleased = attackReleased;
        this.flipReleased = flipReleased;
    }
    
    @Override
    public String toString()
    {
        return "ButtonRelease:"+(attackReleased?"attack":"")+(flipReleased?"flip":"");
    }
}
