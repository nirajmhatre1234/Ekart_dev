package com.example.productservice.dto;

import lombok.*;

import java.util.List;

public class ServiceInfo {

    private String serviceName;

    private List<String> instances;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public List<String> getInstances() {
        return instances;
    }

    public void setInstances(List<String> instances) {
        this.instances = instances;
    }

    public ServiceInfo() {
    }

    @Override
    public String toString() {
        return "ServiceInfo{" +
                "serviceName='" + serviceName + '\'' +
                ", instances=" + instances +
                '}';
    }
}
