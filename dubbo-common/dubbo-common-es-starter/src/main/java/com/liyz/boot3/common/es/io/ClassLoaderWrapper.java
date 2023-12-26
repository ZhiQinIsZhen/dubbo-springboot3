package com.liyz.boot3.common.es.io;

import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/22 15:58
 */
public class ClassLoaderWrapper {

    ClassLoader defaultClassLoader;
    ClassLoader systemClassLoader;

    public ClassLoaderWrapper() {
        try {
            this.systemClassLoader = ClassLoader.getSystemClassLoader();
        } catch (SecurityException e) {
        }
    }

    public URL getResourceAsURL(String resource) {
        return this.getResourceAsURL(resource, this.getClassLoaders(null));
    }

    public URL getResourceAsURL(String resource, ClassLoader classLoader) {
        return this.getResourceAsURL(resource, this.getClassLoaders(classLoader));
    }

    public InputStream getResourceAsStream(String resource) {
        return this.getResourceAsStream(resource, this.getClassLoaders(null));
    }

    public InputStream getResourceAsStream(String resource, ClassLoader classLoader) {
        return this.getResourceAsStream(resource, this.getClassLoaders(classLoader));
    }

    public Class<?> classForName(String name) throws ClassNotFoundException {
        return this.classForName(name, this.getClassLoaders(null));
    }

    public Class<?> classForName(String name, ClassLoader classLoader) throws ClassNotFoundException {
        return this.classForName(name, this.getClassLoaders(classLoader));
    }

    private InputStream getResourceAsStream(String resource, ClassLoader[] classLoader) {
        for (int i = 0, j = classLoader.length; i < j; i++) {
            ClassLoader item = classLoader[i];
            if (Objects.nonNull(item)) {
                InputStream returnValue = item.getResourceAsStream(resource);
                if (Objects.isNull(returnValue)) {
                    returnValue = item.getResourceAsStream("/" + resource);
                }
                return returnValue;
            }
        }
        return null;
    }

    private URL getResourceAsURL(String resource, ClassLoader[] classLoader) {
        for (int i = 0, j = classLoader.length; i < j; i++) {
            ClassLoader item = classLoader[i];
            if (Objects.nonNull(item)) {
                URL url = item.getResource(resource);
                if (Objects.isNull(url)) {
                    url = item.getResource("/" + resource);
                }
                return url;
            }
        }
        return null;
    }

    private Class<?> classForName(String name, ClassLoader[] classLoader) throws ClassNotFoundException {
        for (int i = 0, j = classLoader.length; i < j; i++) {
            ClassLoader item = classLoader[i];
            if (Objects.nonNull(item)) {
                try {
                    return Class.forName(name, true, item);
                } catch (ClassNotFoundException e) {

                }
            }
        }
        throw new ClassNotFoundException("Cannot find class: " + name);
    }

    ClassLoader[] getClassLoaders(ClassLoader classLoader) {
        return new ClassLoader[]{classLoader, this.defaultClassLoader, Thread.currentThread().getContextClassLoader(), this.getClass().getClassLoader(), this.systemClassLoader};
    }
}
