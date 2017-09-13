package mike706574;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import java.util.stream.Collectors;

public class DefaultCoercersTest {
    @Test
    public void intToLong() {
        assertEquals( new Long( 5 ), DefaultCoercers.toLong( 5 ).orElseThrow() );
    }

    @Test
    public void stringToLong() {
        assertEquals( new Long( 5 ), DefaultCoercers.toLong( "5" ).orElseThrow() );
        assertEquals( "stringToLongFormat",
                      DefaultCoercers.toLong( "foo" ).getError() );
    }
}
