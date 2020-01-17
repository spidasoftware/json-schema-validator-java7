package com.networknt.schema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.DecimalNode;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.TextNode;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertTrue;

@Ignore
public class MaximumValidatorPerfTest {
    MaximumValidatorTest test = new MaximumValidatorTest();

    @Test
    public void testTime() throws InvocationTargetException, IllegalAccessException {
        String[] testMethodsToBeExecuted = {"testMaximumDoubleValue"};
        List<Method> testMethods = getTestMethods(testMethodsToBeExecuted);
        long start = System.currentTimeMillis();
        executeTests(testMethods, 200000);
        long end = System.currentTimeMillis();
        System.out.println("time to execute all tests using:" + (end - start) +"ms");
    }

    public void executeTests(List<Method> methods, int executeTimes) throws InvocationTargetException, IllegalAccessException {

        for(int i = 0; i < executeTimes; i++) {
            for(Method testMethod : methods) {
                testMethod.invoke(test);
            }
        }

    }

    public List<Method> getTestMethods(String[] methodNames) {
        Method[] methods = test.getClass().getMethods();
        List<Method> testMethods = new ArrayList<Method>();
        if(methodNames.length > 0) {
            for(String name : methodNames) {
                for (Method method: methods) {
                    if (method.getName().equals(name)) {
                        testMethods.add(method);
                    }
                }
            }
            return testMethods;
        }
        for (Method m : methods) {
            Annotation[] annotations = m.getDeclaredAnnotations();
            boolean isTestMethod = false;
            for(Annotation annotation : annotations) {
                if(annotation.annotationType() == Test.class) {
                    isTestMethod = true;
                }
            }
            if(isTestMethod) {
                //filter out incompatible test cases.
                if(!m.getName().equals("doubleValueCoarsing") && !m.getName().equals("negativeDoubleOverflowTest"))
                testMethods.add(m);
            }
        }
        return testMethods;
    }
}
