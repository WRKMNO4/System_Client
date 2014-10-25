package UserBLService;

import ResultMessage.ResultMessage;
import VO.UserVO;

public class UserBLService_Driver {
	public void drive(UserBLService userBLService){
		ResultMessage result=userBLService.login(new UserVO("0001","0001"));
		if(result==ResultMessage.login_success)
			System.out.println("Login success!");
		
		result=userBLService.add(new UserVO("0002",""));
		if(result==ResultMessage.Exist)
			System.out.println("User exist.");
		
		result=userBLService.delete(new UserVO("0003",""));
		if(result==ResultMessage.Exist)
			System.out.println("User exist.");
		
		result=userBLService.update(new UserVO("0004",""));
		if(result==ResultMessage.Exist)
			System.out.println("User exist.");
		
		userBLService.find("00005");
	}

	public static void main(String[] args){
		UserBLService userBLController=new UserController();
		UserBLService_Driver driver=new UserBLService_Driver();
		driver.drive(userBLController);

	}
}
