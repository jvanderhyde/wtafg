//Entity-component-system architecture, Game class
//Created by James Vanderhyde, 5 July 2019

package us.vanderhyde.ecs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Game<ComponentType>
{
    private final Map<UUID,Entity> entities = new HashMap<>();
    private final Map<ComponentType,Map<Entity,Object>> components = new HashMap<>();
    
    public void addEntity(Entity e)
    {
        entities.put(e.id, e);
    }
    
    public void addComponent(Entity e, ComponentType c, Object data)
    {
        Map<Entity,Object> m = this.components.get(c);
        if (m==null)
        {
            this.components.put(c, new HashMap<>());
            m = this.components.get(c);
        }
        m.put(e, data);
    }
    
    public void removeComonent(Entity e, ComponentType c)
    {
        this.components.get(c).remove(e);
    }
    
    public Object getComponent(Entity e, ComponentType c)
    {
        return this.components.get(c).get(e);
    }

    public Collection<Entity> getEntities()
    {
        return entities.values();
    }
    
    public Collection<Map.Entry<Entity,Object>> getEntities(ComponentType c)
    {
        return this.components.get(c).entrySet();
    }
    
    @Override
    public String toString()
    {
        String s = "[ ";
        for (Entity e:entities.values())
            for (ComponentType c:components.keySet())
            {
                Object o = components.get(c).get(e);
                if (o != null)
                    s = s.concat(o.toString()+" ");
            }
        return s.concat("]");
    }
}
