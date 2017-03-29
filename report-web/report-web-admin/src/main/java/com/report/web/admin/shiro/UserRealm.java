package com.report.web.admin.shiro;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.alibaba.fastjson.JSON;
import com.report.biz.admin.service.MemberService;
import com.report.common.model.UserModel;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Resource
    private MemberService memberService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	log.info("doGetAuthorizationInfo principals[{}]", principals);
        String username = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(memberService.findRoles(username));
        authorizationInfo.setStringPermissions(memberService.findPermissions(username));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    	log.info("doGetAuthenticationInfo");
        String username = (String)token.getPrincipal();
        UserModel user = memberService.findUserModelByAccNo(username);
        if(user == null) {
            throw new UnknownAccountException();
        }
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getAccNo(), //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(""),//salt=username+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }


}
