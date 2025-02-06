/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package books;


 
 
 
public class Books {
  private String title;
  private String author;
  private double price;
  private int quantity;
  
  public Books(String title,String author,double price, int quantity){
      this.title = title;
      this.author = author;
      this.price = price*1.05;//Add 5%sales tax
      this.quantity = quantity;
  }
  public void setTitle(String title){
      this.title = title;
      
  }
    public String getAuthor(){
        return author;
        
    }
    public void setAuthor(String author){
        this.author = author;
    }
    public double getPrice(){
        return price;
        
    }
    public void setPrice(double price){
        this.price = price*1.05;//Add 5% sales tax
        
    }
     public int getQuantity(){
         return quantity;
         
     }
     public void setQuantity(int quantity){
         this.quantity = quantity;
         
     }
    public void display(){
        System.out.println("Title:"+ title);
        System.out.println("Author:"+ author);
    
   System.out.println("Price:$"+ price);
 System.out.println("Price:$"+ price);
System.out.println("Quantity:"+ quantity);
System.out.println();
}
}

    

