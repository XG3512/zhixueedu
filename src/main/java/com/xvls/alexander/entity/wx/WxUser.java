package com.xvls.alexander.entity.wx;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("wx_user")
public class WxUser extends Model<WxUser> {
    @TableId(type = IdType.AUTO)
    private Integer wxUserId;
    private String nickname;
    private String openid;
    private String sessionKey;
    private String avatarUrl;
    private String city;
    private String country;
    private int gender;
    private String province;
    private Integer wxstatus;
}
