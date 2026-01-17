package blockly_robot.robot.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import blockly_robot.jwinf.en.tasks.conditionals_excercises.find_the_destination.Robot;

class PackageClassLoaderTest
{
    @Test
    @DisplayName("Test instantiateClass with valid class path")
    void testInstantiateClassWithValidClassPath()
            throws ReflectiveOperationException
    {
        var sampleClass = PackageClassLoader.<Robot>instantiateClass(
            "en.tasks.conditionals_excercises.find_the_destination.Robot");
        assertNotNull(sampleClass);
        assertEquals(sampleClass.getClass().getName(),
            "blockly_robot.jwinf.en.tasks.conditionals_excercises.find_the_destination.Robot");
    }

    @Test
    @DisplayName("Test instantiateClass with invalid class path")
    void testInstantiateClassWithInvalidClassPath()
    {
        assertThrows(ReflectiveOperationException.class, () -> {
            PackageClassLoader.instantiateClass("invalid.class.path");
        });
    }
}
