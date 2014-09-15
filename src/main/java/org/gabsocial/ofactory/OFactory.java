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
import java.util.Set;

import org.gabsocial.common.validator.Validate;


/**
 * <pre>
 * This class is a factory creating and handling of child objects.
 * 
 * Before an child can be created and used, the factory needs to be
 * instantiated.   It is up to the caller to determine if the factory is a 
 * singleton for implementation purposes.
 * 
 * After the factory is created and in memory, call the create(xxx) method to
 * create and add children.
 * 
 * Once a child is created, a call to the factory's getChild(key) method will
 * return the child instance associated with the key. Only once child can be
 * associated with a specific key.
 * 
 * A child will be maintained by the factory until that child is removed by
 * calling the factory's removeChild(key) method or the child's close() method.
 * 
 * The factory's close() method will remove all children and prevent calls to
 * other factory methods. Call getInstance() to create a new instance of
 * OFactory so that child objects can be created and added.
 * </pre>
 * 
 * @author Gregory Brown (sysdevone)
 */
public class OFactory
{
    
    // P = parent
    // C = child
    // S = settings
    
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
    private Map<String, OFactoryChild> _children;
    
    /**
     * A flag to determine if the factory has been closed.
     */
    private boolean                    _isClosed;
    
    /*
     * initializes the children table.
     */
    public OFactory()
    {
        this._children = new HashMap<String, OFactoryChild>();
        this._isClosed = false;
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
            
            this._isClosed = true;
            assert (this._children.size() == 0) : "The child table should be empty.";
            
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
            return (child);
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
        C child = this.loadAndStoreOFactoryChild(key, className);
        child.initialize(this, key);
        return child;
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
    public boolean containsChild(String key)
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
    
    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
        return String.format("OFactory [children.size=%s, isClosed=%s]",
                this._children.size(), this._isClosed);
    }
    
}
