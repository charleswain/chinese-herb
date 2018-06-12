package com.cnherb.retrieval.dao;

import java.util.List;

import com.cnherb.retrieval.entity.Prescription;
import org.apache.ibatis.annotations.Param;

public interface PrescriptionDao {

    /**
     * ͨ
     * 
     * @param id
     * @return
     */
	Prescription queryById(long id);

	Prescription queryByName(String name);
    /**
     * 
     * 
     * @param offset 
     * @param limit 
     * @return
     */
    List<Prescription> queryAll(@Param("name")String name ,@Param("offset") int offset,  @Param("limit")int limit);



}