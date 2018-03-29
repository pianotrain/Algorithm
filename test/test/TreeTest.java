package test;

import node_list_tree.BinaryNode;
import node_list_tree.TreeUtils;
import org.junit.Test;

/**
 * Created by rsmno on 2018/3/9.
 */
public class TreeTest {

    @Test
    public void treetest(){
        BinaryNode head = TreeUtils.initBTree(new int[]{56, 23, 454, 76, 4, 4, 5, 76, 5, 344, 3, 55, 65, 3});
        TreeUtils.inOrderUnRecur(head);
        TreeUtils.posOrderUnRecur(head);
    }

    public BinaryNode getTree(){
        return TreeUtils.initBTree(new int[]{56, 23, 454, 76, 4, 4, 5, 76, 5, 344, 3, 55, 65, 3});
    }

    public BinaryNode getTree2(){
        return TreeUtils.initBTree(new int[]{57,34,5,4,23,56,87,98,6,3,33,78,36,88,31});
    }
    @Test
    public void testPrintOrder(){
        BinaryNode tree = getTree();
        TreeUtils.printTree(tree);
    }

    @Test
    public void testPreCreate(){
        BinaryNode head = getTree();
        TreeUtils.printTree(head);
        String s = TreeUtils.serialByPre(head);
        System.out.println(s);
        BinaryNode reconByPreString = TreeUtils.reconByPreString(s);
        TreeUtils.printTree(reconByPreString);
    }

    @Test
    public void testMorrisln(){
        BinaryNode tree = getTree();
        TreeUtils.morrisln(tree);
        TreeUtils.inOrderUnRecur(tree);
    }

    @Test
    public void testGetBiggestSubBST(){
        BinaryNode tree = getTree2();
        TreeUtils.printTree(tree);
        BinaryNode biggestSubBST = TreeUtils.getBiggestSubBST(tree);
        TreeUtils.printTree(biggestSubBST);
    }

    @Test
    public void testTopoSize1(){
        BinaryNode tree = getTree();
        TreeUtils.printTree(tree);
        System.out.println(TreeUtils.bstTopoSize1(tree));
        System.out.println(TreeUtils.bstTopoSize2(tree));
    }

    @Test
    public void testPrintBtLevel(){
        BinaryNode tree = getTree();
        TreeUtils.printTree(tree);
        TreeUtils.printByLevel(tree);
        TreeUtils.printByZigzag(tree);
    }

    @Test
    public void testGenetateBySortArr(){
        int[] sortArr = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
        BinaryNode binaryNode = TreeUtils.generateTreeBySortArr(sortArr);
        TreeUtils.printTree(binaryNode);
    }
}
