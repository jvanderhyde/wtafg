//Component for the character's facing direction
//Created by James Vanderhyde, 15 May 2021

package us.vanderhyde.wtfg;

public class FacingDirection
{
    final MovementSystem.Facing direction;

    FacingDirection(MovementSystem.Facing direction)
    {
        this.direction = direction;
    }
    
    @Override
    public String toString()
    {
        return "facing:"+direction;
    }
}
