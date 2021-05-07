package com.xiaowu5759.common.util;


import cn.hutool.core.util.StrUtil;
import com.xiaowu5759.common.model.TreeNode;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 构建树结构
 *
 * @author xiaowu
 * @date 2020/7/27 9:39
 */
public class TreeUtils {

    /**
     * 构建树
     *
     * @param treeNodeList TreeNode的list
     * @return List<TreeNode>，多个父节点
     */
    public static List<TreeNode> buildTree(List<TreeNode> treeNodeList) {
        // 遍历查找 根节点
        List<TreeNode> rootTreeNodeList = new ArrayList<>();
        for (TreeNode treeNode : treeNodeList) {
            Object parentId = treeNode.getParentId();
            if (isRootTreeNode(parentId)) {
                // 设置等级值，1级目录
                treeNode.setLevel(1);
                rootTreeNodeList.add(treeNode);
            }
        }
        // 外部排序
        rootTreeNodeList = rootTreeNodeList.stream().sorted(Comparator.comparing(TreeNode::getWeight)).collect(Collectors.toList());

        // 根节点 递归查找 子节点
        treeNodeList.removeAll(rootTreeNodeList);
        for (TreeNode rootTreeNode : rootTreeNodeList) {
            addChildTreeNode(rootTreeNode, treeNodeList);
        }
        return rootTreeNodeList;
    }

    /**
     * 通过根节点，不断添加子节点
     * 递归
     *
     * @param rootTreeNode    根节点
     * @param treeNodeList 子节点列表
     */
    private static void addChildTreeNode(TreeNode rootTreeNode, List<TreeNode> treeNodeList) {
        List<TreeNode> nodeDTOS = treeNodeList.stream().filter(treeNode -> treeNode.getParentId().equals(rootTreeNode.getId())).collect(Collectors.toList());
        if (nodeDTOS.isEmpty()) {
            return;
        }
        // 需要根据权重排序，越小越前
        nodeDTOS = nodeDTOS.stream().sorted(Comparator.comparingInt(TreeNode::getWeight)).collect(Collectors.toList());
        rootTreeNode.setChildren(nodeDTOS);
        for (TreeNode nodeDTO : nodeDTOS) {
            nodeDTO.setLevel(rootTreeNode.getLevel() + 1);
            addChildTreeNode(nodeDTO, treeNodeList);
        }
    }

    /**
     * 判断是否为根节点
     * 判断方式为: 父节点编号为空或为0, 则认为是根节点.
     *
     * @param parentId 父节点
     * @return 是否
     */
    private static boolean isRootTreeNode(Object parentId) {
        if (parentId == null) {
            return true;
        }
        // 这里由于 treeNodeDTO.getParentId() 不是泛型
        if (parentId instanceof String && (StrUtil.isBlankIfStr(parentId) || "0".equals(parentId))) {
            return true;
        }
        if (parentId instanceof Integer && Integer.valueOf(0).equals(parentId)) {
            return true;
        }
        if (parentId instanceof Long && Long.valueOf(0L).equals(parentId)) {
            return true;
        }
        return false;
    }

    // hutool 中 TreeNode<T> 具有泛型，我们就就简单一点 Long, Integer，对特定领域模型TreeNodeDTO使用

    // 有一种利用反射机制，高可复用性的，treeutil
    // https://cloud.tencent.com/developer/article/1561785

}
