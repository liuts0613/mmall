package com.mmall.Controller.portal;

import com.mmall.common.Const;
import com.mmall.common.ResponseCode;
import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;
import com.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     *
     * @param Username
     * @param Password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<User> login(String Username, String Password, HttpSession session){
        ServerResponse<User> response = iUserService.login(Username,Password);
        if (response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    @RequestMapping(value = "logout.do",method = RequestMethod.GET)
    @ResponseBody
    public  ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return  ServerResponse.createBySuccess();
    }

    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    @ResponseBody
    public  ServerResponse<String> register(User user){
        ServerResponse<String> response = iUserService.register(user);
        return  response;
    }

    @RequestMapping(value = "check_valid.do",method = RequestMethod.GET)
    @ResponseBody
    public  ServerResponse<String> checkValid(String username,String email){
        ServerResponse<String> response = iUserService.checkValid(username,Const.USERNAME);
        return  response;
    }

    /**
     * 获取当前登录用户信息
     * @param session
     * @return
     */
    @RequestMapping(value = "get_user_info.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> getUserInfo(HttpSession session){
      User user = (User) session.getAttribute(Const.CURRENT_USER);
      if (user!=null){
          return ServerResponse.createBySuccess(user);
      }
      return  ServerResponse.createByErrorMsg("用户未登录，无法获取当前用户的信息");
    }
    @RequestMapping(value = "forget_get_question.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
        return  iUserService.selectQuestion(username);
    }
    @RequestMapping(value = "forget_check_answer.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        return iUserService.checkAnswer(username,question,answer);
    }

    /**
     * 忘记密码下的重置密码
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @RequestMapping(value = "forget_reset_password.do",method = RequestMethod.GET)
    @ResponseBody
    public  ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
        return iUserService.forgetResetPassword(username,passwordNew,forgetToken);

    }

    @RequestMapping(value = "forget_reset_password.do",method = RequestMethod.GET)
    @ResponseBody
    public  ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user==null){
            return ServerResponse.createByErrorMsg("用户未登录");
        }
        return iUserService.resetPassword(passwordOld,passwordNew,user);
    }

    @RequestMapping(value = "updateInfo.do",method = RequestMethod.GET)
    @ResponseBody
    public ServerResponse<User> updateInfo(HttpSession session,User user){
        User currUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currUser==null){
            return ServerResponse.createByErrorMsg("用户未登录");
        }

        user.setId(currUser.getId());
        user.setUsername(currUser.getUsername());
        ServerResponse<User> response = iUserService.updateUserInfo(user);
        if (response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }
    @RequestMapping(value = "getInfomation.do",method = RequestMethod.GET)
    @ResponseBody
    public  ServerResponse<User> getInfomation(HttpSession session){
        User currUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currUser==null){
            return ServerResponse.createByErrorCodeMsg(ResponseCode.NEED_LOGIN.getCode(),"用户未登录,需要强制登录");
        }
        return iUserService.getUserInfo(currUser.getId());
    }
}
