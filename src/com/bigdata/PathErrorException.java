package com.bigdata;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PathErrorException extends Exception{
  String path;
  public String getError(){
    return path + " is not correct!";
  }
}
