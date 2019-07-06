//Entity-component-system architecture, Game class
//Created by James Vanderhyde, 5 July 2019

package us.vanderhyde.ecs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Game<Component>
{
    private final Map<UUID,Entity> entities = new HashMap<>();
    
    public void addEntity(Entity e)
    {
        entities.put(e.id, e);
    }
    
    public void addComponent(Entity e, Component c, Object data)
    {
        e.components.put(c, data);
    }
    
    public Object getComponent(Entity e, Component c)
    {
        return e.components.get(c);
    }

    public Collection<Entity> getEntities()
    {
        return entities.values();
    }
    
    public Collection<Entity> getEntities(Component c)
    {
        ArrayList<Entity> r = new ArrayList<>();
        for (Entity e:entities.values())
            if (e.components.get(c) != null)
                r.add(e);
        return r;
        //This will be inefficient, to filter the whole list
        //every time. Better to cache them by component
        //and mark dirty when necessary.
    }
    
}
