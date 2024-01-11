package com.yupi.springbootinit.model.vo;

import com.yupi.springbootinit.model.entity.Picture;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户视图（脱敏）
 *
 */
@Data
public class SearchVO implements Serializable {

    List<UserVO> userVOList;

    List<PostVO> postVOList;

    List<Picture> pictureList;

    private static final long serialVersionUID = 1L;
}