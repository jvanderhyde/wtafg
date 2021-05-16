//Movement input component
//Created by James Vanderhyde, 15 May 2021

package us.vanderhyde.wtfg;

public class MovementInputComponent
{
    final boolean left, right;
    
    MovementInputComponent(boolean left, boolean right)
    {
        this.left = left;
        this.right = right;
    }
    
    @Override
    public String toString()
    {
        return "move:"+(left?"left":"")+(right?"right":"");
    }
    
}
