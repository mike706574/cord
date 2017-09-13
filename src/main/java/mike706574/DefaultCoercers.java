package mike706574;

public class DefaultCoercers {
    public static Coerced toLong( Object x ) {
        if( x instanceof Integer ) {
            return Coerced.value( new Long( (Integer)x ) );
        }
        if( x instanceof String ) {
            try {
                return Coerced.value( Long.parseLong( (String)x ) );
            }
            catch( NumberFormatException ex ) {
                return Coerced.error( "stringToLongFormat" );
            }
        }
        return Coerced.error( "stringToLongUnsupported" );
    }
}
