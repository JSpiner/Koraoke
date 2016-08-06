package net.jspiner.koraoke.Model;

/**
 * Copyright 2016 JSpiner. All rights reserved.
 *
 * @author JSpiner (jspiner@naver.com)
 * @project Android
 * @since 2016. 8. 6.
 */
public class UserModel {

    public String email;
    public String name;
    public String nickname;
    public String enc_id;
    public String profile_image;
    public String age;
    public String gender;
    public String id;
    public String birthday;

    private static UserModel userModel;

    public UserModel(){

    }

    public static UserModel create(String[] f_array){

        userModel = new UserModel();
        userModel.email = f_array[0];
        userModel.nickname = f_array[1];
        userModel.enc_id = f_array[2];
        userModel.profile_image = f_array[3];
        userModel.age = f_array[4];
        userModel.gender = f_array[5];
        userModel.id = f_array[6];
        userModel.name = f_array[7];
        userModel.birthday = f_array[8];

        return  userModel;
    }

    public static UserModel getInstance(){
        if(userModel == null){
            return new UserModel();
        }
        else{
            return userModel;
        }
    }


}
