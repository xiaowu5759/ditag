package com.xiaowu5759.common.model;

import java.util.List;

/**
 * 目标三个维度的树节点DTO
 * 树结构至少需要这几个属性
 * id	parentId	name	weight
 *
 * @author xiaowu
 * @date 2020/7/20 17:31
 */
public class TreeNode {

    /**
     * id键，必须
     */
    private Long id;

    /**
     * value值
     */
    private String name;

    /**
     * 父id，必须
     */
    private Long parentId;

    /**
     * 权重，必须
     */
    private Integer weight = 0;

    /**
     * 等级
     */
    private Integer level;

    /**
     * 子节点
     */
    private List<TreeNode> children;

    public TreeNode() {
    }

    public TreeNode(Long id, Long parentId, String name, Integer weight) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.weight = weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}
