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

package com.gabstudios.manager;

import com.gabstudios.validate.Validate;



/**
 * <pre>
 * This class is a manager creating and handling of child objects that have settings.
 * 
 * Before an child can be created and used, the manager needs to be
 * instantiated. It is up to the caller to determine if the manager is a 
 * singleton for implementation purposes.
 * 
 * After the manager is created and in memory, call the create(xxx) method to
 * create and add children.
 * 
 * Once a child is created, a call to the manager's getChild(key) method will
 * return the child instance associated with the key. Only once child can be
 * associated with a specific key.
 * 
 * A child will be maintained by the manager until that child is removed by
 * calling the manager's removeChild(key) method or the child's close() method.
 * 
 * The manager's close() method will remove all children and prevent calls to
 * other manager methods. Call getInstance() to create a new instance of
 * Manager so that child objects can be created and added.
 * </pre>
 * 
 * @author Gregory Brown (sysdevone)
 */
public class PropertiedManager<C extends PropertiedManageable, S> extends
        Manager<C>
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
     * @return A <code>ManagerChild</code> instance bound to the key.
     * 
     * @throws ManageableException
     *             Thrown when an ManagerChild already exists with that key.
     */
    public C create(final Class<C> clazz, final S settings)
            throws ManageableException
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
     * @return A <code>ManagerChild</code> instance bound to the key.
     * 
     * @throws ManageableException
     *             Thrown when an ManagerChild already exists with that key.
     */
    public C create(final String key, final Class<C> clazz, final S settings)
            throws ManageableException
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
     * @return A <code>ManagerChild</code> instance bound to the key.
     * 
     * @throws ManageableException
     *             Thrown when an ManagerChild already exists with that key.
     */
    public C create(final String className, final S settings)
            throws ManageableException
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
     * @return A <code>ManagerChild</code> instance bound to the key.
     * 
     * @throws ManageableException
     *             Thrown when an ManagerChild already exists with that key.
     */
    public C create(final String key, final String className, final S settings)
            throws ManageableException
    {
    	Validate.defineString(key).testNotNullEmpty().testMaxLength(KEY_MAX_LENGTH).throwValidationExceptionOnFail().validate();
    	Validate.defineString(className).testNotNullEmpty().testMaxLength(CLASS_NAME_MAX_LENGTH).throwValidationExceptionOnFail().validate();
    	Validate.defineObject(settings).testNotNull().throwValidationExceptionOnFail().validate();
        
        // other methods do parameter validation.
        final C child = this.loadAndStoreManagerChild(key, className);
        child.initialize(this, key, settings);
//        this.notifyObservers(new Event(Event.Type.CREATE, key, child));
        return child;
    }
    
}
