//package com.xe.demo.shrio;
//
//import com.czxy.book.UserService;
//import org.apache.shiro.authc.AuthenticationInfo;
//import org.apache.shiro.authc.AuthenticationToken;
//import org.apache.shiro.authc.UsernamePasswordToken;
//import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
//
//import javax.annotation.Resource;
//
///**
// * 密码比较器
// *
// */
//public class BosCredentialsMatcher extends SimpleCredentialsMatcher {
//    @Resource
//    private UserService userService;
//    /**
//     * 执行凭证匹配
//     *
//     * @param token：用户页面输入的用户信息
//     * @return true： 密码正确
//     *         false：密码错误
//     */
//    @Override
//    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
//        //1 获取用户页面信息
//        //1.1 强转
//        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
//        //1.2 获取密码信息
//        char[] pwd = upToken.getPassword();
//        //1.3 将char数组转成String
//        String myPwd = new String(pwd);
//        System.out.println("--------------------------------"+myPwd);
//
//        //1.4 加密
//        //String newPwd = Encrypt.md5(myPwd, upToken.getUsername());
////        System.out.println("------------------------------------------------"+newPwd);
//
//        //2 获取数据库中的信息
//        Object dbPwd = info.getCredentials();
//        System.out.println("--------------------------------------------------"+dbPwd);
//        //3 比较
//        return equals(myPwd,dbPwd);
//    }
//
//}
