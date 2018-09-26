package com.example.user.childhoodclass;

public class Story {
    private int id;
    private String content;
    private byte[] image;

    public Story(String content,byte[] image){
        this(0,content,image);
    }

   public Story(int id,String content,byte[] image){
       this.id=id;
       this.content=content;
       this.image=image;
   }
   public void setId(int id){
       this.id=id;
   }
   public int getId(){
       return id;
   }
   public void setContent(String content){
       this.content=content;
   }
   public String getContent(){
       return content;
   }
   public void setImage(byte[] image){
       this.image=image;
   }
   public byte[] getImage(){
       return image;
    }

}
