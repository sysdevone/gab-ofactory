/*****************************************************************************************
 * 
 * Copyright 2014 Gregory Brown. All Rights Reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 * 
 ***************************************************************************************** 
 */

package org.gabsocial.ofactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import org.gabsocial.common.validator.Validate;


/**
 * <pre>
 * This class is an observable factory for creating and handling of child objects.
 * 
 * Before an child can be created and used, the factory needs to be
 * instantiated.   It is up to the caller to determine if the factory is a 
 * singleton for implementation purposes.
 * 
 * After the factory is created and in memory,  add an observer to the OFactory using 
 * the addObserver(Observer) or remove using removeObserver(Observer).
 * 
 * Call the create(xxx) method to create and add children and notify observers with a 
 * CREATE type event.
 * 
 * Once a child is created, a call to the factory's getChild(key) method will
 * return the child instance associated with the key. Only one child can be
 * associated with a specific key.  Calling the getChild(key) will notify observers 
 * with a GET type event.
 * 
 * A child will be maintained by the factory until that child is removed by
 * calling the factory's removeChild(key) method or the child's close() method.  
 * Calling the removeChild(key) or the child's close() will notify observers with a 
 * REMOVE type event that the child was removed from the OFactory.
 * 
 * The factory's close() method will remove all children and all observers and prevent calls to
 * other factory methods. Calling the close() method will notify all observers with a CLOSE type 
 * event before the OFactory removes the observers.
 * 
 * </pre>
 * 
 * @author Gregory Brown (sysdevone)
 */
public class OFactory extends Observable
{
    
    // P = parent
    // C = child
    // S = settings
    
    /**
     * OFactory Event that is sent to Observers when an child is created,
     * removed, get or when the OFactory is closed.
     * 
     * 
     * @author Gregory Brown (sysdevone)
     * 
     */
    public static class Event
    {
        public static enum Type
        {
            CLOSE, CREATE, GET, REMOVE;
        }
        
        private OFactoryChild    _child;
        private final Event.Type _eventType;
        private String           _key;
        
        /**
         * Constructor used when the key and child will be null.
         * 
         * @param eventType
         *            An enum <code>Type</code> that defined the type of event.
         */
        public Event(final Event.Type eventType)
        {
            this._eventType = eventType;
        }
        
        /**
         * Constructor used when the key and child will be null.
         * 
         * @param eventType
         *            An enum <code>Type</code> that defined the type of event.
         */
        public Event(final Event.Type eventType, final String key,
                final OFactoryChild child)
        {
            this(eventType);
            this._key = key;
            this._child = child;
        }
        
        public OFactoryChild getChild()
        {
            return (this._child);
        }
        
        public String getKey()
        {
            return (this._key);
        }
        
        public Event.Type getType()
        {
            return (this._eventType);
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString()
        {
            StringBuilder builder = new StringBuilder();
            builder.append("Event [_eventType=");
            builder.append(this._eventType);
            builder.append(", _key=");
            builder.append(this._key);
            builder.append(", _child=");
            builder.append(this._child);
            builder.append("]");
            return builder.toString();
        }
        
        
    }
    
    /**
     * Loads an OFactoryChild using the classname to get a new instances.
     * 
     * @param className
     *            A <code>String</code> value that is a fully qualified class
     *            name.
     * @return A subclass of <code>OFactoryChild</code>
     */
    protected final static <C extends OFactoryChild> C loadOFactoryChild(
            final String className)
    {
        Validate.isNullOrEmpty(
                className,
                "loadOFactoryChild() - the parameter 'className' should not be null or empty",
                OFactory.class);
        
        C child;
        try
        {
            child = (C) Class.forName(className).newInstance();
        }
        catch (final IllegalAccessException e)
        {
            throw (new OFactorySysException("Illegal access to class name - "
                    + className, e));
        }
        catch (final ClassNotFoundException e)
        {
            throw (new OFactorySysException(
                    "Unable to locate the class name - " + className, e));
        }
        catch (final InstantiationException e)
        {
            throw (new OFactorySysException(
                    "Unable to instantiate the class name - " + className, e));
        }
        return (child);
    }
    
