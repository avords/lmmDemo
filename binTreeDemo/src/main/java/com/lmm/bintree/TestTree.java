package com.lmm.bintree;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by arno.yan on 2018/12/25.
 */
public class TestTree {
    
    private static final String SQLProvice = "INSERT INTO XIMA_CNT.CNT_PROVINCE(CODE,NAME) values('%s','%s');";
    private static final String SQLCity = "INSERT INTO XIMA_CNT.CNT_CITY(CODE,NAME,PROVINCE_CODE) values('%s','%s','%s');";
    private static final String SQLArea = "INSERT INTO XIMA_CNT.CNT_AREA (CODE, NAME, CITY_CODE) VALUES ('%s', '%s', '%s');";
    
    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("/Users/xmly/pcaTest.csv"));

        List<ProviceCityAreaTree> proviceCityAreaTreeList = new ArrayList<>();
        String str;
        while ((str = in.readLine()) != null) {
            String[] pca = str.split(",");

            ProviceCityAreaTree proviceCityAreaTree = new ProviceCityAreaTree(Long.valueOf(pca[1]), Long.valueOf(pca[0]), pca[2]);
            proviceCityAreaTreeList.add(proviceCityAreaTree);
        }

        DefaultMutableTreeNode defaultMutableTreeNode = TreeUtils.getTree(100000L, proviceCityAreaTreeList);

        System.out.println("depth:" + defaultMutableTreeNode.getDepth());
        System.out.println("level:" + defaultMutableTreeNode.getLevel());

        Enumeration<DefaultMutableTreeNode> enumeration = defaultMutableTreeNode.breadthFirstEnumeration();

        while (enumeration.hasMoreElements()) {
            DefaultMutableTreeNode node = enumeration.nextElement();
            ProviceCityAreaTree proviceCityAreaTree = (ProviceCityAreaTree) node.getUserObject();
            String sql = null;
            if (node.getLevel() == 1) {
                sql = String.format(SQLProvice, proviceCityAreaTree.getObjectId(),proviceCityAreaTree.getName());
            }else if(node.getLevel() == 2){
                sql = String.format(SQLCity, proviceCityAreaTree.getObjectId(), proviceCityAreaTree.getName(), proviceCityAreaTree.getParentId());
            }else if(node.getLevel() == 3){
                sql = String.format(SQLArea, proviceCityAreaTree.getObjectId(), proviceCityAreaTree.getName(), proviceCityAreaTree.getParentId());
            }

            System.out.println(sql);
        }
    }
}
