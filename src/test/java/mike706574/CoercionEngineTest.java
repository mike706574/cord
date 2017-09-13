package mike706574;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import java.util.stream.Collectors;

import java.util.function.Function;

public class CoercionEngineTest {
    private CoercionEngine engine;

    public void setUp() {
        CoercionEngine engine = new CoercionEngine();
        engine.register( "toLong", DefaultCoercers::toLong );
        engine.register( "toLong", DefaultCoercers::toLong );
    }

    @Test
    public void singleSuccessfulCoercion() {
        Map<String, Object> data = new HashMap<>();
        data.put( "foo", "5" );

        Map<String, List<String>> coercions = new HashMap<>();
        coercions.put( "foo", Arrays.asList( "stringToLong" ) );

        Map<String, Object> coercedData = engine.coerce( data, coercions );

        assertEquals( new Long( 5 ), coercedData.get( "foo" ) );
    }

    @Test
    public void singleFailedCoercion() {
        CoercionEngine engine = new CoercionEngine();

        engine.register( "intToLong", DefaultCoercers::toLong );
        engine.register( "stringToLong", DefaultCoercers::toLong );

        Map<String, Object> data = new HashMap<>();
        data.put( "foo", "5" );

        Map<String, List<String>> coercions = new HashMap<>();
        coercions.put( "foo", Arrays.asList( "stringToLong" ) );

        Map<String, Object> coercedData = engine.coerce( data, coercions );

        assertEquals( new Long( 5 ), coercedData.get( "foo" ) );
    }
}