    /**
     * A table of children.
     */
    private final Map<String, OFactoryChild> _children;
    
    /**
     * A flag to determine if the factory has been closed.
     */
    private boolean                          _isClosed;
    
    /*
     * initializes the children table.
     */
    public OFactory()
    {
        this._children = new HashMap<String, OFactoryChild>();
        this._isClosed = false;
    }
    
    /**
     * Add an observer to the OFactory. The observer will be notified of events.
     * 
     * @param observer
     *            An <code>Observer</code> instance that wants to be notified of
     *            events.
     */
    public synchronized void addObserver(final Observer observer)
    {
        if (this.isClosed())
        {
            throw (new OFactoryClosedException(
                    "This factory is closed and unable to process calls."));
        }
        else
        {
            super.addObserver(observer);
        }
    }
    
    /**
     * A method that adds to the OFactory child table.
     * 
     * Other classes that extend OFactory can override this method for unique
     * behavior.
     * 
     * @param key
     *            A <code>String</code> instance that is bound to the child and
     *            used for lookups.
     * 
     * @param child
     *            A <code>OFactoryChild</code> instance that will be added to
     *            the cache.
     */
    protected <C extends OFactoryChild> C addToChildTable(final String key,
            final C child)
    {
        assert ((key != null) && (key.trim().length() > 0)) : "addToChildTable() - the key was null, spaces or empty.";
        assert (child != null) : "addToChildTable() - the child was null.";
        this._children.put(key, child);
        return (child);
    }
    
    /**
     * Closes the factory, and removes then closes the children. Once closed,
     * calls to methods on the child should return a
     * <code>OFactoryClosedException</code> exception. This method calls the
     * OFactory.closeChild(key) method.
     * 
     * @throws OFactoryClosedException
     *             if this method is called and the OFactory is closed.
     */
    public void close()
    {
        if (this.isClosed())
        {
            throw (new OFactoryClosedException(
                    "The OFactory has been closed and may not be used."));
        }
        else
        {
            
            // close children.
            final Set<String> keys = this.getKeys();
            for (final String key : keys)
            {
                this.closeChild(key);
            }
            assert (this._children.size() == 0) : "The child table should be empty.";
            
            this._isClosed = true;
            
            this.notifyObservers(new Event(Event.Type.CLOSE));
            this.deleteObservers();
            assert (this.countObservers() == 0) : "The observable table should be empty.";
        }
    }
    
    /**
     * Closes the child associated with the key.
     * 
     * @param key
     *            The <code>String</code> key associated with a child.
     * 
     * @return Returns the child that was found and closed. If the key is not
     *         associated with child then null is returned.
     * 
     * @throws OFactoryClosedException
     *             if this method is called and the OFactory is closed.
     */
    public <C extends OFactoryChild> C closeChild(final String key)
    {
        if (this.isClosed())
        {
            throw (new OFactoryClosedException(
                    "This factory is closed and unable to process calls."));
        }
        else
        {
            Validate.isNullOrEmpty(key,
                    "closeChild() - The parameter 'key' was null or empty",
                    this.getClass());
            
            final C child = (C) this._children.remove(key);
            if (child != null)
            {
                child.closeWithoutRemove();
                assert (!this._children.containsKey(child.getKey())) : "The children table still contains the factory child when the factory child was closed.";
            }
            this.notifyObservers(new Event(Event.Type.REMOVE, key, child));
            return (child);
        }
    }
    
    /**
     * Returns a <code>boolean</code> value (true or false) if a key is
     * associated with a <code>OFactoryChild</code>.
     * 
     * @param key
     *            A <code>String</code> instance that is a key.
     * @return A <code>boolean</code> value (true or false).
     * 
     * @throws OFactoryClosedException
     *             if this method is called and the OFactory is closed.
     */
    public boolean containsChild(final String key)
    {
        if (this.isClosed())
        {
            throw (new OFactoryClosedException(
                    "This factory is closed and unable to process calls."));
        }
        else
        {
            return (this._children.containsKey(key));
        }
    }
    
