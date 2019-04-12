package com.gradteam.porsaty.repository.projection;

/**
 * Created by tawfik on 5/1/2018.
 */

public class NormalUserProjects{
    public static interface UserNoPassword {
         long getId();
         String getUsername();
         String getEmail();
         String getFullName();
         String getSSN();

    }
}