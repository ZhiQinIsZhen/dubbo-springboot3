package com.liyz.boot3.common.es.io;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Properties;

/**
 * Desc:
 *
 * @author lyz
 * @version 1.0.0
 * @date 2023/12/22 15:58
 */
public class Resources {

    private static final ClassLoaderWrapper classLoaderWrapper = new ClassLoaderWrapper();
    private static Charset charset;

    public static ClassLoader getDefaultClassLoader() {
        return classLoaderWrapper.defaultClassLoader;
    }

    public static void setDefaultClassLoader(ClassLoader defaultClassLoader) {
        classLoaderWrapper.defaultClassLoader = defaultClassLoader;
    }

    public static URL getResourceURL(String resource) throws IOException {
        return getResourceURL(null, resource);
    }

    public static URL getResourceURL(ClassLoader loader, String resource) throws IOException {
        URL url = classLoaderWrapper.getResourceAsURL(resource, loader);
        if (url == null) {
            throw new IOException("Could not find resource " + resource);
        } else {
            return url;
        }
    }

    public static InputStream getResourceAsStream(String resource) throws IOException {
        return getResourceAsStream(null, resource);
    }

    public static InputStream getResourceAsStream(ClassLoader loader, String resource) throws IOException {
        InputStream in = classLoaderWrapper.getResourceAsStream(resource, loader);
        if (in == null) {
            throw new IOException("Could not find resource " + resource);
        } else {
            return in;
        }
    }

    public static Properties getResourceAsProperties(String resource) throws IOException {
        Properties props = new Properties();
        InputStream in = getResourceAsStream(resource);

        try {
            props.load(in);
        } catch (Throwable var6) {
            if (in != null) {
                try {
                    in.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }
            }

            throw var6;
        }

        if (in != null) {
            in.close();
        }

        return props;
    }

    public static Properties getResourceAsProperties(ClassLoader loader, String resource) throws IOException {
        Properties props = new Properties();
        InputStream in = getResourceAsStream(loader, resource);

        try {
            props.load(in);
        } catch (Throwable var7) {
            if (in != null) {
                try {
                    in.close();
                } catch (Throwable var6) {
                    var7.addSuppressed(var6);
                }
            }

            throw var7;
        }

        if (in != null) {
            in.close();
        }

        return props;
    }

    public static Reader getResourceAsReader(String resource) throws IOException {
        InputStreamReader reader;
        if (charset == null) {
            reader = new InputStreamReader(getResourceAsStream(resource));
        } else {
            reader = new InputStreamReader(getResourceAsStream(resource), charset);
        }

        return reader;
    }

    public static Reader getResourceAsReader(ClassLoader loader, String resource) throws IOException {
        InputStreamReader reader;
        if (charset == null) {
            reader = new InputStreamReader(getResourceAsStream(loader, resource));
        } else {
            reader = new InputStreamReader(getResourceAsStream(loader, resource), charset);
        }

        return reader;
    }

    public static File getResourceAsFile(String resource) throws IOException {
        return new File(getResourceURL(resource).getFile());
    }

    public static File getResourceAsFile(ClassLoader loader, String resource) throws IOException {
        return new File(getResourceURL(loader, resource).getFile());
    }

    public static InputStream getUrlAsStream(String urlString) throws IOException {
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        return conn.getInputStream();
    }

    public static Reader getUrlAsReader(String urlString) throws IOException {
        InputStreamReader reader;
        if (charset == null) {
            reader = new InputStreamReader(getUrlAsStream(urlString));
        } else {
            reader = new InputStreamReader(getUrlAsStream(urlString), charset);
        }

        return reader;
    }

    public static Properties getUrlAsProperties(String urlString) throws IOException {
        Properties props = new Properties();
        InputStream in = getUrlAsStream(urlString);

        try {
            props.load(in);
        } catch (Throwable var6) {
            if (in != null) {
                try {
                    in.close();
                } catch (Throwable var5) {
                    var6.addSuppressed(var5);
                }
            }

            throw var6;
        }

        if (in != null) {
            in.close();
        }

        return props;
    }

    public static Class<?> classForName(String className) throws ClassNotFoundException {
        return classLoaderWrapper.classForName(className);
    }

    public static Charset getCharset() {
        return charset;
    }

    public static void setCharset(Charset charset) {
        Resources.charset = charset;
    }
}