    /**
     * Creates a child associated with a key that is the classname.
     * 
     * @param Class
     *            The class type to create a child from. Uses the Classes fully
     *            qualified name as the key.
     * 
     * @return A <code>OFactoryChild</code> instance bound to the key.
     * 
     * @throws OFactoryClosedException
     *             if this method is called and the OFactory is closed.
     * @throws OFactoryChildException
     *             Thrown when an OFactoryChild already exists with that key.
     */
    public <C extends OFactoryChild> C create(final Class<C> clazz)
            throws OFactoryChildException
    {
        return (this.create(clazz.getName(), clazz.getName()));
    }
    
    /**
     * Creates a child associated with a key that is the fully qualifed
     * className.
     * 
     * @param className
     *            The fully qualified classname to create a child from. The
     *            fully qualified classname is the key.
     * 
     * @return A <code>OFactoryChild</code> instance bound to the key.
     * 
     * @throws OFactoryClosedException
     *             if this method is called and the OFactory is closed.
     * @throws OFactoryChildException
     *             Thrown when an OFactoryChild already exists with that key.
     */
    public <C extends OFactoryChild> C create(final String className)
            throws OFactoryChildException
    {
        return (this.create(className, className));
    }
    
    /**
     * Creates a child using the class type and is associated with a key.
     * 
     * @param key
     *            The key to bind to the new child.
     * @param Class
     *            The class type to create a child from.
     * 
     * @return A <code>OFactoryChild</code> instance bound to the key.
     * 
     * @throws OFactoryClosedException
     *             if this method is called and the OFactory is closed.
     * @throws OFactoryChildException
     *             Thrown when an OFactoryChild already exists with that key.
     */
    public <C extends OFactoryChild> C create(final String key,
            final Class<C> clazz) throws OFactoryChildException
    {
        return (this.create(key, clazz.getName()));
    }
    
    /**
     * Creates a child using the fully qualified classname and is associated
     * with a key.
     * 
     * @param key
     *            A <code>String</code> instance. The key to bind to the new
     *            child.
     * @param className
     *            A <code>String</code> instance of the fully qualified
     *            classname.
     * 
     * @return A <code>OFactoryChild</code> instance bound to the key.
     * 
     * @throws OFactoryClosedException
     *             if this method is called and the OFactory is closed.
     * @throws OFactoryChildException
     *             Thrown when an OFactoryChild already exists with that key.
     */
    public <C extends OFactoryChild> C create(final String key,
            final String className) throws OFactoryChildException
    {
        // other methods do parameter validation.
        final C child = this.loadAndStoreOFactoryChild(key, className);
        child.initialize(this, key);
        this.notifyObservers(new Event(Event.Type.CREATE, key, child));
        return child;
    }
    
    /**
     * Gets the child by the bounded key.
     * 
     * @param key
     *            The key that is bound to the logger.
     * 
     * @return An <code>OFactoryChild</code> child instance associated with the
     *         key. May return null.
     * 
     * @throws OFactoryClosedException
     *             if this method is called and the OFactory is closed.
     */
    public <C extends OFactoryChild> C get(final String key)
    {
        if (this.isClosed())
        {
            throw (new OFactoryClosedException(
                    "This factory is closed and unable to process calls."));
        }
        else
        {
            Validate.isNullOrEmpty(key,
                    "get() - the parameter 'key' should not be null or empty",
                    this.getClass());
            
            final C child = (C) this._children.get(key);
            this.notifyObservers(new Event(Event.Type.GET, key, child));
            return (child);
            
        }
    }
    
