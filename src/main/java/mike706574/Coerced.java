package mike706574;

import java.util.Optional;

import java.util.function.Function;

public class Coerced<T> {
    private final T value;
    private final String error;

    private Coerced( T value, String error ) {
        this.value = value;
        this.error = error;
    }

    public static <U> Coerced<U> value( U value ) {
        return new Coerced<U>( value, null );
    }

    public static <U> Coerced<U> error( String error ) {
        return new Coerced<U>( null, error );
    }

    public boolean isPresent() {
        return this.value != null;
    }

    public T orElse( T other ) {
        if( this.value == null ) {
            return other;
        }
        return this.value;
    }

    public String getError() {
        if( this.error == null ) {
            throw new NoErrorException();
        }
        return this.error;
    }

    public T orElseThrow() {
        if( this.value == null ) {
            throw new CoercionException( this.error );
        }
        return this.value;
    }

    public <X extends Throwable> T orElseThrow( Function<String, ? extends X> errorFunction ) throws X {
        if( this.value == null ) {
            throw errorFunction.apply( this.error );
        }
        return this.value;
    }
}
