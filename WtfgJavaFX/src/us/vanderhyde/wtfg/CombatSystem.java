//World's tiniest fighting game combat system
//Created by James Vanderhyde, 6 July 2019

package us.vanderhyde.wtfg;

public class CombatSystem
{
    static enum Pose {
        block(0), recoverBlock(3), 
        prepareAttack(2), attack(3), recoverAttack(5),
        prepareThrow(1), doThrow(3), recoverThrow(5),
        thrown(10), blocked(6), attacked(10),
        walk(10), turn(10);
        public final int duration;//in frames
        Pose(int duration) {this.duration=duration;}
    };
    
    
}
