import com.carrotsearch.junitbenchmarks.AbstractBenchmark;
import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import org.junit.Test;

/**
 * @since 08/02/15.
 */
public class StKeysBenchmark extends AbstractBenchmark {
    @BenchmarkOptions(benchmarkRounds = 50)


    @Test
    public void prepare() {
        StKeys stKeys = new StKeys();
        stKeys.setEndCharsetLength(4);
        stKeys.prepare();
    }

    @Test
    public void crack() {
        StKeys stKeys = new StKeys();
        stKeys.setStartYear(2005);
        stKeys.setStartYear(2005);
        stKeys.setEndCharsetLength(2);
        stKeys.crack("aaaaaa");
    }


    @Test
    public void toSha1() {
        StKeys stKeys = new StKeys();
        stKeys.toSha1("1234567890");
    }

}
