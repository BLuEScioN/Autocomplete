/******************************************************************************
  *  Name:    Nick Barnett
  *  NetID:   nrbarnett
  *  Precept: P04
  *
  *  Partner Name:    Maegan Richards
  *  Partner NetID:   maeganr
  *  Partner Precept: P03
  * 
  *  Description:   When binary searching a sorted array that contains more 
  *  than one key equal to the search key, the client may want to know the 
  *  index of either the first or the last such key.
******************************************************************************/
import java.util.Comparator;

public class BinarySearchDeluxe {
    
    // Returns the index of the first key in a[] that equals the search key,
    //or -1 if no such key.
    public static <Key> int firstIndexOf(Key[] a, Key key, 
                                         Comparator<Key> comparator) {
        if ((a == null || key == null) || comparator == null) {
            throw new NullPointerException(); 
        }
        int lo = 0;
        int hi = a.length - 1;
        // int round = 0;
        int ph = -1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            //System.out.println("round " + round + ": lo->" + lo + " mid->" +
            //mid + " hi->" + hi);
            int comp = comparator.compare(key, a[mid]);
            if      (comp < 0) hi = mid - 1;
            else if (comp > 0) lo = mid + 1;
            else {
                ph = mid;
                hi = mid - 1;
            }
//Key is found, but the rest of the array to the left has not been checked, so
//restart BS
            //round++;
        }
        return ph;
    }
    
    // Returns the index of the last key in a[] that equals the search key, 
    //or -1 if no such key.
    public static <Key> int lastIndexOf(Key[] a, Key key, 
                                        Comparator<Key> comparator) {
        if ((a == null || key == null) || comparator == null) { 
            throw new NullPointerException(); 
        }
        int lo = 0;
        int hi = a.length - 1;
        //int round = 0;
        int ph = -1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            //System.out.println("round " + round + ": lo->" + lo + " mid->" +
            //mid + " hi->" + hi);
            int comp = comparator.compare(key, a[mid]);
            if      (comp < 0) hi = mid - 1;
            else if (comp > 0) lo = mid + 1;
            else {
                ph = mid;
                lo = mid + 1;
            }
//Key is found, but the rest of the array to the right has not been checked, 
//so restart BS
            //round++;
        }
        return ph;
    }
    
    public static void main(String[] args) {
        Term[] a = new Term[9];
        a[0] = new Term("Dawn of the Planet of the Apes", 100);
        a[1] = new Term("Fear and Loathing in Los Vegas", 1);
        a[2] = new Term("Great Gatsby", 10);
        a[3] = new Term("Phantom of the Opera", 10);
        a[4] = new Term("Planet Earth", 10);
        a[5] = new Term("Planet of the Apes", 10);
        a[6] = new Term("Planet of the Apes 2", 10);
        a[7] = new Term("Platform 9 3/4", 10);
        a[8] = new Term("Rise of the Planet of the Apes", 10);
        Term key = new Term("Plan", 0);
        Comparator<Term> c = Term.byPrefixOrder(4);
        int one = firstIndexOf(a, key, c);
        int two = lastIndexOf(a, key, c);
        System.out.println(one + "\t" + two);
    }
}