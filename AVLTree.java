class AVLNode  
{  
	int key, height;  
    AVLNode left, right;  
  
    AVLNode(int key)  
    {  
        this.key = key;  
        height = 1;  
    }  
}  
  
class AVLTree  
{  
    AVLNode root;  
    private int comparisons = 0;
  
    int height(AVLNode node)  
    {  
        if (node == null)  return 0;  
        return node.height;  
    }  
  
    // Right rotation
    AVLNode rightRotate(AVLNode node)  
    {  
    	comparisons+=2; // AVLNode a = node.left & AVLNode b = a.right
        AVLNode a = node.left;  
        AVLNode b = a.right;  
  
        comparisons+=2;
        a.right = node;  
        node.left = b;  
  
        node.height = max(height(node.left), height(node.right)) + 1;  
        a.height = max(height(a.left), height(a.right)) + 1;  
        comparisons+=4; // accessing the left and right of a and node
  
        return a;  
    }  
  
    // Left rotation
    AVLNode leftRotate(AVLNode node)  
    {  
    	comparisons+=2;
        AVLNode a = node.right;  
        AVLNode b = a.left;  
  
        comparisons+=2;
        a.left = node;  
        node.right = b;  
  
        comparisons+=4;
        node.height = max(height(node.left), height(node.right)) + 1;  
        a.height = max(height(a.left), height(a.right)) + 1;  
  
        return a;  
    }  
    
    int max(int a, int b)  
    {  
        return (a > b) ? a : b;  
    }  
  
    int getBalance(AVLNode node)  
    {  
        if (node == null)  return 0; 
        comparisons+=2;
        return height(node.left) - height(node.right);  
    }  
  
    public void insert(int key) {
    	root = insert(root, key);
    }
    
    AVLNode insert(AVLNode node, int key)  
    {  
        if (node == null)  return (new AVLNode(key));  
  
        if (key < node.key)  {
            node.left = insert(node.left, key);  
            comparisons++;
        }
        else if (key > node.key)  {
            node.right = insert(node.right, key);  
            comparisons++;
        }
        else // No duplicates
            return node;  
  
        comparisons+=2;
        node.height = 1 + max(height(node.left), height(node.right));  
  
        // Check balance and do something if unbalanced
        int balance = getBalance(node);  
  
        // Left left
        if (balance > 1 && key < node.left.key)  return rightRotate(node);  
  
        // Right right  
        if (balance < -1 && key > node.right.key)  return leftRotate(node);  
  
        comparisons+=2;
        
        // Left right 
        if (balance > 1 && key > node.left.key)  
        {  
            node.left = leftRotate(node.left);  
            comparisons++;
            return rightRotate(node);  
        }  
  
        // Right left  
        if (balance < -1 && key < node.right.key)  
        {  
            node.right = rightRotate(node.right);  
            comparisons++;
            return leftRotate(node);  
        }  
        
        comparisons+=2;
  
        return node;  
    }  
    
    public void remove(int key) {
        root = remove(root, key);
    }
    
    private AVLNode remove(AVLNode root, int key)  
    {  
        if (root == null)  
            return root;
  
        if (key < root.key)  {
            root.left = remove(root.left, key);  
            comparisons++;
        }
   
        else if (key > root.key)  {
            root.right = remove(root.right, key);  
            comparisons++;
        }
        
        else
        {  
        	comparisons+=2;
            if (root.left == null || root.right == null)  
            {  
                AVLNode temp = null;  
                if (temp == root.left)   
                	temp = root.right;  
                else
                    temp = root.left; 
                comparisons+=2;
  
                // No children  
                if (temp == null)  
                {  
                    temp = root;  
                    root = null;  
                }  
                else // One child  
                    root = temp;                
            }  
            else
            {  
                // Two children: need smallest value in right subtree  
                AVLNode temp = minValue(root.right);  
  
                root.key = temp.key;  
  
                comparisons++;
                root.right = remove(root.right, temp.key);  
            }  
        }  
  
        // Return if tree had only one node  
        if (root == null)  return root;  
  
        comparisons+=2;
        root.height = max(height(root.left), height(root.right)) + 1;  
  
        int balance = getBalance(root);  
  
        // Left left
        comparisons++;
        if (balance > 1 && getBalance(root.left) >= 0)  
            return rightRotate(root);  
  
        // Left right  
        comparisons++;
        if (balance > 1 && getBalance(root.left) < 0)  
        {  
            root.left = leftRotate(root.left); 
            comparisons++;
            return rightRotate(root);  
        }  
  
        // Right right  
        comparisons++;
        if (balance < -1 && getBalance(root.right) <= 0)  
            return leftRotate(root);  
  
        // Right left  
        comparisons++;
        if (balance < -1 && getBalance(root.right) > 0)  
        {  
            root.right = rightRotate(root.right);  
            comparisons++;
            return leftRotate(root);  
        }  
  
        return root;  
    }  
    
    AVLNode minValue(AVLNode node)  
    {  
        AVLNode current = node;  
  
        comparisons++;
        while (current.left != null) {
        	current = current.left; 
        	comparisons++;
        }
  
        return current;  
    }  
  
    public void preorder() {
        preorder(root);
    }
    
    void preorder(AVLNode node)  
    {  
        if (node != null)  
        {  
            System.out.print(node.key + " ");  
            preorder(node.left);  
            preorder(node.right);  
        }  
    }  
    
//    void preorder(AVLNode node)
//    {
//    	if (node == null) 
//    		return;
//    	Stack<AVLNode> s = new Stack<AVLNode>();
//    	s.add(node);
//    	while (!s.isEmpty())
//    	{
//    		node = s.pop();
//    		System.out.print("[" + node.key);
//    		if (node.right != null)
//    		{
//    			s.push(node.right);
//    		}
//    		if (node.left != null)
//    		{
//    			s.push(node.left);
//    		}
//    	}
//    }
    public void display() {
    	preorder();
    }
    
    public int comparisons() {
    	return comparisons;
    }
}