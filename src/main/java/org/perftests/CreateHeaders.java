
package org.perftests;

import org.jgroups.Header;
import org.jgroups.conf.ClassConfigurator;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

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
    protected static final short uuid_id=68;
    protected long total;



    @Setup
    public void setup() throws Exception {

    }

    @TearDown
    public void destroy() {
    }


    @Benchmark
    public void createNakackHeader2() throws Throwable {
        Header hdr=ClassConfigurator.create(id);
        total+=hdr.size();
    }


    @Benchmark
    public void createUUID(Blackhole bh) throws Throwable {
        Object obj=ClassConfigurator.create(uuid_id);
        bh.consume(obj);
    }


}
