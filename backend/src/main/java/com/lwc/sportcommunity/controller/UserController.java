package com.lwc.sportcommunity.controller;

import com.alibaba.druid.util.StringUtils;
import com.lwc.sportcommunity.controller.bo.UsersBO;
import com.lwc.sportcommunity.controller.vo.FriendVo;
import com.lwc.sportcommunity.controller.vo.UserVo;
import com.lwc.sportcommunity.dataobject.User;
import com.lwc.sportcommunity.dataobject.UserPassword;
import com.lwc.sportcommunity.error.EmSportError;
import com.lwc.sportcommunity.error.SportException;
import com.lwc.sportcommunity.response.CommonReturnType;
import com.lwc.sportcommunity.service.UserService;
import com.lwc.sportcommunity.service.model.UserModel;
import com.lwc.sportcommunity.utils.FastDFSClient;
import com.lwc.sportcommunity.utils.FileUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;

import javax.jws.soap.SOAPBinding;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Create by LWC on 2023/3/17 16:33
 */
@RestController("/user")
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private FastDFSClient fastDFSClient;

    //登录
    @RequestMapping(value = "/login", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType login(@RequestParam("telephone")String telephone,
                                  @RequestParam("password")String password) throws SportException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (org.apache.commons.lang3.StringUtils.isEmpty(telephone) || StringUtils.isEmpty(password)){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }

        UserModel userModel = userService.validateLogin(telephone, EncodeByMd5(password));
        UserVo userVo = convertFromUserModel(userModel);
        System.out.println("success");
        return CommonReturnType.create(userVo);
    }

    //注册
    @RequestMapping(value = "/register", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType register(@RequestParam("telephone")String telephone,
                                     @RequestParam("password")String password,
                                     @RequestParam("otpCode")String otpCode) throws SportException, UnsupportedEncodingException, NoSuchAlgorithmException {
        String redisOtpCode = (String)redisTemplate.opsForValue().get(telephone);
        if (!StringUtils.equals(otpCode, redisOtpCode)){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR,"短信验证码不符合");
        }
        //注册流程
        UserModel userModel = new UserModel();
        userModel.setTelephone(telephone);
        userModel.setRegisterMode("byphone");
        userModel.setName(telephone);
        userModel.setEncrpyPassword(this.EncodeByMd5(password));
        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    //验证码
    @RequestMapping(value = "/getotp", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType getOtp(@RequestParam(name = "telephone")String telephone) throws Exception {
        //验证码未过期
        if (redisTemplate.hasKey(telephone)){
            throw new SportException(EmSportError.USER_OTP_REPEAT);
        }

        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);
        //存Redis,设置过期时间
        redisTemplate.opsForValue().set(telephone ,otpCode);
        redisTemplate.expire(telephone,1, TimeUnit.MINUTES);

        //otp验证码通过短信通道发送给用户
        System.out.println("telephone:"+telephone+"  otpCode:"+otpCode);

        return CommonReturnType.create(otpCode);
    }

    //密码加密
    public String EncodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        //加密字符串
        String newstr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }

    @RequestMapping(value = "/updateSignature", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType updateSignature(@RequestParam(name = "telephone")String telephone,
                                            @RequestParam(name = "signature")String signature) throws SportException {
        if (telephone == null){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }
        User user = new User();
        user.setTelephone(telephone);
        user.setSignature(signature);
        UserModel userModel;
        try {
            userModel = userService.update(user);
        }catch (Exception e){
            throw new SportException(EmSportError.UNKNOWN_ERROR);
        }
        if (userModel == null){
            throw new SportException(EmSportError.UNKNOWN_ERROR);
        }
        UserVo userVo = convertFromUserModel(userModel);
        return CommonReturnType.create(userVo);
    }

    @RequestMapping(value = "/updateGender", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType updateGender(@RequestParam(name = "telephone")String telephone,
                                      @RequestParam(name = "gender")Byte gender) throws SportException {
        if (telephone == null){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }
        User user = new User();
        user.setTelephone(telephone);
        user.setGender(gender);
        UserModel userModel;
        try {
            userModel = userService.update(user);
        }catch (Exception e){
            throw new SportException(EmSportError.UNKNOWN_ERROR);
        }
        if (userModel == null){
            throw new SportException(EmSportError.UNKNOWN_ERROR);
        }
        UserVo userVo = convertFromUserModel(userModel);
        return CommonReturnType.create(userVo);
    }



    @RequestMapping(value = "/updateAge", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType updateAge(@RequestParam(name = "telephone")String telephone,
                                            @RequestParam(name = "age")String age) throws SportException {
        if (telephone == null){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }
        User user = new User();
        user.setTelephone(telephone);
        user.setAge(Integer.parseInt(age));
        UserModel userModel;
        try {
            userModel = userService.update(user);
        }catch (Exception e){
            throw new SportException(EmSportError.UNKNOWN_ERROR);
        }
        if (userModel == null){
            throw new SportException(EmSportError.UNKNOWN_ERROR);
        }
        UserVo userVo = convertFromUserModel(userModel);
        return CommonReturnType.create(userVo);
    }


    @RequestMapping(value = "/updateName", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType updateName(@RequestParam(name = "telephone")String telephone,
                                       @RequestParam(name = "name")String name)throws SportException {
        if (telephone == null){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }
        User user = new User();
        user.setTelephone(telephone);
        user.setName(name);
        UserModel userModel;
        try {
            userModel = userService.update(user);
        }catch (Exception e){
            throw new SportException(EmSportError.UNKNOWN_ERROR);
        }
        if (userModel == null){
            throw new SportException(EmSportError.UNKNOWN_ERROR);
        }
        UserVo userVo = convertFromUserModel(userModel);
        return CommonReturnType.create(userVo);
    }

    @RequestMapping(value = "/updatePassWord", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType updatePassword(@RequestParam(name = "telephone")String telephone,
                                       @RequestParam(name = "oldPassword")String oldPassword,
                                           @RequestParam(name = "newPassword")String newPassword) throws SportException, UnsupportedEncodingException, NoSuchAlgorithmException {
        if (telephone == null){
            throw new SportException(EmSportError.PARAMETER_VALIDATION_ERROR);
        }

        boolean flag = userService.updatePassword(telephone, EncodeByMd5(oldPassword), EncodeByMd5(newPassword));

        return CommonReturnType.create(null);
    }

    /**
     * @Description: 上传用户头像
     */
    @PostMapping("/uploadFaceBase64")
    public CommonReturnType uploadFaceBase64(@RequestBody UsersBO userBO) throws Exception {

        // 获取前端传过来的base64字符串, 然后转换为文件对象再上传
        String base64Data = userBO.getFaceData();
        String userFacePath = "D:\\" + userBO.getUserId() + "userface64.png";
        FileUtils.base64ToFile(userFacePath, base64Data);

        // 上传文件到fastdfs
        MultipartFile faceFile = FileUtils.fileToMultipart(userFacePath);
        String url = fastDFSClient.uploadBase64(faceFile);
        System.out.println(url);

        // 获取缩略图的url
        String thump = "_80x80.";
        String arr[] = url.split("\\.");
        String thumpImgUrl = arr[0] + thump + arr[1];
//        System.out.println(thumpImgUrl);

        // 更细用户头像
        User user = new User();
        user.setId(Integer.parseInt(userBO.getUserId()));
        user.setFaceImage(thumpImgUrl);
        user.setFaceImageBig(url);

        UserModel userModel = userService.updateUserInfo(user);
        UserVo userVo = convertFromUserModel(userModel);

        return CommonReturnType.create(userVo);
    }

    @RequestMapping(value = "/getFace", method = {RequestMethod.POST},consumes = {CONTENT_TYPE_FORMED})
    public CommonReturnType getFace(@RequestParam("userId") Integer userId) throws SportException{
        User face = userService.getFace(userId);
        FriendVo friendVo = new FriendVo();
        friendVo.setUserId(face.getId());
        friendVo.setName(face.getName());
        friendVo.setImgUrl(face.getFaceImage());
        return CommonReturnType.create(friendVo);
    }

    private UserVo convertFromUserModel(UserModel userModel){
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userModel,userVo);
        return userVo;
    }
}
