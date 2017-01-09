package com.semion.demo.cglib;

import com.semion.demo.invocationHandle.Hello;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.UndeclaredThrowableException;

public final class $Proxy0 extends Proxy implements Hello {
    private static Method m1;
    private static Method m2;
    private static Method m6;
    private static Method m3;
    private static Method m4;
    private static Method m5;
    private static Method m0;

    public $Proxy0(InvocationHandler paramInvocationHandler) {
        super(paramInvocationHandler);
    }

    public final boolean equals(Object paramObject) {
        try {
            return ((Boolean) this.h.invoke(this, m1, new Object[]{paramObject})).booleanValue();
        } catch (RuntimeException localRuntimeException) {
            throw localRuntimeException;
        } catch (Throwable localThrowable) {
            throw new UndeclaredThrowableException(localThrowable);
        }
    }

    public final String toString() {
        try {
            return ((String) this.h.invoke(this, m2, null));
        } catch (RuntimeException localRuntimeException) {
            throw localRuntimeException;
        } catch (Throwable localThrowable) {
            throw new UndeclaredThrowableException(localThrowable);
        }
    }

    public final void dispaly() {
        try {
            this.h.invoke(this, m6, null);
            return;
        } catch (RuntimeException localRuntimeException) {
            throw localRuntimeException;
        } catch (Throwable localThrowable) {
            throw new UndeclaredThrowableException(localThrowable);
        }
    }

    public final void setInfo(String paramString1, String paramString2) {
        try {
            this.h.invoke(this, m3, new Object[]{paramString1, paramString2});
            return;
        } catch (RuntimeException localRuntimeException) {
            throw localRuntimeException;
        } catch (Throwable localThrowable) {
            throw new UndeclaredThrowableException(localThrowable);
        }
    }

    public final String getinfos1() {
        try {
            return ((String) this.h.invoke(this, m4, null));
        } catch (RuntimeException localRuntimeException) {
            throw localRuntimeException;
        } catch (Throwable localThrowable) {
            throw new UndeclaredThrowableException(localThrowable);
        }
    }

    public final String getinfos2() {
        try {
            return ((String) this.h.invoke(this, m5, null));
        } catch (RuntimeException localRuntimeException) {
            throw localRuntimeException;
        } catch (Throwable localThrowable) {
            throw new UndeclaredThrowableException(localThrowable);
        }
    }

    public final int hashCode() {
        try {
            return ((Integer) this.h.invoke(this, m0, null)).intValue();
        } catch (RuntimeException localRuntimeException) {
            throw localRuntimeException;
        } catch (Throwable localThrowable) {
            throw new UndeclaredThrowableException(localThrowable);
        }
    }

    static {
        try {
            m1 = Class.forName("java.lang.Object").getMethod("equals", new Class[]{Class.forName("java.lang.Object")});
            m2 = Class.forName("java.lang.Object").getMethod("toString", new Class[0]);
            m6 = Class.forName("com.semion.demo.invocationHandle.Hello").getMethod("dispaly", new Class[0]);
            m3 = Class.forName("com.semion.demo.invocationHandle.Hello").getMethod("setInfo", new Class[]{Class.forName("java.lang.String"), Class.forName("java.lang.String")});
            m4 = Class.forName("com.semion.demo.invocationHandle.Hello").getMethod("getinfos1", new Class[0]);
            m5 = Class.forName("com.semion.demo.invocationHandle.Hello").getMethod("getinfos2", new Class[0]);
            m0 = Class.forName("java.lang.Object").getMethod("hashCode", new Class[0]);
        } catch (NoSuchMethodException localNoSuchMethodException) {
            throw new NoSuchMethodError(localNoSuchMethodException.getMessage());
        } catch (ClassNotFoundException localClassNotFoundException) {
            throw new NoClassDefFoundError(localClassNotFoundException.getMessage());
        }
    }
}
