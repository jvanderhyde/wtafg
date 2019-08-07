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
    
    private void addComponent(Entity e, ComponentType c, Object data)
    {
        Map<Entity,Object> m = this.components.get(c);
        if (m==null)
        {
            m = new HashMap<>();
            this.components.put(c, m);
        }
        m.put(e, data);
    }
    
    private void removeComponent(Entity e, ComponentType c)
    {
        this.components.get(c).remove(e);
    }
    
    private Object getComponent(Entity e, ComponentType c)
    {
        return this.components.get(c).get(e);
    }

    public Collection<Entity> getEntities()
    {
        return entities.values();
    }
    
    //This is the fastest way to get all the components, but the code is ugly.
    public Collection<Map.Entry<Entity,Object>> getEntities(ComponentType c)
    {
        return this.components.get(c).entrySet();
    }

    //Used for type checking component data
    public class ComponentMapper<T>
    {
        ComponentType type;
        
        public ComponentMapper(ComponentType type, Class<T> componentClass)
        {
            this.type = type;
        }

        @SuppressWarnings("unchecked") //The component is stored as an Object
        public T get(Entity e)
        {
            return (T)getComponent(e,type);
        }
        
        public void add(Entity e, T data)
        {
            addComponent(e, type, data);
        }
        
        public void remove(Entity e)
        {
            removeComponent(e,type);
        }
        
        public Collection<Entity> getEntities()
        {
            return components.get(type).keySet();
        }
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
