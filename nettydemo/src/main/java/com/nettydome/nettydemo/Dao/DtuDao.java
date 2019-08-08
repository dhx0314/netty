package com.nettydome.nettydemo.Dao;

import com.nettydome.nettydemo.entity.Dtu;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
public interface DtuDao {

    List<Dtu> selectAll();

    void insertIp(Dtu dtu);

    void undateIp(Dtu dtu);


    Dtu selectOneByIp(String dtuIp);
    Dtu selectOneByMac(String dtuMac);
}
