package com.bigdata;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * 孩子兄弟表示法
 */
public class Tree{

  private Node root;
  
  private ArrayList<Callback> callbackList = new ArrayList<Callback>();

  public Tree(){
    root = new Node("/", "/", null, null, null);
  }

  private String[] parsePath(String path) throws PathErrorException{
    if(path.charAt(0) != '/'){
      throw new PathErrorException(path);
    }
    String[] arr = path.split("/+");
    arr = Arrays.stream(arr).filter(x -> x.length() > 0).toArray(String[]::new);
    return arr;
  }

  public void log(String msg){
    System.out.println(msg);
  }

  public void setValue(String path, String value) throws PathErrorException{
    String[] pathNodes = parsePath(path);
    int i = 0;
    Node p = root;
    Node leftSibling = null;
    while(i < pathNodes.length){ // 树高-1
      if (p == null)
        throw new PathErrorException(path);

      if(p.getCallback() != null){
        callbackList.add(p.getCallback());
      }
     
      p = p.getChild();
    
      // 遍历一层
      while(p != null && !pathNodes[i].equals(p.getPath())){
        p = p.getRightSibling();
      }

      // 找到节点
      if(p != null && i == pathNodes.length - 1 && pathNodes[i].equals(p.getPath())){
        p.setValue(value);
      }
      i++;
    }
    for(Callback callback: callbackList){
      callback.onComplete("set", path, value);
    }
    callbackList.clear();
  }


  public String getValue(String path) throws PathErrorException{
    if(path.equals("/")) return root.getValue();
    String[] pathNodes = parsePath(path);

    int i = 0;
    Node p = root;
    Node leftSibling = null;
    while(i < pathNodes.length){ // 树高-1
      if (p == null)
        throw new PathErrorException(path);

      p = p.getChild();
       
      // 遍历一层
      while(p != null && !pathNodes[i].equals(p.getPath())){
        p = p.getRightSibling();
      }

      // 找到节点
      if(p != null && i == pathNodes.length - 1 && pathNodes[i].equals(p.getPath())){
        return p.getValue();
      }
      i++;
    }
    throw new PathErrorException(path);
  }

  public void watch(String path, Callback callback) throws PathErrorException{
    if(path.equals("/")) {
      root.setCallback(callback);
      return;
    }
    String[] pathNodes = parsePath(path);

    int i = 0;
    Node p = root;
    Node leftSibling = null;
    while(i < pathNodes.length){ // 树高-1
      if (p == null)
        throw new PathErrorException(path);

      if(p.getCallback() != null){
        callbackList.add(p.getCallback());
      }
     
      p = p.getChild();
       
      // 遍历一层
      while(p != null && !pathNodes[i].equals(p.getPath())){
        p = p.getRightSibling();
      }

      // 找到节点
      if(p != null && i == pathNodes.length - 1 && pathNodes[i].equals(p.getPath())){
        p.setCallback(callback);
        return;
      }
      i++;
    }
    throw new PathErrorException(path);
  }

  public void create(String path, String value) throws PathErrorException{
    if(path.equals("/")) throw new PathErrorException(path);

    String[] pathNodes = parsePath(path);

    int i = 0;
    Node p = root;
    Node leftSibling = null;
    while(i < pathNodes.length){ // 树高-1

      if(p.getCallback() != null){
        callbackList.add(p.getCallback());
      }
      // 添加第一个孩子
      if(i == pathNodes.length - 1 && p.getChild() == null){
        p.setChild(new Node(pathNodes[i], value, null, null, null));
        break;
      } 
     
      p = p.getChild();
      leftSibling = null;
      
      // 遍历一层
      while(p != null && !pathNodes[i].equals(p.getPath())){
        leftSibling = p;
        p = p.getRightSibling();
      }

      // 节点已经存在，覆盖值
      if(p != null &&i == pathNodes.length - 1 && pathNodes[i].equals(p.getPath())){
        p.setValue(value);
      }

      // 添加兄弟节点
      if(i == pathNodes.length - 1 && p == null){
        leftSibling.setRightSibling(new Node(pathNodes[i], value, null, null, null));
        break;
      }

      // 还有节点未创建，抛出异常
      if(i < pathNodes.length - 1 && p == null){
        throw new PathErrorException(path);
      }
      
      i++;
    }
    for(Callback callback: callbackList){
      callback.onComplete("create", path, value);
    }
    callbackList.clear();
  }

}

@Getter
@Setter
@AllArgsConstructor
class Node{
  String path;
  String value;
  Node child;
  Node rightSibling;
  Callback callback;
}

