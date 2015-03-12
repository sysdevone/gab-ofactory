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

import java.util.Properties;

import org.gabsocial.ofactory.impl.MockPropertiedOFactoryChildImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * 
 * Test class for the <code>PropertiedOFactory</code>
 * 
 * @author Gregory Brown (sysdevone)
 */
public class PropertiedOFactoryTest
{
    
    PropertiedOFactory<MockPropertiedOFactoryChildImpl, Properties> _propertiedOFactory;
    
    @Before
    public void setup()
    {
        this._propertiedOFactory = new PropertiedOFactory<MockPropertiedOFactoryChildImpl, Properties>();
    }
    
    @Test
    public void testCreateChildWithClassKey()
    {
        Assert.assertTrue(this._propertiedOFactory != null);
        final String className = "org.gabsocial.ofactory.impl.MockPropertiedOFactoryChildImpl";
        try
        {
            Properties properties = new Properties();
            final MockPropertiedOFactoryChildImpl child = this._propertiedOFactory
                    .create(MockPropertiedOFactoryChildImpl.class, properties);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockPropertiedOFactoryChildImpl);
            Assert.assertTrue(className.equals(child.getKey()));
            
        }
        catch (final OFactoryChildException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testCreateChildWithClassNameKey()
    {
        Assert.assertTrue(this._propertiedOFactory != null);
        
        final String className = "org.gabsocial.ofactory.impl.MockPropertiedOFactoryChildImpl";
        
        try
        {
            final PropertiedOFactoryChild child = this._propertiedOFactory
                    .create(className);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockPropertiedOFactoryChildImpl);
            Assert.assertTrue(className.equals(child.getKey()));
        }
        catch (final OFactoryChildException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testCreateChildWithKeyAndClass()
    {
        Assert.assertTrue(this._propertiedOFactory != null);
        
        final String key = "test-mock-o";
        
        try
        {
            final PropertiedOFactoryChild child = (PropertiedOFactoryChild) this._propertiedOFactory.create(key,
                    MockPropertiedOFactoryChildImpl.class);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockPropertiedOFactoryChildImpl);
            Assert.assertTrue(key.equals(child.getKey()));
        }
        catch (final OFactoryChildException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testCreateChildWithKeyAndClassName()
    {
        Assert.assertTrue(this._propertiedOFactory != null);
        
        final String key = "test-mock-o";
        final String className = "org.gabsocial.ofactory.impl.MockPropertiedOFactoryChildImpl";
        
        try
        {
            final PropertiedOFactoryChild child = this._propertiedOFactory.create(key,
                    className);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockPropertiedOFactoryChildImpl);
            Assert.assertTrue(key.equals(child.getKey()));
        }
        catch (final OFactoryChildException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testFactoryCreation()
    {
        Assert.assertTrue(this._propertiedOFactory != null);
    }
    
    @Test
    public void testGetChildWithClassNameKey()
    {
        
        Assert.assertTrue(this._propertiedOFactory != null);
        
        final String className = "org.gabsocial.ofactory.impl.MockPropertiedOFactoryChildImpl";
        
        try
        {
            this._propertiedOFactory.create(className);
            
            final String key = MockPropertiedOFactoryChildImpl.class.getName();
            final OFactoryChild child = this._propertiedOFactory.get(key);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child.getKey().equals(key));
            Assert.assertTrue(child instanceof MockPropertiedOFactoryChildImpl);
        }
        catch (final OFactoryChildException e)
        {
            Assert.fail(e.toString());
        }
        
    }
    
    @Test
    public void testGetChildWithKey()
    {
        
        Assert.assertTrue(this._propertiedOFactory != null);
        
        final String key = "test-mock-o";
        final String className = "org.gabsocial.ofactory.impl.MockPropertiedOFactoryChildImpl";
        
        try
        {
            this._propertiedOFactory.create(key, className);
            
            final OFactoryChild child = this._propertiedOFactory.get(key);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child.getKey().equals(key));
            Assert.assertTrue(child instanceof MockPropertiedOFactoryChildImpl);
        }
        catch (final OFactoryChildException e)
        {
            Assert.fail(e.toString());
        }
        
    }
    
    // TODO - test close
}
