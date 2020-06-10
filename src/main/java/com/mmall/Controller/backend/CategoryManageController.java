package com.mmall.Controller.backend;

import com.mmall.common.Const;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.ICategoryService;
import com.mmall.service.IUserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "manager/category")
public class CategoryManageController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;
    @RequestMapping("add_category.do")
    @ResponseBody
    public ServerResponse addCategory(HttpSession session,String categoryName ,@RequestParam(value = "parentId",defaultValue = "0") Integer parentId){
        User currUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currUser==null){
            return ServerResponse.createByErrorMsg("用户未登录");
        }
        //校验是否是管理员
        if(iUserService.checkAdminRole(currUser).isSuccess()){
            //是管理员
            //增加我们处理分类的逻辑
            return iCategoryService.addCategory(categoryName,parentId);
        }else{
            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
        }
    }

    @RequestMapping("set_category_name.do")
    @ResponseBody
    public ServerResponse setCategoryName(HttpSession session,Integer categoryId,String categoryName){
        User currUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currUser==null){
            return ServerResponse.createByErrorMsg("用户未登录");
        }
        //校验是否是管理员
        if(iUserService.checkAdminRole(currUser).isSuccess()){
            //是管理员
            //增加我们处理分类的逻辑
            return iCategoryService.updateCategoryName(categoryName,categoryId);
        }else{
            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
        }
    }
    @RequestMapping("get_category.do")
    @ResponseBody
    public ServerResponse getChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User currUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currUser==null){
            return ServerResponse.createByErrorMsg("用户未登录");
        }
        //校验是否是管理员
        if(iUserService.checkAdminRole(currUser).isSuccess()){
            //是管理员
            //查询子节点的category信息，并且不递归，保持平级
            return iCategoryService.getChildrenParallelCategory(categoryId);
        }else{
            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
        }
    }

    @RequestMapping("get_deep_category.do")
    @ResponseBody
    public ServerResponse getCategoryAndDeepChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User currUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currUser==null){
            return ServerResponse.createByErrorMsg("用户未登录");
        }
        //校验是否是管理员
        if(iUserService.checkAdminRole(currUser).isSuccess()){
            //是管理员
            //查询当前子节点的id和递归子节点的id
            return iCategoryService.selectCategoryAndChildrenById(categoryId);
        }else{
            return ServerResponse.createByErrorMsg("无权限操作，需要管理员权限");
        }
    }

}
