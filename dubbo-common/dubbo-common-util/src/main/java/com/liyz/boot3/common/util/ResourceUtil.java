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

    private static final ClassLoaderWrapper WRAPPER = new ClassLoaderWrapper();

    public static Class<?> classForName(String className) throws ClassNotFoundException {
        return WRAPPER.classForName(className);
    }

    class ClassLoaderWrapper {

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
            return new ClassLoader[]{classLoader, Thread.currentThread().getContextClassLoader(),
                    this.getClass().getClassLoader(), this.systemClassLoader};
        }
    }
}
