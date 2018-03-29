package nodeAndListTest;

import bTree.BinaryTree;
import bTree.TreeUtils;
import org.junit.Test;

/**
 * create by renshengmiao on 2018/3/9 .
 */
public class TreeTest {

    @Test
    public void initTest(){
        BinaryTree root = TreeUtils.initBTree(new int[]{48,4,12,5,48,56,2,8,456,321});
        TreeUtils.preOrderRecur(root);
        System.out.println();
        TreeUtils.inOrderRecur(root);
        System.out.println();
        TreeUtils.posOrderRecur(root);
    }

    @Test
    public void unRecurprintTest(){
        BinaryTree root = TreeUtils.initBTree(new int[]{48,4,12,5,48,56,2,8,456,321});
        TreeUtils.preOrderUnRecur(root);
        TreeUtils.inOrderUnRecur(root);
    }

    @Test
    public void testCheckContains(){
        BinaryTree tree = TreeUtils.initBTree(new int[]{49, 48, 56, 21, 35, 47, 94, 82, 1, 5, 8, 7, 6});
        BinaryTree tree1 = TreeUtils.initBTree(new int[]{ 48,21, 35});
        boolean b = TreeUtils.checkContains2(tree, tree1);
        System.out.println(b);
    }

    @Test
    public void testKMP(){
        String s = "abababababc";
        String m = "abc";
        int indexOfByKMP = TreeUtils.getIndexOfByKMP(s, m);
        System.out.println(indexOfByKMP);
        s = "aaaaaaaaaaaaac";
        m = "aaa";
        System.out.println(TreeUtils.getIndexOfByKMP(s, m));
    }

    @Test
    public void testPrint(){
        int[] arr = getArr(20);
        BinaryTree head = TreeUtils.createBalanceTreeBySortArr(arr);
        TreeUtils.printTree(head);
        BinaryTree ancestor = TreeUtils.getLowestAncestor(head, head.getRight().getRight().getRight(), head.getRight().getLeft().getLeft());
        TreeUtils.printTree(ancestor);
    }

    public int[] getArr(int len){
        int[] ints = new int[len];
        for (int i = 0; i < ints.length ; i ++){
            ints[i] = i;
        }
        return ints;
    }
}
