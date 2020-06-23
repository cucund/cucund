package com.cucund.project.member.dao;


import com.cucund.project.db.anno.SQLName;
import com.cucund.project.db.dao.BaseDao;
import com.cucund.project.member.entity.MemberInfoDat;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MemberInfoDatMapper extends BaseDao<MemberInfoDat> {

}
