package pi.graphics.boxes;

public class ContainerizedChild<T extends Box>
{
    public ContainerBox container;

    public T child;

    @SuppressWarnings("unchecked")
    public ContainerizedChild(ContainerBox container)
    {
        this.container = container;
        this.child = (T) container.child;
    }
}
