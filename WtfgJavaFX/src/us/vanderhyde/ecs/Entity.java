//Entity-component-system architecture, Entity class
//Created by James Vanderhyde, 5 July 2019

package us.vanderhyde.ecs;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Entity<Component>
{
    final UUID id = UUID.randomUUID();
    final Map<Component, Object> components = new HashMap<>();
    
    @Override
    public String toString()
    {
        String s = "{ ";
        for (Object c:components.values())
            s = s.concat(c.toString()+" ");
        return s.concat("}");
    }
}
