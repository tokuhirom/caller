/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package me.geso.caller;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import me.geso.nanobench.Benchmark;

/**
 *
 * @author Tokuhiro Matsuno <tokuhirom@gmail.com>
 */
public class CallerTest {

	public CallerTest() {
	}

	@Test
	public void testCaller() throws Exception {
		assertEquals("me.geso.caller.CallerTest", Caller.caller(0).getClassName());
		testit();
		if (Caller.getStackTraceElement != null) {
			// Reflection method was enabled... Testing with normal way.

			// Clear the native method information.
			Caller.getStackTraceElement = null;

			// run test again.
			testit();
		}
	}

	@Test
	public void testBenchmark() throws Exception {
		Caller.initialized = false;
		if (!Caller.privateMethodAvailable()) {
			System.out.println("Reflection unavailable in your environment...");
			return;
		}

		new Benchmark().add("normal", new Benchmark.Code() {
			@SuppressWarnings("ThrowableInstanceNotThrown")
			@Override
			public void run() {
				StackTraceElement e = new Throwable().getStackTrace()[1];
			}
		}).add("Reflection", new Benchmark.Code() {
			@Override
			public void run() {
				try {
					Caller.caller(0);
				} catch (Caller.CallerExcetion ex) {
					Logger.getLogger(CallerTest.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}).runByTime(1).timethese().cmpthese();
	}

	private void testit() throws Caller.CallerExcetion {
		assertEquals("me.geso.caller.CallerTest", Caller.caller(0).getClassName());
		assertEquals("testit", Caller.caller(0).getMethodName());
		assertEquals("testCaller", Caller.caller(1).getMethodName());
	}

}
