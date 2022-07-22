package com.example.domain;

import com.example.component.Labeled;

import javax.persistence.*;


/**
 * @BelongsProject: bigquarkus
 * @BelongsPackage: com.example.domain
 * @Author: vergil young
 * @CreateTime: 2022-07-19  17:36
 * @Description: 服务器实例
 */

@Entity
//@Cacheable
public class CloudComputer implements Labeled {

    private int cpuPower; // in gigahertz
    private int memory; // in gigabyte RAM
    private int networkBandwidth; // in gigabyte per hour
    private int cost; // in euro per month

    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CloudComputer() {
    }

    public CloudComputer(int cpuPower, int memory, int networkBandwidth, int cost) {
        this.cpuPower = cpuPower;
        this.memory = memory;
        this.networkBandwidth = networkBandwidth;
        this.cost = cost;
    }

    public int getCpuPower() {
        return cpuPower;
    }

    public void setCpuPower(int cpuPower) {
        this.cpuPower = cpuPower;
    }

    public int getMemory() {
        return memory;
    }

    public void setMemory(int memory) {
        this.memory = memory;
    }

    public int getNetworkBandwidth() {
        return networkBandwidth;
    }

    public void setNetworkBandwidth(int networkBandwidth) {
        this.networkBandwidth = networkBandwidth;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    public int getMultiplicand() {
        return cpuPower * memory * networkBandwidth;
    }

    @Override
    public String getLabel() {
        return "Computer " + id;
    }
}
