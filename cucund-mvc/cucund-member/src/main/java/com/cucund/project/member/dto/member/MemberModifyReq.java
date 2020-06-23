package com.cucund.project.member.dto.member;

import com.cucund.project.tool.utils.valid.DataInfo;
import com.cucund.project.tool.utils.valid.ValidMessage;
import com.cucund.project.tool.utils.valid.ValidResult;
import com.cucund.project.tool.utils.valid.ValidationUtils;
import lombok.Data;

import javax.validation.Payload;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.valueextraction.Unwrapping;
import java.util.Date;

@Data
public class MemberModifyReq {

    @DataInfo(key = ValidMessage.FIELD_NAME,value = "昵称 ")
    @Size(message = ValidMessage.SIZE,  min = 1,max = 20)
    private String nickname;

    @DataInfo(key = ValidMessage.FIELD_NAME,value = "头像 ")
    @Size(message = ValidMessage.SIZE,  min = 1,max = 300)
    private String virtualHead;

    @DataInfo(key = ValidMessage.FIELD_NAME,value = "年龄 ")
    @Min(message = ValidMessage.MIN,value = 0)
    @Max(message = ValidMessage.MAX,value = 999)
    private Integer age;

    @DataInfo(key = ValidMessage.FIELD_NAME,value = "性别 ")
    @Min(message = ValidMessage.MIN,value = 0)
    @Max(message = ValidMessage.MAX,value = 1)
    private Integer gender;

    @DataInfo(key = ValidMessage.FIELD_NAME,value = "生日 ")
    private Date birthday;

}
