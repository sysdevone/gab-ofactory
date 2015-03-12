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

/**
 * 
 * An interface to implement if an object is to be managed by the
 * <code>OFactory</code>.
 * 
 * @author Gregory Brown (sysdevone)
 */
public abstract interface OFactoryChild
{
    
    // P = parent
    // C = child
    // S = settings
    
    /**
     * Removes the child from the parent then closes any resources. This should
     * call the parent OFactory.closeChild(key).
     * 
     */
    public abstract void close();
    
    /**
     * Used by OFactory as a callback when the OFactory.close() method is
     * called. Closes down the child by releasing all referenced members. Does
     * not call the OFactory.closeChild(key). The OFactory.closeChild(key) calls
     * back to this method.
     */
    public abstract void closeWithoutRemove();
    
    /**
     * Gets the key associated with this OFactoryChild. This should return the
     * key value that was assigned when the OFactoryChild.initialize(parent,key)
     * was called.
     * 
     * @return A <code> String </code> instance.
     */
    public abstract String getKey();
    
    /**
     * Gets the parent that this OFactoryChild belongs too. This should return
     * the parent reference that was assigned when the
     * OFactoryChild.initialize(parent,key) was called.
     * 
     * @return An instance that extends <code>OFactory</code>.
     */
    public abstract <P extends OFactory> P getParent();
    
    /**
     * Initializes the OFactoryChild.
     * 
     * @param parent
     *            An <code>OFactory</code> instance that is the parent to this
     *            child.
     * 
     * @param key
     *            A <code>String</code> instance that is the key associated with
     *            this child. OFactoryChild.getKey() should return this value.
     */
    public abstract <P extends OFactory> void initialize(final P parent, final String key);
    
}
