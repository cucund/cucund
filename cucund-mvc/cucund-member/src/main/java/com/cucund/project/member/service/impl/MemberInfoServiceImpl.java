package com.cucund.project.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cucund.project.db.dao.DBSqlDao;
import com.cucund.project.member.dao.MemberInfoDatMapper;
import com.cucund.project.member.dto.member.MemberModifyReq;
import com.cucund.project.member.dto.register.UserRegisterReq;
import com.cucund.project.member.entity.MemberInfoDat;
import com.cucund.project.member.service.MemberInfoService;
import com.cucund.project.tool.utils.exception.SQLUtils;
import com.cucund.project.tool.utils.valid.ValidationUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MemberInfoServiceImpl implements MemberInfoService {

    @Autowired
    MemberInfoDatMapper memberInfoDatMapper;


    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    @Override
    public MemberInfoDat findMemberByName(String username) {
        QueryWrapper<MemberInfoDat> ex = new QueryWrapper<>();
        ex.eq("username",username);
        ex.eq("del",0);
        MemberInfoDat dat = memberInfoDatMapper.selectOne(ex);
        return dat;
    }

    /**
     * 新增 用户
     * @param registerReq
     * @return
     */
    @Override
    public MemberInfoDat insert(UserRegisterReq registerReq) {
        MemberInfoDat dat = new MemberInfoDat();
        dat.setUsername(registerReq.getUsername());
        if(StringUtils.isNotBlank(registerReq.getPassword())){//密码是否为空
            dat.setPassword(registerReq.getPassword());
            encodePassword(dat);
        }
        int i = memberInfoDatMapper.insert(dat);
        SQLUtils.check(i,"新增用户失败");
        return dat;
    }

    @Override
    public void update(MemberModifyReq req) {
        ValidationUtils.fastFailValidate(req).throwException();
        MemberInfoDat dat = new MemberInfoDat();
        BeanUtils.copyProperties(req,dat);
        int i = memberInfoDatMapper.updateById(dat);
        SQLUtils.check(i,"更新用户信息失败");
    }

    @Autowired
    DBSqlDao dbSqlDao;

    @Override
    public Map<String,Object> getData(Map<String,Object> map ) {
        return dbSqlDao.selectOne4DbSql("getData",map);
    }

    /**
     * 混淆密码
     * @param dat
     */
    private void encodePassword(MemberInfoDat dat) {
        String password = dat.getPassword();
        String username = dat.getUsername();
        PasswordEncoder e = new BCryptPasswordEncoder();
        dat.setPassword(e.encode(password+username));
    }
}
