package com.shequgo.shequgoweb.controller;

import org.junit.Test;
import utils.Md5Util;

import static org.junit.Assert.*;

/**
 * @Author: Colin
 * @Date: 2019/5/2 16:34
 */
public class AdminControllerTest {

    @Test
    public void initAdmin() throws Exception {
       System.out.println( Md5Util.md5("000000",1+"passWord"));
    }
}