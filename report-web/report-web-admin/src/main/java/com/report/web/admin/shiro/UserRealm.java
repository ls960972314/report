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

import com.report.biz.admin.service.MemberService;
import com.report.common.model.SessionUtil;
import com.report.common.model.ShiroUser;

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
    	log.debug("username[{}]doGetAuthorizationInfo principals[{}]", SessionUtil.getUserInfo().getMember().getAccNo(), principals);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(SessionUtil.getUserInfo().getRoleCodeSet());
        authorizationInfo.setStringPermissions(SessionUtil.getUserInfo().getPermissionCodeSet());
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    	log.debug("username[{}]doGetAuthenticationInfo", token.getPrincipal());
        String username = (String)token.getPrincipal();
        ShiroUser user = memberService.findUserModelByAccNo(username);
        if(user == null) {
            throw new UnknownAccountException();
        }
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getAccNo(),
                user.getPassword(),
                ByteSource.Util.bytes(""),//加盐
                getName()  //realm name
        );
        return authenticationInfo;
    }


}
