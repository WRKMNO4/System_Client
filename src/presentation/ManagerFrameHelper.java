package presentation;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;


import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import businesslogicservice.GoodsBLService.GoodsController;
import businesslogicservice.PromotionBLService.PromotionController;
import Config.Level;
import Config.PromotionSort;
import PO.GoodsPO;
import PO.PurchaseListItemPO;
import PO.SalesListItemPO;
import PO.TransferListItemPO;
import ResultMessage.ResultMessage;
import VO.GoodsVO;
import VO.PromotionVO;

public class ManagerFrameHelper extends JFrame{
	GoodsController gController=new GoodsController();
	ArrayList<PurchaseListItemPO> purchaseListItems;
	ArrayList<SalesListItemPO> salesListItems;
	ArrayList<TransferListItemPO> transfers;
	 public ManagerFrameHelper(String command){
		 this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		switch (command){
			case "package":new AddPackageFrame(); break;
			case "gifts": new AddGiftsFrame(); break;
			case "voucher": new AddVoucherFrame(); break;
			case "deletePromotion": new deletePromotionFrame(); break;
			case "purchaseGoodsInfo": new purchaseGoodsInfoFrame(); break;
			case "salesGoodsInfo": new saleGoodsInfoFrame(); break;
			case "transfer": new transferFrame(); break;
			default: break;
		}
	}
	
	
	public void setPurchaseItems(ArrayList<PurchaseListItemPO> purchaseItems) {
		this.purchaseListItems = purchaseItems;
	}

	public void setSalesListItems(ArrayList<SalesListItemPO> salesListItems) {
		this.salesListItems = salesListItems;
	}

	public void setTransfers(ArrayList<TransferListItemPO> transfers) {
		this.transfers = transfers;
	}
	
