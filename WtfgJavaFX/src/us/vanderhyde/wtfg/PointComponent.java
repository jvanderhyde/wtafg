//Point scored
//Created by James Vanderhyde, 19 May 2021

package us.vanderhyde.wtfg;

public class PointComponent
{
    final int numPointsScored;

    public PointComponent(int numPointsScored)
    {
        this.numPointsScored = numPointsScored;
    }

    @Override
    public String toString()
    {
        return "points:"+numPointsScored;
    }
    
}
