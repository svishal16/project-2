package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class HelloWorldTest {
    @Test
    public void testGreet() {
        HelloWorld hw = new HelloWorld();
        assertEquals("Hello, World", hw.greet("World"));
    }
}
