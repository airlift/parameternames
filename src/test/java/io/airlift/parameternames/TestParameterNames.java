/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.airlift.parameternames;

import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

import static io.airlift.parameternames.ParameterNames.getParameterNames;
import static io.airlift.parameternames.ParameterNames.getParameterNamesFromBytecode;
import static java.util.Arrays.asList;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class TestParameterNames
{
    @Test
    public void testReadParameterNames()
            throws Exception
    {
        assertParameters(TestMethods.class.getMethod("method"));

        assertParameters(TestMethods.class.getMethod(
                        "method",
                        boolean.class,
                        byte.class,
                        char.class,
                        short.class,
                        int.class,
                        long.class,
                        float.class,
                        double.class,
                        String.class),
                "a", "b", "c", "d", "e", "f", "g", "h", "i");

        assertParameters(TestMethods.class.getMethod(
                        "method",
                        boolean[].class,
                        byte[].class,
                        char[].class,
                        short[].class,
                        int[].class,
                        long[].class,
                        float[].class,
                        double[].class,
                        String[].class),
                "a", "b", "c", "d", "e", "f", "g", "h", "i");

        assertParameters(TestMethods.class.getMethod(
                        "method",
                        List.class,
                        List.class,
                        List.class,
                        List.class,
                        List.class,
                        List.class,
                        List.class,
                        List.class,
                        List.class),
                "a", "b", "c", "d", "e", "f", "g", "h", "i");

        assertParameters(TestMethods.class.getMethod(
                        "staticMethod",
                        boolean.class,
                        byte.class,
                        char.class,
                        short.class,
                        int.class,
                        long.class,
                        float.class,
                        double.class,
                        String.class),
                "a", "b", "c", "d", "e", "f", "g", "h", "i");

        assertParameters(false,
                TestMethods.class.getMethod(
                        "abstractMethod",
                        boolean.class,
                        byte.class,
                        char.class,
                        short.class,
                        int.class,
                        long.class,
                        float.class,
                        double.class,
                        String.class),
                "a", "b", "c", "d", "e", "f", "g", "h", "i");

        assertParameters(false,
                TestInterface.class.getMethod(
                        "interfaceMethod",
                        boolean.class,
                        byte.class,
                        char.class,
                        short.class,
                        int.class,
                        long.class,
                        float.class,
                        double.class,
                        String.class),
                "a", "b", "c", "d", "e", "f", "g", "h", "i");
    }

    private static void assertParameters(Method method, String... expectedParameterNames)
    {
        assertParameters(true, method, expectedParameterNames);
    }

    private static void assertParameters(boolean hasByteCode, Method method, String... expectedParameterNames)
    {
        assertTrue(asList(method.getParameters()).stream().allMatch(Parameter::isNamePresent),
                "This code must be compiled with the '-parameters' option to javac");
        assertEquals(getParameterNames(method), asList(expectedParameterNames));
        if (hasByteCode) {
            assertEquals(getParameterNamesFromBytecode(method).get(), asList(expectedParameterNames));
        }
    }

    @SuppressWarnings("unused")
    public abstract static class TestMethods
    {
        public void method()
        {
        }

        public void method(boolean a, byte b, char c, short d, int e, long f, float g, double h, String i)
        {
        }

        public void method(boolean[] a, byte[] b, char[] c, short[] d, int[] e, long[] f, float[] g, double[] h, String[] i)
        {
        }

        public void method(List<boolean[]> a, List<byte[]> b, List<char[]> c, List<short[]> d, List<int[]> e, List<long[]> f, List<float[]> g, List<double[]> h, List<String[]> i)
        {
        }

        public static void staticMethod(boolean a, byte b, char c, short d, int e, long f, float g, double h, String i)
        {
        }

        public abstract void abstractMethod(boolean a, byte b, char c, short d, int e, long f, float g, double h, String i);
    }

    @SuppressWarnings("unused")
    public interface TestInterface
    {
        void interfaceMethod(boolean a, byte b, char c, short d, int e, long f, float g, double h, String i);
    }
}
