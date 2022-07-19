package com.example.domain;

import com.example.component.Labeled;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @BelongsProject: bigquarkus
 * @BelongsPackage: com.example.domain
 * @Author: vergil young
 * @CreateTime: 2022-07-19  19:06
 * @Description: TODO
 */
public class CloudProcess implements Labeled {

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
    private int requiredCpuPower; // in gigahertz
    private int requiredMemory; // in gigabyte RAM
    private int requiredNetworkBandwidth; // in gigabyte per hour

    // Planning variables: changes during planning, between score calculations.
    private CloudComputer computer;

    public CloudProcess() {
    }

    public CloudProcess(long id, int requiredCpuPower, int requiredMemory, int requiredNetworkBandwidth) {

        this.requiredCpuPower = requiredCpuPower;
        this.requiredMemory = requiredMemory;
        this.requiredNetworkBandwidth = requiredNetworkBandwidth;
    }

    public int getRequiredCpuPower() {
        return requiredCpuPower;
    }

    public void setRequiredCpuPower(int requiredCpuPower) {
        this.requiredCpuPower = requiredCpuPower;
    }

    public int getRequiredMemory() {
        return requiredMemory;
    }

    public void setRequiredMemory(int requiredMemory) {
        this.requiredMemory = requiredMemory;
    }

    public int getRequiredNetworkBandwidth() {
        return requiredNetworkBandwidth;
    }

    public void setRequiredNetworkBandwidth(int requiredNetworkBandwidth) {
        this.requiredNetworkBandwidth = requiredNetworkBandwidth;
    }

    public CloudComputer getComputer() {
        return computer;
    }

    public void setComputer(CloudComputer computer) {
        this.computer = computer;
    }

    // ************************************************************************
    // Complex methods
    // ************************************************************************

    public int getRequiredMultiplicand() {
        return requiredCpuPower * requiredMemory * requiredNetworkBandwidth;
    }

    @Override
    public String getLabel() {
        return "Process " + id;
    }
}
