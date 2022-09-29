import java.util.*; 
/**
 * This class reads a grammar file in Backus-Naur Form randomly generate elements of the grammar.
 * @author Zohar Le 
 * @version 10/25/2021
 */
public class GrammarSolver {
   private Map<String, List<String>> map = new TreeMap<>();
   /**
    * This constructor will intake the set of rules and store it into a map
    * @param rules This is the list of rules that are selected by the user
    */
   public GrammarSolver(List<String> rules) 
   {
      //System.out.println(rules); checking
      if (rules.isEmpty())
         throw new IllegalArgumentException();
      String nonTerminal;
      for(String x: rules)
      { //for each rule
         List<String> tempTerminalList = new ArrayList<>();
         Scanner scanRule = new Scanner(x); //first scanner
         scanRule.useDelimiter("::="); //separate the non-terminal name and terminal name using ::=
         nonTerminal = scanRule.next().trim();
         String tempTerminalString = scanRule.next().trim(); //store the string after ::= to a terminal or non terminal String holder after trimming it
         Scanner scanSubRule = new Scanner(tempTerminalString); //scan these non terminals or terminals
         scanSubRule.useDelimiter("\\|"); // separate the terminals by | into tokens
         while(scanSubRule.hasNext())
         {
            String scanTerminal = scanSubRule.next().trim();
            tempTerminalList.add(scanTerminal.trim()); //add scanWord to the terminal list
         }
         if(!map.containsKey(nonTerminal))
         {
            map.put(nonTerminal,new ArrayList<String>()); //Setting keys
            map.get(nonTerminal).addAll(tempTerminalList); //set values
         }
         else
            throw new IllegalArgumentException(); //a non terminal or terminal name is seen more than once
      }
      //System.out.println(map);  // checking
   }
   /**
    * This method checks if the symbol is a non-terminal or not
    * @param symbol a symbol that will be passed by the main
    * @return true if the symbol is found as a key in the map and false otherwise
    */
   public boolean contains(String symbol)
   {
      if(symbol.length()==0 || symbol == null)
         throw new IllegalArgumentException(); 
      else
      {
         if(map.containsKey(symbol))
            return true;
         else  return false;
      }
   }
   /**
    * Method returns the keys a.k.a non-terminal from the map that we got in the contructor.
    * @return the set of rules or the non-terminal names
    */
   public Set<String> getSymbols()
   {
      Set<String> sortedSet = new TreeSet<>();
      for(String x: map.keySet())
      {
         sortedSet.add(x); //Tree Set will help us sort the list
      }
      return sortedSet; 
   }
   /**
    * This method randomly generate words from a given symbol and returns a string representative of grammar rules
    * @param symbol non-terminal or terminal value passed by the user
    * @return a string after going through the rules
    */
   public String generate(String symbol) 
   {
      String line = "";
      Random rand = new Random();
      if(symbol.length()==0)
         throw new IllegalArgumentException(); 
      else
      {
         List<String> tempValues = new ArrayList<>();
         if(!map.containsKey(symbol)) // symply assume that symbol is a terminal and return it a.k.a Base Case
            return symbol;
         else                          // symbol is a non-terminal, start recursion.
         {
            List<String> tempList = new ArrayList<>();
            tempList.addAll(map.get(symbol)); //add all rules of that symbol to the new list.
            int randIndex = rand.nextInt(map.get(symbol).size()); //store a random number from 0 (inclusive) to size (exclusively)
            String randRule = tempList.get(randIndex); //random a rule
            //System.out.println(randRule);   // checking
            Scanner scan = new Scanner(randRule); //scan the random rule that we chose previously
            while(scan.hasNext())   // scan each subRule in rule
            {  
               tempValues.add(scan.next().trim()); //add the scanned word or rule to a temp
            }
            for(String x: tempValues) // now when we have the current level of rule do the reccursion of it to get its random terminals
            {
               line += generate(x) + " "; //do recursion of and add the base case words into the string and return it
            }
         }
      }
      return line.trim();
   }
}