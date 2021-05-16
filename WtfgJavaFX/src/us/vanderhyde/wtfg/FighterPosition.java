//Component to keep track of the fighter's position in the arena
//Created by James Vanderhyde, 16 May 2021

package us.vanderhyde.wtfg;

public class FighterPosition
{
    final double x;

    public FighterPosition(double x)
    {
        this.x = x;
    }

    @Override
    public String toString()
    {
        return "FighterPosition{" + "x=" + x + '}';
    }
    
}
