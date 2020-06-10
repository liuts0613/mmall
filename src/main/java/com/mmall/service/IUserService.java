package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.User;

import javax.servlet.http.HttpSession;

public interface IUserService {
    ServerResponse<User> login(String username, String passWord);

    ServerResponse<String> register(User user);

    ServerResponse<String> checkValid(String str,String type);

    public ServerResponse selectQuestion(String username);

    public ServerResponse<String> checkAnswer(String username,String question,String answer);

    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken);

    public  ServerResponse<String> resetPassword(String passwordOld, String passwordNew,User user);

    public ServerResponse<User> updateUserInfo(User user);

    public  ServerResponse<User> getUserInfo(Integer userId);

    //backend
    //校验是否是管理员
    public ServerResponse checkAdminRole(User user);
}
