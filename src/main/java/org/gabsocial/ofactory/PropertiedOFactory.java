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

import org.gabsocial.gabdev.validate.Validate;



/**
 * <pre>
 * This class is a factory creating and handling of child objects that have settings.
 * 
 * Before an child can be created and used, the factory needs to be
 * instantiated. It is up to the caller to determine if the factory is a 
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
public class PropertiedOFactory<C extends PropertiedOFactoryChild, S> extends
        OFactory<C>
{
    // P = parent
    // C = child
    // S = settings
    
    /**
     * Creates a child whose key is the classname.
     * 
     * @param clazz
     *            The class type to create a child from.
     * 
     * @param settings
     *            An object that holds data used to initialize the child after
     *            it is created.
     * 
     * @return A <code>OFactoryChild</code> instance bound to the key.
     * 
     * @throws OFactoryChildException
     *             Thrown when an OFactoryChild already exists with that key.
     */
    public C create(final Class<C> clazz, final S settings)
            throws OFactoryChildException
    {
        return (this.create(clazz.getName(), clazz.getName(), settings));
    }
    
    /**
     * Creates a child whose key is defined by the parameter key.
     * 
     * @param key
     *            The key associated with the new child.
     * @param clazz
     *            The class type to create a child from.
     * @param settings
     *            An object that holds data used to initialize the child after
     *            it is created.
     * 
     * @return A <code>OFactoryChild</code> instance bound to the key.
     * 
     * @throws OFactoryChildException
     *             Thrown when an OFactoryChild already exists with that key.
     */
    public C create(final String key, final Class<C> clazz, final S settings)
            throws OFactoryChildException
    {
        return (this.create(key, clazz.getName(), settings));
    }
    
    /**
     * Creates a child. The key associated with the new child is the className.
     * 
     * @param className
     *            The configuration setup.
     * @param settings
     *            An object that holds data used to initialize the child after
     *            it is created.
     * @return A <code>OFactoryChild</code> instance bound to the key.
     * 
     * @throws OFactoryChildException
     *             Thrown when an OFactoryChild already exists with that key.
     */
    public C create(final String className, final S settings)
            throws OFactoryChildException
    {
        return (this.create(className, className, settings));
    }
    
    /**
     * Creates a child associated with a key. This child will be initialized
     * with a provided properties object.
     * 
     * @param key
     *            A <code>String</code> instance. The key to bind to the new
     *            child.
     * @param className
     *            A <code>String</code> instance. The configuration setup.
     * @param settings
     *            An object that holds data used to initialize the child after
     *            it is created.
     * @return A <code>OFactoryChild</code> instance bound to the key.
     * 
     * @throws OFactoryChildException
     *             Thrown when an OFactoryChild already exists with that key.
     */
    public C create(final String key, final String className, final S settings)
            throws OFactoryChildException
    {
        Validate.isNotNullOrEmpty(this.getClass(), key);
        Validate.isLessThanMaxLength(this.getClass(), KEY_MAX_LENGTH, key);
        Validate.isNotNullOrEmpty(this.getClass(), className);
        Validate.isLessThanMaxLength(this.getClass(), CLASS_NAME_MAX_LENGTH,
                className);
        Validate.isNotNull(this.getClass(), settings);
        
        // other methods do parameter validation.
        final C child = this.loadAndStoreOFactoryChild(key, className);
        child.initialize(this, key, settings);
        this.notifyObservers(new Event(Event.Type.CREATE, key, child));
        return child;
    }
    
}
