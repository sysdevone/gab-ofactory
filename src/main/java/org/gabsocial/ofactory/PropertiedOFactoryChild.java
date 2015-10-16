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
 * <code>PropertiedOFactory</code>.
 * 
 * @author Gregory Brown (sysdevone)
 */
public abstract interface PropertiedOFactoryChild
        extends OFactoryChild
{
    // P = parent
    // C = child
    // S = settings
    
    /**
     * Initializes the PropertiedOFactoryChild with settings.
     * 
     * @param parent
     *            An <code>OFactory</code> instance that is the parent to this
     *            child. Must not be null.
     * 
     * @param key
     *            A <code>String</code> instance that is the key associated with
     *            this child. OFactoryChild.getKey() should return this value.
     *            Must not be null.
     * 
     * @param settings
     *            An object that holds setting information. Must not be null.
     *            
     * @param <P> A type that extends <code>PropertiedOFactory</code>.
     * @param <S> A type representing the settings passed into this instance.
     */
    public abstract <P extends PropertiedOFactory, S> void initialize(final P parent, final String key,
            final S settings);
    
    /**
     * Returns the settings used when the child was initialized.
     * 
     * @param <S> A type representing the settings passed into this instance.
     * @return The settings object passed in when the child was created.
     */
    public abstract <S> S getSettings();
    
}
