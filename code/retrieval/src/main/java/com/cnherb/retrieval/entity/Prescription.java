package com.cnherb.retrieval.entity;

public class Prescription {

    private long id;

    private String name;

    private String comDivide;

    private String function;
    
    private String cite;

    public String getname() {
    	return name;
    }
    public String getcomDivide() {
    	return comDivide;
    }
    public String getfunction() {
    	return function;
    }
    
    public String getcite() {
    	return cite;
    }
}