    /**
     * Returns the number of children created and managed by this Factory.
     * 
     * @return An integer value (0 <= x <= n) that is the number of children.
     * 
     * @throws OFactoryClosedException
     *             if this method is called and the OFactory is closed.
     */
    public int getChildCount()
    {
        if (this.isClosed())
        {
            throw (new OFactoryClosedException(
                    "This factory is closed and unable to process calls."));
        }
        else
        {
            return (this._children.size());
        }
    }
    
    /**
     * Returns a <code>Set</code> containing <code>String</code> keys.
     * 
     * @return A <code>Set</code> containing <code>String</code> keys.
     * 
     * @throws OFactoryClosedException
     *             if this method is called and the OFactory is closed.
     */
    public Set<String> getKeys()
    {
        if (this.isClosed())
        {
            throw (new OFactoryClosedException(
                    "This factory is closed and unable to process calls."));
        }
        else
        {
            final Set<String> keys = this._children.keySet();
            return (keys);
            
        }
    }
    
    /**
     * Get the current number of observers wanting to be notified of events.
     * 
     * @return An <code>int</code> value 0 <= x <= n.
     */
    public int getObserverCount()
    {
        if (this.isClosed())
        {
            throw (new OFactoryClosedException(
                    "This factory is closed and unable to process calls."));
        }
        else
        {
            final int count = this.countObservers();
            return (count);
        }
    }
    
    /**
     * Returns a boolean (true or false) if this <code>OFactory</code> is
     * closed.
     * 
     * @return A <code>boolean</code> value. True if the factory is closed,
     *         otherwise it is false.
     */
    public boolean isClosed()
    {
        return (this._isClosed);
    }
    
    /**
     * Loads and Stores the OFactoryChild for use.
     * 
     * @param key
     *            A <code>String</code> instance. The key to bind to the new
     *            child.
     * 
     * @param className
     *            A <code>String</code> instance of the fully qualified
     *            classname.
     * 
     * @return A <code>OFactoryChild</code> instance bound to the key.
     * 
     * @throws OFactoryClosedException
     *             if this method is called and the OFactory is closed.
     * @throws OFactoryChildException
     *             Thrown when an OFactoryChild already exists with that key.
     */
    protected final <C extends OFactoryChild> C loadAndStoreOFactoryChild(
            final String key, final String className)
            throws OFactoryChildException
    {
        if (this.isClosed())
        {
            throw (new OFactoryClosedException(
                    "This factory is closed and unable to process calls."));
        }
        else
        {
            Validate.isNullOrEmpty(
                    key,
                    "create() - the parameter 'key' should not be null or empty",
                    this.getClass());
            Validate.isNullOrEmpty(
                    className,
                    "create() - the parameter 'className' should not be null or empty",
                    this.getClass());
            
            if (this.containsChild(key))
            {
                throw (new OFactoryChildException(
                        "A OFactoryChild already exists with that key='" + key
                                + "'"));
            }
            else
            {
                C child = OFactory.loadOFactoryChild(className);
                child = this.addToChildTable(key, child);
                return (child);
            }
        }
    }
    
    /**
     * Notifies the <code>Observers</code> of an event within the OFactory.
     * 
     * @param event
     *            An <code>OFactory</code> event that indicates a CREATE,
     *            REMOVE, GET, CLOSE;
     */
    protected void notifyObservers(final Event event)
    {
        this.setChanged();
        if (this.countObservers() > 0)
        {
            super.notifyObservers(event);
        }
        this.clearChanged();
    }
    
    /**
     * Remove an observer from the OFactory. The observer will no longer be
     * notified of events.
     * 
     * @param observer
     *            An <code>Observer</code> instance that wants to be removed
     *            from being notified of events.
     */
    public void removeObserver(final Observer observer)
    {
        if (this.isClosed())
        {
            throw (new OFactoryClosedException(
                    "This factory is closed and unable to process calls."));
        }
        else
        {
            this.deleteObserver(observer);
        }
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("OFactory [_children=");
        builder.append(this._children);
        builder.append(", _isClosed=");
        builder.append(this._isClosed);
        builder.append("]");
        return builder.toString();
    }
    
}
