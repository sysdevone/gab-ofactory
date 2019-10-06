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

/**
 * 
 * An interface to implement if an object is to be managed by the
 * <code>Manager</code>.
 * 
 * @author Gregory Brown (sysdevone)
 */
public abstract interface Manageable
{
    
    // P = parent
    // C = child
    // S = settings
    
    /**
     * Removes the child from the parent then closes any resources. This should
     * call the parent Manager.closeChild(key).
     * 
     */
    public abstract void close();
    
    /**
     * Used by Manager as a callback when the Manager.close() method is
     * called. Closes down the child by releasing all referenced members. Does
     * not call the Manager.closeChild(key). The Manager.closeChild(key) calls
     * back to this method.
     */
    public abstract void closeWithoutRemove();
    
    /**
     * Gets the key associated with this ManagerChild. This should return the
     * key value that was assigned when the ManagerChild.initialize(parent,key)
     * was called.
     * 
     * @return A <code> String </code> instance.
     */
    public abstract String getKey();
    
    /**
     * Gets the parent that this ManagerChild belongs too. This should return
     * the parent reference that was assigned when the
     * ManagerChild.initialize(parent,key) was called.
     * 
     * @param <P> An instance that extends <code>Manager</code>.
     * @return <P> An instance that extends <code>Manager</code>.
     */
    public abstract <P extends Manager> P getParent();
    
    /**
     * Initializes the ManagerChild.
     * 
     * @param <P> An instance that extends <code>Manager</code>.
     * @param parent
     *            An <code>Manager</code> instance that is the parent to this
     *            child.
     * 
     * @param key
     *            A <code>String</code> instance that is the key associated with
     *            this child. ManagerChild.getKey() should return this value.
     */
    public abstract <P extends Manager> void initialize(final P parent, final String key);
    
}
