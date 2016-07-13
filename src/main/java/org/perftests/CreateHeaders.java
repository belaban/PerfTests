
package org.perftests;

import org.jgroups.Header;
import org.jgroups.conf.ClassConfigurator;
import org.openjdk.jmh.annotations.*;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode({Mode.AverageTime})
@Warmup(iterations=5)
@Measurement(timeUnit=TimeUnit.NANOSECONDS,iterations=5)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(value=1)
@Threads(25)
public class CreateHeaders {
    protected static final short id=93;
    protected long total;
    protected static final Class<?> clazz;
    protected static final Constructor<?> constructor;
    protected static final MethodType mt;
    protected static final MethodHandle mh;
    protected static final MethodHandles.Lookup lookup=MethodHandles.publicLookup();
    protected static final MethodHandle handle;


    static {
        try {
            clazz=ClassConfigurator.get(id);
            constructor=clazz.getConstructor();
            mt=MethodType.methodType(void.class);
            mh=lookup.findConstructor(clazz, mt);
            MethodType tmp_type=mh.type().changeReturnType(Header.class);
            handle=mh.asType(tmp_type);
        }
        catch(Throwable t) {
            throw new RuntimeException(t);
        }
    }

    @Setup
    public void setup() throws Exception {
        System.out.println("-- setup()");

    }

    @TearDown
    public void destroy() {
        System.out.println("-- destroy()");
    }


    @Benchmark
    public void createWithReflection() throws Exception {
        Header hdr=(Header)clazz.newInstance();
        total+=hdr.size();
    }
/*
   @Benchmark
   public void createWithMethodHandle() throws Throwable {
       Header hdr=ClassConfigurator.create(id);
       total+=hdr.size();
   }*/




}
