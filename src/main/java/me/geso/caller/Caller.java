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

/**
 *
 * @author Tokuhiro Matsuno <tokuhirom@gmail.com>
 */
public class Caller {

	static boolean initialized = false;
	static Method getStackTraceElement;

	@SuppressWarnings("ThrowableInstanceNotThrown")
	public static StackTraceElement caller(int n) throws CallerExcetion {
		if (!initialized) {
			Caller.initialize();
		}

		if (Caller.getStackTraceElement != null) {
			try {
				return (StackTraceElement) Caller.getStackTraceElement.invoke(new Throwable(), n + 1);
			} catch (InvocationTargetException | IllegalAccessException | IllegalArgumentException ex) {
				throw new CallerExcetion(ex);
			}
		} else {
			return new Throwable().getStackTrace()[n + 1];
		}
	}

	private static synchronized void initialize() {
		try {
			initializeReflectionStrategy();
		} catch (NoSuchMethodException ex) {
			Logger.getLogger(Caller.class.getName()).log(Level.SEVERE, null, "Throwable.getStackTraceElement is not available: " + ex);
		} finally {
			initialized = true;
		}
	}

	private static void initializeReflectionStrategy() throws NoSuchMethodException {
		Method method = Throwable.class.getDeclaredMethod("getStackTraceElement", int.class);
		method.setAccessible(true);
		Caller.getStackTraceElement = method;
	}

	public static class CallerExcetion extends Exception {
		public CallerExcetion(java.lang.Exception ex) {
			super(ex);
		}
	}

}
