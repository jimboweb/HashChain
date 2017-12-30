import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.StringTokenizer;

public class HashChains {
	////////////////////////////////////////////////////
	// Delete before turning in                       //
	private String[] naiveArray;					  //
	////////////////////////////////////////////////////
	
	
	
	
    private FastScanner in;
    private PrintWriter out;
    // store all strings in one list
    private static ArrayList<Entry>[] hashChains;
    private static int a;
    private static int b;
    private static Random rnd = new Random();
    // for hash function
    private static int bucketCount;
    private static int prime = 1000000007;
    private static int multiplier = 263;
    private static boolean naive = false;
    private static boolean debug = false;
    private static int m = 1000;

   

    public static void main(String[] args) throws IOException {
        //new HashChains().processQueries();
        new HashChains().unitTest();
    }
    
    private static void unitTest(){
    	int collisionCount = 0;
    	int randomIndex;
    	int hsh;
    	Random rnd = new Random();
		hashChains = (ArrayList<Entry>[])(new ArrayList[m]);
		for(int i=0;i<hashChains.length;i++)
			hashChains[i] = new ArrayList<Entry>();
		
		int collisionTest[] = new int[m];
		a = rnd.nextInt(prime - 1);
		b = rnd.nextInt(prime -1);
		int trials = 1000;
    	for(int i=0;i<trials;i++){
    		randomIndex = rnd.nextInt(9999998 + 1);
    		hsh = hashIndex(randomIndex);
    		if(collisionTest[hsh]!=0){
    			collisionCount++;
    		}
    		collisionTest[hsh] = collisionTest[hsh] + 1;
    		/*if(collisionTest[hsh] != 0){
    			if(collisionTest[hsh] != randomIndex)
    				collisionCount++;
    		} else {
    			collisionTest[hsh] = randomIndex;
    		}*/
    	}
		int filledCells = 0;
		for(int i=0;i<collisionTest.length;i++){
			if(collisionTest[i]!=0)
				filledCells++;
		}
    	System.out.printf("There were %d collisions, at a rate of %f%n", collisionCount, (double)collisionCount/(double)trials);
    	System.out.printf("%d of the cells were filled%n", filledCells);
    }
    
    private class Entry{
    	public Entry(int index, String name){
    		this.index = index;
    		this.name = name;
    	}
    	public int index;
    	public String name;
    }

    
    private void processQuery(Query query){
    	hashIndex(query.ind);
    }


    private static boolean hasKey(int index){
    	int hashVal = hashIndex(index);
    	ArrayList<Entry> chain = hashChains[hashVal];
    	for(int i=0;i<chain.size();i++){
    		Entry entry = chain.get(i);
    		if(entry.index==index)
    			return true;
    	}
    	return false;
    }
    
    private static void put(int index, String value){
    	
    }
    
    private static String get(int index){
    	int hashVal = hashIndex(index);
    	ArrayList<Entry> chain = hashChains[hashVal];
    	for(int i=0;i<chain.size();i++){
    		Entry entry = chain.get(i);
    		if(entry.index==index)
    			return entry.name;
    	}
    	return "Not Found";
    }
    
    private static int hashIndex(int index){
    	long hashVal = (((long)a * (long)index + (long)b) % (long)prime) % (long)m;
    	if(debug)
    	{
    		System.out.printf("Hash value for int %d is %d%n", index, hashVal);
    	}
    	return (int)hashVal;
    }
        
    private Query readQuery() throws IOException {
        String line = in.readLn();
        String[] elems = line.split(" ");
        String type = elems[0];
        int ind = Integer.parseInt(elems[1]);
        if(elems.length==2){
        	return new Query(type, ind);
        } else {
        	String s = elems[2];
        	return new Query(type, ind, s);
        }
        
   }


    private void processQueryNaive(Query query) {
        switch (query.type) {
            case "add":
                naiveArray[query.ind] = query.s;
                break;
            case "del":
                naiveArray[query.ind] = null;
                break;
            case "find":
            	if(naiveArray[query.ind] != null)
            		out.println(naiveArray[query.ind]);
            	else
            		out.println("not found");
                break;
            /*case "check":
                for (String cur : elems)
                    if (hashFunc(cur) == query.ind)
                        out.print(cur + " ");
                out.println();
                // Uncomment the following if you want to play with the program interactively.
                // out.flush();
                break;*/
            default:
                throw new RuntimeException("Unknown query: " + query.type);
        }
    }

    @SuppressWarnings("unchecked")
	public void processQueries() throws IOException {
    	if(naive){
    		naiveArray = new String[9999999];
    	}
    	else{
    		hashChains = (ArrayList<Entry>[])(new ArrayList[m]);
    		for(int i=0;i<hashChains.length;i++)
    			hashChains[i] = new ArrayList<Entry>();
    		
    		//[tk] make a loop that test the hash function
    		
    	}
		a = rnd.nextInt(prime - 1);
		b = rnd.nextInt(prime -1);
        in = new FastScanner();
        out = new PrintWriter(new BufferedOutputStream(System.out));
        bucketCount = in.nextInt();
        for (int i = 0; i < bucketCount; ++i) {
        	if(naive){
        		if(debug){
        			System.out.printf("a is %d, b is %d", a, b);
        		}
        		processQueryNaive(readQuery());
        	}
        	else
        		processQuery(readQuery());
        }
        out.close();
    }

    static class Query {
        String type;
        String s;
        int ind;

        public Query(String type, String s) {
            this.type = type;
            this.s = s;
        }

        public Query(String type, int ind) {
            this.type = type;
            this.ind = ind;
        }
        
        public Query(String type, int ind, String s){
        	this.type = type;
        	this.ind = ind;
        	this.s = s;
        }
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    static class FastScanner {
        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public FastScanner() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = null;
        }

        public String next() throws IOException {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }
        
        public String readLn() throws IOException {
        	return reader.readLine();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
