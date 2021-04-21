// Pair class
class Pair<U, V>
{
    public U first;       // the first field of a pair
    public V second;      // the second field of a pair
 
    // Constructs a new pair with specified values
    public Pair(U first, V second)
    {
        this.first = first;
        this.second = second;
    }

    public U getKey(){return this.first;}
    public V getValue(){return this.second;}
    public void setKey(U first){this.first = first;}
    public void setValue(V second){this.second = second;}
 
    @Override
    // Checks specified object is "equal to" the current object or not
    public boolean equals(Object o)
    {
        if (this == o) {
            return true;
        }
 
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
 
        Pair<?, ?> pair = (Pair<?, ?>) o;
 
        // call `equals()` method of the underlying objects
        if (!first.equals(pair.first)) {
            return false;
        }
        return second.equals(pair.second);
    }
 
    @Override
    // Computes hash code for an object to support hash tables
    public int hashCode()
    {
        // use hash codes of the underlying objects
        return 31 * first.hashCode() + second.hashCode();
    }
 
    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
 
    // Factory method for creating a typed Pair immutable instance
    public static <U, V> Pair <U, V> of(U a, V b)
    {
        // calls public constructor
        return new Pair<>(a, b);
    }
}