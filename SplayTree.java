class SplayNode 
{    
	SplayNode left, right, parent;
    int key;
 
    public SplayNode() {
        this(0, null, null, null);
    }          
    public SplayNode(int key) {
         this(key, null, null, null);
    } 

    public SplayNode(int key, SplayNode left, SplayNode right, SplayNode parent) 
    {
    	this.left = left;
        this.right = right;
        this.parent = parent;
        this.key = key;         
    }    
}
 
class SplayTree 
{
	private SplayNode root;
	private int comparisons = 0;
	
    public SplayTree() {
    	root = null;
    }
 
    public void insert(int key)
    {
        SplayNode a = root;
        SplayNode b = null;
        
        while (a != null)
        {
        	b = a;
        	comparisons++; // b = a
        	
            if (key > b.key) 
                a = a.right;
            else 
                a = a.left;
            comparisons++; // a = a.right or a = a.left
        }
        
        a = new SplayNode();
        a.key = key;
        a.parent = b;
        
        if (b == null)
            root = a;
        else if (key > b.key) 
            b.right = a;
        else 
            b.left = a;
        
        comparisons++; // if-else above
        
        Splay(a);
    }
    
    public void makeLeftChildParent(SplayNode c, SplayNode p)
    {
    	comparisons++; // if (p.parent != null)
        if (p.parent != null)
        {
        	comparisons++; // if (p == p.parent.left)
            if (p == p.parent.left)
                p.parent.left = c;
            else
                p.parent.right = c;
            comparisons++; // p.parent.left = c or p.parent.right = c
        }
        
        comparisons++; // if (c.right != null)
        if (c.right != null) {
            c.right.parent = p;
            comparisons++; // c.right.parent = p
        }
        
        c.parent = p.parent;
        p.parent = c;         
        p.left = c.right;
        c.right = p;
        comparisons+=4; // the four above
    }
 
    public void makeRightChildParent(SplayNode c, SplayNode p)
    {
    	comparisons++; // if (p.parent != null)
        if (p.parent != null)
        {
        	comparisons++; // if (p == p.parent.left)
            if (p == p.parent.left)
                p.parent.left = c;
            else
                p.parent.right = c;
            comparisons++; // p.parent.left = c or p.parent.right = c
        }
        
        comparisons++; // if (c.left != null)
        if (c.left != null) {
            c.left.parent = p;
            comparisons++; // c.left.parent = p
        }
       
        c.parent = p.parent;
        p.parent = c;
        p.right = c.left;
        c.left = p;
        comparisons+=4; // the four above
    }
 
    private void Splay(SplayNode node)
    {
    	comparisons++; // first instance of while
        while (node.parent != null)
        {
        	comparisons+=2; // SplayNode Parent = node.parent & SplayNode grandParent = Parent.parent
            SplayNode Parent = node.parent;
            SplayNode grandParent = Parent.parent;
            if (grandParent == null)
            {
            	comparisons++; // if (node == Parent.left)
                if (node == Parent.left)
                    makeLeftChildParent(node, Parent);
                else
                    makeRightChildParent(node, Parent);
            } 
            else
            {
            	comparisons++; // if (node == Parent.left)
                if (node == Parent.left)
                {
                	comparisons++; // if (Parent == grandParent.left)
                    if (Parent == grandParent.left)
                    {
                        makeLeftChildParent(Parent, grandParent);
                        makeLeftChildParent(node, Parent);
                    }
                    else 
                    {
                        makeLeftChildParent(node, node.parent);
                        makeRightChildParent(node, node.parent);
                    }
                }
                else 
                {
                	comparisons++; // if (Parent == grandParent.left)
                    if (Parent == grandParent.left)
                    {
                        makeRightChildParent(node, node.parent);
                        makeLeftChildParent(node, node.parent);
                    } 
                    else 
                    {
                        makeRightChildParent(Parent, grandParent);
                        makeRightChildParent(node, Parent);
                    }
                }
            }
            comparisons++; // while
        }
        
        root = node;
    }
 
    public void remove(int key)
    {
        SplayNode node = find(key);
        remove(node);
    }
 
    private void remove(SplayNode node)
    {
        if (node == null)	return;
 
        Splay(node);
        
        comparisons+=2; // if (node.left != null && node.right !=null)
        if (node.left != null && node.right != null)
        { 
        	comparisons++; // SplayNode min = node.left
            SplayNode min = node.left;
            
            comparisons++; // first instance of while
            while (min.right != null) {
                min = min.right;
                comparisons+=2; // min = min.right and while
            }
            
            min.right = node.right;
            node.right.parent = min;
            node.left.parent = null;
            root = node.left;
            comparisons+=4; // the four above
        }
        else if (node.right != null)
        {
            node.right.parent = null;
            root = node.right;
            comparisons+=3; // node.right.parent = null & root = node.right & else-if
        } 
        else if (node.left !=null)
        {
            node.left.parent = null;
            root = node.left;
            comparisons+=3; // node.left.parent = null & root = node.left & else-if
        }
        else 
        {
        	root = null;
        	comparisons+=2; // the two else-ifs above
        }
        
        node.parent = null;
        node.left = null;
        node.right = null;
        node = null;
        comparisons+=4; // the four above
    }
 
    public boolean search(int key) {
        return find(key) != null;
    }
 
    private SplayNode find(int key)
    {
    	SplayNode PrevNode = null;
        SplayNode node = root;
        while (node != null)
        {
        	PrevNode = node;
            if (key > node.key) 
            {
                node = node.right;
                comparisons++; // node = node.right
            }
            else if (key < node.key) 
            {
                node = node.left;
            	comparisons++; // node = node.left
            }
            else if(key == node.key) {
                Splay(node);
                return node;
            }
        }
        
        if (PrevNode != null)
        {
            Splay(PrevNode);
            return null;
        }
        
        return null;
    }
 
    public void preorder() {
        preorder(root);
    }
    
    private void preorder(SplayNode r)
    {
    	if (r != null)
    	{
    		if (r.left != null && r.right != null) 
   		 	{
    			System.out.print("[" + r.key);
                preorder(r.left);
                preorder(r.right);
            }
    		else if (r.left == null && r.right == null)
            {
    			if (oneChild(r.parent) || r.parent == root || isLeft(r))
    				System.out.print("[");
    			 
            	System.out.print(r.key);
            	
            	int closeBraceCount = howManyParents(r, 1);
                while (closeBraceCount > 0)
                {
                	System.out.print("]");
               	 	closeBraceCount--;
                }
            }
    		else
    		{
    			System.out.print("[" + r.key);
    			preorder(r.left);
   			 	preorder(r.right);
    		} 
    	}        
    }
    
    private boolean isLeft(SplayNode node)
    {
    	if (node.parent != null)	
    		return node.parent.left == node;
    	else	return false;
    }
     
    private boolean oneChild(SplayNode r)
    {
    	if (r.left != null && r.right == null)	return true;
    	if (r.right != null && r.left == null)	return true;
    	else	return false;
    }
    
    /**
     * Initialise counter as 1 when using.
     * */
    private int howManyParents(SplayNode r, int counter) 
    {	 
    	if (r.parent == null || r.parent == root)	return counter;
    	else if (r.parent.left != null && r.parent.right != null)	return counter;
    	else	
    		return howManyParents(r.parent, counter + 1);
    }
 
    public void display() {
    	preorder();
    }
    
    public int comparisons() {
    	return comparisons;
    }
}