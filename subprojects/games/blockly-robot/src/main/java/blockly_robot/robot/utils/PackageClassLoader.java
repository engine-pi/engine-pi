package blockly_robot.robot.utils;

public class PackageClassLoader
{
    /**
     * Instantiates a class based on the given relative class path.
     *
     * @param <O> the type of the object to be instantiated
     * @param relClassPath the relative class path (relative to the package
     *     blockly_robot.jwinf)
     *
     * @return an instance of the specified class
     *
     * @throws ReflectiveOperationException if the class cannot be found or
     *     instantiated
     */
    @SuppressWarnings("unchecked")
    public static <O> O instantiateClass(String relClassPath)
            throws ReflectiveOperationException
    {
        Class<?> cls = Class
            .forName("blockly_robot.jwinf." + relClassPath.replace("/", "."));
        O object = (O) cls.getDeclaredConstructor().newInstance();
        return object;
    }
}
