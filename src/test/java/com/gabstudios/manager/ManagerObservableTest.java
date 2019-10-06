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

import java.util.Observable;
import java.util.Observer;

import com.gabstudios.manager.Manager.Event;
import com.gabstudios.manager.impl.MockManagerChildImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * 
 * Test class for the <code>Manager</code>
 * 
 * @author Gregory Brown (sysdevone)
 */
public class ManagerObservableTest
{
    
    static Manager<MockManagerChildImpl> s_manager;
    static boolean  s_observerCalled;
    
    @Before
    public void setup()
    {
        s_manager = new Manager<MockManagerChildImpl>();
        s_observerCalled = false;
    }
    
    @Test
    public void testCreateChildWithClassKey()
    {
        Assert.assertTrue(ManagerObservableTest.s_manager != null);
        final String className = "com.gabstudios.manager.impl.MockManagerChildImpl";
        
        Observer managerObserver = new Observer()
        {
            
            @Override
            public void update(Observable o, Object arg)
            {
                // System.out.println("Observable: " + o + " Event: " + arg);
                ManagerObservableTest.s_observerCalled = true;
                Manager<?> manager = (Manager<?>) o;
                Assert.assertTrue(manager
                        .equals(ManagerObservableTest.s_manager));
                Event event = (Event) arg;
                Assert.assertTrue(Event.Type.CREATE.equals(event.getType()));
            }
            
        };
        
        ManagerObservableTest.s_manager.addObserver(managerObserver);
        
        try
        {
            final Manageable child = ManagerObservableTest.s_manager
                    .create(MockManagerChildImpl.class);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockManagerChildImpl);
            Assert.assertTrue(className.equals(child.getKey()));
            Assert.assertTrue(ManagerObservableTest.s_observerCalled);
        }
        catch (final ManageableException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testCreateChildWithClassNameKey()
    {
        Assert.assertTrue(ManagerObservableTest.s_manager != null);
        
        Observer managerObserver = new Observer()
        {
            
            @Override
            public void update(Observable o, Object arg)
            {
                // System.out.println("Observable: " + o + " Event: " + arg);
                Manager<?> manager = (Manager<?>) o;
                Assert.assertTrue(manager
                        .equals(ManagerObservableTest.s_manager));
                ManagerObservableTest.s_observerCalled = true;
                Event event = (Event) arg;
                Assert.assertTrue(Event.Type.CREATE.equals(event.getType()));
            }
            
        };
        
        ManagerObservableTest.s_manager.addObserver(managerObserver);
        
        final String className = "com.gabstudios.manager.impl.MockManagerChildImpl";
        
        try
        {
            final Manageable child = ManagerObservableTest.s_manager.create(className);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockManagerChildImpl);
            Assert.assertTrue(className.equals(child.getKey()));
            Assert.assertTrue(ManagerObservableTest.s_observerCalled);
        }
        catch (final ManageableException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testCreateChildWithKeyAndClass()
    {
        Assert.assertTrue(ManagerObservableTest.s_manager != null);
        
        Observer managerObserver = new Observer()
        {
            
            @Override
            public void update(Observable o, Object arg)
            {
                // System.out.println("Observable: " + o + " Event: " + arg);
                Manager<?> manager = (Manager<?>) o;
                Assert.assertTrue(manager
                        .equals(ManagerObservableTest.s_manager));
                ManagerObservableTest.s_observerCalled = true;
                Event event = (Event) arg;
                Assert.assertTrue(Event.Type.CREATE.equals(event.getType()));
            }
            
        };
        
        ManagerObservableTest.s_manager.addObserver(managerObserver);
        
        final String key = "test-mock-o";
        
        try
        {
            final Manageable child = ManagerObservableTest.s_manager.create(key,
                    MockManagerChildImpl.class);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockManagerChildImpl);
            Assert.assertTrue(key.equals(child.getKey()));
            Assert.assertTrue(ManagerObservableTest.s_observerCalled);
        }
        catch (final ManageableException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testCreateChildWithKeyAndClassName()
    {
        Assert.assertTrue(ManagerObservableTest.s_manager != null);
        
        Observer managerObserver = new Observer()
        {
            
            @Override
            public void update(Observable o, Object arg)
            {
                // System.out.println("Observable: " + o + " Event: " + arg);
                Manager<?> manager = (Manager<?>) o;
                Assert.assertTrue(manager
                        .equals(ManagerObservableTest.s_manager));
                ManagerObservableTest.s_observerCalled = true;
                Event event = (Event) arg;
                Assert.assertTrue(Event.Type.CREATE.equals(event.getType()));
            }
            
        };
        
        ManagerObservableTest.s_manager.addObserver(managerObserver);
        
        final String key = "test-mock-o";
        final String className = "com.gabstudios.manager.impl.MockManagerChildImpl";
        
        try
        {
            final Manageable child = ManagerObservableTest.s_manager.create(key, className);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child instanceof MockManagerChildImpl);
            Assert.assertTrue(key.equals(child.getKey()));
            Assert.assertTrue(ManagerObservableTest.s_observerCalled);
        }
        catch (final ManageableException e)
        {
            Assert.fail(e.toString());
        }
    }
    
    @Test
    public void testManagerAddObserver()
    {
        Assert.assertTrue(ManagerObservableTest.s_manager != null);
        
        Observer managerObserver = new Observer()
        {
            
            @Override
            public void update(Observable o, Object arg)
            {
                // should not be called.
                Assert.fail("observer should not be called.");
            }
            
        };
        
        ManagerObservableTest.s_manager.addObserver(managerObserver);
        Assert.assertTrue(ManagerObservableTest.s_manager.countObservers() == 1);
    }
    
    @Test
    public void testManagerDeleteObserver()
    {
        Assert.assertTrue(ManagerObservableTest.s_manager != null);
        
        Observer managerObserver = new Observer()
        {
            
            @Override
            public void update(Observable o, Object arg)
            {
                
                // should not be called.
                Assert.fail("observer should not be called.");
            }
            
        };
        
        ManagerObservableTest.s_manager.addObserver(managerObserver);
        Assert.assertTrue(ManagerObservableTest.s_manager.countObservers() == 1);
        ManagerObservableTest.s_manager.deleteObserver(managerObserver);
        Assert.assertTrue(ManagerObservableTest.s_manager.countObservers() == 0);
    }
    
    @Test
    public void testGetChildWithClassNameKey()
    {
        
        Assert.assertTrue(ManagerObservableTest.s_manager != null);
        
        final String className = "com.gabstudios.manager.impl.MockManagerChildImpl";
        
        try
        {
            ManagerObservableTest.s_manager.create(className);
            
            Observer managerObserver = new Observer()
            {
                
                @Override
                public void update(Observable o, Object arg)
                {
                    // System.out.println("Observable: " + o + " Event: " +
                    // arg);
                    Manager<?> manager = (Manager<?>) o;
                    Assert.assertTrue(manager
                            .equals(ManagerObservableTest.s_manager));
                    ManagerObservableTest.s_observerCalled = true;
                    Event event = (Event) arg;
                    Assert.assertTrue(Event.Type.GET.equals(event.getType()));
                }
                
            };
            
            ManagerObservableTest.s_manager.addObserver(managerObserver);
            
            final String key = MockManagerChildImpl.class.getName();
            final Manageable child = ManagerObservableTest.s_manager.get(key);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child.getKey().equals(key));
            Assert.assertTrue(child instanceof MockManagerChildImpl);
            Assert.assertTrue(ManagerObservableTest.s_observerCalled);
        }
        catch (final ManageableException e)
        {
            Assert.fail(e.toString());
        }
        
    }
    
    @Test
    public void testGetChildWithKey()
    {
        
        Assert.assertTrue(ManagerObservableTest.s_manager != null);
        
        final String key = "test-mock-o";
        final String className = "com.gabstudios.manager.impl.MockManagerChildImpl";
        
        try
        {
            ManagerObservableTest.s_manager.create(key, className);
            
            Observer managerObserver = new Observer()
            {
                
                @Override
                public void update(Observable o, Object arg)
                {
                    // System.out.println("Observable: " + o + " Event: " +
                    // arg);
                    Manager<?> manager = (Manager<?>) o;
                    Assert.assertTrue(manager
                            .equals(ManagerObservableTest.s_manager));
                    ManagerObservableTest.s_observerCalled = true;
                    Event event = (Event) arg;
                    Assert.assertTrue(Event.Type.GET.equals(event.getType()));
                }
                
            };
            
            ManagerObservableTest.s_manager.addObserver(managerObserver);
            
            final Manageable child = ManagerObservableTest.s_manager.get(key);
            Assert.assertTrue(child != null);
            Assert.assertTrue(child.getKey().equals(key));
            Assert.assertTrue(child instanceof MockManagerChildImpl);
            Assert.assertTrue(ManagerObservableTest.s_observerCalled);
        }
        catch (final ManageableException e)
        {
            Assert.fail(e.toString());
        }
        
    }
    
    @Test
    public void testManagerClose()
    {
        
        Observer managerObserver = new Observer()
        {
            
            @Override
            public void update(Observable o, Object arg)
            {
                // System.out.println("Observable: " + o + " Event: " + arg);
                Manager<?> manager = (Manager<?>) o;
                Assert.assertTrue(manager
                        .equals(ManagerObservableTest.s_manager));
                ManagerObservableTest.s_observerCalled = true;
                Event event = (Event) arg;
                Assert.assertTrue(Event.Type.CLOSE.equals(event.getType()));
            }
            
        };
        
        ManagerObservableTest.s_manager.addObserver(managerObserver);
        
        ManagerObservableTest.s_manager.close();
        Assert.assertTrue(ManagerObservableTest.s_observerCalled);
        
    }
    
    // public static final void main(String[] args)
    // {
    // Manager manager = new Manager();
    // Observer managerObserver = new Observer()
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
    // manager.addObserver(managerObserver);
    //
    // try
    // {
    // final ManagerChild child = manager
    // .create(MockManagerChildImpl.class);
    // System.out.println("DONE");
    // }
    // catch (final ManagerChildException e)
    // {
    // System.out.println("Exception: " + e);
    // }
    // }
}
