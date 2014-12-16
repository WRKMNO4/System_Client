package businesslogicservice.FinanceBLService;

import java.util.ArrayList;

import PO.CollectionOrPaymentPO;
import ResultMessage.ResultMessage;
import VO.AccountVO;
import VO.CashVO;
import VO.CollectionOrPaymentVO;

public class FinanceBLService_Driver {
	public void drive(FinanceBLService financeBLService){
//		financeBLService.addAccount(new AccountVO()) ;
//		financeBLService.deletAccount(new AccountVO()) ;
//		financeBLService.updateAccount(new AccountVO()) ;
//		financeBLService.findAccount("0001") ;
//		financeBLService.addCollectionOrPaymentVO(new CollectionOrPaymentVO()) ;
//		financeBLService.addCash(new CashVO()) ;
//		financeBLService.init() ; 
//		CollectionOrPaymentPO po = new CollectionOrPaymentPO("SKD-20141216-0001", "shengyu", "1", "shengyu", null, 1000,true,false) ;
//		financeBLService.updateCollectionOrPayment(po) ;
	}
	public static void main(String[] args){
//		FinanceBLService_Driver controller = new FinanceBLService_Driver() ;
		
		int count  = 0 ;
		FinanceController c = new FinanceController() ; 
////		controller.drive(c);
//		ArrayList<CollectionOrPaymentVO> receipts = c.showCollectionOrPaymentVOs() ;
//		for(CollectionOrPaymentVO theVO :receipts){
//			if(theVO.isApprovedByFinancer()){
//				count++ ;
//			}
//		}
//		CollectionOrPaymentPO po = new CollectionOrPaymentPO("SKD-20141216-0001", "shengyu", "1", "shengyu", null, 1000,true,false) ;
//		ResultMessage result = c.updateCollectionOrPayment(po) ;
		
		ArrayList<CollectionOrPaymentVO> pos = c.showCollectionOrPaymentVOs() ;
		for(CollectionOrPaymentVO theVO : pos){
			System.out.println(theVO.isApprovedByManager());
			System.out.println(theVO.isApprovedByFinancer());
		}
//		System.out.println(receipts.size());
//		System.out.println(count) ;
//		System.out.println(result);
	}
}
