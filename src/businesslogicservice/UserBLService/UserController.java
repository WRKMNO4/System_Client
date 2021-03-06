package businesslogicservice.UserBLService;

import java.rmi.RemoteException;
import java.util.ArrayList;

import PO.UserPO;
import RMI.Communication;
import RMI.Communication_Start;
import ResultMessage.ResultMessage;
import VO.UserVO;

public class UserController implements UserBLService  {
	ResultMessage result=null;
	@Override
	public ResultMessage login(UserVO vo) {
		// TODO Auto-generated method stub
		UserPO userpo=new UserPO(vo.getUserName(), vo.getPassword(),vo.getUserSort(), 0);
		Communication_Start com=new Communication_Start();
		com.initial();
		try {
			result=com.client.messageCommand("userLogin", userpo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResultMessage add(UserVO vo) {
		// TODO Auto-generated method stub
		UserPO userpo=new UserPO(vo.getUserName(), vo.getPassword(),vo.getUserSort(), vo.getLevel());
		Communication_Start com=new Communication_Start();
		com.initial();
		try {
			result=com.client.messageCommand("userAdd", userpo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResultMessage delete(UserVO vo) {
		// TODO Auto-generated method stub
		UserPO userpo=new UserPO(vo.getUserName(), vo.getPassword(),vo.getUserSort(), vo.getLevel());
		Communication_Start com=new Communication_Start();
		com.initial();
		try {
			result=com.client.messageCommand("userDelete", userpo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ResultMessage update(UserVO vo) {
		// TODO Auto-generated method stub
		UserPO userpo=new UserPO(vo.getUserName(),vo.getPassword(),vo.getUserSort(),vo.getLevel());
		Communication_Start com=new Communication_Start();
		com.initial();
		try {
			result=com.client.messageCommand("userUpdate", userpo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public ArrayList<UserVO> find(String keywords) {
		// TODO Auto-generated method stub
		Communication_Start com=new Communication_Start();
		com.initial();
		ArrayList<Object> objects=new ArrayList<Object>();
		ArrayList<UserVO> uservos=new ArrayList<UserVO>();
		try {
			objects=com.client.findObject("userFind", keywords);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Object user:objects){
			UserPO tmp=(UserPO) user;
			uservos.add(new UserVO(tmp.getUserName(),tmp.getPassword(),tmp.getUserSort(),tmp.getLevel()));
		}
		return uservos;
	}

	@Override
	public ArrayList<UserVO> show() {
		// TODO Auto-generated method stub
		ArrayList<Object> objects=new ArrayList<Object>();
		ArrayList<UserVO> uservos=new ArrayList<UserVO>();
		Communication_Start com=new Communication_Start();
		com.initial();
		try {
			objects=com.client.showObject("userShow");
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for(Object user:objects){
			UserPO po=(UserPO)user;
			UserVO vo=new UserVO(po.getUserName(), po.getPassword(), po.getUserSort(), po.getLevel());
			uservos.add(vo);
		}
		return uservos;
	}
	
	public UserPO VOtoPO(UserVO v){
		return new UserPO(v.getUserName(), v.getPassword(), v.getUserSort(), v.getLevel());
	}

}
