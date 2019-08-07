//Combat pose component
//Created by James Vanderhyde, 1 August 2019

package us.vanderhyde.wtfg;

public class CombatPoseComponent
{
    final CombatSystem.Pose pose;
    int timeLeft;
    
    CombatPoseComponent(CombatSystem.Pose pose)
    {
        this.pose = pose;
        timeLeft = pose.duration;
    }
    
}
