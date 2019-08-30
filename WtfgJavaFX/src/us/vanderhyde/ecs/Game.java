//Entity-component-system architecture, Game class
//Created by James Vanderhyde, 5 July 2019

package us.vanderhyde.ecs;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Game
{
    private final Map<UUID,Entity> entities = new HashMap<>();
    private final Map<Class,Map<Entity,Object>> components = new HashMap<>();
    
    public void addEntity(Entity e)
    {
        entities.put(e.id, e);
    }
    
    public <T> void add(Entity e, T data)
    {
        Class c = data.getClass();
        Map<Entity,Object> m = this.components.get(c);
        if (m==null)
        {
            m = new HashMap<>();
            this.components.put(c, m);
        }
        m.put(e, data);
    }
    
    public void remove(Entity e, Class c)
    {
        this.components.get(c).remove(e);
    }
    
    @SuppressWarnings("unchecked") //The component is stored as an Object
    public <T> T get(Entity e, Class<T> c)
    {
        if (this.components.get(c) == null)
            return null;
        return (T)this.components.get(c).get(e);
    }

    public Collection<Entity> getEntities()
    {
        return entities.values();
    }
    
    //This is the fastest way to get all the components, but the code is ugly.
    public <T> Collection<Map.Entry<Entity,Object>> getEntityEntries(Class<T> c)
    {
        return this.components.get(c).entrySet();
    }

    public <T> Collection<Entity> getEntities(Class<T> c)
    {
        return this.components.get(c).keySet();
    }

    @Override
    public String toString()
    {
        String s = "[ ";
        for (Entity e:entities.values())
        {
            s = s.concat("{ ");
            for (Class c:components.keySet())
            {
                Object o = components.get(c).get(e);
                if (o != null)
                    s = s.concat(o.toString()+" ");
            }
            s = s.concat("} ");
        }
        return s.concat("]");
    }
}
