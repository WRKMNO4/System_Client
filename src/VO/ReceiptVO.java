package VO;



public class ReceiptVO {
//�������ģ�壬���еĵ��ݶ�Ҫ�̳������
	//�������ģ�壬���еĵ��ݶ�Ҫ�̳������
		String time = null ;//���ݴ���ʱ��
		String number = null ;//���ݱ�ţ������������͵���Ϣ�����磺SKD-xxxxxxxxx�������۵���FKD-xxxxxxxxxx�������
		CustomerVO customer = null ;
		UserVO user = null ;
		String storage = null ;
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		public String getNumber() {
			return number;
		}
		public void setNumber(String number) {
			this.number = number;
		}
		public CustomerVO getCustomer() {
			return customer;
		}
		public void setCustomer(CustomerVO customer) {
			this.customer = customer;
		}
		public UserVO getUser() {
			return user;
		}
		public void setUser(UserVO user) {
			this.user = user;
		}
		public String getStorage() {
			return storage;
		}
		public void setStorage(String storage) {
			this.storage = storage;
		}
		
}