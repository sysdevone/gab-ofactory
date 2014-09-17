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

import java.util.Observable;
import java.util.Observer;

import org.gabsocial.ofactory.OFactory.Event;
import org.gabsocial.ofactory.impl.MockOFactoryChildImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * 
 * Test class for the <code>OFactory</code>
 * 
 * @author Gregory Brown (sysdevone)
 */
public class OFactoryObservableTest
{
    
    static OFactory _ofactory;
    static boolean  _observerCalled;
    
    @Before
    public void setup()
    {
        this._ofactory = new OFactory();
        this._observerCalled = false;
    }
    
    @Test
    public void testCreateChildWithClassKey()
    {
        Assert.assertTrue(this._ofactory != null);
        final String className = "org.gabsocial.ofactory.impl.MockOFactoryChildImpl";
        
        Observer ofactoryObserver = new Observer()
        {
            
            @Override
            public void update(Observable o, Object arg)
            {
                // System.out.println("Observable: " + o + " Event: " + arg);
                OFactoryObservableTest._observerCalled = true;
                OFactory ofactory = (OFactory) o;
                Assert.assertTrue(ofactory
                        .equals(OFactoryObservableTest._ofactory));
                Event event = (Event) arg;
                Assert.assertTrue(Event.Type.CREATE.equals(event.getType()));
            }
            
        };
        
        this._ofactory.addObserver(ofactoryObserver);
        
        try
        {
            final OFactoryChild child = this._ofactory
                    .create(MockOFactoryChildImpl.class);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockOFactoryChildImpl);
            Assert.assertTrue(className.equals(child.getKey()));
            Assert.assertTrue(OFactoryObservableTest._observerCalled);
        }
        catch (final OFactoryChildException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testCreateChildWithClassNameKey()
    {
        Assert.assertTrue(this._ofactory != null);
        
        Observer ofactoryObserver = new Observer()
        {
            
            @Override
            public void update(Observable o, Object arg)
            {
                // System.out.println("Observable: " + o + " Event: " + arg);
                OFactory ofactory = (OFactory) o;
                Assert.assertTrue(ofactory
                        .equals(OFactoryObservableTest._ofactory));
                OFactoryObservableTest._observerCalled = true;
                Event event = (Event) arg;
                Assert.assertTrue(Event.Type.CREATE.equals(event.getType()));
            }
            
        };
        
        this._ofactory.addObserver(ofactoryObserver);
        
        final String className = "org.gabsocial.ofactory.impl.MockOFactoryChildImpl";
        
        try
        {
            final OFactoryChild child = this._ofactory.create(className);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockOFactoryChildImpl);
            Assert.assertTrue(className.equals(child.getKey()));
            Assert.assertTrue(OFactoryObservableTest._observerCalled);
        }
        catch (final OFactoryChildException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testCreateChildWithKeyAndClass()
    {
        Assert.assertTrue(this._ofactory != null);
        
        Observer ofactoryObserver = new Observer()
        {
            
            @Override
            public void update(Observable o, Object arg)
            {
                // System.out.println("Observable: " + o + " Event: " + arg);
                OFactory ofactory = (OFactory) o;
                Assert.assertTrue(ofactory
                        .equals(OFactoryObservableTest._ofactory));
                OFactoryObservableTest._observerCalled = true;
                Event event = (Event) arg;
                Assert.assertTrue(Event.Type.CREATE.equals(event.getType()));
            }
            
        };
        
        this._ofactory.addObserver(ofactoryObserver);
        
        final String key = "test-mock-o";
        
        try
        {
            final OFactoryChild child = this._ofactory.create(key,
                    MockOFactoryChildImpl.class);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockOFactoryChildImpl);
            Assert.assertTrue(key.equals(child.getKey()));
            Assert.assertTrue(OFactoryObservableTest._observerCalled);
        }
        catch (final OFactoryChildException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testCreateChildWithKeyAndClassName()
    {
        Assert.assertTrue(this._ofactory != null);
        
        Observer ofactoryObserver = new Observer()
        {
            
            @Override
            public void update(Observable o, Object arg)
            {
                // System.out.println("Observable: " + o + " Event: " + arg);
                OFactory ofactory = (OFactory) o;
                Assert.assertTrue(ofactory
                        .equals(OFactoryObservableTest._ofactory));
                OFactoryObservableTest._observerCalled = true;
                Event event = (Event) arg;
                Assert.assertTrue(Event.Type.CREATE.equals(event.getType()));
            }
            
        };
        
        this._ofactory.addObserver(ofactoryObserver);
        
        final String key = "test-mock-o";
        final String className = "org.gabsocial.ofactory.impl.MockOFactoryChildImpl";
        
        try
        {
            final OFactoryChild child = this._ofactory.create(key, className);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockOFactoryChildImpl);
            Assert.assertTrue(key.equals(child.getKey()));
            Assert.assertTrue(OFactoryObservableTest._observerCalled);
        }
        catch (final OFactoryChildException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testFactoryAddObserver()
    {
        Assert.assertTrue(this._ofactory != null);
        
        Observer ofactoryObserver = new Observer()
        {
            
            @Override
            public void update(Observable o, Object arg)
            {
                // should not be called.
                Assert.fail("observer should not be called.");
            }
            
        };
        
        this._ofactory.addObserver(ofactoryObserver);
        Assert.assertTrue(this._ofactory.countObservers() == 1);
    }
    
    @Test
    public void testFactoryDeleteObserver()
    {
        Assert.assertTrue(this._ofactory != null);
        
        Observer ofactoryObserver = new Observer()
        {
            
            @Override
            public void update(Observable o, Object arg)
            {
                
                // should not be called.
                Assert.fail("observer should not be called.");
            }
            
        };
        
        this._ofactory.addObserver(ofactoryObserver);
        Assert.assertTrue(this._ofactory.countObservers() == 1);
        this._ofactory.deleteObserver(ofactoryObserver);
        Assert.assertTrue(this._ofactory.countObservers() == 0);
    }
    
    @Test
    public void testGetChildWithClassNameKey()
    {
        
        Assert.assertTrue(this._ofactory != null);
        
        final String className = "org.gabsocial.ofactory.impl.MockOFactoryChildImpl";
        
        try
        {
            this._ofactory.create(className);
            
            Observer ofactoryObserver = new Observer()
            {
                
                @Override
                public void update(Observable o, Object arg)
                {
                    // System.out.println("Observable: " + o + " Event: " +
                    // arg);
                    OFactory ofactory = (OFactory) o;
                    Assert.assertTrue(ofactory
                            .equals(OFactoryObservableTest._ofactory));
                    OFactoryObservableTest._observerCalled = true;
                    Event event = (Event) arg;
                    Assert.assertTrue(Event.Type.GET.equals(event.getType()));
                }
                
            };
            
            this._ofactory.addObserver(ofactoryObserver);
            
            final String key = MockOFactoryChildImpl.class.getName();
            final OFactoryChild child = this._ofactory.get(key);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child.getKey().equals(key));
            Assert.assertTrue(child instanceof MockOFactoryChildImpl);
            Assert.assertTrue(OFactoryObservableTest._observerCalled);
        }
        catch (final OFactoryChildException e)
        {
            Assert.fail(e.toString());
        }
        
    }
    
    @Test
    public void testGetChildWithKey()
    {
        
        Assert.assertTrue(this._ofactory != null);
        
        final String key = "test-mock-o";
        final String className = "org.gabsocial.ofactory.impl.MockOFactoryChildImpl";
        
        try
        {
            this._ofactory.create(key, className);
            
            Observer ofactoryObserver = new Observer()
            {
                
                @Override
                public void update(Observable o, Object arg)
                {
                    // System.out.println("Observable: " + o + " Event: " +
                    // arg);
                    OFactory ofactory = (OFactory) o;
                    Assert.assertTrue(ofactory
                            .equals(OFactoryObservableTest._ofactory));
                    OFactoryObservableTest._observerCalled = true;
                    Event event = (Event) arg;
                    Assert.assertTrue(Event.Type.GET.equals(event.getType()));
                }
                
            };
            
            this._ofactory.addObserver(ofactoryObserver);
            
            final OFactoryChild child = this._ofactory.get(key);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child.getKey().equals(key));
            Assert.assertTrue(child instanceof MockOFactoryChildImpl);
            Assert.assertTrue(OFactoryObservableTest._observerCalled);
        }
        catch (final OFactoryChildException e)
        {
            Assert.fail(e.toString());
        }
        
    }
    
    @Test
    public void testOFactoryClose()
    {
        
        Observer ofactoryObserver = new Observer()
        {
            
            @Override
            public void update(Observable o, Object arg)
            {
                // System.out.println("Observable: " + o + " Event: " + arg);
                OFactory ofactory = (OFactory) o;
                Assert.assertTrue(ofactory
                        .equals(OFactoryObservableTest._ofactory));
                OFactoryObservableTest._observerCalled = true;
                Event event = (Event) arg;
                Assert.assertTrue(Event.Type.CLOSE.equals(event.getType()));
            }
            
        };
        
        this._ofactory.addObserver(ofactoryObserver);
        
        this._ofactory.close();
        Assert.assertTrue(OFactoryObservableTest._observerCalled);
        
    }
    
    // public static final void main(String[] args)
    // {
    // OFactory ofactory = new OFactory();
    // Observer ofactoryObserver = new Observer()
    // {
    //
    // @Override
    // public void update(Observable o, Object arg)
    // {
    // System.out.println("Observable: " + o + " Event: " + arg);
    // }
    //
    // };
    //
    // ofactory.addObserver(ofactoryObserver);
    //
    // try
    // {
    // final OFactoryChild child = ofactory
    // .create(MockOFactoryChildImpl.class);
    // System.out.println("DONE");
    // }
    // catch (final OFactoryChildException e)
    // {
    // System.out.println("Exception: " + e);
    // }
    // }
}
