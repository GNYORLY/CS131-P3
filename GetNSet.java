import java.util.concurrent.atomic.AtomicIntegerArray;

class GetNSet implements State
{
    //instead of byte[], value is now an AtomicIntegerArray
    private AtomicIntegerArray value;
    private byte maxval;
    
    /*since the AtomicIntegerArray constructor only works with int[] or int,
     this function is used to set the AtomicIntegerArray value manually with byte[]*/
    private void byteToAtomicInt(byte[] v)
    {
        value = new AtomicIntegerArray(v.length);
        for(int i = 0; i < v.length; i++)
        {
            value.set(i, v[i]);
        }
    }
    
    /*since one of the functions returns a byte array, this function
     is used to convert the AtomicIntegerArray value back into a byte[]*/
    private byte[] atomicIntToByte()
    {
        byte[] v = new byte[size()];
        for(int i = 0; i < size(); i++)
        {
            v[i] = (byte) value.get(i);
        }
        return v;
    }

    GetNSet(byte[] v)
    {
        byteToAtomicInt(v);
        maxval = 127;
    }

    GetNSet(byte[] v, byte m)
    {
        byteToAtomicInt(v);
        maxval = m;
    }

    public int size() { return value.length(); }

    public byte[] current()
    {
        return atomicIntToByte();
    }

    /*all instances of byte[] operations were converted to their AtomicIntegerArray variants
     since value is now an AtomicIntegerArray*/
    public boolean swap(int i, int j)
    {
        if (value.get(i) <= 0 || value.get(j) >= maxval)
        {
            return false;
        }
        value.getAndDecrement(i);
        value.getAndIncrement(j);
        return true;
    }
}
