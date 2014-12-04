package PO;

import java.io.Serializable;
import java.util.ArrayList;

import VO.TransferListItemVO;

public class CollectionOrPaymentPO implements Serializable{
	String number ;
	String customer ;
	String typeOfCustomer ;
	String user ;
	ArrayList<TransferListItemPO> trList = new ArrayList<TransferListItemPO>();//转账列表
	double total ;
	public CollectionOrPaymentPO(){
		number = null ;
		customer = null ;
		user = null ;
		total = 0 ;
	}
	public CollectionOrPaymentPO(String theNumber,String theCustomer,String theTypeOfCustomer,String theUser,ArrayList<TransferListItemPO> theTrList , double theSum){
		number = theNumber ; 
		customer = theCustomer ; 
		typeOfCustomer = theTypeOfCustomer ;
		user = theUser ; 
		trList = theTrList ;
		total = theSum ;
	}
	public String getTypeOfCustomer() {
		return typeOfCustomer;
	}
	public void setTypeOfCustomer(String typeOfCustomer) {
		this.typeOfCustomer = typeOfCustomer;
	}
	public ArrayList<TransferListItemPO> getTrList() {
		return trList;
	}
	public void setTrList(ArrayList<TransferListItemPO> trList) {
		this.trList = trList;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public void add(TransferListItemPO theItem){
		trList.add(theItem) ;
	}
	public double getTotal(){
		double total = 0 ;
		for(TransferListItemPO theItem:trList){
			total += theItem.getTransferMoney() ;
		}
		return total ;
	}
	public String getNumber() {
		return number;
	}
	
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	

}