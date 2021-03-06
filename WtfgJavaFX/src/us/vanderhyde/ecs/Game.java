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

    private Map<Class,Map<Entity,Object>> addedComponents = new HashMap<>();
    private Map<Class,Entity> removedComponents = new HashMap<>();
    
    public void addEntity(Entity e)
    {
        entities.put(e.id, e);
    }
    
    public void removeEntity(Entity e)
    {
        entities.remove(e.id);
    }
    
    public <T> void add(Entity e, T data)
    {
        Class c = data.getClass();
        Map<Entity,Object> m = this.addedComponents.get(c);
        if (m==null)
        {
            m = new HashMap<>();
            this.addedComponents.put(c, m);
        }
        m.put(e, data);
    }
    
    public void remove(Entity e, Class c)
    {
        this.removedComponents.put(c,e);
    }
    
    public void commit()
    {
        for (Map.Entry<Class,Map<Entity,Object>> entry : this.addedComponents.entrySet())
        {
            Class c = entry.getKey();
            Map<Entity,Object> addedComponentsOfThisType = entry.getValue();
            Map<Entity,Object> m = this.components.get(c);
            if (m != null)
                m.putAll(addedComponentsOfThisType);
            else
                this.components.put(c, addedComponentsOfThisType);            
        }
        for (Map.Entry<Class,Entity> entry : this.removedComponents.entrySet())
        {
            Class c = entry.getKey();
            Entity removedComponentOfThisType = entry.getValue();
            this.components.get(c).remove(removedComponentOfThisType);
        }
        this.addedComponents = new HashMap<>();
        this.removedComponents = new HashMap<>();
    }
    
    @SuppressWarnings("unchecked") //The component is stored as an Object
    public <T> T get(Entity e, Class<T> c)
    {
        Map<Entity,Object> m = this.components.get(c);
        if (m == null)
            return null;
        return (T)m.get(e);
    }

    public Collection<Entity> getEntities()
    {
        return entities.values();
    }
    
    //This is the fastest way to get all the components, but the code is ugly.
    public <T> Collection<Map.Entry<Entity,Object>> getEntityEntries(Class<T> c)
    {
        Map<Entity,Object> m = this.components.get(c);
        if (m != null)
            return m.entrySet();
        else return new java.util.TreeSet<>();
    }

    public <T> Collection<Entity> getEntities(Class<T> c)
    {
        Map<Entity,Object> m = this.components.get(c);
        if (m != null)
            return m.keySet();
        else return new java.util.TreeSet<>();
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
            s = s.concat("}\n  ");
        }
        return s.concat("]");
    }
}
