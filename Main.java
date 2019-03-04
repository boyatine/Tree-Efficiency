import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {

	public static void main(String[] args) {

		SplayTree spt = new SplayTree();
		AVLTree avl = new AVLTree();
		
		File file = new File(args[0]);
		BufferedReader reader = null;

		try {
			/* ************************************SplayTree************************************ */
			reader = new BufferedReader(new FileReader(file));
		    String text = null;
		    text = reader.readLine();
		    String[] str = text.trim().split("\\s+"); 
		    
		    for (int i = 0; i < str.length; i++)  
		    	spt.insert(Integer.parseInt(str[i]));
		    
		    System.out.println("Displaying SplayTree");
		    spt.display();
		    System.out.println("\nComparisons: " + spt.comparisons() + "\n");
		    
		    text = reader.readLine();
		    text = reader.readLine();
		    str = text.trim().split("\\s+");
		    
		    reader.close();
		    
		    System.out.println("Sequence of removals for SplayTree");

		    for (int i = 0; i < str.length; i++) {
		    	spt.remove(Integer.parseInt(str[i]));
		    	spt.display();
		    	System.out.println("\n");
		    }
		    
			/* ***********************************AVLTree*********************************** */
			reader = new BufferedReader(new FileReader(file));		    
		    text = reader.readLine();
		    str = text.trim().split("\\s+"); 
		    
		    for (int i = 0; i < str.length; i++)
		    	avl.insert(Integer.parseInt(str[i]));
		    	
		    System.out.println("Displaying AVLTree");
		    
		    avl.display();
		    System.out.println("\nComparisons: " + avl.comparisons() + "\n");
		    
		    text = reader.readLine();
		    text = reader.readLine();
		    str = text.trim().split("\\s+");
		    
		    reader.close();
		    
		    System.out.println("Sequence of removals for AVLTree");
		    
		    for (int i = 0; i < str.length; i++) {
		    	avl.remove(Integer.parseInt(str[i]));
		    	avl.display();
		    	System.out.println("\n");
		    }
		    
		    System.out.println("Comparing the two trees...\n");
		    System.out.println("Splay tree comparisons: " + spt.comparisons() 
		    	+ "\nAVL tree comparisons: " + avl.comparisons());
		    
		    int faster = spt.comparisons() - avl.comparisons();
		    
		    if (faster < 0)
		    	System.out.println("\nThe Splay tree had fewer comparisons.");
		    else if (faster > 0)
		    	System.out.println("\nThe AVL tree had fewer comparisons.");
		    else
		    	System.out.println("\nThe AVL and Splay trees had the same number of comparisons.");
		} 
		catch (IOException e) {
		    System.out.println("File could not be opened.");
		}
	}
}
