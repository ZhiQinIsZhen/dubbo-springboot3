package com.liyz.boot3.common.util;

import lombok.experimental.UtilityClass;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/30 14:41
 */
@UtilityClass
public class ResourceUtil {

    private static final ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper();
    private static final ClassLoaderWrapper WRAPPER = new ClassLoaderWrapper();

    /**
     * Loads a class
     *
     * @param className
     *          - the class to fetch
     *
     * @return The loaded class
     *
     * @throws ClassNotFoundException
     *           If the class cannot be found (duh!)
     */
    public static Class<?> classForName(String className) throws ClassNotFoundException {
        return WRAPPER.classForName(className);
    }

    /**
     * Returns the default classloader (may be null).
     *
     * @return The default classloader
     */
    public static ClassLoader getDefaultClassLoader() {
        return classLoaderWrapper.defaultClassLoader;
    }

    /**
     * Sets the default classloader
     *
     * @param defaultClassLoader
     *          - the new default ClassLoader
     */
    public static void setDefaultClassLoader(ClassLoader defaultClassLoader) {
        classLoaderWrapper.defaultClassLoader = defaultClassLoader;
    }

    class ClassLoaderWrapper {

        ClassLoader defaultClassLoader;
        private final ClassLoader systemClassLoader;

        public ClassLoaderWrapper() {
            this.systemClassLoader = ClassLoader.getSystemClassLoader();
        }

        public Class<?> classForName(String name) throws ClassNotFoundException {
            return this.classForName(name, this.getClassLoaders(null));
        }

        private Class<?> classForName(String name, ClassLoader[] classLoaders) throws ClassNotFoundException {
            for (ClassLoader classLoader : classLoaders) {
                if (classLoader != null) {
                    try {
                        return Class.forName(name, true, classLoader);
                    } catch (ClassNotFoundException ignored) {
                    }
                }
            }
            throw new ClassNotFoundException("Cannot find class: " + name);
        }

        private ClassLoader[] getClassLoaders(ClassLoader classLoader) {
            return new ClassLoader[]{classLoader, defaultClassLoader, Thread.currentThread().getContextClassLoader(),
                    this.getClass().getClassLoader(), this.systemClassLoader};
        }
    }
}
