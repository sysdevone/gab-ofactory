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

import java.util.Properties;

import com.gabstudios.manager.impl.MockPropertiedManagerChildImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * 
 * Test class for the <code>PropertiedManager</code>
 * 
 * @author Gregory Brown (sysdevone)
 */
public class PropertiedManagerTest
{
    
    PropertiedManager<MockPropertiedManagerChildImpl, Properties> _propertiedManager;
    
    @Before
    public void setup()
    {
        this._propertiedManager = new PropertiedManager<MockPropertiedManagerChildImpl, Properties>();
    }
    
    @Test
    public void testCreateChildWithClassKey()
    {
        Assert.assertTrue(this._propertiedManager != null);
        final String className = "com.gabstudios.manager.impl.MockPropertiedManagerChildImpl";
        try
        {
            Properties properties = new Properties();
            final MockPropertiedManagerChildImpl child = this._propertiedManager
                    .create(MockPropertiedManagerChildImpl.class, properties);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockPropertiedManagerChildImpl);
            Assert.assertTrue(className.equals(child.getKey()));
            
        }
        catch (final ManageableException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testCreateChildWithClassNameKey()
    {
        Assert.assertTrue(this._propertiedManager != null);
        
        final String className = "com.gabstudios.manager.impl.MockPropertiedManagerChildImpl";
        
        try
        {
            final PropertiedManageable child = this._propertiedManager
                    .create(className);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockPropertiedManagerChildImpl);
            Assert.assertTrue(className.equals(child.getKey()));
        }
        catch (final ManageableException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testCreateChildWithKeyAndClass()
    {
        Assert.assertTrue(this._propertiedManager != null);
        
        final String key = "test-mock-o";
        
        try
        {
            final PropertiedManageable child = (PropertiedManageable) this._propertiedManager.create(key,
                    MockPropertiedManagerChildImpl.class);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockPropertiedManagerChildImpl);
            Assert.assertTrue(key.equals(child.getKey()));
        }
        catch (final ManageableException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testCreateChildWithKeyAndClassName()
    {
        Assert.assertTrue(this._propertiedManager != null);
        
        final String key = "test-mock-o";
        final String className = "com.gabstudios.manager.impl.MockPropertiedManagerChildImpl";
        
        try
        {
            final PropertiedManageable child = this._propertiedManager.create(key,
                    className);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockPropertiedManagerChildImpl);
            Assert.assertTrue(key.equals(child.getKey()));
        }
        catch (final ManageableException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testManagerCreation()
    {
        Assert.assertTrue(this._propertiedManager != null);
    }
    
    @Test
    public void testGetChildWithClassNameKey()
    {
        
        Assert.assertTrue(this._propertiedManager != null);
        
        final String className = "com.gabstudios.manager.impl.MockPropertiedManagerChildImpl";
        
        try
        {
            this._propertiedManager.create(className);
            
            final String key = MockPropertiedManagerChildImpl.class.getName();
            final Manageable child = this._propertiedManager.get(key);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child.getKey().equals(key));
            Assert.assertTrue(child instanceof MockPropertiedManagerChildImpl);
        }
        catch (final ManageableException e)
        {
            Assert.fail(e.toString());
        }
        
    }
    
    @Test
    public void testGetChildWithKey()
    {
        
        Assert.assertTrue(this._propertiedManager != null);
        
        final String key = "test-mock-o";
        final String className = "com.gabstudios.manager.impl.MockPropertiedManagerChildImpl";
        
        try
        {
            this._propertiedManager.create(key, className);
            
            final Manageable child = this._propertiedManager.get(key);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child.getKey().equals(key));
            Assert.assertTrue(child instanceof MockPropertiedManagerChildImpl);
        }
        catch (final ManageableException e)
        {
            Assert.fail(e.toString());
        }
        
    }
    
    // TODO - test close
}
