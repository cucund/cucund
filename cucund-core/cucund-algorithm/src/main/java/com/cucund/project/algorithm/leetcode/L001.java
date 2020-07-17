package com.cucund.project.algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

public class L001 {

    /**
     * 暴力破解
     */
    public static int[] doubleForLoop(int[] nums ,int target){
        for (int i = 0; i < nums.length; i++) {
            for (int j = 1; j < nums.length-1; j++) {
                if((nums[i] + nums[j])-target == 0)
                    return new int[]{i,j};
            }
        }
        throw new RuntimeException("No two sum solution");
    }


    public static int[] twiceHash(int[] nums ,int target){
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i],i);
        }
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if(map.containsKey(complement)&&map.get(complement)!= i)
                return new int[]{i,map.get(complement)};
        }
        throw new RuntimeException("No two sum solution");
    }

    public static int[] onceHash(int[] nums ,int target){
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if(map.containsKey(complement))
                return new int[]{i,map.get(complement)};
            map.put(nums[i],i);
        }
        throw new RuntimeException("No two sum solution");
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        int[] ints = onceHash(new int[]{2, 7, 8, 10}, 10);
        long end = System.currentTimeMillis();
        System.out.println(start - end+"ms");
        System.out.println(ints[0]+","+ints[1]);
    }
}
