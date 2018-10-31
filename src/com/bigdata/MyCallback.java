package com.bigdata;

import lombok.NoArgsConstructor;

//@NoArgsConstructor
public class MyCallback implements Callback{
  public void MyCallback(){
    System.out.println("the callback has been created!");
  }
  
  public void onComplete(String action, String path, String value){
    System.out.println("Watch info:【action】" + action + "; 【path】" + path + "; 【value】" + value);
  }
}


