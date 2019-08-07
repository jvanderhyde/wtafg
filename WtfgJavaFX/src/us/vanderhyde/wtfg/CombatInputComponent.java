//Combat input component
//Created by James Vanderhyde, 7 August 2019

package us.vanderhyde.wtfg;

public class CombatInputComponent
{
    final boolean left, right, attack, flip;
    
    CombatInputComponent(boolean left, boolean right, boolean attack, boolean flip)
    {
        this.left = left;
        this.right = right;
        this.attack = attack;
        this.flip = flip;
    }
    
}
