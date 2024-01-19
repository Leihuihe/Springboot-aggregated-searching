package com.yupi.springbootinit.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.springbootinit.common.BaseResponse;
import com.yupi.springbootinit.common.ErrorCode;
import com.yupi.springbootinit.common.ResultUtils;
import com.yupi.springbootinit.datasource.DataSource;
import com.yupi.springbootinit.datasource.PictureDataSource;
import com.yupi.springbootinit.datasource.PostDataSource;
import com.yupi.springbootinit.datasource.UserDataSource;
import com.yupi.springbootinit.exception.ThrowUtils;
import com.yupi.springbootinit.model.dto.post.PostQueryRequest;
import com.yupi.springbootinit.model.dto.search.SearchRequest;
import com.yupi.springbootinit.model.dto.user.UserQueryRequest;
import com.yupi.springbootinit.model.entity.Picture;
import com.yupi.springbootinit.model.enums.SearchTypeEnum;
import com.yupi.springbootinit.model.vo.PostVO;
import com.yupi.springbootinit.model.vo.SearchVO;
import com.yupi.springbootinit.model.vo.UserVO;
import com.yupi.springbootinit.service.PictureService;
import com.yupi.springbootinit.service.PostService;
import com.yupi.springbootinit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class SearchFacade {

    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PostDataSource postDataSource;

    public SearchVO searchAll(@RequestBody SearchRequest searchRequest,
                                            HttpServletRequest request) {
        String type = searchRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
        String searchText = searchRequest.getSearchText();
        long current = searchRequest.getCurrent();
        long size = searchRequest.getPageSize();
        SearchVO searchVO = new SearchVO();
        if(searchTypeEnum == null) {
            Page<Picture> picturePage = pictureDataSource.doSearch(searchText, current, size);
            PostQueryRequest postQueryRequest = new PostQueryRequest();
            postQueryRequest.setSearchText(searchText);
            Page<PostVO> postVOPage = postDataSource.doSearch(searchText,  current, size);
            UserQueryRequest userQueryRequest = new UserQueryRequest();
            userQueryRequest.setUserName(searchText);
            Page<UserVO> userVOPage = userDataSource.doSearch(searchText, current, size);

            searchVO.setPictureList(picturePage.getRecords());
            searchVO.setUserList(userVOPage.getRecords());
            searchVO.setPostList(postVOPage.getRecords());


        } else {
            Map<String, DataSource> typeDataSourceMap = new HashMap() {{
                put(searchTypeEnum.POST.getValue(), postDataSource);
                put(searchTypeEnum.PICTURE.getValue(), pictureDataSource);
                put(searchTypeEnum.USER.getValue(), userDataSource);
            }};
            DataSource dataSource = typeDataSourceMap.get(type);
            Page page = dataSource.doSearch(searchText, current, size);
            searchVO.setDataList(page.getRecords());

//            switch (searchTypeEnum) {
//                case POST:
//                    dataSource = postDataSource;
//                    break;
//                case PICTURE:
//                    dataSource = postDataSource;
//                    break;
//                case USER:
//                    dataSource = userDataSource;
//                    break;
//                default:
//            }

//            switch (searchTypeEnum) {
//                case POST:
//                    PostQueryRequest postQueryRequest = new PostQueryRequest();
//                    postQueryRequest.setSearchText(searchText);
//                    Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
//                    searchVO.setPostList(postVOPage.getRecords());
//                    break;
//                case PICTURE:
//                    Page<Picture> picturePage = pictureService.searchPicture(searchText, 1, 10);
//                    searchVO.setPictureList(picturePage.getRecords());
//                    break;
//                case USER:
//                    UserQueryRequest userQueryRequest = new UserQueryRequest();
//                    userQueryRequest.setUserName(searchText);
//                    Page<UserVO> userVOPage = userService.listVOByPage(userQueryRequest);
//                    searchVO.setUserList(userVOPage.getRecords());
//                    break;
//                default:
//            }
        }
        return searchVO;


    }
}
