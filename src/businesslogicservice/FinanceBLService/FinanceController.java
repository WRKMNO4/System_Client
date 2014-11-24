package businesslogicservice.FinanceBLService;

import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.AccountPO;
import PO.CaseListItemPO;
import PO.CashPO;
import PO.CollectionPO;
import PO.PaymentPO;
import PO.TransferListItemPO;
import RMI.Communication_Start;
import ResultMessage.ResultMessage;
import VO.AccountVO;
import VO.CaseListItemVO;
import VO.CashVO;
import VO.CollectionVO;
import VO.PaymentVO;
import VO.TransferListItemVO;

public class FinanceController implements FinanceBLService{
	ResultMessage result = null ;
	@Override
	public ResultMessage addAccount(AccountVO vo) {
		AccountPO account = new AccountPO(vo.getName(),vo.getBalance());
		Communication_Start com = new Communication_Start();
		com.initial();
		try {
			result = com.client.messageCommand("accountAdd", account) ;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResultMessage deletAccount(AccountVO vo) {
		// TODO Auto-generated method stub
		AccountPO account = new AccountPO(vo.getName(),vo.getBalance());
		Communication_Start com = new Communication_Start() ;
		com.initial();
		try {
			result = com.client.messageCommand("accountDelete", vo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResultMessage updateAccount(AccountVO vo) {
		// TODO Auto-generated method stub
		AccountPO account = new AccountPO(vo.getName(),vo.getBalance());
		Communication_Start com = new Communication_Start() ;
		com.initial();
		try {
			result = com.client.messageCommand("accountDelete", vo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ArrayList<AccountVO> findAccount(String keyword) {
		// TODO Auto-generated method stub
		ArrayList<Object> objects = new ArrayList<Object>() ;
		ArrayList<AccountVO> accounts = new ArrayList<AccountVO>() ;
		Communication_Start com = new Communication_Start();
		com.initial();
		try {
			objects = com.client.findObject("accountFind", keyword) ;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Object theAccount:objects){
			AccountPO acc = (AccountPO) theAccount ;
			accounts.add(new AccountVO(acc.getName(),acc.getBalance())) ;
		}
		return accounts;
	}

	@Override
	public ResultMessage addCollection(CollectionVO vo) {
		// TODO Auto-generated method stub
		ArrayList<TransferListItemPO> trList = new ArrayList<TransferListItemPO>();
		for(TransferListItemVO theItem:vo.getTrList()){
			trList.add(new TransferListItemPO(theItem.getAccount(),theItem.getTransferMoney(),theItem.getRemark())) ;
		}
		CollectionPO collection = new CollectionPO(vo.getNumber(),vo.getCustomer(),vo.getUser(),trList,vo.getTotal());
		Communication_Start com = new Communication_Start();
		com.initial();
		try {
			result = com.client.messageCommand("collectionAdd", collection) ;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResultMessage addPayment(PaymentVO vo) {
		// TODO Auto-generated method stubArrayList<TransferListItemPO> trList = new ArrayList<TransferListItemPO>();
		ArrayList<TransferListItemPO> trList = new ArrayList<TransferListItemPO>();
		for(TransferListItemVO theItem:vo.getTrList()){
			trList.add(new TransferListItemPO(theItem.getAccount(),theItem.getTransferMoney(),theItem.getRemark())) ;
		}
		PaymentPO payment = new PaymentPO(vo.getNumber(),vo.getCustomer(),vo.getUser(),trList,vo.getTotal()) ;
		Communication_Start com = new Communication_Start() ;
		com.initial();
		try {
			result = com.client.messageCommand("paymentAdd", payment) ;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResultMessage addCash(CashVO vo) {
		// TODO Auto-generated method stub
		ArrayList<CaseListItemPO> caseList = new ArrayList<CaseListItemPO>();
		for(CaseListItemVO theCase:vo.getCases()){
			caseList.add(new CaseListItemPO(theCase.getCasename(),theCase.getCaseMoney(),theCase.getRemark()));
		}
		CashPO cash = new CashPO(vo.getNumber(),vo.getAccount(),vo.getUser(),caseList,vo.getTotal()) ;
		Communication_Start com = new Communication_Start() ;
		com.initial(); 
		try {
			result = com.client.messageCommand("cashAdd", cash) ;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResultMessage init() {
		// TODO Auto-generated method stub
		return null;
	}
	public static void main(String[] args){
		ResultMessage result = null ;
		AccountVO account  = new AccountVO("0001",100);
		FinanceController controller = new FinanceController() ;
		result = controller.addAccount(account) ;
		System.out.println(result);
	}
}
