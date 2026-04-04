package pi.resources;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

public class ResourcesContainerTest
{
    @Test
    public void getCachesAndForceLoadReloads()
    {
        var container = new TestContainer();

        TestResource first = container.get("hero");
        TestResource second = container.get("hero");
        TestResource reloaded = container.get("hero", true);

        assertSame(first, second);
        assertEquals(new TestResource("resource-1"), first);
        assertEquals(new TestResource("resource-2"), reloaded);
        assertEquals(2, container.loadCount);
    }

    @Test
    public void getWithSupplierLoadsOnlyOnce()
    {
        var container = new TestContainer();
        var supplierCalls = new AtomicInteger();

        TestResource first = container.get("coin", () -> {
            supplierCalls.incrementAndGet();
            return new TestResource("coin-resource");
        });
        TestResource second = container.get("coin", () -> {
            supplierCalls.incrementAndGet();
            return new TestResource("other-resource");
        });

        assertSame(first, second);
        assertEquals(new TestResource("coin-resource"), first);
        assertEquals(1, supplierCalls.get());
    }

    @Test
    public void addManipulatorTransformsResourceAndListenerGetsTransformedValue()
    {
        var container = new TestContainer();
        var listener = new RecordingListener();
        container.addContainerListener(listener);
        container.addManipulator(
            (name, resource) -> new TestResource(resource.value() + "-m"));

        TestResource added = container.add("x", new TestResource("payload"));

        assertEquals(new TestResource("payload-m"), added);
        assertTrue(container.contains("x"));
        assertEquals("x", listener.addedName);
        assertEquals(new TestResource("payload-m"), listener.addedResource);
    }

    @Test
    public void removeNotifiesListener()
    {
        var container = new TestContainer();
        var listener = new RecordingListener();
        container.addContainerListener(listener);
        container.add("to-remove", new TestResource("data"));

        TestResource removed = container.remove("to-remove");

        assertEquals(new TestResource("data"), removed);
        assertFalse(container.contains("to-remove"));
        assertEquals("to-remove", listener.removedName);
        assertEquals(new TestResource("data"), listener.removedResource);
    }

    @Test
    public void aliasCanResolveLoadedResource()
    {
        var container = new TestContainer();
        container.useAlias = true;

        TestResource byName = container.get("tree");
        TestResource byAlias = container.get("alias:tree");

        assertNotNull(byName);
        assertSame(byName, byAlias);
    }

    @Test
    public void loadExceptionIsWrapped()
    {
        var container = new TestContainer();
        container.throwOnLoad = true;

        assertThrows(ResourceLoadException.class,
            () -> container.get("missing", true));
    }

    record TestResource(String value)
    {
    }

    static class TestContainer extends ResourcesContainer<TestResource>
    {
        int loadCount;

        boolean useAlias;

        boolean throwOnLoad;

        @Override
        protected TestResource load(URL name) throws Exception
        {
            loadCount++;
            if (throwOnLoad)
            {
                throw new IOException("load failed");
            }
            return new TestResource("resource-" + loadCount);
        }

        @Override
        protected String alias(String name, TestResource resource)
        {
            if (useAlias)
            {
                return "alias:" + name;
            }
            return null;
        }
    }

    static class RecordingListener
            implements ResourcesContainerListener<TestResource>
    {
        String addedName;

        TestResource addedResource;

        String removedName;

        TestResource removedResource;

        @Override
        public void added(String resourceName, TestResource resource)
        {
            this.addedName = resourceName;
            this.addedResource = resource;
        }

        @Override
        public void removed(String resourceName, TestResource resource)
        {
            this.removedName = resourceName;
            this.removedResource = resource;
        }
    }
}
