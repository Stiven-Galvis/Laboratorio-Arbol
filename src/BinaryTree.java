import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BinaryTree {
    TreeNode root;

    public BinaryTree() {
        this.root = null;
    }

    public BinaryTree(TreeNode root) {
        this.root = root;
    }
    /*
    IMPORTANTE: respete EXACTAMENTE los nombres y firmas de los metodos que se
    piden abajo; la revision automatica los busca por nombre. Puede agregar
    todos los metodos auxiliares (privados) que necesite.
    */

    /*
    1. Construccion del arbol: implemente aqui

    public void insertRoot(Object data)
        Si el arbol esta vacio, crea la raiz con 'data'.

    public TreeNode search(Object data)
        Devuelve el nodo cuyo dato es igual (equals) a 'data', o null si no
        existe.

    public boolean insertLeft(Object parent, Object data)
        Agrega 'data' como hijo izquierdo del nodo cuyo dato es 'parent'.
        Devuelve false (y no modifica nada) si 'parent' no existe o si ya
        tiene hijo izquierdo; true en otro caso.

    public boolean insertRight(Object parent, Object data)
        Igual que insertLeft, pero con el hijo derecho.

    public void buildTree(Scanner scanner)
        Construye el arbol leyendo los datos con 'scanner' (en el main se le
        pasa new Scanner(System.in)). Primero se lee la raiz y luego, por
        niveles, el hijo izquierdo y el hijo derecho de cada nodo
        ("Hijo izquierdo de A: ", "Hijo derecho de A: ", ...). Una linea
        vacia o "-" significa que ese hijo no existe.
    */
    public void insertRoot(Object data) {
        if (this.root == null) {
            this.root = new TreeNode(data);
        }
    }

    public TreeNode search(Object data) {
        return searchRec(this.root, data);
    }

    private TreeNode searchRec(TreeNode current, Object data) {
        if (current == null) {
            return null;
        }
        if ((current.data == null && data == null) || (current.data != null && current.data.equals(data))) {
            return current;
        }
        TreeNode found = searchRec(current.left, data);
        if (found != null) {
            return found;
        }
        return searchRec(current.right, data);
    }

    public boolean insertLeft(Object parent, Object data) {
        TreeNode parentNode = search(parent);
        if (parentNode == null || parentNode.left != null) {
            return false;
        }
        parentNode.left = new TreeNode(data);
        return true;
    }

    public boolean insertRight(Object parent, Object data) {
        TreeNode parentNode = search(parent);
        if (parentNode == null || parentNode.right != null) {
            return false;
        }
        parentNode.right = new TreeNode(data);
        return true;
    }

    public void buildTree(Scanner scanner) {
        System.out.println("Ingrese el dato de la raíz:");
        if (scanner.hasNextLine()) {
            String rootData = scanner.nextLine().trim();
            if (!rootData.isEmpty() && !rootData.equals("-")) {
                insertRoot(rootData);
                buildTreeRecursive(this.root, scanner);
            }
        }
    }

    private void buildTreeRecursive(TreeNode parent, Scanner scanner) {
        if (parent == null) return;

        System.out.println("Hijo izquierdo de " + parent.data + " (deje vacío o '-' si no tiene):");
        if (scanner.hasNextLine()) {
            String leftData = scanner.nextLine().trim();
            if (!leftData.isEmpty() && !leftData.equals("-")) {
                insertLeft(parent.data, leftData);
                buildTreeRecursive(parent.left, scanner);
            }
        }

        System.out.println("Hijo derecho de " + parent.data + " (deje vacío o '-' si no tiene):");
        if (scanner.hasNextLine()) {
            String rightData = scanner.nextLine().trim();
            if (!rightData.isEmpty() && !rightData.equals("-")) {
                insertRight(parent.data, rightData);
                buildTreeRecursive(parent.right, scanner);
            }
        }
    }

    /*
    2. Visualizacion: implemente aqui

    public void printTree()
        Muestra el arbol en consola de forma grafica, indicando claramente
        la relacion entre padres e hijos.
    */
    public void printTree() {
        printTreeRecursive(this.root, "");
    }

    private void printTreeRecursive(TreeNode node, String space) {
        if (node == null) {
            return;
        }
        System.out.println(space + node.data);
        if (node.left != null || node.right != null) {
            printTreeRecursive(node.left, space + "   ├── Izq: ");
            printTreeRecursive(node.right, space + "   └── Der: ");
        }
    }

    /*
    3. Recorridos: implemente aqui. Cada recorrido debe ser una funcion
    RECURSIVA independiente (por ejemplo, un metodo privado que reciba un
    TreeNode). Los metodos publicos devuelven la secuencia separada por
    espacios, p.ej. "A B D H I E C F G".

    public String preorder()
    public String inorder()
    public String postorder()
    */
    public String preorder() {
        StringBuilder sb = new StringBuilder();
        preorderRec(this.root, sb);
        return sb.toString().trim();
    }

    private void preorderRec(TreeNode node, StringBuilder sb) {
        if (node == null) return;
        sb.append(node.data).append(" ");
        preorderRec(node.left, sb);
        preorderRec(node.right, sb);
    }

    public String inorder() {
        StringBuilder sb = new StringBuilder();
        inorderRec(this.root, sb);
        return sb.toString().trim();
    }

    private void inorderRec(TreeNode node, StringBuilder sb) {
        if (node == null) return;
        inorderRec(node.left, sb);
        sb.append(node.data).append(" ");
        inorderRec(node.right, sb);
    }

    public String postorder() {
        StringBuilder sb = new StringBuilder();
        postorderRec(this.root, sb);
        return sb.toString().trim();
    }

    private void postorderRec(TreeNode node, StringBuilder sb) {
        if (node == null) return;
        postorderRec(node.left, sb);
        postorderRec(node.right, sb);
        sb.append(node.data).append(" ");
    }

    /*
    4. Factores de equilibrio: implemente aqui

    public int height(TreeNode node)
        Altura del subarbol que empieza en 'node' (null tiene altura 0 y una
        hoja altura 1).

    public int balanceFactor(TreeNode node)
        Factor de equilibrio de 'node': height(node.right) - height(node.left).

    public boolean isBalanced()
        true si TODOS los nodos tienen factor de equilibrio -1, 0 o 1.

    public List<Object> unbalancedNodes()
        Lista con los datos de los nodos cuyo factor de equilibrio es menor
        que -1 o mayor que 1 (los que requieren balanceo).

    public void printBalanceFactors()
        Muestra el arbol en consola indicando en cada nodo su factor de
        equilibrio.
    */
    public int height(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + Math.max(height(node.left), height(node.right));
    }

    public int balanceFactor(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return height(node.right) - height(node.left);
    }

    public boolean isBalanced() {
        return isBalancedRec(this.root);
    }

    private boolean isBalancedRec(TreeNode node) {
        if (node == null) return true;
        int bf = balanceFactor(node);
        if (bf < -1 || bf > 1) return false;
        return isBalancedRec(node.left) && isBalancedRec(node.right);
    }

    public List<Object> unbalancedNodes() {
        List<Object> list = new ArrayList<>();
        unbalancedNodesRec(this.root, list);
        return list;
    }

    private void unbalancedNodesRec(TreeNode node, List<Object> list) {
        if (node == null) return;
        int bf = balanceFactor(node);
        if (bf < -1 || bf > 1) {
            list.add(node.data);
        }
        unbalancedNodesRec(node.left, list);
        unbalancedNodesRec(node.right, list);
    }

    public void printBalanceFactors() {
        printBalanceFactorsRec(this.root);
    }

    private void printBalanceFactorsRec(TreeNode node) {
        if (node == null) return;
        System.out.println("Nodo: " + node.data + " | Factor de Equilibrio: " + balanceFactor(node));
        printBalanceFactorsRec(node.left);
        printBalanceFactorsRec(node.right);
    }

    /*
    5. Balanceo AVL: implemente aqui. Cada rotacion recibe la raiz del
    subarbol desbalanceado y devuelve la NUEVA raiz de ese subarbol.

    public TreeNode rotateRight(TreeNode node)       rotacion simple a la derecha
    public TreeNode rotateLeft(TreeNode node)        rotacion simple a la izquierda
    public TreeNode rotateLeftRight(TreeNode node)   rotacion doble izquierda-derecha
    public TreeNode rotateRightLeft(TreeNode node)   rotacion doble derecha-izquierda

    public void balance()
        Identifica los nodos desbalanceados y aplica las rotaciones necesarias
        hasta que el arbol sea AVL (actualiza 'root' si cambia). Debe mostrar
        el arbol con sus factores de equilibrio antes y despues de balancear.
    */
    public TreeNode rotateRight(TreeNode node) {
        if (node == null || node.left == null) return node;
        TreeNode x = node.left;
        TreeNode T2 = x.right;

        x.right = node;
        node.left = T2;

        return x;
    }

    public TreeNode rotateLeft(TreeNode node) {
        if (node == null || node.right == null) return node;
        TreeNode y = node.right;
        TreeNode T2 = y.left;

        y.left = node;
        node.right = T2;

        return y;
    }

    public TreeNode rotateLeftRight(TreeNode node) {
        if (node == null || node.left == null) return node;
        node.left = rotateLeft(node.left);
        return rotateRight(node);
    }

    public TreeNode rotateRightLeft(TreeNode node) {
        if (node == null || node.right == null) return node;
        node.right = rotateRight(node.right);
        return rotateLeft(node);
    }

    public void balance() {
        System.out.println("--- Factores antes de balancear ---");
        printBalanceFactors();

        this.root = balanceRec(this.root);

        System.out.println("--- Factores después de balancear ---");
        printBalanceFactors();
    }

    private TreeNode balanceRec(TreeNode node) {
        if (node == null) return null;

        node.left = balanceRec(node.left);
        node.right = balanceRec(node.right);

        int bf = balanceFactor(node);

        if (bf < -1) {
            if (balanceFactor(node.left) > 0) {
                return rotateLeftRight(node);
            } else {
                return rotateRight(node);
            }
        }
        if (bf > 1) {
            if (balanceFactor(node.right) < 0) {
                return rotateRightLeft(node);
            } else {
                return rotateLeft(node);
            }
        }

        return node;
    }
}

