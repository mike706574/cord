package mike706574;

import java.util.Set;

public class CoercionsNotFoundException extends RuntimeException {
    public CoercionsNotFoundException( Set<String> coercionIds ) {
        super( "The following coercions were not found: " + String.join( ", ", coercionIds ) );
    }
}