	class AddPackageFrame extends JFrame{
		private JTextField textField;
		private JButton button;
		private JTextField goods1Field;
		private JTextField goods2Field;
		private JLabel offPriceLabel;
		private JTextField offPriceField;
		private JLabel startLabel;
		private JLabel Goods1Label;
		private JLabel GoodsLabel2;
		private JComboBox startMonthNum;
		private JLabel label_4;
		private JComboBox startDayNum;
		private JLabel lblNewLabel_3;
		private JLabel endTimeLabel;
		private JLabel label_8;
		private JComboBox customerLevel;
		private JComboBox endYearNum;
		private JLabel label_5;
		private JComboBox endMonthNum;
		private JLabel label_6;
		private JComboBox endDayNum;
		private JLabel label_9;
		private JButton confirmButton;
		private JButton cancelButton;
		private JLabel yuanLabel;
		private GoodsVO goods1;
		private GoodsVO goods2;
		public AddPackageFrame(){
			this.setVisible(true);
			setBounds(100, 100, 556, 475);
			this.setLocationRelativeTo(null);
			this.setLayout(null);
			
			JLabel titleLabel = new JLabel("制定特价包促销策略");
			titleLabel.setBounds(177, 22, 118, 30);
			getContentPane().add(titleLabel);
			
			JLabel GoodsInPutLabel = new JLabel("请输入特价包中的商品编号：");
			GoodsInPutLabel.setBounds(24, 96, 180, 25);
			getContentPane().add(GoodsInPutLabel);
			
			goods1Field = new JTextField();
			goods1Field.setBounds(249, 82, 85, 21);
			getContentPane().add(goods1Field);
			goods1Field.setColumns(10);
			
			JButton checkGoods1Button=new JButton("查询");
			checkGoods1Button.setBounds(341,82,60,21);
			getContentPane().add(checkGoods1Button);
			
			JLabel checkGoods1Label=new JLabel("未选择商品");
			checkGoods1Label.setBounds(408,82,140,21);
			getContentPane().add(checkGoods1Label);
			
			checkGoods1Button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(goods1Field.getText().equals("")){
						checkGoods1Label.setText("未选择商品");
						return;
					}
					goods1=gController.getGoodsByID(Long.parseLong(goods1Field.getText()));
					if(goods1!=null)
						checkGoods1Label.setText(goods1.name);
					else
						checkGoods1Label.setText("无此商品");
				}
			});
			
			goods2Field = new JTextField();
			goods2Field.setColumns(10);
			goods2Field.setBounds(249, 125, 85, 21);
			getContentPane().add(goods2Field);
			
			JButton checkGoods2Button=new JButton("查询");
			checkGoods2Button.setBounds(341,125,60,21);
			getContentPane().add(checkGoods2Button);
			
			JLabel checkGoods2Label=new JLabel("未选择商品");
			checkGoods2Label.setBounds(408,125,140,21);
			getContentPane().add(checkGoods2Label);
			
			checkGoods2Button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(goods2Field.getText().equals("")){
						checkGoods2Label.setText("未选择商品");
						return;
					}
					goods2=gController.getGoodsByID(Long.parseLong(goods2Field.getText()));
					if(goods2!=null)
						checkGoods2Label.setText(goods2.name);
					else
						checkGoods2Label.setText("无此商品");
				}
			});
			
			offPriceLabel = new JLabel("请输入降价金额：");
			offPriceLabel.setBounds(75, 161, 111, 15);
			getContentPane().add(offPriceLabel);
			
			offPriceField = new JTextField();
			offPriceField.setColumns(10);
			offPriceField.setBounds(196, 158, 97, 21);
			getContentPane().add(offPriceField);
			
			startLabel = new JLabel("请选择策略的起始时间：");
			startLabel.setBounds(24, 210, 150, 15);
			getContentPane().add(startLabel);
			
			Goods1Label = new JLabel("商品1：");
			Goods1Label.setBounds(197, 85, 54, 15);
			getContentPane().add(Goods1Label);
			
			GoodsLabel2 = new JLabel("商品2：");
			GoodsLabel2.setBounds(196, 128, 54, 15);
			getContentPane().add(GoodsLabel2);
			
			int yearNow=getYearNow();
			int monthNow=getMonthNow();
			int dayNow=getDateNow();
			
			String []startyears=new String[5];
			String []endyears=new String[5];
			String []months=new String[12];
			String []dates=new String[31];
			for(int i=0;i<5;i++){
				startyears[i]=String.valueOf(yearNow);
				endyears[i]=String.valueOf(yearNow++);
			}
			for(int i=0;i<12;i++){
				months[i]=String.valueOf(i+1);
			}
			for(int i=0;i<31;i++){
				dates[i]=String.valueOf(i+1);
			}
			
			JComboBox startYearNum = new JComboBox(startyears);
			startYearNum.setBounds(177, 207, 74, 21);
			getContentPane().add(startYearNum);
			
			JLabel lblNewLabel_2 = new JLabel("年");
			lblNewLabel_2.setBounds(261, 210, 20, 15);
			getContentPane().add(lblNewLabel_2);
			
			startMonthNum = new JComboBox(months);
			startMonthNum.setBounds(283, 207, 49, 21);
			startMonthNum.setSelectedIndex(getMonthNow()-1);
			getContentPane().add(startMonthNum);
			
			label_4 = new JLabel("月");
			label_4.setBounds(342, 210, 20, 15);
			getContentPane().add(label_4);
			
			startDayNum = new JComboBox(dates);
			startDayNum.setBounds(366, 207, 49, 21);
			startDayNum.setSelectedIndex(getDateNow()-1);
			getContentPane().add(startDayNum);
			
			lblNewLabel_3 = new JLabel("日");
			lblNewLabel_3.setBounds(425, 210, 20, 15);
			getContentPane().add(lblNewLabel_3);
			
			endTimeLabel = new JLabel("请选择策略的终止时间：");
			endTimeLabel.setBounds(24, 265, 150, 15);
			getContentPane().add(endTimeLabel);
			
			label_8 = new JLabel("请选择享受优惠的最低客户等级：");
			label_8.setBounds(24, 318, 200, 15);
			getContentPane().add(label_8);
			
			customerLevel = new JComboBox(new String[]{"一级","二级","三级","四级","五级VIP"});
			customerLevel.setBounds(238, 315, 60, 21);
			getContentPane().add(customerLevel);
			
			endYearNum = new JComboBox(endyears);
			endYearNum.setBounds(177, 259, 74, 21);
			endYearNum.setSelectedIndex(1);
			getContentPane().add(endYearNum);
			
			label_5 = new JLabel("年");
			label_5.setBounds(261, 262, 20, 15);
			getContentPane().add(label_5);
			
			endMonthNum = new JComboBox(months);
			endMonthNum.setBounds(283, 259, 49, 21);
			endMonthNum.setSelectedIndex(getMonthNow()-1);
			getContentPane().add(endMonthNum);
			
			label_6 = new JLabel("月");
			label_6.setBounds(342, 262, 20, 15);
			getContentPane().add(label_6);
			
			endDayNum = new JComboBox(dates);
			endDayNum.setBounds(366, 259, 49, 21);
			endDayNum.setSelectedIndex(getDateNow()-1);
			getContentPane().add(endDayNum);
			endDayNum.repaint();
			
			label_9 = new JLabel("日");
			label_9.setBounds(425, 262, 20, 15);
			getContentPane().add(label_9);
			
			confirmButton = new JButton("确认");
			confirmButton.setBounds(147, 374, 88, 30);
			getContentPane().add(confirmButton);
			
			cancelButton = new JButton("取消");
			cancelButton.setBounds(296, 374, 88, 30);
			getContentPane().add(cancelButton);
			
			yuanLabel = new JLabel("元");
			yuanLabel.setBounds(308, 161, 54, 15);
			getContentPane().add(yuanLabel);
			
			confirmButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					if(checkValid()){
					Date date=new Date();
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHMMSS");
					
					ArrayList<GoodsPO> goods=new ArrayList<GoodsPO>();
					goods1=gController.getGoodsByID(Long.parseLong(goods1Field.getText()));
					goods2=gController.getGoodsByID(Long.parseLong(goods2Field.getText()));
					goods.add(goods1.toPO());
					goods.add(goods2.toPO());
					
					Level customer=getCustomerLevel(customerLevel.getSelectedItem());
					
					PromotionVO newvo=new PromotionVO(PromotionSort.Package, dateFormat.format(date),
							goods, 0, Double.parseDouble(offPriceField.getText()), null, 0, 
							(String)startYearNum.getSelectedItem()+"-"+(String)startMonthNum.getSelectedItem()+"-"+(String)startDayNum.getSelectedItem(), 
							(String)endYearNum.getSelectedItem()+"-"+(String)endMonthNum.getSelectedItem()+"-"+(String)endDayNum.getSelectedItem(), 
							customer);
					
					ResultMessage result=new PromotionController().addPackage(newvo);
					if(result==ResultMessage.add_success){
					dispose();
					}
					else{
						new warningDialog("已存在相同的策略编号");
					}
					}
					else{
						new warningDialog("商品编号和降价金额不能为空！");
					}
				}
				
			});
		
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			}); 
			this.repaint();
		}
		public boolean checkValid(){
			boolean valid=false;
			if(!goods1Field.getText().equals("")&&!goods2Field.getText().equals("")&&
					!offPriceField.getText().equals(""))
				valid=true;
			return valid;
		}
	}

	class AddGiftsFrame extends JFrame{
		private JTextField textField;
		private JButton button;
		private JLabel leastPriceLabel;
		private JTextField leastPriceField;
		private JLabel startLabel;
		private JLabel presentLabel;
		private JComboBox startMonthNum;
		private JLabel label_4;
		private JComboBox startDayNum;
		private JLabel lblNewLabel_3;
		private JLabel endTimeLabel;
		private JLabel label_8;
		private JComboBox customerLevel;
		private JComboBox endYearNum;
		private JLabel label_5;
		private JComboBox endMonthNum;
		private JLabel label_6;
		private JComboBox endDayNum;
		private JLabel label_9;
		private JButton confirmButton;
		private JButton cancelButton;
		private JLabel yuanLabel;
		private JTextField presentsField;
		private GoodsVO presentVO;
		public AddGiftsFrame(){
				this.setVisible(true);
				setBounds(100, 100, 556, 475);
				this.setLocationRelativeTo(null);
				this.setLayout(null);
				
				JLabel titleLabel = new JLabel("制定送赠品促销策略");
				titleLabel.setBounds(214, 22, 118, 30);
				getContentPane().add(titleLabel);
				
				leastPriceLabel = new JLabel("请输入购物需满金额：");
				leastPriceLabel.setBounds(24, 89, 134, 15);
				getContentPane().add(leastPriceLabel);
				
				leastPriceField = new JTextField();
				leastPriceField.setColumns(10);
				leastPriceField.setBounds(177, 86, 97, 21);
				getContentPane().add(leastPriceField);
				
				startLabel = new JLabel("请选择策略的起始时间：");
				startLabel.setBounds(24, 210, 150, 15);
				getContentPane().add(startLabel);
				
				presentLabel = new JLabel("请输入赠品的商品编号：");
				presentLabel.setBounds(24, 149, 150, 15);
				getContentPane().add(presentLabel);
				
				presentsField = new JTextField();
				presentsField.setColumns(10);
				presentsField.setBounds(177, 146, 97, 21);
				getContentPane().add(presentsField);
				
				JButton goodsChosenButton=new JButton("查询");
				goodsChosenButton.setBounds(282, 146, 60, 21);
				getContentPane().add(goodsChosenButton);
				
				JLabel goodsChosenLabel = new JLabel("未选择商品");
				goodsChosenLabel.setBounds(350, 146, 200, 21);
				getContentPane().add(goodsChosenLabel);
				
				goodsChosenButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(presentsField.getText().equals("")){
							goodsChosenLabel.setText("未选择商品");
							return;
						}
						presentVO=gController.getGoodsByID(Long.parseLong(presentsField.getText()));
						if(presentVO!=null)
							goodsChosenLabel.setText(presentVO.name);
						else
							goodsChosenLabel.setText("无此商品");
					}
				});
				
				int yearNow=getYearNow();
				int monthNow=getMonthNow();
				int dayNow=getDateNow();
				
				String []startyears=new String[5];
				String []endyears=new String[5];
				String []months=new String[12];
				String []dates=new String[31];
				for(int i=0;i<5;i++){
					startyears[i]=String.valueOf(yearNow);
					endyears[i]=String.valueOf(yearNow++);
				}
				for(int i=0;i<12;i++){
					months[i]=String.valueOf(i+1);
				}
				for(int i=0;i<31;i++){
					dates[i]=String.valueOf(i+1);
				}
				
				JComboBox startYearNum = new JComboBox(startyears);
				startYearNum.setBounds(177, 207, 74, 21);
				getContentPane().add(startYearNum);
				
				JLabel lblNewLabel_2 = new JLabel("年");
				lblNewLabel_2.setBounds(261, 210, 20, 15);
				getContentPane().add(lblNewLabel_2);
				
				startMonthNum = new JComboBox(months);
				startMonthNum.setBounds(283, 207, 49, 21);
				startMonthNum.setSelectedIndex(getMonthNow()-1);
				getContentPane().add(startMonthNum);
				
				label_4 = new JLabel("月");
				label_4.setBounds(342, 210, 20, 15);
				getContentPane().add(label_4);
				
				startDayNum = new JComboBox(dates);
				startDayNum.setBounds(366, 207, 49, 21);
				startDayNum.setSelectedIndex(getDateNow()-1);
				getContentPane().add(startDayNum);
				
				lblNewLabel_3 = new JLabel("日");
				lblNewLabel_3.setBounds(425, 210, 20, 15);
				getContentPane().add(lblNewLabel_3);
				
				endTimeLabel = new JLabel("请选择策略的终止时间：");
				endTimeLabel.setBounds(24, 265, 150, 15);
				getContentPane().add(endTimeLabel);
				
				label_8 = new JLabel("请选择享受优惠的最低客户等级：");
				label_8.setBounds(24, 318, 200, 15);
				getContentPane().add(label_8);
				
				customerLevel = new JComboBox(new String[]{"一级","二级","三级","四级","五级VIP"});
				customerLevel.setBounds(238, 315, 57, 21);
				getContentPane().add(customerLevel);
				
				endYearNum = new JComboBox(endyears);
				endYearNum.setBounds(177, 259, 74, 21);
				endYearNum.setSelectedIndex(1);
				getContentPane().add(endYearNum);
				
				label_5 = new JLabel("年");
				label_5.setBounds(261, 262, 20, 15);
				getContentPane().add(label_5);
				
				endMonthNum = new JComboBox(months);
				endMonthNum.setBounds(283, 259, 49, 21);
				endMonthNum.setSelectedIndex(getMonthNow()-1);
				getContentPane().add(endMonthNum);
				
				label_6 = new JLabel("月");
				label_6.setBounds(342, 262, 20, 15);
				getContentPane().add(label_6);
				
				endDayNum = new JComboBox(dates);
				endDayNum.setBounds(366, 259, 49, 21);
				endDayNum.setSelectedIndex(getDateNow()-1);
				getContentPane().add(endDayNum);
				
				label_9 = new JLabel("日");
				label_9.setBounds(425, 262, 20, 15);
				getContentPane().add(label_9);
				
				confirmButton = new JButton("确认");
				confirmButton.setBounds(147, 374, 88, 30);
				getContentPane().add(confirmButton);
				
				cancelButton = new JButton("取消");
				cancelButton.setBounds(296, 374, 88, 30);
				getContentPane().add(cancelButton);
				
				yuanLabel = new JLabel("元");
				yuanLabel.setBounds(284, 89, 54, 15);
				getContentPane().add(yuanLabel);
				

				
				confirmButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(checkValid()){
						Date date=new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHMMSS");
						
						ArrayList<GoodsPO> presents=new ArrayList<GoodsPO>();
						presentVO=gController.getGoodsByID(Long.parseLong(presentsField.getText()));
						presents.add(presentVO.toPO());
						
						Level customer=getCustomerLevel(customerLevel.getSelectedItem());
						
						PromotionVO vo=new PromotionVO(PromotionSort.Gifts, dateFormat.format(date), 
								null, Double.parseDouble(leastPriceField.getText()), 0, presents, 0, 
								(String)startYearNum.getSelectedItem()+"-"+(String)startMonthNum.getSelectedItem()+"-"+(String)startDayNum.getSelectedItem(),
								(String)endYearNum.getSelectedItem()+"-"+(String)endMonthNum.getSelectedItem()+"-"+(String)endDayNum.getSelectedItem(),
								customer);
						
						ResultMessage result=new PromotionController().addGift(vo);
						if(result==ResultMessage.add_success){
							dispose();
							}
						else{
								new warningDialog("已存在相同的策略编号！");
							}
						}
						else{
							new warningDialog("金额和赠品编号不得为空！");
						}
					}
				});
				
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				}); 
				
				this.repaint();
			}
		public boolean checkValid(){
			boolean valid=false;
			if(!leastPriceField.getText().equals("")&&!presentsField.getText().equals(""))
				valid=true;
			return valid;
			
		}
		}

	class AddVoucherFrame extends JFrame{
		private JTextField textField;
		private JButton button;
		private JTextField goods1Field;
		private JLabel leastPriceLabel;
		private JTextField leastPriceField;
		private JLabel startLabel;
		private JLabel presentLabel;
		private JComboBox startMonthNum;
		private JLabel label_4;
		private JComboBox startDayNum;
		private JLabel lblNewLabel_3;
		private JLabel endTimeLabel;
		private JLabel label_8;
		private JComboBox customerLevel;
		private JComboBox endYearNum;
		private JLabel label_5;
		private JComboBox endMonthNum;
		private JLabel label_6;
		private JComboBox endDayNum;
		private JLabel label_9;
		private JButton confirmButton;
		private JButton cancelButton;
		private JLabel yuanLabel;
		private JTextField textField_1;
		public AddVoucherFrame(){
			
				this.setVisible(true);
				setBounds(100, 100, 556, 475);
				this.setLocationRelativeTo(null);
				getContentPane().setLayout(null);
				
				JLabel titleLabel = new JLabel("制定送代金券促销策略");
				titleLabel.setBounds(214, 22, 150, 30);
				getContentPane().add(titleLabel); 
		
				leastPriceLabel = new JLabel("请输入购物需满金额：");
				leastPriceLabel.setBounds(24, 89, 134, 15);
				getContentPane().add(leastPriceLabel);
				
				leastPriceField = new JTextField();
				leastPriceField.setColumns(10);
				leastPriceField.setBounds(177, 86, 97, 21);
				getContentPane().add(leastPriceField);
				
				startLabel = new JLabel("请选择策略的起始时间：");
				startLabel.setBounds(24, 210, 150, 15);
				getContentPane().add(startLabel);
				
				presentLabel = new JLabel("请输入赠送代金券的金额：");
				presentLabel.setBounds(24, 149, 180, 15);
				getContentPane().add(presentLabel);
				
				int yearNow=getYearNow();
				int monthNow=getMonthNow();
				int dayNow=getDateNow();
				
				String []startyears=new String[5];
				String []endyears=new String[5];
				String []months=new String[12];
				String []dates=new String[31];
				for(int i=0;i<5;i++){
					startyears[i]=String.valueOf(yearNow);
					endyears[i]=String.valueOf(yearNow++);
				}
				for(int i=0;i<12;i++){
					months[i]=String.valueOf(i+1);
				}
				for(int i=0;i<31;i++){
					dates[i]=String.valueOf(i+1);
				}
				
				JComboBox startYearNum = new JComboBox(startyears); 
				startYearNum.setBounds(177, 207, 74, 21);
				getContentPane().add(startYearNum);
				
				JLabel lblNewLabel_2 = new JLabel("年");
				lblNewLabel_2.setBounds(261, 210, 20, 15);
				getContentPane().add(lblNewLabel_2);
				
				startMonthNum = new JComboBox(months);
				startMonthNum.setBounds(283, 207, 49, 21);
				startMonthNum.setSelectedIndex(getMonthNow()-1);
				getContentPane().add(startMonthNum);
				
				label_4 = new JLabel("月");
				label_4.setBounds(342, 210, 20, 15);
				getContentPane().add(label_4);
				
				startDayNum = new JComboBox(dates);
				startDayNum.setBounds(366, 207, 49, 21);
				startDayNum.setSelectedIndex(getDateNow()-1);
				getContentPane().add(startDayNum);
				
				lblNewLabel_3 = new JLabel("日");
				lblNewLabel_3.setBounds(425, 210, 20, 15);
				getContentPane().add(lblNewLabel_3);
				
				endTimeLabel = new JLabel("请选择策略的终止时间：");
				endTimeLabel.setBounds(24, 265, 150, 15);
				getContentPane().add(endTimeLabel);
				
				label_8 = new JLabel("请选择享受优惠的最低客户等级：");
				label_8.setBounds(24, 318, 200, 15);
				getContentPane().add(label_8);
				
				customerLevel = new JComboBox(new String[]{"一级","二级","三级","四级","五级VIP"});
				customerLevel.setBounds(238, 315, 57, 21);
				getContentPane().add(customerLevel);
				
				endYearNum = new JComboBox(endyears);
				endYearNum.setBounds(177, 259, 74, 21);
				endYearNum.setSelectedIndex(1);
				getContentPane().add(endYearNum);
				
				label_5 = new JLabel("年");
				label_5.setBounds(261, 262, 20, 15);
				getContentPane().add(label_5);
				
				endMonthNum = new JComboBox(months);
				endMonthNum.setBounds(283, 259, 49, 21);
				endMonthNum.setSelectedIndex(getMonthNow()-1);
				getContentPane().add(endMonthNum);
				
				label_6 = new JLabel("月");
				label_6.setBounds(342, 262, 20, 15);
				getContentPane().add(label_6);
				
				endDayNum = new JComboBox(dates);
				endDayNum.setBounds(366, 259, 49, 21);
				endDayNum.setSelectedIndex(getDateNow()-1);
				getContentPane().add(endDayNum);
				
				label_9 = new JLabel("日");
				label_9.setBounds(425, 262, 20, 15);
				getContentPane().add(label_9);
				
				confirmButton = new JButton("确认");
				confirmButton.setBounds(147, 374, 88, 30);
				getContentPane().add(confirmButton);
				
				cancelButton = new JButton("取消");
				cancelButton.setBounds(296, 374, 88, 30);
				getContentPane().add(cancelButton);
				
				yuanLabel = new JLabel("元");
				yuanLabel.setBounds(284, 89, 54, 15);
				getContentPane().add(yuanLabel);
				
				textField_1 = new JTextField();
				textField_1.setColumns(10);
				textField_1.setBounds(177, 146, 97, 21);
				getContentPane().add(textField_1);
				
				JLabel voucherYuanLabel = new JLabel("元");
				voucherYuanLabel.setBounds(284, 146, 234, 21);
				getContentPane().add(voucherYuanLabel);
				
				confirmButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(checkValid()){
						Date date=new Date();
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHMMSS");
						
						Level customer=getCustomerLevel(customerLevel.getSelectedItem());
						PromotionVO vo=new PromotionVO(PromotionSort.Voucher, dateFormat.format(date), 
								null,Double.parseDouble(leastPriceField.getText()), 0, null,Integer.parseInt(textField_1.getText()) , 
								(String)startYearNum.getSelectedItem()+"-"+(String)startMonthNum.getSelectedItem()+"-"+(String)startDayNum.getSelectedItem(),
								(String)endYearNum.getSelectedItem()+"-"+(String)endMonthNum.getSelectedItem()+"-"+(String)endDayNum.getSelectedItem(), 
								customer);
						
						ResultMessage result=new PromotionController().addVoucher(vo);
						if(result==ResultMessage.add_success){
							dispose();
							}
						else{
							new warningDialog("已存在相同的策略编号！");
							}
						}
						else{
							new warningDialog("金额栏不能为空！");
						}
					}
				});
				
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				}); 
				this.repaint();
			}
		public boolean checkValid(){
			boolean valid=false;
			if(!leastPriceField.getText().equals("")&&!textField_1.getText().equals(""))
				valid=true;
			return valid;
		}
		}
	
	class deletePromotionFrame extends JFrame{
		private JTextField textField;
		private JButton button;
		private JTextField goods1Field;
		private JButton confirmButton;
		private JTextField idField;
		public deletePromotionFrame(){
				this.setVisible(true);
				setBounds(100, 100, 529, 389);
				this.setLocationRelativeTo(null);
				getContentPane().setLayout(null);
				
				JLabel titleLabel = new JLabel("删除促销策略");
				titleLabel.setBounds(219, 43, 88, 30);
				getContentPane().add(titleLabel);
			
				
				JLabel remindLabel = new JLabel("请输入删除策略的编号：");
				remindLabel.setBounds(100, 137, 154, 15);
				getContentPane().add(remindLabel);
				
				idField = new JTextField();
				idField.setBounds(264, 134, 120, 21);
				getContentPane().add(idField);
				idField.setColumns(10);
				
				JButton confirmButton = new JButton("确认");
				confirmButton.setBounds(132, 236, 96, 37);
				getContentPane().add(confirmButton);
				
				JButton cancelButton = new JButton("取消");
				cancelButton.setBounds(276, 236, 96, 37);
				getContentPane().add(cancelButton);
				
				confirmButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if(checkValid()){
						String promotionId=idField.getText();
						PromotionVO vo=new PromotionVO(null, promotionId, null, 0, 0, null, 0, null, null, null);
						ResultMessage result=new PromotionController().delete(vo);
						if(result==ResultMessage.delete_success)
							dispose();
						else
							new warningDialog("不存在此策略编号！");
						}
						else{
							new warningDialog("策略编号不能为空！");
						}
					}
				});
				
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						dispose();
					}
				});
				
				this.repaint();
			}
		public boolean checkValid(){
			boolean valid=false;
			if(!idField.getText().equals(""))
				valid=true;
		
		return valid;
		}
	}
	
	class purchaseGoodsInfoFrame extends JFrame{
		public purchaseGoodsInfoFrame(){
			this.setVisible(true);
			setBounds(100, 100, 585, 451);
			this.setLocationRelativeTo(null);
			getContentPane().setLayout(null);

			Vector<String> titles=new Vector<String>();
			titles.add("商品编号");titles.add("名称");titles.add("型号");
			titles.add("数量");titles.add("单价");titles.add("金额");//titles.add("备注");
			
			Vector allItems=new Vector();
			for(int i=0;i<purchaseListItems.size();i++){
				Vector oneItem=new Vector();
				oneItem.add(purchaseListItems.get(i).getGoodsPO().getSerialNumber());
				oneItem.add(purchaseListItems.get(i).getGoodsPO().getName());
				oneItem.add(purchaseListItems.get(i).getGoodsPO().getModel());
				oneItem.add(purchaseListItems.get(i).getQuantity());
				oneItem.add(purchaseListItems.get(i).getGoodsPO().getPrice());
				oneItem.add(purchaseListItems.get(i).getTotalPrice());
				//oneItem.add(purchaseListItems.get(i).getComment());
				allItems.add(oneItem);
			}
			JTable table=new JTable(new DefaultTableModel(allItems,titles));
			
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();   //设置单元格内容居中
		    render.setHorizontalAlignment(SwingConstants.CENTER);
		    table.setDefaultRenderer(Object.class, render);
		    
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(37, 70, 501, 320);
			getContentPane().add(scrollPane);
			
			JLabel label = new JLabel("商品列表");
			label.setBounds(259, 29, 54, 15);
			getContentPane().add(label);
			
			this.repaint();
		}
	}
	
	class saleGoodsInfoFrame extends JFrame{
		public saleGoodsInfoFrame(){
			this.setVisible(true);
			setBounds(100, 100, 585, 451);
			this.setLocationRelativeTo(null);
			getContentPane().setLayout(null);

			Vector<String> titles=new Vector<String>();
			titles.add("商品编号");titles.add("名称");titles.add("型号");
			titles.add("数量");titles.add("单价");titles.add("金额");//titles.add("备注");
			
			Vector allItems=new Vector();
			for(int i=0;i<salesListItems.size();i++){
				Vector oneItem=new Vector();
				oneItem.add(salesListItems.get(i).getGoodsPO().getSerialNumber());
				oneItem.add(salesListItems.get(i).getGoodsPO().getName());
				oneItem.add(salesListItems.get(i).getGoodsPO().getModel());
				oneItem.add(salesListItems.get(i).getQuantity());
				oneItem.add(salesListItems.get(i).getGoodsPO().getPrice());
				oneItem.add(salesListItems.get(i).getTotalPrice());
				//oneItem.add(salesListItems.get(i).getComment());
				allItems.add(oneItem);
			}
			JTable table=new JTable(new DefaultTableModel(allItems,titles));
			
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();   //设置单元格内容居中
		    render.setHorizontalAlignment(SwingConstants.CENTER);
		    table.setDefaultRenderer(Object.class, render);
		    
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(37, 70, 501, 320);
			getContentPane().add(scrollPane);
			
			JLabel label = new JLabel("商品列表");
			label.setBounds(259, 29, 54, 15);
			getContentPane().add(label);
			
			this.repaint();
		}
	}
	
	class transferFrame extends JFrame{
		public transferFrame(){
			this.setVisible(true);
			setBounds(100, 100, 585, 451);
			this.setLocationRelativeTo(null);
			getContentPane().setLayout(null);
			
			Vector<String> titles=new Vector<String>();
			titles.add("银行账户");titles.add("转账金额");titles.add("备注");
			
			Vector oneTransfer=new Vector();
			Vector allTransfers=new Vector();
			oneTransfer.add("131250008"); oneTransfer.add("1000"); oneTransfer.add("收款");
			
			allTransfers.add(oneTransfer);
			
			JTable table=new JTable(new DefaultTableModel(allTransfers,titles));
			
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();   //设置单元格内容居中
		    render.setHorizontalAlignment(SwingConstants.CENTER);
		    table.setDefaultRenderer(Object.class, render);
		    
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setBounds(37, 70, 501, 320);
			getContentPane().add(scrollPane);
			
			JLabel label = new JLabel("转账列表");
			label.setBounds(259, 29, 54, 15);
			getContentPane().add(label);
			 
			this.repaint();
		}
	}
	
	class warningDialog extends JDialog{
		public warningDialog(String warnings){
			this.setSize(284, 158);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			this.setModal(true);
			
			JLabel warningLabel = new JLabel(warnings,JLabel.CENTER);
			warningLabel.setBounds(50, 28, 200, 50);
			warningLabel.setFont(new Font("宋体",Font.BOLD,14));
			
			this.add(warningLabel);
		}
	}
	
	
	
	public int getYearNow(){
		Calendar c=Calendar.getInstance();
		return c.get(Calendar.YEAR);
	}
	
	public int getMonthNow(){
		Calendar c=Calendar.getInstance();
		return c.get(Calendar.MONTH)+1;
	}
	
	public int getDateNow(){
		Calendar c=Calendar.getInstance();
		return c.get(Calendar.DATE);
	}
	
	public String standardHM(){
		Calendar c=Calendar.getInstance();
		String hour=String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String minute=String.valueOf(c.get(Calendar.MINUTE));
		if(hour.length()==1){
			hour="0"+hour;
		}
		if(minute.length()==1){
			minute="0"+minute;
		}
		return hour+minute;
	}
	
	public Level getCustomerLevel(Object selected){
		Level customer=null;
		switch((String) selected){
		case "一级": customer=Level.firstClass; break;
		case "二级": customer=Level.secondClass; break;
		case "三级": customer=Level.thirdClass; break;
		case "四级": customer=Level.forthClass; break;
		case "五级VIP": customer=Level.fiveClassVIP; break;
		}
		return customer;
	}

	public static void main(String[] args){
		new ManagerFrameHelper("voucher");
	}
}
