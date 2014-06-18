package com.smapp.wedelis;

public class Order {
	  private long id;
	  private String item;
      private String price;
      private String quantity;
	  public long getId() {
	    return id;
	  }

	  public void setId(long id) {
	    this.id = id;
	  }

	  public String getItem() {
	    return item;
	  }
	  public String getPrice() {
		    return price;
		  }
	  public String getQty() {
		    return quantity;
		  }
	  public void setItem(String item) {
	    this.item = item;
	  }
	  public void setPrice(String price) {
		    this.price = price;
		  }
	  public void setQty(String quantity) {
		    this.quantity = quantity;
		  }

	  // Will be used by the ArrayAdapter in the ListView
	  @Override
	  public String toString() {
	    return item;
	  }
	} 