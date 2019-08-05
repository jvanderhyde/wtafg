//Component for player control
//Created by James Vanderhyde, 24 July 2019

package us.vanderhyde.wtfg;

public class PlayerControlComponent
{
    public final String left, right, attack, flip;

    public PlayerControlComponent(String left, String right, String attack, String flip)
    {
        this.left = left;
        this.right = right;
        this.attack = attack;
        this.flip = flip;
    }
}
