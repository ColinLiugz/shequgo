package com.shequgo.shequgoweixin.util;

/**
 * @Author: Colin
 * @Date: 2019/3/30 13:32
 */
public class DistanceUtil {
    /**
     * location为经度#纬度格式
     * @param location1
     * @param location2
     * @return
     */
    public static Double getDistance(String location1,String location2){
        String[] location1Arr = location1.split("#");
        String[] location2Arr = location2.split("#");
        Double distancePow = Math.pow((Double.valueOf(location1Arr[0])-Double.valueOf(location2Arr[0])) + Double.valueOf(location1Arr[1])-Double.valueOf(location2Arr[1]),2);
        return Math.sqrt(distancePow);
    }
}
