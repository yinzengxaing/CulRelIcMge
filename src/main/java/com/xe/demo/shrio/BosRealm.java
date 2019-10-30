//package com.xe.demo.shrio;
//
//import com.czxy.book.PermissionService;
//import com.czxy.book.RoleService;
//import com.czxy.book.UserService;
//import com.czxy.book.domain.Permission;
//import com.czxy.book.domain.Role;
//import com.czxy.book.domain.User;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.SimpleAuthenticationInfo;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.authz.SimpleAuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
////自定义Realm ，实现安全数据 连接
//public class BosRealm extends AuthorizingRealm {
//
//
//    @Autowired
//    private UserService userService;
//    @Autowired
//    private RoleService roleService;
//    @Autowired
//    private PermissionService permissionService;
//
//    // 认证...
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(
//            AuthenticationToken token) {
//        System.out.println("shiro 认证管理... ");
//        //1 用户信息
//        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
//
//        //2 通过username从数据库中查找 User对象，如果找到，没找到.
//        User user = userService.finUserByLogin(upToken.getUsername());
//        if(user == null){
//            //返回null表示账号不存在
//            return null;
//        }
//        // 进入此位置表明用户名存在，返回信息
//        /**
//         * Object principal, Object credentials, String realmName
//         * 第一个参数principal：身份，将当前数据库中的对象放进去
//         * 第二个参数credentials：凭证，密码， 将数据库中查出来的密码放进去
//         * 第三个参数realmName：Realm的名字，可以随意，一般写类名
//         */
//
//        return new SimpleAuthenticationInfo(user, user.getPass(), this.getName());
//    }
//
//    @Override
//    // 授权...
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
//        System.out.println("shiro 授权管理...");
//        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
//        // 根据当前登录用户 查询对应角色和权限
////    User user = (User) SecurityUtils.getSubject().getPrincipal();
//        User user = (User) pc.getPrimaryPrincipal();
//        // 调用业务层，查询角色
//        List<Role> roles = roleService.findByUser(user);
//        for (Role role : roles) {
//            authorizationInfo.addRole(role.getKeyword());
//        }
//        // 调用业务层，查询权限
//        List<Permission> permissions = permissionService.findByUser(user);
//        for (Permission permission : permissions) {
//            authorizationInfo.addStringPermission(permission.getKeyword());
//        }
//
//        return authorizationInfo;
//    }
//}
