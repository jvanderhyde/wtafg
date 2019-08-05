//Combat pose component
//Created by James Vanderhyde, 1 August 2019

package us.vanderhyde.wtfg;

public class CombatPoseComponent
{
    final CombatSystem.Pose pose;
    int timeLeft;
    boolean left, right, attack, flip;
    
    CombatPoseComponent(CombatSystem.Pose pose)
    {
        this.pose = pose;
        timeLeft = pose.duration;
    }
    
    public void setInput(boolean left, boolean right, boolean attack, boolean flip)
    {
        this.left = left;
        this.right = right;
        this.attack = attack;
        this.flip = flip;
    }
    
}
