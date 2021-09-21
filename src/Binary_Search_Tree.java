//Creator: Jamie (JT) O'Roke
//Java Program to create a Binary Search Tree (BST) using recursion.
//Strictly Object-Oriented, only using classes.
//Error handling is dealt with explicitly via Try/catch block.
import java.util.*;

public class Binary_Search_Tree {
    private static node root;

    //------------------------   MAIN and MENU  --------------------------//

    public static void main (String[] args){
        System.out.println("Welcome to the Binary Search Tree (BST) creator, what do you want to do?\n");
        menu();
    }
    static void menu(){
        char ans;
       try {
           do {
               System.out.println("i - insert\nf - auto fill\nd - display\nr - remove\nq - quit");
               Scanner scan = new Scanner(System.in);
               ans = scan.next(".").charAt(0);
               switch(ans) {
                   case 'i':
                       insert();
                       System.out.println("Resulting tree");
                       display();
                       break;
                   case 'f':
                       fill();
                       System.out.println("Resulting tree");
                       display();
                       break;
                   case 'd':
                       display();
                       break;
                   case 'r':
                       remove();
                       System.out.println("Resulting tree");
                       display();
                       break;
                   case 'q':
                       System.out.println("Goodbye.");
                       break;
               }
           }while (ans != 'q');
       }catch(InputMismatchException e) {
           System.out.println("Too many characters or wrong data type.");
           menu();
       }
    }

    //---------------------- END MAIN and MENU ---------------------------//


    static void fill(){    //<--- This functions gathers information and kickstarts the recursive fill(int) method.
        try {
            System.out.println("How many auto generated nodes do you want to add? ");
            Scanner scan = new Scanner(System.in);
            int size = scan.nextInt();
            fill(size);   //<--- calling the recursive fill with the desired size as the argument.
        }catch(InputMismatchException e){
            System.out.println("Wrong data type, try again.");
            fill();
        }

    }
    static void fill(int count){   //<--- Recursive function to auto generate a random BST of a user defined size.
        int min = -50;
        int max = 50;
       while(count > 0){
           count--;
           int rand = (int)Math.floor(Math.random()*(max-min+1)+min);  //<-- Math.floor(Math.random()) is a predefined "random number" method in the Math library.
           insert(root, rand);
       }
    }
    static void insert(){  //<--- Insert function to gather information and kickstart the recursive insert.
        try {
            System.out.println("Enter the number you wish to add to the tree");
            Scanner scan = new Scanner(System.in);
            int val = scan.nextInt();
            insert(root, val);
        }catch(InputMismatchException e){
            System.out.println("Wrong data type.");
            insert();
        }
    }
    static node insert(node curr, int val){ //<--- Recursive function to insert a node in the BST.
        if(root == null){          //<--- If root is empty, then create a root node.
            root = new node(val);
            return curr;
        }
        if(curr == null){          //<--- If the current node is empty, create a new node and return its address.
            curr = new node(val);
            return curr;
        }
        else if(curr.comp_data(val) != 1){   //<--- If val is smaller than current node, go to left child
                curr.connect_left(insert(curr.go_left(), val));
            }
        else{           //<--- If val is bigger or equal to current node, go to right child
            curr.connect_right(insert(curr.go_right(), val));
        }
        return curr; //<--- return the new nodes address to the "connect_next" method call from the parent node.
    }
    static void display(){ //<--- Display method to gather information and kickstart recursive display method.
        display(root);
        System.out.println();
        if(root != null) {
            System.out.println("The root of this tree is: " + root.push_key());
            int count = count_nodes(root);
            System.out.println("There are " + count + " nodes in this tree.");
        }
        System.out.println();
    }
    static void display(node curr){  //<--- Recursive display method
        if(root == null){
            System.out.println("List empty");
        }
        if(curr != null) {
            display(curr.go_left()); //<--- Traverse through the left children until the smallest key is reached
            if(curr.go_left() != null) System.out.print("->");
            curr.display();
            if(curr.go_right() != null) System.out.print("->");
            display(curr.go_right()); //<--- Traverse the right children on the way back to root until the biggest key is reached.
        }
    }
    static int count_nodes(node root){
        if(root == null) return 0;
        return 1 + count_nodes(root.go_left()) + count_nodes(root.go_right());
    }
    static void remove() {
        try {
                System.out.println("r - remove one\na - remove all");
                Scanner scan = new Scanner(System.in);
                char ans = scan.next(".").charAt(0);
                switch(ans) {
                    case 'r':
                        System.out.println("Enter number you wish to remove: ");
                        scan = new Scanner(System.in);
                        int val = scan.nextInt();
                        root = remove(root, val);
                        break;
                    case 'a':
                        remove_all();
                        break;
                    default:
                        System.out.println("Invalid Input");
                        break;
                }
            }catch(InputMismatchException e){
                System.out.println("Wrong data type, try again.");
                remove();
            }
    }
    static node remove(node root, int val){  //<--- Recursive remove function to remove one node
        if(root == null) return null;
        if(root.comp_data(val) == 1) root.connect_right(remove(root.go_right(), val));   //<--- if val is bigger than node.data then go to right subtree
        else if(root.comp_data(val) == -1) root.connect_left(remove(root.go_left(), val));   //<--- if val is smaller than node.data then go to left subtree
        else{   //<--- If val is equal to node.data
           if(root.go_left() == null) return root.go_right();   //<---if left subtree is empty then return right subtree
           else if(root.go_right() == null) return root.go_left();   //<---if right subtree is empty then return left subtree
            //if both children are filled
           root.pull_key(inorder_successor(root.go_right())); //<---make temp node equal to the lowest value in the right subtree
           root.connect_right(remove(root.go_right(), root.push_key()));
        }
        return root;
    }
    static void remove_all(){
        root = null;  //<--- Java does memory garbage collection implicitly, so we can just make the root node null.
    }
    static node inorder_successor(node root){   //<--- function to return the leftmost node of a tree
            if(root.go_left() != null) root = inorder_successor(root.go_left());   //<---traverse the left subtree until at lowest key
       return root;
    }
}
class node{
    private node right_child, left_child;
    private int data;
    public node(int val){
       data = val;
    }
    public node go_right(){return right_child;}
    public node go_left(){return left_child;}
    public void connect_left(node smaller){left_child = smaller;}
    public void connect_right(node bigger){right_child = bigger;}
    public void display(){System.out.print(data);}
    public int comp_data(int val){
        if(val < data) return -1;
        if(val > data) return 1;
        else return 0;
    }
    public void pull_key(node a_node){data = a_node.data;}
    public int push_key(){return data;}
}