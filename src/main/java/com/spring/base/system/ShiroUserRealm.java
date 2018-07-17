package com.spring.base.system;

import com.spring.base.entity.Role;
import com.spring.base.entity.User;
import com.spring.base.service.impl.UserServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author GaoWeicai.(gaowc@gfire.cn)
 * @date 2018/7/6
 */
public class ShiroUserRealm extends AuthorizingRealm {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserServiceImpl userService;

    /**
     * 登录验证的方法
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        String loginId = userToken.getUsername();

        //获取用户登录信息
        User user = userService.findByLoginName(loginId);
        if (user == null) {
            throw new UnknownAccountException("用户不存在!");
        }

        //进行秘密验证  密码验证的方法 使用MD5 或者SHA1 shiro中配置
        //4). 盐值.
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getSalt());
        //返回验证的信息
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), credentialsSalt, super.getName());
        this.setSession("currentUser", user);
        return info;
    }

    /**
     * 授权认知的方法
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();
        user = userService.findByLoginName(user.getLoginName());
        if (CollectionUtils.isNotEmpty(user.getRoleList())) {
            for (Role role : user.getRoleList()) {
                authorizationInfo.addRole(role.getCode());
                if(null != role && CollectionUtils.isNotEmpty(role.getPermissionList())) {
                    role.getPermissionList().stream().forEach(permission -> {
                        authorizationInfo.addStringPermission(permission.getCode());
                    });
                }
            }
        }
        return authorizationInfo;
    }

    /**
     * 保存登录名
     */
    private void setSession(Object key, Object value) {
        Session session = getSession();
        if (null != session) {
            log.info("Session默认超时时间为[" + session.getTimeout() + "]毫秒");
            session.setAttribute(key, value);
        }
    }

    private Session getSession() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null) {
                session = subject.getSession();
            }
            if (session != null) {
                return session;
            }
        } catch (InvalidSessionException e) {

        }
        return null;
    }

    public static String getPasswrod(String password, String salt) {
        return new SimpleHash("MD5",password,ByteSource.Util.bytes(salt),1024).toString();
    }

    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    public UserServiceImpl getUserService() {
        return userService;
    }
}
