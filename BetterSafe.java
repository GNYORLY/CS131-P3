import java.util.concurrent.locks.ReentrantLock;

class BetterSafe implements State
{
    private byte[] value;
    private byte maxval;
    private ReentrantLock rlock = new ReentrantLock;

    BetterSafe(byte[] v) { value = v; maxval = 127; }

    BetterSafe(byte[] v, byte m) { value = v; maxval = m; }

    public int size() { return value.length; }

    public byte[] current() { return value; }

    //the critical section of the code is put under the built-in reentrant lock
    //the try-finally ensures that the code is unlocked in case of exceptions
    public boolean swap(int i, int j)
    {
        try{
            rlock.lock();
            if (value[i] <= 0 || value[j] >= maxval)
            {
                return false;
            }
            value[i]--;
            value[j]++;
        }
        finally{
            rlock.unlock();
        }

        return true;
    }
}
