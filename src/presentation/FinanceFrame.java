package presentation;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import Config.UserSort;
import PO.CaseListItemPO;
import PO.CollectionOrPaymentPO;
import PO.GoodsPO;
import PO.PurchaseListItemPO;
import PO.PurchaseReceiptPO;
import PO.SalesListItemPO;
import PO.SalesReceiptPO;
import ResultMessage.ResultMessage;
import VO.AccountVO;
import VO.CaseListItemVO;
import VO.CashVO;
import VO.CollectionOrPaymentVO;
import VO.GoodsVO;
import VO.PurchaseListItemVO;
import VO.PurchaseReceiptVO;
import VO.ReportCommodityVO;
import VO.SalesListItemVO;
import VO.SalesReceiptVO;
import VO.ScreeningConditionVO;
import VO.SendCommodityVO;
import VO.TransferListItemVO;
import VO.UserVO;
import businesslogicservice.ApprovalBLService.ApprovalBLService_Controller;
import businesslogicservice.CommodityBLService.CommodityController;
import businesslogicservice.FinanceBLService.FinanceController;
import businesslogicservice.InfoBLService.InfoController;
import businesslogicservice.PurchseBLService.PurchaseController;
import businesslogicservice.SaleBLService.SalesController;

/*
 * 财务人员界面
 * @author 
 */
public class FinanceFrame extends JFrame{
	private FinanceController fController = new FinanceController() ;
	private InfoController infoController = new InfoController() ;
	private JFrame theFrame ;
	private JLabel exitButton,mangeAccountLabel,infoLabel,makeReceiptLabel; 
	private AccountPanel accountPanel = new AccountPanel(this) ;
	private UserVO user ;
	private ReceiptPanel receiptPanel = new ReceiptPanel(this) ;
	private InfoPanel infoPanel = new InfoPanel(this) ;
//	private 
	
