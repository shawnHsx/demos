package com.semion.web.action;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class UserUtil {

	public static final String TOKEN_NAME_KEY = "_login_token_" ;


	public LoginUser getCurrentUser(HttpServletRequest request){
		//判断是否已经登录成功
		Cookie[] cookies = request.getCookies() ;
		if(cookies != null){
			for (Cookie cookie : cookies) {
				if(TOKEN_NAME_KEY.equals(cookie.getName())){
					String value = cookie.getValue() ;
					if(value != null){
						/*Object obj = cacheService.getByKey(value) ;
						if(obj != null){
							LoginUser loginUser = (LoginUser)obj ;
							return loginUser ;
						}*/
					}
					break ;
				}
			}
		}
		return null ;
	}
	
	public boolean setCurrentUser(String token ,LoginUser user ){
		//return cacheService.put(token, user) ;
		return false;
	}

}
