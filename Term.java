/******************************************************************************
  *  Name:    Nick Barnett
  *  NetID:   nrbarnett
  *  Precept: P04
  *
  *  Partner Name:    Maegan Richards
  *  Partner NetID:   maeganr
  *  Partner Precept: P03
  * 
  *  Description: Writes an immutable data type Term.java that represents an
  *  autocomplete term: a query string and an associated integer weight. 
  *  Supports comparing terms by three different orders: lexicographic order by
  *  query string (the natural order); in descending order by weight
  *  (an alternate order); and lexicographic order by query string but using
  *  only the first r characters (a family of alternate orderings).
******************************************************************************/
import java.util.Comparator;
import edu.princeton.cs.algs4.StdOut;

public class Term implements Comparable<Term> {
    
    private final String query; //holds a term that can be searched. immutable.
    private final long weight;//holds a term that can be searched. immutable.
    
    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null) { throw new NullPointerException(); }
        if (weight < 0) { throw new IllegalArgumentException(); }
        this.query = query;
        this.weight = weight;
    }
    
    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        // if (query == null) { throw new NullPointerException(); }
        // if (weight < 0) { throw new IllegalArgumentException(); }
        return new ReverseWeightOrder();
    }
    
    private static class ReverseWeightOrder implements Comparator<Term> {
        public ReverseWeightOrder() {}
        
        public int compare(Term v, Term w) {
           if (v.weight > w.weight) return -1;
           if (v.weight < w.weight) return +1;
           else return 0;
        }
    }
    
    // Compares the two terms in lexicographic order but using only the first r
    // characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0) { throw new IllegalArgumentException(); }
        return new PrefixOrder(r);
    }
    
    private static class PrefixOrder implements Comparator<Term> {
        private final int r;
        public PrefixOrder(int r) {
            this.r = r;
        }
    
        public int compare(Term v, Term w) {
            for (int i = 0; i < r; i++) {
//                System.out.println(v.query.charAt(i)); //check
//                System.out.println(w.query.charAt(i)); //check
                if (i >= v.query.length()) { return v.compareTo(w); }
                if (i >= w.query.length()) { return v.compareTo(w); }
                if (v.query.charAt(i) != w.query.charAt(i)) { 
                    return v.compareTo(w); 
                }
            }
            return 0;        
        }
    }
    
    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        if (this.query.compareTo(that.query) < 0) return -1; 
// in lexographic order 
        if (this.query.compareTo(that.query) > 0) return 1; 
// not in lexographic order
        return 0;
    }
    
    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        String t = weight + "\t" + query;
        return t;
    }
    
    public static void main(String[] args) {
        String a = args[0];
        String b = args[1];
        long aA = Long.parseLong(args[2]);
        long bB = Long.parseLong(args[3]);
        Term first = new Term(a, aA);
        Term second = new Term(b, bB);
        // Term test1 = new Term(null, aA);
        // Term test2 = new Term(a, -1);
        StdOut.println(first.toString());
        StdOut.println(second.toString());
        //StdOut.println(first.compareTo(second));
        //Comparator c = byReverseWeightOrder();
        //StdOut.println(c.compare(first, second));
        Comparator<Term> d = byPrefixOrder(5);
        StdOut.println(d.compare(second, first));
    }
}