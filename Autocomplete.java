/******************************************************************************
  *  Name:    Nick Barnett
  *  NetID:   nrbarnett
  *  Precept: P04
  *
  *  Partner Name:    Maegan Richards
  *  Partner NetID:   maeganr
  *  Partner Precept: P03
  * 
  *  Description:  Implements a data type that provides autocomplete 
  *  functionality for a given set of string and weights, using Term and
  *  BinarySearchDeluxe. To do so, sorts the terms in lexicographic order; uses
  *  binary search to find() all query strings that start with a given 
  *  prefix; and sorts the matching terms in descending order by weight. 
  *  Organizes program by creating an immutable data type Autocomplete
******************************************************************************/
import edu.princeton.cs.algs4.MergeX;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Autocomplete {
    
    String prefix; //holds a prefix to search an array for matches
    Term[] matches; //holds an array of terms that match the prefix
    Term[] defTerms; //holds a defensive copy of the original array
    
    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) { throw new NullPointerException(); }
        defTerms = new Term[terms.length];
        for (int i = 0; i < terms.length; i++) {
            if (terms[i] == null) { throw new NullPointerException(); } 
//should query and weight be public?
            defTerms[i] = terms[i]; //defensive copy
        }
        MergeX.sort(defTerms); // sort in lexographical order
    }
    
    // Returns all terms that start with the given prefix, in descending order 
    //of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) { throw new NullPointerException(); }
        Term termPrefix = new Term(prefix, 0);
        int r = prefix.length();
        int start = BinarySearchDeluxe.firstIndexOf(defTerms, termPrefix, 
                                                    Term.byPrefixOrder(r));
        int end = BinarySearchDeluxe.lastIndexOf(defTerms, termPrefix, 
                                                 Term.byPrefixOrder(r));
        
        if (start == -1 || end == -1) { return new Term[0]; }
        
        Term[] matches = new Term[end - start + 1];
        
        for (int i = start, j = 0; i <= end; i++, j++) {
            matches[j] = defTerms[i];
        }
        MergeX.sort(matches, Term.byReverseWeightOrder());
        return matches;
    }
    
    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) { throw new NullPointerException(); }
        Term termPrefix = new Term(prefix, 0);
        int r = prefix.length();
        int start = BinarySearchDeluxe.firstIndexOf(defTerms, termPrefix, 
                                                    Term.byPrefixOrder(r));
        int end = BinarySearchDeluxe.lastIndexOf(defTerms, termPrefix, 
                                                 Term.byPrefixOrder(r));
        if (start == -1 || end == -1) { return 0; }
        return (end - start + 1);
    }
    
    public static void main(String[] args) {
        
        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();  //read in the number of entries in the array
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }
        
        // read in queries from standard input and print out the top k matching
        //terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms); //sorts the array
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }
}