	public FinanceFrame(UserVO uservo){
		
		super() ;
		theFrame = this ;
		this.setSize(1000, 600);
		this.setLocationRelativeTo(null);
		this.setLayout(null); 
		this.setUndecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		accountPanel.setVisible(true);
		
		
		user = uservo ;
		this.add(new UserPanel(user)) ;

		exitButton = new JLabel("X",JLabel.CENTER) ;
		exitButton.setBounds(950, 0, 50, 50);
		exitButton.setFont(new Font("default", 1, 20));
		exitButton.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				System.exit(0);
			} 
		});
		this.add(exitButton) ;

		mangeAccountLabel = new JLabel("账户管理",JLabel.CENTER) ;
		mangeAccountLabel.setBounds(40, 100, 100, 50);
		mangeAccountLabel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				accountPanel.setVisible(true);
				receiptPanel.setVisible(false);
				infoPanel.setVisible(false);
			}
		});
		this.add(mangeAccountLabel) ;
		
		makeReceiptLabel = new JLabel("制定单据",JLabel.CENTER) ;
		makeReceiptLabel.setBounds(40, 160, 100, 50);
		this.add(makeReceiptLabel) ;
		makeReceiptLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				receiptPanel.setVisible(true);
				accountPanel.setVisible(false);
				infoPanel.setVisible(false);
			}
		});
		
		infoLabel = new JLabel("查看报表",JLabel.CENTER) ;
		infoLabel.setBounds(40, 220, 100, 50);
		this.add(infoLabel) ;
		infoLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				receiptPanel.setVisible(false);
				accountPanel.setVisible(false);
				infoPanel.setVisible(true);
			}
		});
		
		
		
		MoveOfFrame m = new MoveOfFrame(this);
		this.setVisible(true);
	}
	
	
	/*
	 * 账户管理的panel
	 */
	class AccountPanel extends JPanel{
		JLabel addAccount,deletAccount,updatAccount,findAccount;
		JTable accountTable ;
		JScrollPane scrollPane ;
		
		public AccountPanel(JFrame theFrame){
			super();
			this.setBounds(140, 25, 835, 550);
			this.setBackground(new Color(200, 100, 150, 255));
			this.setLayout(null);
			this.setVisible(true);
			theFrame.add(this) ;
			refreshTable(fController.show()) ;
			this.setVisible(false);
			
			JLabel accountAdd = new JLabel("添加账户",JLabel.CENTER);
			accountAdd.setBounds(100, 23, 120, 50);
			this.add(accountAdd) ;
			accountAdd.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					JFrame frame = new AddAccount();
					frame.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							// TODO Auto-generated method stub
							refreshTable(fController.show());
						}
					});
				}
			});
			
			JLabel accountDelet = new JLabel("删除账户",JLabel.CENTER) ;
			accountDelet.setBounds(200, 23, 120, 50);
			this.add(accountDelet) ;
			accountDelet.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					JFrame frame = new DeleteAccount();
					frame.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent e) {
							// TODO Auto-generated method stub
							refreshTable(fController.show());
						}
					});
				}
			});
			JLabel accNameFind = new JLabel("账户名称：",JLabel.CENTER) ;
			accNameFind.setBounds(300,23,120,50) ;
			this.add(accNameFind) ;
			
			JLabel accountFind = new JLabel("查找",JLabel.CENTER) ;
			accountFind.setBounds(500,23,120,50) ;
			this.add(accountFind) ;
			
			JTextField findField = new JTextField() ;
			findField.setBounds(400, 40, 120, 20);
			this.add(findField) ;
			accountFind.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					String name = findField.getText() ;
					if(!name.equals("")){
						if(fController.findAccount(name) != null){
						refreshTable(fController.findAccount(name));
						findField.setText("");
						}else{
							new warningDialog("该账户不存在！");
						}
					}
				}
			});
			
			JLabel refreshLabel = new JLabel("刷新列表",JLabel.CENTER);
			refreshLabel.setBounds(600, 40, 120, 20);
			this.add(refreshLabel) ;
			refreshLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					refreshTable(fController.show());
				}
			});
			
		}
		public void refreshTable(ArrayList<AccountVO> theAccounts){
			String[] columNames = {"账户名称","账户余额"} ;
			ArrayList<AccountVO> accounts = theAccounts ;
			accountTable = new JTable(new MyTableModel(accounts,columNames)) ;
			accountTable.setBackground(Color.white);
			
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();   //设置单元格内容居中
		    render.setHorizontalAlignment(SwingConstants.CENTER);
		    accountTable.setDefaultRenderer(Object.class, render);
			
//		    accountTable.getModel().addTableModelListener(new TableModelListener(){     //检测是否有内容更改
//		    	public void tableChanged(TableModelEvent e) {     //进行的操作
//		    		int row = e.getFirstRow();
//		    		AccountVO updAccount = new AccountVO((String)accountTable.getValueAt(row, 0),Double.parseDouble((String)accountTable.getValueAt(row, 1))) ;
//		    		fController.updateAccount(updAccount) ;
//		    	}
//		    }) ;
		    if(scrollPane != null)
		    	scrollPane.setVisible(false);
		    scrollPane = new JScrollPane(accountTable) ;	
    		scrollPane.setBounds(80,74, 700,400);
			accountTable.setFillsViewportHeight(true);
			this.add(scrollPane) ;
			
			
		}
		class MyTableModel extends AbstractTableModel{
			private ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
			private String[] column ;

			public MyTableModel(ArrayList<AccountVO> theDatas ,String[] theColumn){
				for(AccountVO theAccount : theDatas){
					ArrayList<Object> oneRow = new ArrayList<Object>() ;
					oneRow.add(theAccount.getName()) ;
					oneRow.add(String.valueOf(theAccount.getBalance())) ;
					datas.add(oneRow) ;
				}
				column = theColumn ;
			}

			@Override
			public String getColumnName(int col)
		     {
		          return column[col];
		     }
			public int getColumnCount() {
				// TODO Auto-generated method stub
				return column.length;
			}

			@Override
			public int getRowCount() {
				// TODO Auto-generated method stub
				return datas.size();
			}

			@Override
			public Object getValueAt(int row, int column) {
				// TODO Auto-generated method stub
				return datas.get(row).get(column);
			}
			public boolean isCellEditable(int row, int col) { 
					return false ;
			}
			public void setValueAt(Object value, int row, int col) {  
		        datas.get(row).set(col,  value);
		        fireTableCellUpdated(row, col);  
		    }	
		}
	}
	
	class AddAccount extends JFrame{
			private JPanel addAccountPane;
			private JTextField nameField;
			private JTextField balanceField;
			ResultMessage result ;
			/**
			 * Create the frame.
			 */
			public AddAccount() {
//				this.setLocationRelativeTo(null);
				this.setTitle("增加账户");
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				setBounds(500, 200, 312, 258);
				addAccountPane = new JPanel();
				addAccountPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				setContentPane(addAccountPane);
				addAccountPane.setLayout(null);
				
				JLabel nameLabel = new JLabel("账户名称：");
				nameLabel.setBounds(10, 28, 100, 15);
				addAccountPane.add(nameLabel);
				
				nameField = new JTextField();
				nameField.setBounds(87, 25, 124, 21);
				addAccountPane.add(nameField);
				nameField.setColumns(10);
				
				JLabel balanceLabel = new JLabel("账户余额：");
				balanceLabel.setBounds(10, 91, 100, 15);
				addAccountPane.add(balanceLabel);
				
				balanceField = new JTextField();
				balanceField.setBounds(87, 88, 124, 21);
				addAccountPane.add(balanceField);
				balanceField.setColumns(10);
				
				JButton addButton = new JButton("确定");
				addButton.setBounds(34, 158, 93, 23);
				addAccountPane.add(addButton);
				addButton.addMouseListener(new MouseAdapter(){
					public void mouseClicked(MouseEvent e){
						String name = nameField.getText() ;
			    		String balance =balanceField.getText() ;
				   		if(name.equals("")||balance.equals("")){
				    		new warningDialog("账户信息不完整！") ;
				   		}else{
				    		AccountVO accout = new AccountVO(name,Double.parseDouble(balance)) ;
					   		result = fController.addAccount(accout) ;
					   		if(result.equals(ResultMessage.add_success)){
       				    		new warningDialog("添加成功") ;
					   		    dispose();
				    		}else{
				    			new warningDialog("该账户已存在，添加失败") ;
				    		}
				   		}
					}
				});
				
				JButton cancel = new JButton("取消");
				cancel.setBounds(154, 158, 93, 23);
				addAccountPane.add(cancel);
				cancel.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						dispose();
					}
				});
				
				this.setVisible(true);
			}

	}
	class DeleteAccount extends JFrame{

		private JPanel delAccountPane;
		private JTextField nameField;
		private JButton deleteButton;
		private JButton cancelButton;
		ResultMessage result ;

		/**
		 * Create the frame.
		 */
		public DeleteAccount() {
			this.setTitle("删除账户");
			this.setVisible(true);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setBounds(500, 200, 307, 220);
			delAccountPane = new JPanel();
			delAccountPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(delAccountPane);
			delAccountPane.setLayout(null);
			
			JLabel nameLabel = new JLabel("账户名称：");
			nameLabel.setBounds(22, 36, 100, 15);
			delAccountPane.add(nameLabel);
			
			nameField = new JTextField();
			nameField.setBounds(108, 34, 114, 18);
			delAccountPane.add(nameField);
			nameField.setColumns(10);
			
			deleteButton = new JButton("确定");
			deleteButton.setBounds(22, 94, 93, 23);
			delAccountPane.add(deleteButton);
			deleteButton.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e){
					String name = nameField.getText() ;
					if(!name.equals("")){
	     		  		result = fController.deletAccount(new AccountVO(name,0)) ;
	     		  		if(result.equals(ResultMessage.delete_success)){
	     		  			new warningDialog("删除成功");
	     		  			dispose();
	     		  			System.out.print("222222");
	     		  		}else{
	     		  			new warningDialog("系统中不存在该账户，删除失败") ;
	     		  			System.out.print("33333333");
	     		  		}
					}else{
						new warningDialog("输入信息不完整") ;
					}
				}
			});
			
			cancelButton = new JButton("取消");
			cancelButton.setBounds(153, 94, 93, 23);
			delAccountPane.add(cancelButton);
			cancelButton.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					dispose() ;
				}
			});
		}

}

	
	
	/*
	 * 制定单据的panel
	 */
	class ReceiptPanel extends JPanel{
		JLabel makeCollectionOrPaymentLabel,makeCashLabel,messageFromManagerLabel; 
		MakeCollectionOrPayment makeCollectionPane ;
		MakeCash makeCashPane ;
		MessageFromManager messagePane ;
		public ReceiptPanel (JFrame theFrame){
			super() ;
			this.setBounds(140, 25, 835, 550);
			this.setBackground(new Color(200, 100, 150, 255));
			this.setLayout(null);
			this.setVisible(false);
			theFrame.add(this) ;
			makeCollectionPane = new MakeCollectionOrPayment() ;
			this.add(makeCollectionPane) ;
			makeCashPane = new MakeCash(); 
			this.add(makeCashPane);
			makeCashPane.setVisible(false);
			messagePane = new MessageFromManager() ;
			this.add(messagePane);
			messagePane.setVisible(false);
			
			makeCollectionOrPaymentLabel = new JLabel("制定收/付款单",JLabel.CENTER) ;
			makeCollectionOrPaymentLabel.setBounds(210, 23, 120, 50);
			this.add(makeCollectionOrPaymentLabel) ;
			makeCollectionOrPaymentLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					makeCollectionPane.setVisible(true);
					makeCashPane.setVisible(false);
					messagePane.setVisible(false);
				}
			});
			makeCashLabel = new JLabel("制定现金费用单",JLabel.CENTER) ;
			makeCashLabel.setBounds(345, 23, 150, 50);
			this.add(makeCashLabel) ;
			makeCashLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					makeCollectionPane.setVisible(false);
					makeCashPane.setVisible(true);
					messagePane.setVisible(false);
				}
			});
			messageFromManagerLabel = new JLabel("单据审批情况",JLabel.CENTER) ;
			messageFromManagerLabel.setBounds(505, 23, 150, 50);
			this.add(messageFromManagerLabel) ;
			messageFromManagerLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					makeCollectionPane.setVisible(false);
					makeCashPane.setVisible(false);
					messagePane.setVisible(true);
				}
			});
		}
		
	}
	class MakeCollectionOrPayment extends JPanel{


		private JTextField nameOfCustomerField;
		private JTextField numberOfReceiptField;
		private JTextField sumOfMoneyField;
		ArrayList<TransferListItemVO> tfAccounts = new ArrayList<TransferListItemVO>() ;//储存转账账户
		double sumOfMoney = 0 ;
		/**
		 * Create the frame.
		 */
		public MakeCollectionOrPayment() {
			setBounds(120, 100, 500, 380);
			this.setBorder(new EmptyBorder(5, 5, 5, 5));
			this.setLayout(null);
			
			JLabel numberLabel = new JLabel("单据编号：");
			numberLabel.setBounds(39, 42, 100, 15);
			this.add(numberLabel);
			
			JRadioButton c = new JRadioButton("收款单");
			c.setBounds(270, 37, 71, 23);
			this.add(c);
			
			JRadioButton f = new JRadioButton("付款单");
			f.setBounds(340, 37, 71, 23);
			this.add(f);
			
			nameOfCustomerField = new JTextField();
			nameOfCustomerField.setBounds(129, 96, 100, 21);
			this.add(nameOfCustomerField);
			nameOfCustomerField.setColumns(10);
			
			numberOfReceiptField = new JTextField();
			numberOfReceiptField.setBounds(129, 39, 135, 21);
			this.add(numberOfReceiptField);
			numberOfReceiptField.setColumns(10);
			
			
			sumOfMoneyField = new JTextField();
			sumOfMoneyField.setBounds(129, 181, 100, 21);
			this.add(sumOfMoneyField);
			sumOfMoneyField.setColumns(10);
			
			c.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					c.setSelected(true);
					f.setSelected(false);
					numberOfReceiptField.setText(fController.getReceiptNumber("SKD"));
				}
			});
			f.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					c.setSelected(false);
					f.setSelected(true);
					numberOfReceiptField.setText(fController.getReceiptNumber("FKD"));
				}
			});
			
			JLabel nameOfCustomeLabel = new JLabel("客户名称：");
			nameOfCustomeLabel.setBounds(39, 99, 100, 15);
			this.add(nameOfCustomeLabel);
			
			JLabel sumOfMoneyLabel = new JLabel("总额汇总：");
			sumOfMoneyLabel.setBounds(39, 184, 100, 15);
			this.add(sumOfMoneyLabel);
			
			
			JRadioButton supplierRadioButton = new JRadioButton("供应商");
			supplierRadioButton.setBounds(240, 95, 71, 23);
			this.add(supplierRadioButton);
			
			JRadioButton retailerRadioButton = new JRadioButton("销售商");
			retailerRadioButton.setBounds(310, 95, 71, 23);
			this.add(retailerRadioButton);
			
			supplierRadioButton.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					supplierRadioButton.setSelected(true);
					retailerRadioButton.setSelected(false);
				}
			});
			retailerRadioButton.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					supplierRadioButton.setSelected(false);
					retailerRadioButton.setSelected(true);
				}
			});
			

			JButton addTfListButton = new JButton("添加转账账户");
			addTfListButton.setBounds(39, 140, 130, 23);
			this.add(addTfListButton);
			addTfListButton.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					new AddTfListFrame() ;
				}
			});
			
			JButton showTfListButton = new JButton("查看已添加账户") ;
			showTfListButton.setBounds(200, 140, 130, 23);
			this.add(showTfListButton) ;
			showTfListButton.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					new ShowTfListFrame() ;
				}
			});
			
			
			
			
			JLabel yuanLabel = new JLabel("元");
			yuanLabel.setBounds(235, 184, 21, 15);
			this.add(yuanLabel);
	
			JButton sureButton = new JButton("确定 ") ;
			sureButton.setBounds(80, 270, 100, 23);
			this.add(sureButton) ;
			sureButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String receiptNumber = numberOfReceiptField.getText() ;
					String nameOfCustomer = nameOfCustomerField.getText() ;
					String sumOfMoneys = sumOfMoneyField.getText() ;
					String typeOfCustomer = "" ;
					if(supplierRadioButton.isSelected()){
						typeOfCustomer = "供应商";
					}
					if(retailerRadioButton.isSelected()){
		     			typeOfCustomer = "销售商" ;
					}
					if(receiptNumber.equals("")||nameOfCustomer.equals("")||sumOfMoneys.equals("")||typeOfCustomer.equals("")){
						new warningDialog("信息不完整");
					}else{
						CollectionOrPaymentVO collectionOrPayment = new CollectionOrPaymentVO(receiptNumber,nameOfCustomer,typeOfCustomer,user.getUserName(),tfAccounts,Double.parseDouble(sumOfMoneys),false,false) ;
						ResultMessage result = fController.addCollectionOrPaymentVO(collectionOrPayment) ;
						if(result.equals(ResultMessage.add_failure)){
						new warningDialog("输入账户不存在！");
						}else{
						new warningDialog("添加成功");
						nameOfCustomerField.setText("");
						numberOfReceiptField.setText("");;
						sumOfMoneyField.setText("");
						supplierRadioButton.setSelected(false);
						retailerRadioButton.setSelected(false);
						f.setSelected(false);
						c.setSelected(false);
						tfAccounts = new ArrayList<TransferListItemVO>() ;
						sumOfMoney = 0 ;
						}
					}
				}
			});
			
			JButton cancelButton = new JButton("取消");
			cancelButton.setBounds(250, 270, 100, 23);
			this.add(cancelButton) ;
			cancelButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					nameOfCustomerField.setText("");
					numberOfReceiptField.setText("");
					sumOfMoneyField.setText("");
					tfAccounts = new ArrayList<TransferListItemVO>() ;
					supplierRadioButton.setSelected(false);
					retailerRadioButton.setSelected(false);
					f.setSelected(false);
					c.setSelected(false);
				}
			});
		}
		class MyTableModel extends AbstractTableModel{
			private ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
			private String[] column ={"银行账户","转账金额","备注","是否删除"} ;

			public MyTableModel(){
				for(TransferListItemVO theAccount : tfAccounts){
					ArrayList<Object> oneRow = new ArrayList<Object>() ;
					oneRow.add(theAccount.getAccount()) ;
					oneRow.add(theAccount.getTransferMoney()) ;
					oneRow.add(theAccount.getRemark()) ;
					oneRow.add("删除");
					datas.add(oneRow) ;
				}
			}

			@Override
			public String getColumnName(int col)
		     {
		          return column[col];
		     }
			public int getColumnCount() {
				// TODO Auto-generated method stub
				return column.length;
			}

			@Override
			public int getRowCount() {
				// TODO Auto-generated method stub
				return datas.size();
			}

			@Override
			public Object getValueAt(int row, int column) {
				// TODO Auto-generated method stub
				return datas.get(row).get(column);
			}
			public boolean isCellEditable(int row, int col) { 
					return false ;
			}
			public void setValueAt(Object value, int row, int col) {  
		        datas.get(row).set(col,  value);
		        fireTableCellUpdated(row, col);  
		    }	
		}
	    

	    class ShowTfListFrame extends JFrame{

	    	private JPanel contentPane;
	    	private JTable table;

	    	/**
	    	 * Create the frame.
	    	 */
	    	public ShowTfListFrame() {
	    		this.setVisible(true);
	    		this.setTitle("转账列表");
	    		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    		setBounds(500, 200, 350, 300);
	    		contentPane = new JPanel();
	    		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	    		contentPane.setBackground(Color.yellow);
	    		setContentPane(contentPane);
	    		contentPane.setLayout(null);
	 
	    		table = new JTable(new MyTableModel());
	    		table.setBackground(Color.white);
	    		table.addMouseListener(new MouseAdapter() {
	    			public void mouseClicked(MouseEvent e){
	    				Point mousePoint = e.getPoint() ;
	    				if(table.columnAtPoint(mousePoint) == 3){
	    					int i = JOptionPane.showConfirmDialog(null, "是否删除？");
	    					if(i==0){
	    						int k = table.rowAtPoint(mousePoint) ;
	    						tfAccounts.remove(k) ;
	    						setNum();
	    						dispose();
	    						new ShowTfListFrame() ;
	    					}
	    				}
	    			}
				});
	    		
	    		JScrollPane jsc = new JScrollPane(table);
	    		jsc.setBounds(0,0,350,170);
	    		jsc.setBackground(Color.green);
	    		contentPane.add(jsc) ;
	    		
	    		JButton sureButton = new JButton("确定");
	    		sureButton.setBounds(110, 200, 100, 30);
	    		contentPane.add(sureButton) ;
	    		sureButton.addMouseListener(new MouseAdapter() {
	    		    public void mouseClicked(MouseEvent e){
	    				dispose() ;
	    			}
				});
	    		
	    	}
	    	
	}


		public void setNum(){
    		sumOfMoney = 0 ;
    		for(TransferListItemVO theItem :tfAccounts){
    			sumOfMoney += theItem.getTransferMoney() ;
    		}
    		sumOfMoneyField.setText(String.valueOf(sumOfMoney));
    	}

		class AddTfListFrame extends JFrame{


			private JPanel contentPane;
			private JTextField nameOfAccountField;
			private JTextField numOfTfField;
			private JTextField markedField;


			/**
			 * Create the frame.
			 */
			public AddTfListFrame() {
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				setBounds(500, 200, 350, 300);
				contentPane = new JPanel();
				contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
				setContentPane(contentPane);
				contentPane.setLayout(null);
				this.setVisible(true);
				
				JLabel nameOfAccountLabel = new JLabel("银行账户：");
				nameOfAccountLabel.setBounds(41, 37, 66, 15);
				contentPane.add(nameOfAccountLabel);
				
				nameOfAccountField = new JTextField();
				nameOfAccountField.setBounds(139, 34, 110, 21);
				contentPane.add(nameOfAccountField);
				nameOfAccountField.setColumns(10);
				
				JLabel numOfMoneyLabel = new JLabel("转账金额：");
				numOfMoneyLabel.setBounds(41, 96, 66, 15);
				contentPane.add(numOfMoneyLabel);
				JLabel yuanLabel = new JLabel("元");
				yuanLabel.setBounds(260, 95, 20, 15);
				contentPane.add(yuanLabel) ;
				
				numOfTfField = new JTextField();
				numOfTfField.setBounds(139, 93, 110, 21);
				contentPane.add(numOfTfField);
				numOfTfField.setColumns(10);
				
				JLabel markedLabel = new JLabel("备        注：");
				markedLabel.setBounds(41, 149, 66, 15);
				contentPane.add(markedLabel);
				
				markedField = new JTextField();
				markedField.setBounds(139, 146, 110, 21);
				contentPane.add(markedField);
				markedField.setColumns(10);
				
				JButton sureButton = new JButton("确定");
				sureButton.setBounds(70, 187, 66, 23);
				contentPane.add(sureButton);
				sureButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						
						String nameOfAccount = nameOfAccountField.getText() ;
						String numOfTf = numOfTfField.getText() ;
						String marked = markedField.getText() ;
						if(nameOfAccount.equals("")||numOfTf.equals("")||marked.equals("")){
							new warningDialog("信息不完整");
						}else{
							TransferListItemVO tfAccount = new TransferListItemVO(nameOfAccount, Double.parseDouble(numOfTf), marked);
					    	tfAccounts.add(tfAccount) ;
					    	dispose() ;
					    	setNum();
						}
					}
				});
				
				JButton cancelButton = new JButton("取消");
				cancelButton.setBounds(170, 187, 74, 23);
				contentPane.add(cancelButton);
				cancelButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						dispose() ;
					}
				});
			}

		}
		
	}

    class MakeCash extends JPanel{


		private JTextField nameOfAccountField;
		private JTextField numberField;
		private JTextField sumOfMoneyField;
		ArrayList<CaseListItemVO> items = new ArrayList<CaseListItemVO>() ;//报销条目
		double sumOfMoney = 0 ;
		/**
		 * Create the frame.
		 */
		public MakeCash() {
			setBounds(120, 100, 500, 380);
			this.setBorder(new EmptyBorder(5, 5, 5, 5));
			this.setLayout(null);
			
			JLabel numberLabel = new JLabel("单据编号：");
			numberLabel.setBounds(39, 42, 100, 15);
			this.add(numberLabel);
			
			JLabel nameOfAccountLabel = new JLabel("银行账户：");
			nameOfAccountLabel.setBounds(39, 99, 100, 15);
			this.add(nameOfAccountLabel);
			
			JLabel sumOfMoneyLabel = new JLabel("总        额："); 
			sumOfMoneyLabel.setBounds(39, 184, 100, 15);
			this.add(sumOfMoneyLabel);
			
			nameOfAccountField = new JTextField();
			nameOfAccountField.setBounds(129, 96, 135, 21);
			this.add(nameOfAccountField);
			nameOfAccountField.setColumns(10);
			
			numberField = new JTextField();
			numberField.setBounds(129, 39, 135, 21);
			this.add(numberField);
			numberField.setColumns(10);
			numberField.setText(fController.getReceiptNumber("XJFYD"));
			
			JButton addCaseItemButton = new JButton("添加条目");
			addCaseItemButton.setBounds(50, 140, 100, 23);
			this.add(addCaseItemButton);
			addCaseItemButton.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					new AddCaseItem() ;
				}
			});
			
			JButton showCaseItemButton = new JButton("查看已添加条目") ;
			showCaseItemButton.setBounds(200, 140, 130, 23);
			this.add(showCaseItemButton) ;
			showCaseItemButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new ShowCaseItem();
				}
			});
			
			sumOfMoneyField = new JTextField();
			sumOfMoneyField.setBounds(129, 181, 135, 21);
			this.add(sumOfMoneyField);
			sumOfMoneyField.setColumns(10);
			
			JLabel yuanLabel = new JLabel("元");
			yuanLabel.setBounds(270, 184, 21, 15);
			this.add(yuanLabel);
			
			JButton sureButton = new JButton("确定 ") ;
			sureButton.setBounds(80, 270, 100, 23);
			this.add(sureButton) ;
			sureButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					String account = nameOfAccountField.getText() ;
					String number = numberField.getText() ;
					String sum = sumOfMoneyField.getText() ;
					if(account.equals("")||number.equals("")||sum.equals("")){
						new warningDialog("信息不完整");
					}else{
						
						CashVO cash = new CashVO(number,user.getUserName(),account,items,Double.parseDouble(sum)) ;
						ResultMessage result = fController.addCash(cash) ;
						if(result.equals(ResultMessage.add_failure)){
							new warningDialog("输入账户不存在！");
						}else{
					    new warningDialog("添加成功");
						
						nameOfAccountField.setText("");
						numberField.setText("");
						sumOfMoneyField.setText("");
						items = new ArrayList<CaseListItemVO>() ;
						sumOfMoney = 0 ;
						numberField.setText(fController.getReceiptNumber("XJFYD"));
						}
					}
					
				}
			});
			
			JButton cancelButton = new JButton("取消");
			cancelButton.setBounds(250, 270, 100, 23);
			this.add(cancelButton) ;
			cancelButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					items = new ArrayList<CaseListItemVO>() ;
					sumOfMoney = 0 ;
					nameOfAccountField.setText("");                                                          
					sumOfMoneyField.setText("");
				}
			});
		}

		
		
		
		   class AddCaseItem extends JFrame{


				private JPanel contentPane;
				private JTextField nameOfCaseField;
				private JTextField numOfTfField;
				private JTextField markedField;


				/**
				 * Create the frame.
				 */
				public AddCaseItem() {
					setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					setBounds(500, 200, 350, 300);
					contentPane = new JPanel();
					contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
					setContentPane(contentPane);
					contentPane.setLayout(null);
					this.setVisible(true);
					
					JLabel nameOfCase = new JLabel("条目名：");
					nameOfCase.setBounds(41, 37, 66, 15);
					contentPane.add(nameOfCase);
					
					nameOfCaseField = new JTextField();
					nameOfCaseField.setBounds(139, 34, 110, 21);
					contentPane.add(nameOfCaseField);
					nameOfCaseField.setColumns(10);
					
					JLabel numOfMoneyLabel = new JLabel("金    额：");
					numOfMoneyLabel.setBounds(41, 96, 66, 15);
					contentPane.add(numOfMoneyLabel);
					JLabel yuanLabel = new JLabel("元");
					yuanLabel.setBounds(260, 95, 20, 15);
					contentPane.add(yuanLabel) ;
					
					numOfTfField = new JTextField();
					numOfTfField.setBounds(139, 93, 110, 21);
					contentPane.add(numOfTfField);
					numOfTfField.setColumns(10);
					
					JLabel markedLabel = new JLabel("备    注：");
					markedLabel.setBounds(41, 149, 66, 15);
					contentPane.add(markedLabel);
					
					markedField = new JTextField();
					markedField.setBounds(139, 146, 110, 21);
					contentPane.add(markedField);
					markedField.setColumns(10);
					
					JButton sureButton = new JButton("确定");
					sureButton.setBounds(70, 187, 66, 23);
					contentPane.add(sureButton);
					sureButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							String nameOfCase = nameOfCaseField.getText() ;
							String money = numOfTfField.getText() ;
							String marked = markedField.getText() ;
							if(nameOfCase.equals("")||money.equals("")||marked.equals("")){
								new warningDialog("信息不完整");
							}else{
								
								CaseListItemVO item = new CaseListItemVO(nameOfCase, Double.parseDouble(money), marked) ;
								
								items.add(item) ;
								setNum();
								dispose() ;
							}
						}
					});
					
					JButton cancelButton = new JButton("取消");
					cancelButton.setBounds(170, 187, 74, 23);
					contentPane.add(cancelButton);
					cancelButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							dispose() ;
						}
					});
				}

			}
	
    
			class ShowCaseItem extends JFrame{

		    	private JPanel contentPane;
		    	private JTable table;

		    	/**
		    	 * Create the frame.
		    	 */
		    	public ShowCaseItem() {
		    		this.setVisible(true);
		    		this.setTitle("条目清单");
		    		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    		setBounds(500, 200, 350, 300);
		    		contentPane = new JPanel();
		    		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		    		contentPane.setBackground(Color.yellow);
		    		setContentPane(contentPane);
		    		contentPane.setLayout(null);
		    		
		    		table = new JTable(new MyTableModel());
		    		table.setBackground(Color.white);
		    		table.addMouseListener(new MouseAdapter() {
		    			public void mouseClicked(MouseEvent e){
		    				Point mousePoint = e.getPoint() ;
		    				if(table.columnAtPoint(mousePoint) == 3){
		    					int i = JOptionPane.showConfirmDialog(null, "确定删除？");
		    					if(i == 0){
		    						int k = table.rowAtPoint(mousePoint) ;
		    						items.remove(k) ;
		    						setNum();
		    						dispose();
		    						new ShowCaseItem() ;
		    					}
		    				}
		    			}
					});
		    		
		    		JScrollPane jsc = new JScrollPane(table);
		    		jsc.setBounds(0,0,350,170);
		    		jsc.setBackground(Color.green);
		    		contentPane.add(jsc) ;
		    		
		    		JButton sureButton = new JButton("确定");
		    		sureButton.setBounds(110, 200, 100, 30);
		    		contentPane.add(sureButton) ;
		    		sureButton.addMouseListener(new MouseAdapter() {
		    			public void mouseClicked(MouseEvent e){
		    				dispose() ;
		    			}
					});
		    		
		    	}
		    	
		}
			void setNum(){
	    		sumOfMoney = 0;
	    		for(CaseListItemVO theItem : items){
	    			sumOfMoney += theItem.getCaseMoney() ;
	    		}
	    		sumOfMoneyField.setText(String.valueOf(sumOfMoney));
	    	}
			class MyTableModel extends AbstractTableModel{
				private ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
				private String[] column ={"条目名","金额","备注","是否删除"} ;

				public MyTableModel(){
					for(CaseListItemVO item : items){
						ArrayList<Object> oneRow = new ArrayList<Object>() ;
						oneRow.add(item.getCasename()) ;
						oneRow.add(item.getCaseMoney()) ;
						oneRow.add(item.getRemark()) ;
						oneRow.add("删除");
						datas.add(oneRow) ;
					}
				}

				@Override
				public String getColumnName(int col)
			     {
			          return column[col];
			     }
				public int getColumnCount() {
					// TODO Auto-generated method stub
					return column.length;
				}

				@Override
				public int getRowCount() {
					// TODO Auto-generated method stub
					return datas.size();
				}

				@Override
				public Object getValueAt(int row, int column) {
					// TODO Auto-generated method stub
					return datas.get(row).get(column);
				}
				public boolean isCellEditable(int row, int col) { 
						return false ;
				}
				public void setValueAt(Object value, int row, int col) {  
			        datas.get(row).set(col,  value);
			        fireTableCellUpdated(row, col);  
			    }	
			}
    }
 
    class MessageFromManager extends JPanel{
    	private JTable passTable ;
    	private JScrollPane passJsc ;
    	private JTable failTable ;
    	private JScrollPane failJsc ;
    	private JLabel freshLabel ;
    	/**
    	 * Create the panel.
    	 */
    	public MessageFromManager() {
    		setBounds(120, 100, 500, 380);
    		this.setBorder(new EmptyBorder(5, 5, 5, 5));
    		this.setLayout(null);
    		
    		JLabel passLabel = new JLabel("审批通过");
    		passLabel.setBounds(195, 10, 54, 15);
    		add(passLabel);
    		
    		
    		JLabel failLabel = new JLabel("审批未通过");
    		failLabel.setBounds(195, 197, 69, 15);
    		add(failLabel);
    		
    		
    		freshLabel = new JLabel("一键转账");
    		freshLabel.setBounds(406, 10, 54, 15);
    		add(freshLabel);
    		
    		
    		
    		
    		refreshTable();
    		
    	}
    	public void refreshTable(){
    		String[] passColumn = {"单据编号","客户","操作员","转账列表","总额汇总"};
			ArrayList<CollectionOrPaymentVO> passReceipts = new FinanceController().showPassReceipt() ;
			ArrayList<CollectionOrPaymentVO> failReceipts = new FinanceController().showFailReceipt() ;
			passTable = new JTable(new MyTableModel(passReceipts, passColumn,true)) ;
			passTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					Point mousePoint = e.getPoint() ;
					if(passTable.columnAtPoint(mousePoint) == 3){
						int i = passTable.rowAtPoint(mousePoint) ;
						if(i!=-1){
						CollectionOrPaymentVO theVO = passReceipts.get(i) ;
						ArrayList<Object> list = new ArrayList<>(theVO.getTrList()) ;
						String[] column2= {"银行账户","转账金额","备注"} ;
						new ShowListFrame(list, column2, "转账列表");
						}
					}
//					if(passTable.columnAtPoint(mousePoint) == 5){
//						int i = passTable.rowAtPoint(mousePoint) ;
//						if(i!=-1){
//						CollectionOrPaymentVO theReceipt = passReceipts.get(i) ;
//						theReceipt.setApprovedByFinancer(true);
//						fController.updateCollectionOrPayment(theReceipt) ;
//						refreshTable();
//						}
//						
//					}
				}
			});
			
			freshLabel.addMouseListener(new MouseAdapter() {
    			public void mouseClicked(MouseEvent e){
    				for(int i=0;i<passReceipts.size();i++){
    					CollectionOrPaymentVO theReceipt = passReceipts.get(i) ;
    					theReceipt.setApprovedByFinancer(true);
    					fController.updateCollectionOrPayment(theReceipt) ;
    					refreshTable();
    				}
    			}
			});
			passTable.setBackground(Color.WHITE);
			
			if(passJsc!=null)
				passJsc.setVisible(false);
			passJsc = new JScrollPane(passTable) ;
			passJsc.setBounds(23, 38, 451, 149);
			passJsc.setBackground(Color.white);
			this.add(passJsc) ;
			
    		
			String[] failColumn = {"单据编号","客户","操作员","转账列表","总额汇总"} ;
    		failTable = new JTable(new MyTableModel(failReceipts, failColumn,false)) ;
    		failTable.addMouseListener(new MouseAdapter() {
    			public void mouseClicked(MouseEvent e){
    				Point mousePoint = e.getPoint() ;
    				if(failTable.columnAtPoint(mousePoint) == 3){
						int i = failTable.rowAtPoint(mousePoint) ;
						CollectionOrPaymentVO theVO = failReceipts.get(i) ;
						ArrayList<Object> list = new ArrayList<>(theVO.getTrList()) ;
						String[] column2= {"银行账户","转账金额","备注"} ;
						new ShowListFrame(list, column2, "转账列表");
					}
    			}
			});
    		failTable.setBackground(Color.WHITE);
    		
    		failJsc = new JScrollPane(failTable) ;
    		failJsc.setBounds(23, 222, 451, 148);
    		failJsc.setBackground(Color.white);
    		this.add(failJsc) ;
		}
}

	
	/*
	 * 查看报表的panel
	 */
	class InfoPanel extends JPanel{
		SaleDetailPanel saleDetailPanel ;
		SaleProcessPanel saleProcessPanel ;
		SaleConditionPanel saleConditionPanel ;
		public InfoPanel(JFrame theFrame){
			super() ;
	    	this.setBounds(140, 25, 835, 550);
	    	this.setBackground(new Color(200, 100, 150, 255));
	    	this.setLayout(null);
	    	theFrame.add(this) ;
	    	saleDetailPanel  = new SaleDetailPanel() ;
	    	this.add(saleDetailPanel) ;
//	    	saleDetailPanel.beginTimeField.requestFocus();
	    	saleProcessPanel = new SaleProcessPanel() ;
	    	this.add(saleProcessPanel);
	    	saleProcessPanel.setVisible(false);
	    	saleConditionPanel = new SaleConditionPanel() ;
	    	this.add(saleConditionPanel) ;
	    	saleConditionPanel.setVisible(false);
	    	this.setVisible(false);
	    	
	    	
	    	JLabel saleDetailLabel = new JLabel("销售明细表",JLabel.CENTER);
			saleDetailLabel.setBounds(100, 23, 120, 50);
			this.add(saleDetailLabel) ;
			saleDetailLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					saleDetailPanel.setVisible(true);
					saleProcessPanel.setVisible(false);
					saleConditionPanel.setVisible(false);
				}
			});
			
			
			JLabel saleProcessLabel = new JLabel("经营历程表",JLabel.CENTER) ;
			saleProcessLabel.setBounds(200, 23, 120, 50);
			this.add(saleProcessLabel) ;
			saleProcessLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					saleDetailPanel.setVisible(false);
					saleProcessPanel.setVisible(true);
					saleConditionPanel.setVisible(false);
				}
			});
			
			JLabel saleConditionLabel = new JLabel("经营情况表",JLabel.CENTER) ;
			saleConditionLabel.setBounds(300,23,120,50) ;
			this.add(saleConditionLabel) ;
			saleConditionLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					saleDetailPanel.setVisible(false);
					saleProcessPanel.setVisible(false);
					saleConditionPanel.setVisible(true);
				}
			});
			
			this.repaint();
	    }
	}
	class SaleDetailPanel extends JPanel{

		private JTextField beginTimeField;
		private JTextField endTimeField;
		private JTextField nameOfGoodField;
		private JTextField nameOfCustomerField;
		private JTextField nameOfRetailerField;
		private JTextField storageField;
		private JTable saleDetailTable;
		JScrollPane jsc  ;
		ArrayList<SalesReceiptPO> receipts = new ArrayList<SalesReceiptPO>() ;

		/**
		 * Create the panel.
		 */
		public SaleDetailPanel() {
			
			super();
			setLayout(null);
			this.setBackground(Color.LIGHT_GRAY);
			this.setBounds(80,74, 700,400);
			 
			refreshTable();
			
			JLabel timesLabel = new JLabel("时间区间：");
			timesLabel.setBounds(44, 24, 100, 15);
			add(timesLabel);
			
			beginTimeField = new JTextField() ;
			beginTimeField.setBounds(187, 21, 122, 21);
			add(beginTimeField);
			beginTimeField.setColumns(10);
			beginTimeField.setText("<例如2014/10/10>");
			
			
			JLabel beginTimeLabel = new JLabel("起始时间");
			beginTimeLabel.setBounds(123, 22, 100, 18);
			add(beginTimeLabel);
			new AddWordsChange(beginTimeField, "<例如2014/10/10>") ;
			
			JLabel endTimeLabel = new JLabel("截止时间");
			endTimeLabel.setBounds(351, 24, 100, 15);
			add(endTimeLabel);
			
			endTimeField = new JTextField();
			endTimeField.setColumns(10);
			endTimeField.setBounds(415, 21, 122, 21);
			add(endTimeField);
			endTimeField.setText("<例如2014/11/10>");
			new AddWordsChange(endTimeField, "<例如2014/11/10>") ;
			
			JLabel nameOfGoodLabel = new JLabel("商品名称");
			nameOfGoodLabel.setBounds(123, 51, 100, 15);
			add(nameOfGoodLabel);
			
			nameOfGoodField = new JTextField();
			nameOfGoodField.setBounds(187, 52, 122, 21);
			add(nameOfGoodField);
			nameOfGoodField.setColumns(10);
			
			JLabel nameOfCustomerLabel = new JLabel("客户名称");
			nameOfCustomerLabel.setBounds(351, 55, 100, 15);
			add(nameOfCustomerLabel);
			
			nameOfCustomerField = new JTextField();
			nameOfCustomerField.setBounds(415, 52, 122, 21);
			add(nameOfCustomerField);
			nameOfCustomerField.setColumns(10);
			
			JLabel nameOfRetailerLabel = new JLabel("业务员");
			nameOfRetailerLabel.setBounds(123, 86, 100, 15);
			add(nameOfRetailerLabel);
			
			nameOfRetailerField = new JTextField();
			nameOfRetailerField.setBounds(187, 83, 122, 21);
			add(nameOfRetailerField);
			nameOfRetailerField.setColumns(10);
			
			JLabel storageLabel = new JLabel("仓库");
			storageLabel.setBounds(351, 86, 100, 15);
			add(storageLabel);
			
			storageField = new JTextField();
			storageField.setBounds(415, 83, 122, 21);
			add(storageField);
			storageField.setColumns(10);
			
			
			
			
			JButton sureButton = new JButton("确定");
			sureButton.setBounds(578, 24, 93, 23);
			add(sureButton);
			sureButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					String beginTime = beginTimeField.getText() ;
					String endTime = endTimeField.getText() ;
					String nameOfGood = nameOfGoodField.getText() ;
					String nameOfCustomer = nameOfCustomerField.getText();
					String nameOfRetailer = nameOfRetailerField.getText() ;
					String storage = storageField.getText() ;
					String typeOfReceipt = "XSD";
					if(beginTime.equals("<例如2014/10/10>")||endTime.equals("<例如2014/11/10>")){
						new warningDialog("请输入完整的时间区间");
					}else{
						ScreeningConditionVO condition = new ScreeningConditionVO(beginTime,endTime,typeOfReceipt,nameOfGood,nameOfCustomer,nameOfRetailer,storage) ;
						receipts = infoController.showSalesDetailsInfo(condition) ;
						refreshTable(receipts); 
					}
				}
			});
			
			JButton cancelButton = new JButton("取消");
			cancelButton.setBounds(578, 68, 93, 23);
			add(cancelButton);
			cancelButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					beginTimeField.setText("<例如2014/10/10>");
					endTimeField.setText("<例如2014/11/10>");
					nameOfGoodField.setText("");
					nameOfCustomerField.setText("");
					nameOfRetailerField.setText("");
					storageField.setText("");
					receipts = new ArrayList<SalesReceiptPO>() ;
					refreshTable( receipts);
				}
			});

		}
		public void refreshTable(){
			String[] columNames ={"单据编号","客户","业务员","操作员","仓库","商品清单","折让前总额","折让","代金券金额","折让后总额","备注"};
			String[][] datas = {{"数据1","数据2","数据3","数据4","数据5","数据6","数据7","数据8","数据9","数据10","数据11"},{"……","……","……","……","……","……","……","……","……","……","……"}} ;
			saleDetailTable = new JTable(datas,columNames) ;
            saleDetailTable.setBackground(Color.white);
            
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();   //设置单元格内容居中
		    render.setHorizontalAlignment(SwingConstants.CENTER);
		    
		    jsc = new JScrollPane(saleDetailTable) ;	
		    jsc.setBounds(5, 121, 690, 240);
            saleDetailTable.setFillsViewportHeight(true);
            this.add(jsc) ;
		}
		public void refreshTable(ArrayList<SalesReceiptPO> receipts){
			String[] columNames ={"单据编号","客户","业务员","操作员","仓库","商品清单","折让前总额","折让","代金券金额","折让后总额","备注"};
			ArrayList<Object> objects = new ArrayList<Object>() ;
			for(SalesReceiptPO item:receipts ){
				objects.add(item) ;
			}
			saleDetailTable = new JTable(new MyTableModel(objects,columNames,"XSD")) ;
			saleDetailTable.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					Point mousePoint = e.getPoint()  ;
					if(saleDetailTable.columnAtPoint(mousePoint)== 5){
						SalesReceiptPO thePO = receipts.get(saleDetailTable.rowAtPoint(mousePoint)) ;
						String[] column2 = {"编号","名称","型号","数量","单价","金额","商品备注"};
						ArrayList<Object> list = new ArrayList<>(thePO.getSalesList()) ;
						new ShowListFrame(list, column2,"商品清单");
					}
				}
			});
			saleDetailTable.setBackground(Color.white);
			
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();   //设置单元格内容居中
		    render.setHorizontalAlignment(SwingConstants.CENTER);
		    saleDetailTable.setDefaultRenderer(Object.class, render);
			
		    if(jsc != null)
		    	jsc.setVisible(false);
		    jsc = new JScrollPane(saleDetailTable) ;	
		    jsc.setBounds(5, 121, 690, 240);
			saleDetailTable.setFillsViewportHeight(true);
			this.add(jsc) ;
		}
		
	}
	class SaleProcessPanel extends JPanel{
		private JTextField beginTimeField;
		private JTextField endTimeField;
		private JTextField nameOfCustomerField;
		private JTextField nameOfRetailerField;
		private JTextField storageField;
		private JTable table;
		private JScrollPane jsc ;
		private JPanel thePanel;
		ArrayList<Object> result ;
		Object theSaveReceipt ; 
		String typeOfReceipt  ;
		int markColumn = 10000 ;
		int currentRow = 10000 ;
		/**
		 * Create the panel.
		 */
		public SaleProcessPanel() {
			freshTable();
			setLayout(null);
			this.setBounds(80,74, 700,400);
			this.setBackground(Color.LIGHT_GRAY);
			thePanel = this ;
			
			JLabel timesLabel = new JLabel("时间区间");
			timesLabel.setBounds(44, 59, 69, 15);
			add(timesLabel);
			
			beginTimeField = new JTextField();
			beginTimeField.setBounds(188, 56, 122, 21);
			add(beginTimeField);
			beginTimeField.setColumns(10);
			beginTimeField.setText("<例如2014/10/10>");
			new AddWordsChange(beginTimeField, "<例如2014/10/10>") ;
			
			JLabel beginTimeLabel = new JLabel("起始时间");
			beginTimeLabel.setBounds(124, 57, 54, 18);
			add(beginTimeLabel);
			
			JLabel endTimeLabel = new JLabel("截止时间");
			endTimeLabel.setBounds(351, 59, 54, 15);
			add(endTimeLabel);
			
			endTimeField = new JTextField();
			endTimeField.setColumns(10);
			endTimeField.setBounds(415, 56, 122, 21);
			add(endTimeField);
			endTimeField.setText("<例如2014/11/10>");
			new AddWordsChange(endTimeField, "<例如2014/11/10>") ;
			
			JLabel nameOfCustomerLabel = new JLabel("客户名称");
			nameOfCustomerLabel.setBounds(44, 90, 54, 15);
			add(nameOfCustomerLabel);
			
			nameOfCustomerField = new JTextField();
			nameOfCustomerField.setBounds(124, 87, 77, 21);
			add(nameOfCustomerField);
			nameOfCustomerField.setColumns(10);
			
			JLabel nameOfRetailerLabel = new JLabel("业务员");
			nameOfRetailerLabel.setBounds(238, 90, 54, 15);
			add(nameOfRetailerLabel);
			
			nameOfRetailerField = new JTextField();
			nameOfRetailerField.setBounds(293, 87, 77, 21);
			add(nameOfRetailerField);
			nameOfRetailerField.setColumns(10);
			
			JLabel storageLabel = new JLabel("仓库");
			storageLabel.setBounds(415, 90, 54, 15);
			add(storageLabel);
			
			storageField = new JTextField();
			storageField.setBounds(459, 87, 77, 21);
			add(storageField);
			storageField.setColumns(10);
			
			JButton sureButton = new JButton("确定");
			sureButton.setBounds(578, 35, 93, 23);
			add(sureButton);
			
			JButton cancelButton = new JButton("取消");
			cancelButton.setBounds(578, 68, 93, 23);
			add(cancelButton);
			
			JLabel typeOfReceiptLabel = new JLabel("单据类型");
			typeOfReceiptLabel.setBounds(44, 25, 54, 15);
			add(typeOfReceiptLabel);
			
			String[] typeOfReceipts1 = {"销售类","进货类","财务类","库存类"} ;
			JComboBox jcs1 = new JComboBox(typeOfReceipts1) ;
			jcs1.setBounds(123, 21, 100, 23);
			add(jcs1) ;
			
			String[][] typeOfReceipts2 = {{"销售出货单","销售退货单"},{"进货单","进货退货单"},{"收款单","付款单","现金费用单"},{"报溢单","报损单","赠送单"}};
			JComboBox jcs2 = new JComboBox() ;
			jcs2.setBounds(250, 21, 100, 23);
			add(jcs2) ;
			
			jcs1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					JComboBox temp = (JComboBox)e.getSource() ;
					String type1 = (String) temp.getSelectedItem() ;
					if(jcs2.getItemCount() !=0){
						jcs2.removeAllItems();
						jcs2.updateUI();
						jcs2.setSelectedItem("");
					}
					if(type1.equals(typeOfReceipts1[0])){
						for(int i = 0 ;i<typeOfReceipts2[0].length;i++){
							jcs2.addItem(typeOfReceipts2[0][i]);
						}
					}
					if(type1.equals(typeOfReceipts1[1])){
						for(int i = 0 ;i<typeOfReceipts2[1].length;i++){
							jcs2.addItem(typeOfReceipts2[1][i]);
						}
					}
					if(type1.equals(typeOfReceipts1[2])){
						for(int i = 0 ;i<typeOfReceipts2[2].length;i++){
							jcs2.addItem(typeOfReceipts2[2][i]);
						}
					}
					if(type1.equals(typeOfReceipts1[3])){
						for(int i = 0 ;i<typeOfReceipts2[3].length;i++){
							jcs2.addItem(typeOfReceipts2[3][i]);
						}
					}
				}
			});;
			
			JLabel hcLablel = new JLabel("红冲");
			hcLablel.setBounds(400, 365, 100, 20);
			add(hcLablel) ;
			hcLablel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					int i = JOptionPane.showConfirmDialog(null, "确定进行红冲操作？");
					if(i == 0)
					hc(typeOfReceipt) ;
					currentRow = 10000 ;
				}
			});
			 
			
			JLabel hcAndfzLabel = new JLabel("红冲并复制");
			hcAndfzLabel.setBounds(510, 365, 150, 20);
			add(hcAndfzLabel) ;
			hcAndfzLabel.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					int i = JOptionPane.showConfirmDialog(null, "确定进行红冲并复制操作？");
					if( i==0 )
					hcAndfz(typeOfReceipt) ;
					currentRow = 10000 ;
				}
			});
			
			
            cancelButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					currentRow = 10000;
					markColumn = 10000;
					typeOfReceipt = "" ;
					jcs1.setSelectedIndex(0);
					jcs2.removeAllItems();
					thePanel.remove(jsc);
					freshTable();
					beginTimeField.setText("<例如2014/10/10>") ;	
					endTimeField.setText("<例如2014/11/10>") ;
					nameOfCustomerField.setText("");
					nameOfRetailerField.setText("");
					storageField.setText("");
				}
			});
			sureButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					currentRow = 10000;
					markColumn = 10000;
					String beginTime = beginTimeField.getText() ;
					String endTime = endTimeField.getText() ;
					String nameOfCustomer = nameOfCustomerField.getText() ;
					String nameOfRetailer = nameOfRetailerField.getText() ;
					String storage = storageField.getText() ;
					if(jcs2.getSelectedItem() == null){
						new warningDialog("请选择单据类型") ;
					}else{
					if(jcs2.getSelectedItem().equals("销售出货单"))
						typeOfReceipt = "XSD";
					else
						if(jcs2.getSelectedItem().equals("销售退货单"))
							typeOfReceipt = "XSTHD" ;
						else
							if((jcs2.getSelectedItem().equals("进货单")))
								typeOfReceipt = "JHD" ;
							else
								if(jcs2.getSelectedItem().equals("进货退货单"))
									typeOfReceipt = "JHTHD" ;
								else
									if(jcs2.getSelectedItem().equals("收款单"))
										typeOfReceipt = "SKD";
									else
										if(jcs2.getSelectedItem().equals("付款单"))
											typeOfReceipt = "FKD";
										else
											if(jcs2.getSelectedItem().equals("现金费用单"))
												typeOfReceipt = "XJFYD" ;
											else
												if(jcs2.getSelectedItem().equals("报溢单"))
													typeOfReceipt = "BYD" ;
												else
													if(jcs2.getSelectedItem().equals("报损单"))
														typeOfReceipt = "BSD" ;
													else
														typeOfReceipt = "ZSD" ;
					
					    if(beginTime.equals("<例如2014/10/10>")||endTime.equals("<例如2014/11/10>")){
					    	new warningDialog("请输入时间区间") ;
					    }else{
					    		result = infoController.showSalesProcessInfo(new ScreeningConditionVO(beginTime,endTime,typeOfReceipt,"",nameOfCustomer,nameOfRetailer,storage)) ;
					    		thePanel.remove(jsc);
					    		System.out.println(result.size());
					    		freshTable(result, typeOfReceipt);
					    }
					}
					
				}
			});
		}
	    private void freshTable() {
	    	String[] columnNames = {"单据属性1","单据属性2","……"};
	    	String[][] data = {{"数据11","数据12","……"},{"数据21","数据22","……"},{"数据31","数据32","……"}};
			
			table = new JTable(data,columnNames) ;
			table.setBackground(Color.white);
			
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();   //设置单元格内容居中
		    render.setHorizontalAlignment(SwingConstants.CENTER);
		    table.setDefaultRenderer(Object.class, render);
		
			jsc = new JScrollPane(table) ;
			 jsc.setBounds(5, 121, 690, 240);
			add(jsc);
		}
		private void freshTable(ArrayList<Object> objects ,String type) {
			if(type.equals("SKD")||type.equals("FKD")){//收付款单
				markColumn = 3 ;
				String[] column = {"单据编号","客户","操作员","转账列表","总额汇总"} ;
				table = new JTable(new MyTableModel(objects, column, type)) ;
				table.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e){
						Point mousePoint = e.getPoint() ;
						int i = table.rowAtPoint(mousePoint) ;
						currentRow = i ;
						if(table.columnAtPoint(mousePoint)== markColumn){
							String[] column2= {"银行账户","转账金额","备注"} ;
							CollectionOrPaymentVO theVO = (CollectionOrPaymentVO)objects.get(i) ;
							ArrayList<Object> list = new ArrayList<>(theVO.getTrList()) ;
							new ShowListFrame(list,column2 , "转账列表") ;
						}
					}
				});
			}
			if(type.equals("XJFYD")){//现金费用单
				markColumn = 3 ;
				String[] column = {"单据编号","操作员","银行账户","条目清单","总额"} ;
				table = new JTable(new MyTableModel(objects, column, type)) ;
				table.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e){
						Point mousePoint = e.getPoint() ;
						int i = table.rowAtPoint(mousePoint) ;
						currentRow = i;
						if(table.columnAtPoint(mousePoint)== markColumn){
							String[] column2 = {"条目名","金额","备注"} ;
							CashVO  theVO = (CashVO)objects.get(i) ;
							ArrayList<Object> list = new ArrayList<>(theVO.getCases()) ;
							new ShowListFrame(list, column2, "条目清单") ;
						}
					}
				});
			}
			if(type.equals("XSD")){//销售单
				markColumn = 5 ;
				String[] column ={"单据编号","客户","业务员","操作员","仓库","商品清单","折让前总额","折让","代金券金额","折让后总额","备注"};
				table = new JTable(new MyTableModel(objects,column,type)) ;
				table.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e){
						Point mousePoint = e.getPoint()  ;
						int i = table.rowAtPoint(mousePoint) ;
						currentRow = i ;
						if(table.columnAtPoint(mousePoint)== markColumn){
							String[] column2 = {"编号","名称","型号","数量","单价","金额","商品备注"};
						
							SalesReceiptPO thePO= (SalesReceiptPO) objects.get(i) ;
							ArrayList<Object> list = new ArrayList<>(thePO.getSalesList()) ;
							new ShowListFrame(list, column2,"出货商品清单");
						}
					}
				});
			}
			if(type.equals("XSTHD")){//销售退货单
				markColumn = 5 ;
				String[] column ={"单据编号","客户","业务员","操作员","仓库","商品清单","折让前总额","折让","代金券金额","折让后总额","备注"};
				table = new JTable(new MyTableModel(objects,column,type)) ;
				table.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e){
						Point mousePoint = e.getPoint()  ;
						int i = table.rowAtPoint(mousePoint) ;
						currentRow = i ;
						if(table.columnAtPoint(mousePoint)== markColumn){
							String[] column2 = {"编号","名称","型号","数量","单价","金额","商品备注"};
							
							SalesReceiptPO thePO= (SalesReceiptPO) objects.get(i) ;
							ArrayList<Object> list = new ArrayList<>(thePO.getSalesList()) ;
							new ShowListFrame(list, column2,"出货商品清单");
						}
					}
				});
			}
			if(type.equals("JHD")){//进货单
				markColumn = 4 ;
				String[] column = {"单据编号","供应商","仓库","操作员","入库商品列表","备注","总额合计"} ;
				table = new JTable(new MyTableModel(objects, column, type)) ;
				table.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e){
						Point mousePoint = e.getPoint()  ;
						int i = table.rowAtPoint(mousePoint) ;
						currentRow = i ;
						if(table.columnAtPoint(mousePoint)== markColumn){
							String[] column2 = {"编号","名称","型号","数量","单价","金额","商品备注"};
							PurchaseReceiptPO thePO= (PurchaseReceiptPO) objects.get(i) ;
							ArrayList<Object> list = new ArrayList<>(thePO.getPurchaseList()) ;
							System.out.println(list.size());
							new ShowListFrame(list, column2,"入库商品列表");
						}
					}
				});
			}
			if(type.equals("JHTHD")){//进货退货单
				markColumn = 4 ;
				String[] column = {"单据编号","供应商","仓库","操作员","出库商品列表","备注","总额合计"} ;
				table = new JTable(new MyTableModel(objects, column, type)) ;
				table.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e){
						Point mousePoint = e.getPoint()  ;
						int i = table.rowAtPoint(mousePoint) ;
						currentRow = i ;
						if(table.columnAtPoint(mousePoint)== markColumn){
							String[] column2 = {"编号","名称","型号","数量","单价","金额","商品备注"};
							
							PurchaseReceiptPO thePO= (PurchaseReceiptPO) objects.get(i) ;
							ArrayList<Object> list = new ArrayList<>() ;
							for(PurchaseListItemPO item :thePO.getPurchaseList()){
								GoodsPO goodPO = item.getGoodsPO() ;
							    GoodsVO good = new GoodsVO(goodPO.getSerialNumber(), goodPO.getName(), goodPO.getModel(), goodPO.getPrice(), goodPO.getSalePrice(), goodPO.getLatestPrice(), goodPO.getLatestSalePrice(), goodPO.getGoodsClassNum()) ;
								PurchaseListItemVO itemVO = new PurchaseListItemVO(good, item.getQuantity()) ;
								list.add(itemVO) ;
							}
							new ShowListFrame(list, column2,"出库商品列表");
						}
					}
				});
			}
			if(type.equals("BYD")){//报溢单
				String[] column = {"商品编号","商品数量","商品单价","日期"};
				table = new JTable(new MyTableModel(objects, column, type));
				table.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e){
						Point mousePoint = e.getPoint() ;
						currentRow = table.rowAtPoint(mousePoint) ;
					}
				});
			}
			if(type.equals("BSD")){//报损单
				String[] column = {"商品编号","商品数量","商品单价","日期"} ;
				table = new JTable(new MyTableModel(objects, column, type)) ;
				table.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e){
						Point mousePoint = e.getPoint() ;
						currentRow = table.rowAtPoint(mousePoint) ;
					}
				});
			}
			if(type.equals("ZSD")){//赠送单
				String[]  column = {"商品编号","客户名称","商品数量","商品单价","日期"} ;
				table = new JTable(new MyTableModel(objects, column, type)) ;
				table.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e){
						Point mousePoint = e.getPoint() ;
						currentRow = table.rowAtPoint(mousePoint) ;
					}
				});
			}
			
            table.setBackground(Color.white);
			
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();   //设置单元格内容居中
		    render.setHorizontalAlignment(SwingConstants.CENTER);
		    table.setDefaultRenderer(Object.class, render);
		
			jsc = new JScrollPane(table) ;
			 jsc.setBounds(5, 121, 690, 240);
			add(jsc);
		}
		
		private void hc(String type){
			if(currentRow == 10000){
				new warningDialog("请选择单据");
			}else{
			if(type.equals("SKD")||type.equals("FKD")){//收付款单	
				CollectionOrPaymentVO theReceipt = new CollectionOrPaymentVO((CollectionOrPaymentVO) result.get(currentRow)) ;
				for(TransferListItemVO theItem : theReceipt.getTrList()){
					theItem.setTransferMoney(-theItem.getTransferMoney());
				}
				theReceipt.setTotal(-theReceipt.getTotal());
				theReceipt.setNumber(fController.getReceiptNumber(type));
				theReceipt.setApprovedByFinancer(true);
				theReceipt.setApprovedByManager(true);
				fController.addCollectionOrPaymentVO(theReceipt) ;
			}
			if(type.equals("XJFYD")){//现金费用单
				CashVO theReceipt = new CashVO((CashVO)result.get(currentRow)) ;
				for(CaseListItemVO theItem:theReceipt.getCases()){
					theItem.setCaseMoney(-theItem.getCaseMoney());
				}
				theReceipt.setNumber(fController.getReceiptNumber(type));
				theReceipt.setSum(-theReceipt.getSum());
				fController.addCash(theReceipt) ;
			}
			if(type.equals("XSD")){//销售单
				SalesController sController = new SalesController() ;
				SalesReceiptPO theReceipt = new SalesReceiptPO((SalesReceiptPO) result.get(currentRow));
				SalesReceiptVO theVO1 = sController.toVO(theReceipt) ;
				theReceipt = sController.toPO(theVO1) ;
				SalesReceiptPO thePO = (SalesReceiptPO) result.get(currentRow) ;
				for(SalesListItemPO item : thePO.getSalesList()){
					System.out.println("####"+item.getQuantity());
				}
//				System.out.println(theReceipt.getSerialNumber()+" "+theReceipt.getCustomerPO().getName()+" "+theReceipt.getRetailer()+" "+theReceipt.getSalesman()+" "+theReceipt.getCommodityNum()+" "+theReceipt.getPriceBefore()+" "+theReceipt.getFinalprice());
				for(SalesListItemPO theItem:theReceipt.getSalesList()){
					theItem.setQuantity(-theItem.getQuantity());
					theItem.setTotalPrice(theItem.getTotalPrice());
				}
				theReceipt.setVocher(-theReceipt.getVocher());
		    	SalesmanFrameHelper saleHelper = new SalesmanFrameHelper();
				theReceipt.setSerialNumber(saleHelper.setSerialNumber(2));
				theReceipt.setDiscout(-theReceipt.getDiscout());
				theReceipt.setPriceBefore(theReceipt.getPriceBefore());
				theReceipt.setFinalprice(theReceipt.getFinalprice());
				
				
				theReceipt.setApprovedByCommodity(true);
				theReceipt.setApprovedByManager(true);
				for(SalesListItemPO item : thePO.getSalesList()){
					System.out.println("####"+item.getQuantity());
				}
//				System.out.println("转VO前");
//				for(SalesListItemPO item :theReceipt.getSalesList()){
//					System.out.println(item.getTotalPrice()+" "+item.getQuantity());
//				}
//				System.out.println(theReceipt.getSerialNumber()+" "+theReceipt.getCustomerPO().getName()+" "+theReceipt.getRetailer()+" "+theReceipt.getSalesman()+" "+theReceipt.getCommodityNum()+" "+theReceipt.getPriceBefore()+" "+theReceipt.getFinalprice());
			
				SalesReceiptVO theVO = sController.toVO(theReceipt) ;
//				System.out.println("转VO后，creat前");
//				for(SalesListItemVO item :theVO.getSalesList()){
//					System.out.println(item.getTotalPrice()+" "+item.getQuantity());
//				}
//				System.out.println(theVO.getSerialNumber()+" "+theVO.getCustomerVO().getName()+" "+theVO.getRetailer()+" "+theVO.getSalesman()+" "+theVO.getCommodityNum()+" "+theVO.getPriceBefore()+" "+theVO.getFinalprice());
			
				sController.creatReceipt(theVO) ;
//				System.out.println("create后");
//				for(SalesListItemVO item :theVO.getSalesList()){
//					System.out.println(item.getTotalPrice()+" "+item.getQuantity());
//				}
//				System.out.println(theVO.getSerialNumber()+" "+theVO.getCustomerVO().getName()+" "+theVO.getRetailer()+" "+theVO.getSalesman()+" "+theVO.getCommodityNum()+" "+theVO.getPriceBefore()+" "+theVO.getFinalprice());
			
				
				
				ArrayList<SalesReceiptPO> list = new ArrayList<SalesReceiptPO>() ;
				list.add(theReceipt) ;
				ApprovalBLService_Controller aController = new ApprovalBLService_Controller() ;
				aController.salesChangeGoods(list);
				aController.salesChangeCustomer(list);
				
				
			}
			if(type.equals("XSTHD")){//销售退货单
				SalesController sController = new SalesController() ;
				SalesReceiptPO theReceipt = new SalesReceiptPO((SalesReceiptPO) result.get(currentRow));
				SalesReceiptVO theVO1 = sController.toVO(theReceipt) ;
				theReceipt = sController.toPO(theVO1) ;
				for(SalesListItemPO theItem:theReceipt.getSalesList()){
					theItem.setQuantity(-theItem.getQuantity());
					theItem.setTotalPrice(-theItem.getTotalPrice());
				}
				SalesmanFrameHelper saleHelper = new SalesmanFrameHelper();
				theReceipt.setSerialNumber(saleHelper.setSerialNumber(-2));
				theReceipt.setDiscout(-theReceipt.getDiscout());
				theReceipt.setPriceBefore(-theReceipt.getPriceBefore());
				theReceipt.setFinalprice(-theReceipt.getFinalprice());
				theReceipt.setVocher(-theReceipt.getVocher());
				theReceipt.setApprovedByCommodity(true);
				theReceipt.setApprovedByManager(true);
				SalesReceiptVO theVO = sController.toVO(theReceipt) ;
				sController.creatReceipt(theVO) ;
				
				ArrayList<SalesReceiptPO> list = new ArrayList<SalesReceiptPO>() ;
				list.add(theReceipt) ;
				ApprovalBLService_Controller aController = new ApprovalBLService_Controller() ;
				aController.salesChangeGoods(list);
				aController.salesChangeCustomer(list);
			}
			if(type.equals("JHD")||type.equals("JHTHD")){//进货单和进货退货单
				PurchaseController pController = new PurchaseController() ;
				PurchaseReceiptPO theReceipt = new PurchaseReceiptPO((PurchaseReceiptPO)result.get(currentRow)) ;
			    PurchaseReceiptVO theVO1 = pController.toVO(theReceipt) ;
			    theReceipt = pController.toPO(theVO1) ;
				for(PurchaseListItemPO theItem : theReceipt.getPurchaseList()){
					theItem.setQuantity(-theItem.getQuantity());
					theItem.setTotalPrice(-theItem.getTotalPrice());
				}
				if(type.equals("JHD")){
					SalesmanFrameHelper saleHelper = new SalesmanFrameHelper();
					theReceipt.setSerialNumber(saleHelper.setSerialNumber(1));
				}else{
					SalesmanFrameHelper saleHelper = new SalesmanFrameHelper();
					theReceipt.setSerialNumber(saleHelper.setSerialNumber(-1));
				}
				theReceipt.setTotalPrice(theReceipt.getTotalPrice()) ;
				theReceipt.setApprovedByCommodity(true);
				theReceipt.setApprovedByManager(true);
				PurchaseReceiptVO theVO = pController.toVO(theReceipt) ;
				pController.creatReceipt(theVO) ;
				ApprovalBLService_Controller aController = new ApprovalBLService_Controller();
				ArrayList<PurchaseReceiptPO> list = new ArrayList<PurchaseReceiptPO>() ;
				list.add(theReceipt) ;
				aController.purchaseChangeGoods(list);
				aController.purchaseChangeCustomer(list);
				
			}
			if(type.equals("BYD")||type.equals("BSD")){//报溢报损单
				System.out.println("进入红冲");
				ReportCommodityVO theReceipt = new ReportCommodityVO((ReportCommodityVO) result.get(currentRow));
				theReceipt.num = -theReceipt.num ;
				theReceipt.date = new Date();
				CommodityController c = new CommodityController() ;
				c.addReportCommodity(theReceipt); 
				
			}
			if(type.equals("ZSD")){//赠送单
				SendCommodityVO theReceipt = (SendCommodityVO) result.get(currentRow) ;
				theReceipt.num = -theReceipt.num ;
				theReceipt.checked = 1 ;
				theReceipt.date = new Date();
				CommodityController c = new CommodityController() ;
				c.addSendCommodity(theReceipt) ;
			}
		}
		}
  
		private void hcAndfz(String type){
			if(currentRow == 10000){
				new warningDialog("请选择单据");
				return ;
			}
			hc(type) ;
			if(type.equals("BYD")||type.equals("BSD")){
				ReportCommodityVO theVO = (ReportCommodityVO) result.get(currentRow) ;
				new ReportFrame(theVO) ;
			}
			if(type.equals("ZSD")){
				SendCommodityVO theVO = (SendCommodityVO) result.get(currentRow) ;
				new SendFrame(theVO) ;
			}
			if(type.equals("SKD")||type.equals("FKD")){
				CollectionOrPaymentVO theVO = (CollectionOrPaymentVO) result.get(currentRow) ;
				new CollectionOrPaymentFrame(theVO) ;
			}
			if(type.equals("XJFYD")){
				CashVO theVO = (CashVO) result.get(currentRow) ;
				new CashForFinancer(theVO) ;
			}
			if(type.equals("XSD")){
				SalesController sController = new SalesController() ;
				SalesReceiptPO thePO = (SalesReceiptPO) result.get(currentRow) ;
				SalesReceiptVO theVO = sController.toVO(thePO) ;
				SalesmanFrameHelper saleHelper = new SalesmanFrameHelper();
				 SalesmanFrameHelper.AddSalesReceiptFrame frame = saleHelper.new AddSalesReceiptFrame(2, theVO, user) ;
			}
			if(type.equals("XSTHD")){
				SalesController sController = new SalesController() ;
				SalesReceiptPO thePO = (SalesReceiptPO) result.get(currentRow) ;
				SalesReceiptVO theVO = sController.toVO(thePO) ;
				SalesmanFrameHelper saleHelper = new SalesmanFrameHelper();
				saleHelper.new AddSalesReceiptFrame(-2, theVO, user) ;
			} 
			if(type.equals("JHD")){
				PurchaseController pController = new PurchaseController() ;
				PurchaseReceiptPO thePO = (PurchaseReceiptPO)result.get(currentRow) ;
				PurchaseReceiptVO theVO = pController.toVO(thePO) ;
				SalesmanFrameHelper saleHelper = new SalesmanFrameHelper();
				saleHelper.new AddPurchaseReceiptFrame(1, theVO, user) ;
			}
			if(type.equals("JHTHD")){
				PurchaseController pController = new PurchaseController() ;
				PurchaseReceiptPO thePO = (PurchaseReceiptPO)result.get(currentRow) ;
				PurchaseReceiptVO theVO = pController.toVO(thePO) ;
				SalesmanFrameHelper saleHelper = new SalesmanFrameHelper();
				saleHelper.new AddPurchaseReceiptFrame(-1, theVO, user) ;
			}
		}
}
    class SaleConditionPanel extends JPanel{
    	private JTextField beginTimeField;
    	private JTextField endTimeField;
    	private JTextField sumOfComeInField;//总收入
    	private JTextField discountField;//总折让
    	private JTextField saleComeInField;//销售收入
    	private JTextField overFlowField;//报溢
    	private JTextField changeCostField;//成本调价
    	private JTextField diffOfInAndOutField;//进退货差价
    	private JTextField voucherField;//代金券与实际金额差价
    	private JTextField sumOfCostField;//总支出
    	private JTextField saleCostField;//销售成本
    	private JTextField breakageCostField;//报损支出
    	private JTextField sendCostField;//商品赠送
    	private JTextField profitField;//总利润

    	/**
    	 * Create the panel.
    	 */
    	public SaleConditionPanel() {
    		setLayout(null);
    		setBounds(80,74, 700,400);
    		
    		JLabel timesLabel = new JLabel("时间区间：");
    		timesLabel.setBounds(44, 21, 68, 15);
    		add(timesLabel);
    		
    		JLabel beginTiemLabel = new JLabel("起始时间");
    		beginTiemLabel.setBounds(122, 21, 68, 15);
    		add(beginTiemLabel);
    		
    		beginTimeField = new JTextField();
    		beginTimeField.setBounds(200, 18, 110, 21);
    		add(beginTimeField);
    		beginTimeField.setColumns(10);
    		beginTimeField.setText("<例如2014/10/10>");
    		new AddWordsChange(beginTimeField, "<例如2014/10/10>") ;
    		
    		JLabel endTimeLabel = new JLabel("截止时间");
    		endTimeLabel.setBounds(339, 21, 68, 15);
    		add(endTimeLabel);
    		
    		endTimeField = new JTextField();
    		endTimeField.setBounds(417, 18, 110, 21);
    		add(endTimeField);
    		endTimeField.setColumns(10);
    		endTimeField.setText("<例如2014/11/10>");
    		new AddWordsChange(endTimeField, "<例如2014/11/10>") ;
    		
    		
    		
    		JPanel comeInPanel = new JPanel();
    		comeInPanel.setBounds(103, 61, 538, 126);
    		comeInPanel.setBackground(Color.red);
    		add(comeInPanel);
    		comeInPanel.setLayout(null);
    		
    		JLabel sumOfComeInLabel = new JLabel("总收入");
    		sumOfComeInLabel.setBounds(10, 21, 54, 15);
    		comeInPanel.add(sumOfComeInLabel);
    		
    		sumOfComeInField = new JTextField();
    		sumOfComeInField.setBounds(76, 18, 66, 21);
    		comeInPanel.add(sumOfComeInField);
    		sumOfComeInField.setColumns(10);
    		
    		JLabel discountLabel = new JLabel("折让");
    		discountLabel.setBounds(167, 21, 54, 15);
    		comeInPanel.add(discountLabel);
    		
    		discountField = new JTextField();
    		discountField.setBounds(212, 18, 66, 21);
    		comeInPanel.add(discountField);
    		discountField.setColumns(10);
    		
    		JLabel saleComeInLabel = new JLabel("销售收入");
    		saleComeInLabel.setBounds(23, 60, 54, 15);
    		comeInPanel.add(saleComeInLabel);
    		
    		saleComeInField = new JTextField();
    		saleComeInField.setBounds(10, 95, 66, 21);
    		comeInPanel.add(saleComeInField);
    		saleComeInField.setColumns(10);
    		
    		JLabel overFlowLabel = new JLabel("商品报溢");
    		overFlowLabel.setBounds(92, 60, 72, 15);
    		comeInPanel.add(overFlowLabel);
    		
    		overFlowField = new JTextField();
    		overFlowField.setBounds(92, 95, 66, 21);
    		comeInPanel.add(overFlowField);
    		overFlowField.setColumns(10);
    		
    		JLabel changCostLabel = new JLabel("成本调价");
    		changCostLabel.setBounds(197, 60, 72, 15);
    		comeInPanel.add(changCostLabel);
    		
    		changeCostField = new JTextField();
    		changeCostField.setBounds(197, 95, 66, 21);
    		comeInPanel.add(changeCostField);
    		changeCostField.setColumns(10);
    	
    		
    		JLabel diffOfInAndOutLabel = new JLabel("进货退货差价");
    		diffOfInAndOutLabel.setBounds(293, 60, 96, 15);
    		comeInPanel.add(diffOfInAndOutLabel);
    		
    		diffOfInAndOutField = new JTextField();
    		diffOfInAndOutField.setBounds(304, 95, 66, 21);
    		comeInPanel.add(diffOfInAndOutField);
    		diffOfInAndOutField.setColumns(10);
    		
    		JLabel voucherLabel = new JLabel("代金券与实际收款差额");
    		voucherLabel.setBounds(399, 60, 140, 15);
    		comeInPanel.add(voucherLabel);
    		
    		voucherField = new JTextField();
    		voucherField.setBounds(425, 95, 66, 21);
    		comeInPanel.add(voucherField);
    		voucherField.setColumns(10);
    		
    		JPanel sumOfCostPanel = new JPanel();
    		sumOfCostPanel.setBounds(103, 207, 538, 101);
    		sumOfCostPanel.setBackground(Color.yellow);
    		add(sumOfCostPanel);
    		sumOfCostPanel.setLayout(null);
    		
    		JLabel sumOfCostLabel = new JLabel("总支出");
    		sumOfCostLabel.setBounds(10, 18, 54, 15);
    		sumOfCostPanel.add(sumOfCostLabel);

    		sumOfCostField = new JTextField();
    		sumOfCostField.setBounds(77, 15, 66, 21);
    		sumOfCostPanel.add(sumOfCostField);
    		sumOfCostField.setColumns(10);
    		
    		JLabel saleCostLabel = new JLabel("销售支出");
    		saleCostLabel.setBounds(87, 46, 54, 15);
    		sumOfCostPanel.add(saleCostLabel);
    		
    		saleCostField = new JTextField();
    		saleCostField.setBounds(77, 70, 66, 21);
    		sumOfCostPanel.add(saleCostField);
    		saleCostField.setColumns(10);
    		
    		JLabel breakageCostLabel = new JLabel("商品报损");
    		breakageCostLabel.setBounds(212, 46, 54, 15);
    		sumOfCostPanel.add(breakageCostLabel);
    		
    		breakageCostField = new JTextField();
    		breakageCostField.setBounds(200, 70, 66, 21);
    		sumOfCostPanel.add(breakageCostField);
    		breakageCostField.setColumns(10);
    		
    		JLabel sendCostLabel = new JLabel("商品赠出");
    		sendCostLabel.setBounds(340, 46, 54, 15);
    		sumOfCostPanel.add(sendCostLabel);
    		
    		sendCostField = new JTextField();
    		sendCostField.setBounds(328, 70, 66, 21);
    		sumOfCostPanel.add(sendCostField);
    		sendCostField.setColumns(10);
    		
    		JPanel profitPanel = new JPanel();
    		profitPanel.setBounds(103, 326, 538, 35);
    		profitPanel.setBackground(Color.green);
    		add(profitPanel);
    		profitPanel.setLayout(null);
    		
    		JLabel profitLabel = new JLabel("利润");
    		profitLabel.setBounds(10, 10, 54, 15);
    		profitPanel.add(profitLabel);
    		
    		profitField = new JTextField();
    		profitField.setBounds(74, 7, 66, 21);
    		profitPanel.add(profitField);
    		profitField.setColumns(10);
    		
    		JLabel typeOfComeInLabel = new JLabel("收入类");
    		typeOfComeInLabel.setBounds(24, 109, 54, 15);
    		add(typeOfComeInLabel);
    		
    		JLabel typeOfCostLabel = new JLabel("支出类");
    		typeOfCostLabel.setBounds(24, 254, 54, 15);
    		add(typeOfCostLabel);
    		
    		JLabel typeOfProfitLabel = new JLabel("利润");
    		typeOfProfitLabel.setBounds(24, 336, 54, 15);
    		add(typeOfProfitLabel);
    		
    		JLabel sureLabel = new JLabel("查询",JLabel.CENTER) ;
    		sureLabel.setBounds(535, 21, 68, 15);
    		add(sureLabel) ;
    		sureLabel.addMouseListener(new MouseAdapter() {
    			public void mouseClicked(MouseEvent e){
    				String beginTime = beginTimeField.getText() ;
    				String endTime = endTimeField.getText() ;
    				if(beginTime.equals("<例如2014/10/10>")||endTime.equals("<例如2014/10/10>")){
    					new warningDialog("请输入时间区间");
    				}else{
    					double[] result = infoController.showSalesConditionInfo(beginTime, endTime) ;
    					double inCome = result[1] +result[2] +result[3] +result[4] +result[5] ;
    					double getOut = result[6] +result[7] +result[8] ;
    					double profit = inCome - getOut ;
    					sumOfComeInField.setText(String.valueOf(inCome)); 
    					discountField.setText(String.valueOf(result[0]));
    					saleComeInField.setText(String.valueOf(result[1]));
    					overFlowField.setText(String.valueOf(result[2]));
    					changeCostField.setText(String.valueOf(result[3]));
    					diffOfInAndOutField.setText(String.valueOf(result[4]));
    					voucherField.setText(String.valueOf(result[5])) ;
    					sumOfCostField.setText(String.valueOf(getOut));
    					saleCostField.setText(String.valueOf(result[6]));
    					breakageCostField.setText(String.valueOf(result[7]));
    					sendCostField.setText(String.valueOf(result[8]));
    					profitField.setText(String.valueOf(profit));
    				}
    			}
			});
    		JLabel cancle = new JLabel("撤销");
    		cancle.setBounds(610, 21, 68, 15);
    		add(cancle) ;
    		cancle.addMouseListener(new MouseAdapter() {
    			public void mouseClicked(MouseEvent e){
    				beginTimeField.setText("<例如2014/10/10>");
    				endTimeField.setText("<例如2014/11/10>");
    				sumOfComeInField.setText("");
    				discountField.setText("");
    				saleComeInField.setText("");
    				overFlowField.setText("");
    				changeCostField.setText("");
    				diffOfInAndOutField.setText("");
    				voucherField.setText("");
    				sumOfCostField.setText("");
    				saleCostField.setText("");
    				breakageCostField.setText("");
    				sendCostField.setText("");
    				profitField.setText("");
    			}
			});
    	}
}

	class MyTableModel extends AbstractTableModel{
		private ArrayList<ArrayList<Object>> datas = new ArrayList<ArrayList<Object>>();
		private String[] columnOfReceipt ;
		 

		public MyTableModel(ArrayList<Object> theDatas ,String[] theColumn,String type){
			if(type.equals("XSD")){//销售单
			for(Object object : theDatas){
				SalesReceiptPO receipt = (SalesReceiptPO)object ;
				ArrayList<Object> oneRow = new ArrayList<Object>() ;
				oneRow.add(receipt.getSerialNumber()) ;
				oneRow.add(receipt.getCustomerPO().getName()) ;
				oneRow.add(receipt.getSalesman()) ;
				oneRow.add(receipt.getUserPO().getUserName()) ;
				oneRow.add(receipt.getCommodityNum()) ;
				oneRow.add("展开");
				oneRow.add(receipt.getPriceBefore()) ;
				oneRow.add(receipt.getDiscout()) ;
				oneRow.add(String.valueOf(receipt.getVocher())) ;
				oneRow.add(receipt.getFinalprice()) ;
				oneRow.add(receipt.getComment()) ;
				datas.add(oneRow) ;
			}
			}
			if(type.equals("XSTHD")){//销售退货单
				for(Object object : theDatas){
					SalesReceiptPO receipt = (SalesReceiptPO)object ;
					ArrayList<Object> oneRow = new ArrayList<Object>() ;
					oneRow.add(receipt.getSerialNumber()) ;
					oneRow.add(receipt.getCustomerPO().getName()) ;
					oneRow.add(receipt.getSalesman()) ;
					oneRow.add(receipt.getUserPO().getUserName()) ;
					oneRow.add(receipt.getCommodityNum()) ;
					oneRow.add("展开");
					oneRow.add(String.valueOf(receipt.getPriceBefore())) ;
					oneRow.add(String.valueOf(receipt.getDiscout())) ;
					oneRow.add(String.valueOf(receipt.getVocher())) ;
					oneRow.add(String.valueOf(receipt.getFinalprice())) ;
					oneRow.add(receipt.getComment()) ;
					datas.add(oneRow) ;                                          
				}
			}
			
			if(type.equals("商品清单")||type.equals("出货商品清单")){//商品清单
				for(Object object : theDatas){
					ArrayList<Object> oneRow = new ArrayList<Object>() ;
					SalesListItemPO item = (SalesListItemPO)object ;
					oneRow.add(item.getGoodsPO().getSerialNumber()) ;
					oneRow.add(item.getGoodsPO().getName()) ;
					oneRow.add(item.getGoodsPO().getModel()) ;
					oneRow.add(item.getQuantity());
					oneRow.add(item.getGoodsPO().getSalePrice()) ;
					oneRow.add(item.getTotalPrice()) ;
					oneRow.add(item.getGoodsPO().getComment()) ;
					datas.add(oneRow) ;
				}
			}
			
			if(type.equals("SKD")||type.equals("FKD")){//收付款单
				for(Object object: theDatas){
					CollectionOrPaymentVO receipt = (CollectionOrPaymentVO)object;
					ArrayList<Object> oneRow = new ArrayList<Object>() ;
					oneRow.add(receipt.getNumber());
					oneRow.add(receipt.getCustomer()) ;
					oneRow.add(receipt.getUser()) ;
					oneRow.add("展开");
					oneRow.add(receipt.getTotal()) ;
					datas.add(oneRow);
				}
			}
			if(type.equals("转账列表")){//收付款单中的转账列表
				for(Object object : theDatas){
					TransferListItemVO theItem = (TransferListItemVO) object ;
					ArrayList<Object> oneRow = new ArrayList<Object>() ;
					oneRow.add(theItem.getAccount()) ;
					oneRow.add(theItem.getTransferMoney()) ;
					oneRow.add(theItem.getRemark()) ;
					datas.add(oneRow) ;
				}
			}
			if(type.equals("XJFYD")){//现金费用单
				for(Object object : theDatas){
					CashVO receipt = (CashVO) object ;
					ArrayList<Object> oneRow = new ArrayList<Object>() ;
					oneRow.add(receipt.getNumber()) ;
					oneRow.add(receipt.getUser()) ;
					oneRow.add(receipt.getAccount()) ;
					oneRow.add("展开") ;
					oneRow.add(receipt.getSum()) ;
					datas.add(oneRow) ;
 				}
			}
			if(type.equals("条目清单")){//销售单中的条目清单
				for(Object object : theDatas){
					CaseListItemVO theCase = (CaseListItemVO)object ;
					ArrayList<Object> oneRow = new ArrayList<Object>() ;
					oneRow.add(theCase.getCasename()) ;
					oneRow.add(theCase.getCaseMoney()) ;
					oneRow.add(theCase.getRemark()) ;
					datas.add(oneRow) ;
				}
			}
			if(type.equals("JHD")){//进货单
				for(Object object : theDatas){
					PurchaseReceiptPO receipt = (PurchaseReceiptPO) object ;
					ArrayList<Object> oneRow = new ArrayList<Object>() ;
					oneRow.add(receipt.getSerialNumber()) ;
					oneRow.add(receipt.getCustomerPO().getName()) ;
					oneRow.add("仓库一") ;
					oneRow.add(receipt.getUserPO().getUserName()) ;
					oneRow.add("展开") ;
					oneRow.add(receipt.getComments()) ;
					oneRow.add(receipt.getTotalPrice()) ;
					datas.add(oneRow) ;
				}
			}
			if(type.equals("入库商品列表")){//进货单中的入库商品列表
				for(Object object : theDatas){
					PurchaseListItemPO thePO  = (PurchaseListItemPO) object ;
					ArrayList<Object> oneRow = new ArrayList<Object>() ;
					oneRow.add(thePO.getGoodsPO().getSerialNumber()) ;
					oneRow.add(thePO.getGoodsPO().getName()) ;
					oneRow.add(thePO.getGoodsPO().getModel()) ;
					oneRow.add(thePO.getQuantity()) ;
					oneRow.add(thePO.getGoodsPO().getPrice()) ;
					oneRow.add(thePO.getTotalPrice()) ;
					oneRow.add(thePO.getGoodsPO().getComment()) ;
					datas.add(oneRow) ;
				}
			}
			if(type.equals("JHTHD")){//进货退货单
				for(Object object : theDatas){
					PurchaseReceiptPO receipt = (PurchaseReceiptPO) object ;
					ArrayList<Object> oneRow = new ArrayList<Object>() ;
					oneRow.add(receipt.getSerialNumber()) ;
					oneRow.add(receipt.getCustomerPO().getName()) ;
					oneRow.add("仓库一") ;
					oneRow.add(receipt.getUserPO().getUserName()) ;
					oneRow.add("展开") ;
					oneRow.add(receipt.getComments()) ;
					oneRow.add(receipt.getTotalPrice()) ;
					datas.add(oneRow) ;
				}
			}
			if(type.equals("出库商品列表")){//进货退货单中的出库商品列表
				for(Object object : theDatas){
					PurchaseListItemPO thePO  = (PurchaseListItemPO) object ;
					ArrayList<Object> oneRow = new ArrayList<Object>() ;
					oneRow.add(thePO.getGoodsPO().getSerialNumber()) ;
					oneRow.add(thePO.getGoodsPO().getName()) ;
					oneRow.add(thePO.getGoodsPO().getModel()) ;
					oneRow.add(thePO.getGoodsPO().getPrice()) ;
					oneRow.add(thePO.getTotalPrice()) ;
					oneRow.add(thePO.getGoodsPO().getComment()) ;
					datas.add(oneRow) ;
				}
			}
			if(type.equals("BYD") || type.equals("BSD")){//报溢单和报损单
				for(Object object:theDatas){
					ReportCommodityVO theVO = (ReportCommodityVO) object ;
					ArrayList<Object> oneRow = new ArrayList<Object>() ;
					oneRow.add(theVO.goodsVOId) ;
					oneRow.add(theVO.num) ;
					oneRow.add(theVO.price) ;
					oneRow.add(new String(new SimpleDateFormat("yyyy/MM/dd").format(theVO.date))) ;
					datas.add(oneRow) ;
				}
			}
			if(type.equals("ZSD")){
				for(Object object :theDatas){
					SendCommodityVO theVO = (SendCommodityVO) object ;
					ArrayList<Object> oneRow = new ArrayList<Object>() ;
					oneRow.add(theVO.goodsVOId) ;
					oneRow.add(theVO.customerVOName) ;
					oneRow.add(theVO.num) ;
					oneRow.add(theVO.price) ;
					oneRow.add(new String(new SimpleDateFormat("yyyy/MM/dd").format(theVO.date))) ;
					datas.add(oneRow) ;
				}
			}
			columnOfReceipt = theColumn ;
		}

	    public MyTableModel(ArrayList<CollectionOrPaymentVO> receipts ,String[] column,boolean isPass) {
			// TODO Auto-generated constructor stub
	    	for(CollectionOrPaymentVO receipt : receipts){
				ArrayList<Object> oneRow = new ArrayList<Object>() ;
				oneRow.add(receipt.getNumber());
				oneRow.add(receipt.getCustomer()) ;
				oneRow.add(receipt.getUser()) ;
				oneRow.add("展开");
				oneRow.add(receipt.getTotal()) ;
				datas.add(oneRow);
			}
	    	columnOfReceipt = column ;
		}
		@Override
		public String getColumnName(int col)
	     {
	          return columnOfReceipt[col];
	     }
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return columnOfReceipt.length;
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return datas.size();
		}

		@Override
		public Object getValueAt(int row, int column) {
			// TODO Auto-generated method stub
			return datas.get(row).get(column);
		}
		public boolean isCellEditable(int row, int col) { 
				return false ;
		}
		public void setValueAt(Object value, int row, int col) {  
	        datas.get(row).set(col,  value);
	        fireTableCellUpdated(row, col);  
	    }	
	}
    class ShowListFrame extends JFrame{

    	private JPanel contentPane;
    	private JTable table;

    	/**
    	 * Create the frame.
    	 */
    	public ShowListFrame(ArrayList<Object> items,String[] column) {
    		this.setVisible(true);
    		this.setTitle("商品清单");
    		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    		setBounds(500, 200, 350, 300);
    		contentPane = new JPanel();
    		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    		contentPane.setBackground(Color.yellow);
    		setContentPane(contentPane);
    		contentPane.setLayout(null);
 
    		table = new JTable(new MyTableModel(items,column,"SPQD"));
    		table.setBackground(Color.white);
    		
    		JScrollPane jsc = new JScrollPane(table);
    		jsc.setBounds(0,0,350,170);
    		jsc.setBackground(Color.green);
    		contentPane.add(jsc) ;
    		
    		JButton sureButton = new JButton("确定");
    		sureButton.setBounds(110, 200, 100, 30);
    		contentPane.add(sureButton) ;
    		sureButton.addMouseListener(new MouseAdapter() {
    			public void mouseClicked(MouseEvent e){
    				dispose() ;
    			}
			});
    		
    	}
    	public ShowListFrame(ArrayList<Object> items,String[] column,String name){
    		this.setVisible(true);
    		this.setTitle(name);
    		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    		setBounds(500, 200, 350, 300);
    		contentPane = new JPanel();
    		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    		contentPane.setBackground(Color.yellow);
    		setContentPane(contentPane);
    		contentPane.setLayout(null);
 
    		table = new JTable(new MyTableModel(items,column,name));
    		table.setBackground(Color.white);
    		
    		JScrollPane jsc = new JScrollPane(table);
    		jsc.setBounds(0,0,350,170);
    		jsc.setBackground(Color.green);
    		contentPane.add(jsc) ;
    		
    		JButton sureButton = new JButton("确定");
    		sureButton.setBounds(110, 200, 100, 30);
    		contentPane.add(sureButton) ;
    		sureButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					dispose();
				}
			});
    		
    	}
}
    class warningDialog extends JDialog{
		public warningDialog(String warnings){
			this.setSize(284, 158);
			this.setLocationRelativeTo(null);
			this.setVisible(true);
			this.setModal(true);
			
			JLabel warningLabel = new JLabel(warnings,JLabel.CENTER);
			warningLabel.setFont(new Font("宋体",Font.BOLD,14));
			
			this.add(warningLabel);
		}
	}
	
    
    
    public static void main(String[] marg){
		new FinanceFrame(new UserVO("shengyu", "123", UserSort.Commodity, 1)) ;
	}
}