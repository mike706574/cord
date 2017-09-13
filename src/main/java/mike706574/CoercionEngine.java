package mike706574;

import java.util.function.Function;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import java.util.concurrent.ConcurrentHashMap;

public class CoercionEngine {
    private final Map<String, Function<Object, Coerced<Object>>> coercers;

    public CoercionEngine() {
        this.coercers = new ConcurrentHashMap<String, Function<Object, Coerced<Object>>>();
    }

    public boolean register( String coercionId, Function<Object, Coerced<Object>> coercer ) {
        if( coercers.containsKey( coercionId ) ) {
            return false;
        }
        coercers.put( coercionId, coercer );
        return true;
    }

    public boolean coercionRegistered( String coercionId ) {
        return coercers.containsKey( coercionId );
    }

    public Map<String, Object> coerce( Map<String, Object> data,
                                       Map<String, List<String>> coercions ) {
        Set<String> missingCoercionIds = new HashSet<>();
        Map<String, Object> output = new HashMap<>( data );
        Map<String, String> coercionFailures = new HashMap<>();

        for( Map.Entry<String, List<String>> entry : coercions.entrySet() ) {
            String key = entry.getKey();
            System.out.println( "Processing key " + key + "." );

            List<String> coercionIds = entry.getValue();

            if( !coercionIds.isEmpty() ) {
                boolean successful = true;
                Iterator<String> it = coercionIds.iterator();

                Object value = data.get( key );
                do {
                    String coercionId = it.next();
                    if( !coercionRegistered( coercionId ) ) {
                        missingCoercionIds.add( coercionId );
                        successful = false;
                    }
                    else {
                        System.out.println( "Coercing " + value + " with coercer " + coercionId + "." );
                        Function<Object, Coerced<Object>> coercer = coercers.get( coercionId );

                        Coerced<Object> coerced = coercer.apply( value );

                        if( coerced.isPresent() ) {
                            value = coerced.orElseThrow();
                        }
                        else {
                            coercionFailures.put( key, coerced.getError() );
                            successful = false;
                        }
                    }
                }
                while( successful && it.hasNext() );

                output.put( key, value );
            }
        }

        if( !missingCoercionIds.isEmpty() ) {
            throw new CoercionsNotFoundException( missingCoercionIds );
        }

        return output;
    }
}
