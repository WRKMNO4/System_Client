package presentation;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Config.Level;
import Config.PromotionSort;
import Config.Sort;
import Config.UserSort;
import PO.CustomerPO;
import PO.PurchaseLogPO;
import PO.PurchaseReceiptPO;
import PO.SalesLogPO;
import PO.SalesReceiptPO;
import ResultMessage.ResultMessage;
import VO.CustomerVO;
import VO.GoodsVO;
import VO.PromotionVO;
import VO.PurchaseListItemVO;
import VO.PurchaseReceiptVO;
import VO.SalesListItemVO;
import VO.SalesReceiptVO;
import VO.UserVO;
import businesslogicservice.CustomerBLService.CustomerController;
import businesslogicservice.GoodsBLService.GoodsController;
import businesslogicservice.LogBLService.PurchaseLogController;
import businesslogicservice.LogBLService.SalesLogController;
import businesslogicservice.PromotionBLService.PromotionController;
import businesslogicservice.PurchseBLService.PurchaseController;
import businesslogicservice.SaleBLService.SalesController;

public class SalesmanFrame extends JFrame {

	public UserVO userVO;
	private JLabel exitButton, customerLabel, salesLabel, purchaseLabel;
	
	GoodsController goodsController=new GoodsController();
	//表格所需要的数据
	Vector customerTableData = new Vector();
	Vector salesLogTableData=new Vector();
	Vector purchaseLogTableData=new Vector();
	
	JTable customerTable=new JTable();
	JTable purchaseLogTable=new JTable();
	JTable salesLogTable=new JTable();
	
	private CustomerPanel customerPanel = new CustomerPanel(this);
	private SalesPanel salesPanel = new SalesPanel(this);
	private PurchasePanel purchasePanel = new PurchasePanel(this);
	


	// 参数可以用于获取当前操作员信息
	public SalesmanFrame(UserVO uservo) { // 总Frame
		this.setSize(1000, 600);
		this.setLocationRelativeTo(null);
		this.setTitle("welcome");
		getContentPane().setLayout(null);
		this.setUndecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(new UserPanel(uservo));

		this.userVO = uservo;

		exitButton = new JLabel("X", JLabel.CENTER);
		exitButton.setBounds(950, 0, 50, 50);
		exitButton.setFont(new Font("default", 1, 20));
		exitButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});

		customerLabel = new JLabel("客户管理", JLabel.CENTER);
		customerLabel.setBackground(new Color(147, 224, 255, 255));
		customerLabel.setBounds(40, 100, 100, 50);
		customerLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				updateCustomerPanelTable();
				customerPanel.setVisible(true);
				salesPanel.setVisible(false);
				purchasePanel.setVisible(false);
			}
		});

		salesLabel = new JLabel("销售管理", JLabel.CENTER);
		salesLabel.setBackground(new Color(185, 227, 217, 255));
		salesLabel.setBounds(40, 160, 100, 50);
		salesLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				customerPanel.setVisible(false);
				salesPanel.setVisible(true);
				purchasePanel.setVisible(false);
			}
		});

		purchaseLabel = new JLabel("进货管理", JLabel.CENTER);
		purchaseLabel.setBounds(40, 220, 100, 50);
		purchaseLabel.setBackground(new Color(173, 137, 115, 255));
		purchaseLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				customerPanel.setVisible(false);
				salesPanel.setVisible(false);
				purchasePanel.setVisible(true);
			}
		});

		getContentPane().add(exitButton);
		getContentPane().add(customerLabel);
		getContentPane().add(salesLabel);
		getContentPane().add(purchaseLabel);

		MoveOfFrame m = new MoveOfFrame(this);
		this.setVisible(true);
	}

	class CustomerPanel extends JPanel {
		// private JTable goodsTable;
		public CustomerPanel(JFrame theFrame) {
			this.setLayout(null);
			this.setBounds(140, 25, 835, 550);
			this.setBackground(new Color(147, 224, 255, 255));

			// 初始化表格
//			JTable customerTabel = new JTable();// 客户列表
			DefaultTableModel model = new DefaultTableModel();// 表格模型
			Vector tableColName = new Vector();
			tableColName.add("编号");
			tableColName.add("分类");
			tableColName.add("级别");
			tableColName.add("姓名");
			tableColName.add("电话");
			tableColName.add("地址");
			tableColName.add("邮编");
			tableColName.add("电子邮箱");
			tableColName.add("业务员");
			tableColName.add("应收");
			tableColName.add("应付");
			tableColName.add("应收额度");


			ArrayList<CustomerPO> customers = new CustomerController().show();
//			System.out.println(customers);

			for (Iterator iterator = customers.iterator(); iterator.hasNext();) {
				CustomerPO customerPO = (CustomerPO) iterator.next();
				Vector tableRows = new Vector();
				tableRows.add(customerPO.getNumber());
				tableRows.add(customerPO.getSort());
				tableRows.add(customerPO.getLevel());
				tableRows.add(customerPO.getName());
				tableRows.add(customerPO.getPhone());
				tableRows.add(customerPO.getAddress());
				tableRows.add(customerPO.getZipCode());
				tableRows.add(customerPO.getMail());
				tableRows.add(customerPO.getClerk());
				tableRows.add(customerPO.getGetting());
				tableRows.add(customerPO.getPay());
				tableRows.add(customerPO.getDebt_upper_limit());

				customerTableData.add(tableRows);
				
			}

			customerTable.setModel(model);
			model.setDataVector(customerTableData, tableColName);
			customerTable.setFillsViewportHeight(true); // 显示表头

			DefaultTableCellRenderer render = new DefaultTableCellRenderer(); // 设置单元格内容居中
			render.setHorizontalAlignment(SwingConstants.CENTER);
			customerTable.setDefaultRenderer(Object.class, render);

			JScrollPane tablePane1 = new JScrollPane(customerTable);
			tablePane1.setSize(630, 400);
			tablePane1.setLocation(80, 74);
			this.add(tablePane1);

			JButton addButton1 = new JButton("增加客户");
			addButton1.setBounds(725, 150, 100, 30);
			this.add(addButton1);
			JButton deleteButton1 = new JButton("删除客户");
			deleteButton1.setBounds(725, 185, 100, 30);
			this.add(deleteButton1);
			JButton updateButton1 = new JButton("修改客户");
			updateButton1.setBounds(725, 220, 100, 30);
			this.add(updateButton1);

			JTextField serchField = new JTextField();
			serchField.setBounds(317, 30, 200, 20);
			this.add(serchField);
			JButton serchButton = new JButton("搜索");
			serchButton.setBounds(520, 30, 40, 20);
			this.add(serchButton);

			theFrame.getContentPane().add(this);

			addButton1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new SalesmanFrameHelper("addCustomer", userVO);
				}

			});

			deleteButton1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new SalesmanFrameHelper("deleteCustomer", userVO);

				}
			});

			updateButton1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new SalesmanFrameHelper("updateCustomer", userVO);

				}
			});

			serchButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (serchField.getText().equals("")) {

					} else {
						findCustomer(serchField.getText());
					}

				}
			});
		}
	}

	class SalesPanel extends JPanel { // 查看报表信息panel
		// private JTable goodsTable;
		public SalesPanel(JFrame theFrame) {
			this.setLayout(null);
			this.setBounds(140, 25, 835, 550);
			this.setBackground(new Color(185, 227, 217, 255));
			// 操作日志

			DefaultTableModel model = new DefaultTableModel();// 表格模型
			Vector tableColName = new Vector();
			tableColName.add("操作");
			tableColName.add("操作人员");
			tableColName.add("日期");
						salesLogTable.setFillsViewportHeight(true); // 显示表头

			updateSalesLogTable();
			salesLogTable.setModel(model);
			model.setDataVector(salesLogTableData, tableColName);
			DefaultTableCellRenderer render = new DefaultTableCellRenderer(); // 设置单元格内容居中
			render.setHorizontalAlignment(SwingConstants.LEFT);
			salesLogTable.setDefaultRenderer(Object.class, render);

			JScrollPane tablePane1 = new JScrollPane(salesLogTable);
			tablePane1.setSize(630, 400);
			tablePane1.setLocation(80, 74);
			this.add(tablePane1);

			JButton creatInButton1 = new JButton("创建销售单");
			creatInButton1.setBounds(725, 150, 100, 30);
			this.add(creatInButton1);
			JButton creatOutButton1 = new JButton("创建销售退货单");
			creatOutButton1.setBounds(715, 185, 120, 30);
			this.add(creatOutButton1);

			theFrame.getContentPane().add(this);

			creatInButton1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new SalesmanFrameHelper("addSalesReceipt", userVO);

				}
			});

			creatOutButton1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new SalesmanFrameHelper("addSalesBackReceipt", userVO);

				}
			});
		}
	}

	class PurchasePanel extends JPanel { // 指定促销策略panel
		// private JTable goodsTable;
		public PurchasePanel(JFrame theFrame) {
			this.setLayout(null);
			this.setBounds(140, 25, 835, 550);
			this.setBackground(new Color(100,100,100));

			DefaultTableModel model = new DefaultTableModel();// 表格模型
			Vector tableColName = new Vector();
			tableColName.add("操作");
			tableColName.add("操作人员");
			tableColName.add("日期");
						purchaseLogTable.setFillsViewportHeight(true); // 显示表头

			purchaseLogTable.setModel(model);
			//更新日志内容
			updatePurchaseLogTable();
			
			
			model.setDataVector(purchaseLogTableData, tableColName);
			DefaultTableCellRenderer render = new DefaultTableCellRenderer(); // 设置单元格内容居中
			render.setHorizontalAlignment(SwingConstants.LEFT);
			purchaseLogTable.setDefaultRenderer(Object.class, render);

			JScrollPane tablePane1 = new JScrollPane(purchaseLogTable);
			tablePane1.setSize(630, 400);
			tablePane1.setLocation(80, 74);
			this.add(tablePane1);

			JButton creatInButton1 = new JButton("创建进货单");
			creatInButton1.setBounds(725, 150, 100, 30);
			this.add(creatInButton1);

			JButton creatOutButton1 = new JButton("创建进货退货单");
			creatOutButton1.setBounds(715, 185, 120, 30);
			this.add(creatOutButton1);

			theFrame.getContentPane().add(this);

			creatInButton1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new SalesmanFrameHelper("addPurchaseReceipt", userVO);

				}
			});
			
			creatOutButton1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					new SalesmanFrameHelper("addPurchaseBackReceipt", userVO);

				}
			});

		}
	}

	class MyTableModel extends AbstractTableModel { // 表格模型
		private Object[][] tableData;
		private String[] columnTitle;

		public MyTableModel(Object[][] data, String[] title) {
			tableData = data;
			columnTitle = title;
		}

		public String getColumnName(int col) {
			return columnTitle[col];
		}

		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return tableData.length;
		}

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub

			return columnTitle.length;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			// TODO Auto-generated method stub
			return tableData[rowIndex][columnIndex];
		}

		public Class getColumnClass(int c) {
			return getValueAt(0, c).getClass();
		}

		public boolean isCellEditable(int row, int col) {
			return true;
		}

		public void setValueAt(Object value, int row, int col) {
			tableData[row][col] = value;
			fireTableCellUpdated(row, col);
		}

	}

	class TransferFrame extends JFrame {

	}
	
	public class SalesmanFrameHelper {
		public static final int TYPE_PURCHASE = 1;
		public static final int TYPE_PURCHASE_BACK = -1;

		public static final int TYPE_SALES = 2;
		public static final int TYPE_SALES_BACK = -2;

		public static final int ADD_CUSTOMER = 11;
		public static final int DELETE_CUSTOMER = 12;
		public static final int UPDATE_CUSTOMER = 13;

		public static final int ADD_PURCHASERECEIPT = 21;
		public static final int ADD_PURCHASERECEIPT_BACK = 22;

		public static final int ADD_SALESRECEIPT = 31;
		public static final int ADD_SALESRECEIPT_BACK = 33;

		public ArrayList<GoodsVO> goodsList = new ArrayList<GoodsVO>();

		public UserVO userVO;

		public String yearNow = this.getYearNow()+"";
		public String monthNow = this.getMonthNow()+"";
		public String dayNow = this.getDateNow()+"";
		
		

		public SalesmanFrameHelper(String command, UserVO vo) {
			
			if (dayNow.length() < 2) {
				dayNow = "0" + dayNow;
			}
			if (monthNow.length() < 2) {
				monthNow = "0" + monthNow;
			}
			
			this.userVO = vo;

			switch (command) {
			case "addCustomer":
				new AddCustomerFrame();
				break;

			case "deleteCustomer":
				new DeleteCustomerFrame();
				break;

			case "updateCustomer":
				new UpdateCustomerFrame();
				break;
			case "addPurchaseReceipt":
				new AddPurchaseReceiptFrame(1);
				break;
			case "addPurchaseBackReceipt":
				new AddPurchaseReceiptFrame(-1);
				break;
			case "addSalesReceipt":
				new AddSalesReceiptFrame(2);
				break;
			case "addSalesBackReceipt":
				new AddSalesReceiptFrame(-2);
				break;
			}

		}

		class AddCustomerFrame extends JFrame {
			private JButton confirmButton;
			private JButton cancelButton;

			private JLabel serialnumLabel;
			private JTextField serialnum;
			private JLabel classLabel;
			private JComboBox classes;
			private JLabel levelLabel;
			private JComboBox level;
			private JLabel nameLabel;
			private JTextField name;
			private JLabel phoneLabel;
			private JTextField phone;
			private JLabel adressLabel;
			private JTextField adress;
			private JLabel zipcodeLabel;
			private JTextField zipcode;
			private JLabel emailLabel;
			private JTextField email;
			private JLabel clerkLabel;
			private JTextField clerk;

			private JLabel payLabel, gettingLabel, degreeLabel;
			private JLabel pay, getting, degree;

			public AddCustomerFrame() {
				this.setTitle("增加客户");
				this.setVisible(true);
				setBounds(100, 100, 556, 475);
				this.setLocationRelativeTo(null);
				getContentPane().setLayout(null);

				payLabel = new JLabel("应付：");
				payLabel.setBounds(400, 40, 100, 20);
				getContentPane().add(payLabel);

				pay = new JLabel("0", JLabel.CENTER);
				pay.setBounds(450, 40, 100, 20);
				getContentPane().add(pay);

				gettingLabel = new JLabel("应收：");
				gettingLabel.setBounds(400, 80, 100, 20);
				getContentPane().add(gettingLabel);

				getting = new JLabel("0", JLabel.CENTER);
				getting.setBounds(450, 80, 100, 20);
				getContentPane().add(getting);

				degreeLabel = new JLabel("应收额度：");
				degreeLabel.setBounds(400, 120, 100, 20);
				getContentPane().add(degreeLabel);

				degree = new JLabel("0", JLabel.CENTER);
				degree.setBounds(450, 120, 100, 20);
				getContentPane().add(degree);

				serialnumLabel = new JLabel("客户编号");
				serialnumLabel.setBounds(140, 40, 100, 20);
				serialnumLabel.setBackground(Color.BLACK);
				getContentPane().add(serialnumLabel);

				classLabel = new JLabel("分类");
				classLabel.setBounds(140, 80, 100, 20);
				getContentPane().add(classLabel);

				levelLabel = new JLabel("级别");
				levelLabel.setBounds(140, 120, 100, 20);
				getContentPane().add(levelLabel);

				nameLabel = new JLabel("姓名");
				nameLabel.setBounds(140, 160, 100, 20);
				getContentPane().add(nameLabel);

				phoneLabel = new JLabel("电话");
				phoneLabel.setBounds(140, 200, 100, 20);
				getContentPane().add(phoneLabel);

				adressLabel = new JLabel("地址");
				adressLabel.setBounds(140, 240, 100, 20);
				getContentPane().add(adressLabel);

				zipcodeLabel = new JLabel("邮编");
				zipcodeLabel.setBounds(140, 280, 100, 20);
				getContentPane().add(zipcodeLabel);

				emailLabel = new JLabel("电子邮箱");
				emailLabel.setBounds(140, 320, 100, 20);
				getContentPane().add(emailLabel);

				clerkLabel = new JLabel("业务员");
				clerkLabel.setBounds(140, 360, 100, 20);
				getContentPane().add(clerkLabel);

				confirmButton = new JButton("确认");
				confirmButton.setBounds(147, 394, 88, 30);
				getContentPane().add(confirmButton);

				cancelButton = new JButton("取消");
				cancelButton.setBounds(296, 394, 88, 30);
				getContentPane().add(cancelButton);

				serialnum = new JTextField();
				serialnum.setBounds(240, 40, 100, 20);
				serialnum.setColumns(10);
				getContentPane().add(serialnum);

				classes = new JComboBox(new String[] { "进货商", "销售商" });
				classes.setBounds(240, 80, 100, 21);
				getContentPane().add(classes);

				level = new JComboBox(new String[] { "一级", "二级", "三级", "四级",
						"五级VIP" });
				level.setBounds(240, 120, 100, 21);
				getContentPane().add(level);

				name = new JTextField();
				name.setBounds(240, 160, 100, 20);
				name.setColumns(10);
				getContentPane().add(name);

				phone = new JTextField();
				phone.setBounds(240, 200, 150, 20);
				phone.setColumns(10);
				getContentPane().add(phone);

				adress = new JTextField();
				adress.setBounds(240, 240, 150, 20);
				adress.setColumns(10);
				getContentPane().add(adress);

				zipcode = new JTextField();
				zipcode.setBounds(240, 280, 100, 20);
				zipcode.setColumns(10);
				getContentPane().add(zipcode);

				email = new JTextField();
				email.setBounds(240, 320, 150, 20);
				email.setColumns(10);
				getContentPane().add(email);

				clerk = new JTextField();
				clerk.setBounds(240, 360, 100, 20);
				clerk.setColumns(10);
				getContentPane().add(clerk);

				confirmButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						CustomerVO vo = new CustomerVO(serialnum.getText(),
								getCustomerSort(classes.getSelectedItem()),
								getCustomerLevel(level.getSelectedItem()), name
										.getText(), phone.getText(), adress
										.getText(), zipcode.getText(), email
										.getText(), clerk.getText());

						ResultMessage result = new CustomerController()
								.addCustomer(vo);

						if (result == ResultMessage.add_success) {
							updateCustomerPanelTable();
							dispose();
						} else {
							new warningDialog("已经存在该客户！");
						}
					}
				});

				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();

					}

				});

				this.repaint();
			}

		}

		class DeleteCustomerFrame extends JFrame {
			private JLabel serialNumberLabel;
			private JTextField serialNumber;
			private JButton confirmButton, cancelButton;

			public DeleteCustomerFrame() {
				this.setTitle("删除客户");
				this.setVisible(true);
				setBounds(100, 100, 250, 130);
				this.setLocationRelativeTo(null);
				getContentPane().setLayout(null);

				serialNumberLabel = new JLabel("客户编号");
				serialNumberLabel.setBounds(20, 20, 100, 20);
				getContentPane().add(serialNumberLabel);

				serialNumber = new JTextField();
				serialNumber.setBounds(120, 20, 100, 20);
				getContentPane().add(serialNumber);

				confirmButton = new JButton("确认");
				confirmButton.setBounds(40, 60, 80, 20);
				getContentPane().add(confirmButton);

				cancelButton = new JButton("取消");
				cancelButton.setBounds(130, 60, 80, 20);
				getContentPane().add(cancelButton);

				confirmButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						CustomerPO customerPO = new CustomerController()
								.getCustomerPOById(serialNumber.getText());
						System.out.println(customerPO);
						if (customerPO != null) {
							new CustomerController()
									.deleteCustomer(new CustomerController()
											.toVO(customerPO));
							updateCustomerPanelTable();
							
							dispose();
						} else {
							new warningDialog("查无此人！！");
						}
					}
				});

				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});

			}

		}

		class UpdateCustomerFrame extends JFrame {

			private JButton confirmButton;
			private JButton cancelButton;

			private JLabel serialnumLabel;
			private JTextField serialnum;
			private JLabel classLabel;
			private JComboBox classes;
			private JLabel levelLabel;
			private JComboBox level;
			private JLabel nameLabel;
			private JTextField name;
			private JLabel phoneLabel;
			private JTextField phone;
			private JLabel adressLabel;
			private JTextField adress;
			private JLabel zipcodeLabel;
			private JTextField zipcode;
			private JLabel emailLabel;
			private JTextField email;
			private JLabel clerkLabel;
			private JTextField clerk;

			private JLabel payLabel, gettingLabel, degreeLabel;
			private JLabel pay, getting, degree;

			public UpdateCustomerFrame() {
				this.setTitle("修改客户");
				this.setVisible(true);
				setBounds(100, 100, 556, 475);
				this.setLocationRelativeTo(null);
				getContentPane().setLayout(null);

				payLabel = new JLabel("应付：");
				payLabel.setBounds(400, 40, 100, 20);
				getContentPane().add(payLabel);

				pay = new JLabel("0", JLabel.CENTER);
				pay.setBounds(450, 40, 100, 20);
				getContentPane().add(pay);

				gettingLabel = new JLabel("应收：");
				gettingLabel.setBounds(400, 80, 100, 20);
				getContentPane().add(gettingLabel);

				getting = new JLabel("0", JLabel.CENTER);
				getting.setBounds(450, 80, 100, 20);
				getContentPane().add(getting);

				degreeLabel = new JLabel("应收额度：");
				degreeLabel.setBounds(400, 120, 100, 20);
				getContentPane().add(degreeLabel);

				degree = new JLabel("0", JLabel.CENTER);
				degree.setBounds(450, 120, 100, 20);
				getContentPane().add(degree);

				serialnumLabel = new JLabel("客户编号");
				serialnumLabel.setBounds(140, 40, 100, 20);
				serialnumLabel.setBackground(Color.BLACK);
				getContentPane().add(serialnumLabel);

				classLabel = new JLabel("分类");
				classLabel.setBounds(140, 80, 100, 20);
				getContentPane().add(classLabel);

				levelLabel = new JLabel("级别");
				levelLabel.setBounds(140, 120, 100, 20);
				getContentPane().add(levelLabel);

				nameLabel = new JLabel("姓名");
				nameLabel.setBounds(140, 160, 100, 20);
				getContentPane().add(nameLabel);

				phoneLabel = new JLabel("电话");
				phoneLabel.setBounds(140, 200, 100, 20);
				getContentPane().add(phoneLabel);

				adressLabel = new JLabel("地址");
				adressLabel.setBounds(140, 240, 100, 20);
				getContentPane().add(adressLabel);

				zipcodeLabel = new JLabel("邮编");
				zipcodeLabel.setBounds(140, 280, 100, 20);
				getContentPane().add(zipcodeLabel);

				emailLabel = new JLabel("电子邮箱");
				emailLabel.setBounds(140, 320, 100, 20);
				getContentPane().add(emailLabel);

				clerkLabel = new JLabel("业务员");
				clerkLabel.setBounds(140, 360, 100, 20);
				getContentPane().add(clerkLabel);

				confirmButton = new JButton("确认");
				confirmButton.setBounds(147, 394, 88, 30);
				getContentPane().add(confirmButton);

				cancelButton = new JButton("取消");
				cancelButton.setBounds(296, 394, 88, 30);
				getContentPane().add(cancelButton);

				serialnum = new JTextField("");
				serialnum.setBounds(240, 40, 100, 20);
				serialnum.setColumns(10);
				getContentPane().add(serialnum);

				classes = new JComboBox(new String[] { "进货商", "销售商" });
				classes.setBounds(240, 80, 100, 21);
				getContentPane().add(classes);

				level = new JComboBox(new String[] { "一级", "二级", "三级", "四级",
						"五级VIP" });
				level.setBounds(240, 120, 100, 21);
				getContentPane().add(level);

				name = new JTextField();
				name.setBounds(240, 160, 100, 20);
				name.setColumns(10);
				getContentPane().add(name);

				phone = new JTextField();
				phone.setBounds(240, 200, 150, 20);
				phone.setColumns(10);
				getContentPane().add(phone);

				adress = new JTextField();
				adress.setBounds(240, 240, 150, 20);
				adress.setColumns(10);
				getContentPane().add(adress);

				zipcode = new JTextField();
				zipcode.setBounds(240, 280, 100, 20);
				zipcode.setColumns(10);
				getContentPane().add(zipcode);

				email = new JTextField();
				email.setBounds(240, 320, 150, 20);
				email.setColumns(10);
				getContentPane().add(email);

				clerk = new JTextField();
				clerk.setBounds(240, 360, 100, 20);
				clerk.setColumns(10);
				getContentPane().add(clerk);

				// 监听编号
				Thread listener = new Thread(new Runnable() {

					@Override
					public void run() {
						while (true) {
							if (!serialnum.getText().equals("")) {
								CustomerPO customerPO = new CustomerController()
										.getCustomerPOById(serialnum.getText());
								if (customerPO == null) {
									new warningDialog("查无此人");

								} else {
									classes.setSelectedItem(customerPO.getClass());
									level.setSelectedItem(customerPO.getLevel());
									name.setText(customerPO.getName());
									phone.setText(customerPO.getPhone());
									adress.setText(customerPO.getAddress());
									zipcode.setText(customerPO.getZipCode());
									email.setText(customerPO.getMail());
									clerk.setText(customerPO.getClerk());

									break;
								}
							}
						}
					}
				});

				confirmButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						CustomerVO vo = new CustomerVO(serialnum.getText(),
								getCustomerSort(classes.getSelectedItem()),
								getCustomerLevel(level.getSelectedItem()), name
										.getText(), phone.getText(), adress
										.getText(), zipcode.getText(), email
										.getText(), clerk.getText());

						ResultMessage result = new CustomerController()
								.updateCustmer(vo);
						updateCustomerPanelTable();

						if (result == ResultMessage.update_success) {
							updateCustomerPanelTable();
							listener.stop();
							dispose();
						} else {
							new warningDialog("更新信息失败");
							dispose();
						}
					}
				});

				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						listener.stop();
						dispose();

					}

				});

				this.repaint();
			}

		}

		class AddPurchaseReceiptFrame extends JFrame {
			private JLabel serialNumberLabel, customerLabel, userLabel, timeLabel,
					commentLabel, totalPriceLabel, commodityLabel;
			private JComboBox commodity;
			private JTextField serialNumber, customer, user, time, totalPrice;
			private JTextArea comment;
			private JButton cancelButton, confirmButton, addItemButton;
			private ArrayList<GoodsVO> goodsList;
			// 用来保存商品列表
			ArrayList<PurchaseListItemVO> listItems = new ArrayList<PurchaseListItemVO>();

			private JTable table1 = new JTable();// 商品列表
			private DefaultTableModel model = new DefaultTableModel();// 表格模型
			private Vector tableColName = new Vector();
			private Vector tableData = new Vector();
			private Vector tableRows = new Vector();

			public AddPurchaseReceiptFrame(int type) {

				if (type == 1) {
					this.setTitle("创建进货单");
				} else if (type == -1) {
					this.setTitle("创建进货退货单");
				}
				this.setVisible(true);
				setBounds(100, 100, 556, 475);
				this.setLocationRelativeTo(null);
				getContentPane().setLayout(null);

				goodsList = new ArrayList<GoodsVO>();

				serialNumberLabel = new JLabel("单据编号");
				serialNumberLabel.setBounds(20, 20, 80, 20);
				getContentPane().add(serialNumberLabel);

				if (type == 1) {
					serialNumber = new JTextField(setSerialNumber(1),
							JTextField.CENTER);
				} else if (type == -1) {
					serialNumber = new JTextField(setSerialNumber(-1),
							JTextField.CENTER);
				}
				//TODO
//				serialNumber.setEditable(false);
				serialNumber.setBounds(100, 20, 170, 20);
				getContentPane().add(serialNumber);

				customerLabel = new JLabel("供货商");
				customerLabel.setBounds(20, 60, 100, 20);
				getContentPane().add(customerLabel);

				customer = new JTextField();
				customer.setBounds(100, 60, 100, 20);
				getContentPane().add(customer);

				userLabel = new JLabel("操作员");
				userLabel.setBounds(210, 60, 100, 20);
				getContentPane().add(userLabel);
				// 自动填充
				user = new JTextField();
				user.setBounds(270, 60, 100, 20);
				user.setText(userVO.getUserName());
				getContentPane().add(user);

				timeLabel = new JLabel("创建时间");
				timeLabel.setBounds(20, 100, 100, 20);
				getContentPane().add(timeLabel);
				// 自动添加
				time = new JTextField(yearNow + "-" + monthNow + "-" + dayNow);
				time.setEditable(false);
				time.setBounds(100, 100, 100, 20);
				getContentPane().add(time);

				commodityLabel = new JLabel("仓库");
				commodityLabel.setBounds(210, 100, 100, 20);
				getContentPane().add(commodityLabel);

				commodity = new JComboBox(new String[] { "仓库1" });
				commodity.setBounds(270, 100, 100, 20);
				getContentPane().add(commodity);

				totalPriceLabel = new JLabel("总价");
				totalPriceLabel.setBounds(400, 100, 100, 20);
				getContentPane().add(totalPriceLabel);
				// TODO 自动填充
				totalPrice = new JTextField();
				totalPrice.setText("0");
				totalPrice.setBounds(440, 100, 100, 20);
				getContentPane().add(totalPrice);

				commentLabel = new JLabel("备注");
				commentLabel.setBounds(20, 140, 100, 20);
				getContentPane().add(commentLabel);

				comment = new JTextArea(50, 50);
				comment.setBounds(100, 140, 440, 40);
				getContentPane().add(comment);

				addItemButton = new JButton("添加商品");
				addItemButton.setBounds(10, 200, 80, 20);
				getContentPane().add(addItemButton);

				addItemButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						new AddItemFrame();
					}
				});


				tableColName.add("商品编号");
				tableColName.add("商品名称");
				tableColName.add("商品数量");
				tableColName.add("商品总价");


				table1.setModel(model);

				model.setDataVector(tableData, tableColName);

				// table1=new JTable(new MyTableModel(tableData1,columnTitle1));
				table1.setFillsViewportHeight(true); // 显示表头

				DefaultTableCellRenderer render = new DefaultTableCellRenderer(); // 设置单元格内容居中
				render.setHorizontalAlignment(SwingConstants.LEFT);
				table1.setDefaultRenderer(Object.class, render);

				JScrollPane tablePane1 = new JScrollPane(table1);
				tablePane1.setSize(440, 200);
				tablePane1.setLocation(100, 200);
				getContentPane().add(tablePane1);

				confirmButton = new JButton("确认");
				confirmButton.setBounds(147, 410, 88, 30);
				getContentPane().add(confirmButton);

				cancelButton = new JButton("取消");
				cancelButton.setBounds(296, 410, 88, 30);
				getContentPane().add(cancelButton);

				confirmButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// 有点复杂
						
						PurchaseReceiptVO receipt = new PurchaseReceiptVO(
								new CustomerController()
										.toVO(new CustomerController()
												.getCustomerPOById(customer
														.getText())), serialNumber
										.getText(), userVO, time.getText(), comment
										.getText(), new Double(0)
										.parseDouble(totalPrice.getText()));
						
						receipt.setPurchaseList(listItems);
						ResultMessage result = new PurchaseController()
								.creatReceipt(receipt);

						// 刷新外部表格 TODO 显示操作日志
						Vector logRow=new Vector();
						
						if(serialNumber.getText().substring(0, 3).equals("JHD")){
							logRow.add(new String("创建进货单"));
						}else{
							logRow.add(new String("创建进货退货单"));
						}
						logRow.add(user.getText());
						logRow.add(time.getText());
						purchaseLogTableData.add(logRow);
						
						new PurchaseLogController().add(new PurchaseLogPO((String)logRow.get(0),(String)logRow.get(1),(String)logRow.get(2)));
						
						purchaseLogTable.updateUI();
						
						
						if (result.equals(ResultMessage.add_success)) {
							dispose();
						} else {
							new warningDialog("保存失败!");
						}
					}
				});

				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}

				});

				this.repaint();

			}

			/**
			 * @author gaoyang 默认添加的商品在仓库中可以找到
			 */
			class AddItemFrame extends JFrame {
				private JLabel goodsSerialNumberLabel, goodsQuantityLabel;
				private JTextField goodsSerialNumber, goodsQuantity;
				private JButton confirmButton, cancelButton;

				public AddItemFrame() {
					this.setTitle("添加商品");
					this.setVisible(true);
					setBounds(100, 100, 250, 150);
					this.setLocationRelativeTo(null);
					getContentPane().setLayout(null);

					goodsSerialNumberLabel = new JLabel("商品编号");
					goodsSerialNumberLabel.setBounds(20, 20, 100, 20);
					getContentPane().add(goodsSerialNumberLabel);

					goodsSerialNumber = new JTextField();
					goodsSerialNumber.setBounds(120, 20, 100, 20);
					getContentPane().add(goodsSerialNumber);

					goodsQuantityLabel = new JLabel("商品数量");
					goodsQuantityLabel.setBounds(20, 60, 100, 20);
					getContentPane().add(goodsQuantityLabel);

					goodsQuantity = new JTextField();
					goodsQuantity.setBounds(120, 60, 100, 20);
					getContentPane().add(goodsQuantity);

					confirmButton = new JButton("确认");
					confirmButton.setBounds(40, 100, 80, 20);
					getContentPane().add(confirmButton);

					cancelButton = new JButton("取消");
					cancelButton.setBounds(130, 100, 80, 20);
					getContentPane().add(cancelButton);

					this.confirmButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {

							GoodsVO good = goodsController.getGoodsByID(Long.parseLong(goodsSerialNumber.getText()));
							
							if (good != null) {
								// 添加商品列表
								listItems.add(new PurchaseListItemVO(good,
										new Integer(0).parseInt(goodsQuantity
												.getText())));
								// 刷新外部类商品列表表格
								Vector newRows = new Vector();
								newRows.add(good.serialNumber);
								newRows.add(good.name);
								newRows.add(goodsQuantity.getText());
								// TODO 这么多价格是个意思？
								newRows.add(new Integer(0).parseInt(goodsQuantity
										.getText()) * good.price);
								tableData.add(newRows);
								table1.updateUI();
								// 刷新总价
								totalPrice.setText((new Double(0)
										.parseDouble(totalPrice.getText()) + new Integer(
										0).parseInt(goodsQuantity.getText())
										* good.price)
										+ "");

								dispose();

							} else {
								new warningDialog("不存在此商品，添加失败!");
							}
						}
					});

					cancelButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							dispose();
						}

					});

				}

			}

		}

		class AddSalesReceiptFrame extends JFrame {

			private JLabel serialNumberLabel, customerLabel, userLabel, clerkLabel,
					timeLabel, commentLabel, beforePriceLabel, discountLabel,
					finalPriceLabel, commodityLabel, promotionLabel,vocherLabel;
			private JComboBox commodity, promotion;
			private JTextField serialNumber, customer, user, time, beforePrice,
					discount, finalPrice, clerk,vocher;
			private JTextArea comment;
			private JButton cancelButton, confirmButton, addItemButton,
					detectButton,calPriceButton;
			private ArrayList<GoodsVO> goodsList;
			// 用来保存商品列表
			ArrayList<PurchaseListItemVO> listItems = new ArrayList<PurchaseListItemVO>();
			ArrayList<SalesListItemVO> salesListItems = new ArrayList<SalesListItemVO>();

			private JTable table1 = new JTable();// 商品列表
			private DefaultTableModel model = new DefaultTableModel();// 表格模型
			private Vector tableColName = new Vector();
			private Vector tableData = new Vector();
			private Vector tableRows = new Vector();
			
			int promotionType=0;
			ArrayList<PromotionVO> promotions=new ArrayList<PromotionVO>();
			
			public AddSalesReceiptFrame(int type) {
				if (type == 2) {
					this.setTitle("创建销售单");
				} else {
					this.setTitle("创建销售退货单");
				}
				this.setVisible(true);
				setBounds(100, 100, 556, 475);
				this.setLocationRelativeTo(null);
				getContentPane().setLayout(null);

				goodsList = new ArrayList<GoodsVO>();

				serialNumberLabel = new JLabel("单据编号");
				serialNumberLabel.setBounds(20, 20, 80, 20);
				getContentPane().add(serialNumberLabel);

				if (type == 2) {
					serialNumber = new JTextField(setSerialNumber(2),
							JTextField.CENTER);

				} else {
					serialNumber = new JTextField(setSerialNumber(-2),
							JTextField.CENTER);
				}
				//TODO
//				serialNumber.setEditable(false);
				serialNumber.setBounds(100, 20, 170, 20);
				getContentPane().add(serialNumber);

				customerLabel = new JLabel("销售商");
				customerLabel.setBounds(20, 60, 100, 20);
				getContentPane().add(customerLabel);

				customer = new JTextField();
				customer.setBounds(100, 60, 100, 20);
				getContentPane().add(customer);

				clerkLabel = new JLabel("业务员");
				clerkLabel.setBounds(210, 60, 100, 20);
				getContentPane().add(clerkLabel);
				// 自动填充
				clerk = new JTextField();
				clerk.setBounds(270, 60, 100, 20);
				getContentPane().add(clerk);

				timeLabel = new JLabel("创建时间");
				timeLabel.setBounds(20, 100, 100, 20);
				getContentPane().add(timeLabel);
				// 自动添加
				time = new JTextField(yearNow + "-" + monthNow + "-" + dayNow);
				time.setEditable(false);
				time.setBounds(100, 100, 100, 20);
				getContentPane().add(time);

				commodityLabel = new JLabel("仓库");
				commodityLabel.setBounds(210, 100, 100, 20);
				getContentPane().add(commodityLabel);

				commodity = new JComboBox(new String[] { "仓库1" });
				commodity.setBounds(270, 100, 100, 20);
				getContentPane().add(commodity);

				userLabel = new JLabel("操作员");
				userLabel.setBounds(400, 100, 100, 20);
				getContentPane().add(userLabel);
				// TODO 自动填充
				user = new JTextField();
				user.setText(userVO.getUserName());
				user.setBounds(440, 100, 100, 20);
				getContentPane().add(user);

				beforePriceLabel = new JLabel("折让前价格");
				beforePriceLabel.setBounds(20, 140, 100, 20);
				getContentPane().add(beforePriceLabel);

				beforePrice = new JTextField();
				beforePrice.setBounds(100, 140, 100, 20);
				beforePrice.setText("0");
				getContentPane().add(beforePrice);

				discountLabel = new JLabel("折让");
				discountLabel.setBounds(210, 140, 100, 20);
				getContentPane().add(discountLabel);

				discount = new JTextField();
				discount.setBounds(270, 140, 100, 20);
				discount.setText("0");
				getContentPane().add(discount);
				
				vocherLabel = new JLabel("代金券");
				vocherLabel.setBounds(400, 140, 100, 20);
				getContentPane().add(vocherLabel);

				vocher = new JTextField();
				vocher.setText("0");//代金券默认为0
				vocher.setBounds(440, 140, 100, 20);
				getContentPane().add(vocher);

				finalPriceLabel = new JLabel("折让后金额");
				finalPriceLabel.setBounds(20, 180, 100, 20);
				getContentPane().add(finalPriceLabel);
				// TODO 自动填充
				finalPrice = new JTextField();
				finalPrice.setBounds(100, 180, 100, 20);
				finalPrice.setText("0");
				getContentPane().add(finalPrice);
				
				calPriceButton=new JButton("计算");
				calPriceButton.setBounds(210, 180, 100, 20);
				getContentPane().add(calPriceButton);

				detectButton = new JButton("检测促销策略");
				detectButton.setBounds(400, 210, 100, 20);
				getContentPane().add(detectButton);
				
				promotionLabel=new JLabel("促销策略");
				promotionLabel.setBounds(20, 210, 100, 20);
				getContentPane().add(promotionLabel);
				
				promotion = new JComboBox(new String[] { "无" });
				promotion.setBounds(100,210, 270, 20);
				getContentPane().add(promotion);

				commentLabel = new JLabel("备注");
				commentLabel.setBounds(20, 240, 100, 20);
				getContentPane().add(commentLabel);

				comment = new JTextArea(50, 50);
				comment.setBounds(100, 240, 440, 40);
				getContentPane().add(comment);

				addItemButton = new JButton("添加商品");
				addItemButton.setBounds(10, 300, 80, 20);
				getContentPane().add(addItemButton);
				
				
				calPriceButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						if(promotion.getSelectedIndex()!=0&&promotions.get(promotion.getSelectedIndex()-1).getPromotionType()==PromotionSort.Package){
							discount.setText((Double.parseDouble(discount.getText())+promotions.get(promotion.getSelectedIndex()-1).getOffPrice())+"");
						}

						finalPrice.setText((Double.parseDouble(beforePrice.getText())-Double.parseDouble(discount.getText())+""));
						
					}
				});

				addItemButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						new AddItemFrame();
					}
				});

				
				tableColName.add("商品编号");
				tableColName.add("商品名称");
				tableColName.add("商品数量");
				tableColName.add("商品总价");

				table1.setModel(model);

				model.setDataVector(tableData, tableColName);

				// table1=new JTable(new MyTableModel(tableData1,columnTitle1));
				table1.setFillsViewportHeight(true); // 显示表头

				DefaultTableCellRenderer render = new DefaultTableCellRenderer(); // 设置单元格内容居中
				render.setHorizontalAlignment(SwingConstants.LEFT);
				table1.setDefaultRenderer(Object.class, render);

				JScrollPane tablePane1 = new JScrollPane(table1);
				tablePane1.setSize(440, 100);
				tablePane1.setLocation(100, 300);
				getContentPane().add(tablePane1);

				confirmButton = new JButton("确认");
				confirmButton.setBounds(147, 410, 88, 30);
				getContentPane().add(confirmButton);

				cancelButton = new JButton("取消");
				cancelButton.setBounds(296, 410, 88, 30);
				getContentPane().add(cancelButton);

				confirmButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// 有点复杂
						SalesReceiptVO receipt = creatSalesReceipt();
						if(promotion.getSelectedIndex()!=0){
							receipt.setPromotionVO(promotions.get(promotion.getSelectedIndex()-1));
						}

						ResultMessage result = new SalesController()
								.creatReceipt(receipt);

						// 刷新外部表格 TODO 显示操作日志
						Vector logRow=new Vector();
						
						if(serialNumber.getText().substring(0, 3).equals("XSD")){
							logRow.add(new String("创建销售单"));
						}else{
							logRow.add(new String("创建销售退货单"));
						}
						logRow.add(user.getText());
						logRow.add(time.getText());
						
						new SalesLogController().add(new SalesLogPO((String)logRow.get(0),(String)logRow.get(1),(String)logRow.get(2)));
						salesLogTableData.add(logRow);
						salesLogTable.updateUI();
						
						if (result.equals(ResultMessage.add_success)) {
							dispose();
						} else {
							new warningDialog("保存失败!");
						}
					}
				});
				
				cancelButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
					}

				});
				//检测可用的销售策略并显示为下拉框的选项
				detectButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						if (serialNumber.getText().equals("")
								|| customer.getText().equals("")
								|| user.getText().equals("")
								|| time.getText().equals("")
								|| beforePrice.getText().equals("")
								|| discount.getText().equals("")
								|| finalPrice.getText().equals("")
								|| clerk.getText().equals("")||salesListItems==null) {
							new warningDialog("内容填写不完整!");
							
						}else{
							SalesReceiptVO receipt=creatSalesReceipt();
							ArrayList<PromotionVO> pakeges=new PromotionController().ifPackage(new SalesController().toPO(receipt));
							
							
							
							for (Iterator iterator = pakeges.iterator(); iterator
									.hasNext();) {
								PromotionVO promotionVO = (PromotionVO) iterator
										.next();
								promotion.addItem("买"+promotionVO.getPromotionGoods().get(0).getName()+"和"+promotionVO.getPromotionGoods().get(1).getName()+"减"+promotionVO.getOffPrice()+"元");
								
								promotions.add(promotionVO);
								promotionType++;
							}
							
							ArrayList<PromotionVO> gifts=new PromotionController().ifGift(new SalesController().toPO(receipt));
							for (Iterator iterator = gifts.iterator(); iterator
									.hasNext();) {
								PromotionVO promotionVO = (PromotionVO) iterator
										.next();
								promotion.addItem("满"+promotionVO.getLeastPrice()+"元送"+promotionVO.getPresents().get(0).getName());
								
								promotions.add(promotionVO);
								promotionType++;
							}
							
							ArrayList<PromotionVO> Vouchers=new PromotionController().ifVoucher(new SalesController().toPO(receipt));
							for (Iterator iterator = Vouchers.iterator(); iterator
									.hasNext();) {
								PromotionVO promotionVO = (PromotionVO) iterator
										.next();
								promotion.addItem("满"+promotionVO.getLeastPrice()+"元送"+promotionVO.getVoucher()+"元代金券");
								
								promotions.add(promotionVO);
								promotionType++;
							}

						}
					}
				});

				this.repaint();

			}

			public SalesReceiptVO creatSalesReceipt() {
				SalesReceiptVO receipt = new SalesReceiptVO(serialNumber.getText(),
						customer.getText(), clerk.getText(), salesListItems,
						userVO, (String) commodity.getSelectedItem(),
						new Double(0).parseDouble(beforePrice.getText()),
						new Double(0).parseDouble(discount.getText()),
						new Double(0).parseDouble(finalPrice.getText()),
						time.getText(), comment.getText());
				
				receipt.setCustomerVO(new CustomerController().toVO(new CustomerController().getCustomerPOById(customer.getText())));
				receipt.setVocher(new Double(0).parseDouble(vocher.getText()));

				return receipt;
			}

			/**
			 * @author gaoyang 默认添加的商品在仓库中可以找到
			 */
			class AddItemFrame extends JFrame {
				private JLabel goodsSerialNumberLabel, goodsQuantityLabel;
				private JTextField goodsSerialNumber, goodsQuantity;
				private JButton confirmButton, cancelButton;

				public AddItemFrame() {
					this.setTitle("添加商品");
					this.setVisible(true);
					setBounds(100, 100, 250, 150);
					this.setLocationRelativeTo(null);
					getContentPane().setLayout(null);

					goodsSerialNumberLabel = new JLabel("商品编号");
					goodsSerialNumberLabel.setBounds(20, 20, 100, 20);
					getContentPane().add(goodsSerialNumberLabel);

					goodsSerialNumber = new JTextField();
					goodsSerialNumber.setBounds(120, 20, 100, 20);
					getContentPane().add(goodsSerialNumber);

					goodsQuantityLabel = new JLabel("商品数量");
					goodsQuantityLabel.setBounds(20, 60, 100, 20);
					getContentPane().add(goodsQuantityLabel);

					goodsQuantity = new JTextField();
					goodsQuantity.setBounds(120, 60, 100, 20);
					getContentPane().add(goodsQuantity);

					confirmButton = new JButton("确认");
					confirmButton.setBounds(40, 100, 80, 20);
					getContentPane().add(confirmButton);

					cancelButton = new JButton("取消");
					cancelButton.setBounds(130, 100, 80, 20);
					getContentPane().add(cancelButton);

					confirmButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							GoodsVO good = goodsController.getGoodsByID(new Long(
									0).parseLong(goodsSerialNumber.getText()));
						
							
							boolean isEnough=true;
							//首先判断商品库存是否足够
							if(serialNumber.getText().substring(0, 3).equals("XSD")){
								if(Integer.parseInt(goodsQuantity.getText())>good.commodityQuantity){
									new warningDialog("商品 "+good.name+" 库存不足！");
									isEnough=false;
								}
							}
							
							
							
							// 在这里应当向frame添加商品列表中的商品
							// 如果返回null说明没有此商品
						if(isEnough){
							if (good != null) {
								// 添加商品列表
								salesListItems.add(new SalesListItemVO(good,
										new Integer(0).parseInt(goodsQuantity
												.getText())));
								// 刷新外部类商品列表表格
								Vector newRows = new Vector();
								newRows.add(good.serialNumber);
								newRows.add(good.name);
								newRows.add(goodsQuantity.getText());
								// TODO 这么多价格是个意思？
								newRows.add(new Integer(0).parseInt(goodsQuantity
										.getText()) * good.salePrice);
								tableData.add(newRows);
								
								// 刷新总价
								beforePrice.setText((Double
										.parseDouble(beforePrice.getText()) + Integer.parseInt(goodsQuantity.getText())
										* good.salePrice)
										+ "");
								
								table1.updateUI();

								dispose();

							} else {
								new warningDialog("不存在此商品，添加失败!");
							}
						}
						}
					});

					cancelButton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							dispose();
						}

					});

				}

			}

		}

		class warningDialog extends JDialog {
			public warningDialog(String warnings) {
				this.setSize(284, 158);
				this.setLocationRelativeTo(null);
				this.setLayout(null);
				this.setVisible(true);
				this.setModal(true);

				JLabel warningLabel = new JLabel(warnings, JLabel.CENTER);
				warningLabel.setBounds(50, 28, 200, 50);
				warningLabel.setFont(new Font("宋体", Font.BOLD, 14));

				this.add(warningLabel);
			}
		}

		public int getYearNow() {
			Calendar c = Calendar.getInstance();
			return c.get(Calendar.YEAR);
		}

		public int getMonthNow() {
			Calendar c = Calendar.getInstance();
			return c.get(Calendar.MONTH) + 1;
		}

		public int getDateNow() {
			Calendar c = Calendar.getInstance();
			return c.get(Calendar.DATE);
		}

		public String standardHM() {
			Calendar c = Calendar.getInstance();
			String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
			String minute = String.valueOf(c.get(Calendar.MINUTE));
			if (hour.length() == 1) {
				hour = "0" + hour;
			}
			if (minute.length() == 1) {
				minute = "0" + minute;
			}
			return hour + minute;
		}

		public Level getCustomerLevel(Object selected) {
			Level customer = null;
			switch ((String) selected) {
			case "一级":
				customer = Level.firstClass;
				break;
			case "二级":
				customer = Level.secondClass;
				break;
			case "三级":
				customer = Level.thirdClass;
				break;
			case "四级":
				customer = Level.forthClass;
				break;
			case "五级VIP":
				customer = Level.fiveClassVIP;
				break;
			}
			return customer;
		}

		public Sort getCustomerSort(Object selected) {
			Sort customer = null;
			switch ((String) selected) {
			case "进货商":
				customer = Sort.importer;
				break;
			case "销售商":
				customer = Sort.retailer;
				break;

			}

			return customer;

		}

		class MyTableModel extends AbstractTableModel { // 表格模型
			private Object[][] tableData;
			private String[] columnTitle;

			public MyTableModel(Object[][] data, String[] title) {
				tableData = data;
				columnTitle = title;
			}

			public String getColumnName(int col) {
				return columnTitle[col];
			}

			@Override
			public int getRowCount() {
				// TODO Auto-generated method stub
				return tableData.length;
			}

			@Override
			public int getColumnCount() {
				// TODO Auto-generated method stub

				return columnTitle.length;
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				// TODO Auto-generated method stub
				return tableData[rowIndex][columnIndex];
			}

			public Class getColumnClass(int c) {
				return getValueAt(0, c).getClass();
			}

			public boolean isCellEditable(int row, int col) {
				return true;
			}

			public void setValueAt(Object value, int row, int col) {
				tableData[row][col] = value;
				fireTableCellUpdated(row, col);
			}

		}

		// 设置单据编号
		public String setSerialNumber(int type) {
			String result = null;
			// 自动填充

			// 日期
			String year = String.valueOf(yearNow);
			String day = String.valueOf(dayNow);
			if (day.length() < 2) {
				day = "0" + day;
			}
			String month = String.valueOf(monthNow);
			if (month.length() < 2) {
				month = "0" + month;
			}
			//更正日期格式
			String date = year + month + day;
			int count = 1;// 计算今天单据的个数
			// 单据次序
			String order = "";

			ArrayList<PurchaseReceiptPO> purchaseList = new PurchaseController().show();
			ArrayList<SalesReceiptPO> salesList=new SalesController().show();
			//进货单
			if(type==TYPE_PURCHASE){
				if (purchaseList != null) {
					for (Iterator iterator = purchaseList.iterator(); iterator.hasNext();) {
						PurchaseReceiptPO purchaseReceiptPO = (PurchaseReceiptPO) iterator
								.next();
						if (purchaseReceiptPO.getSerialNumber().contains(date)&&purchaseReceiptPO.getSerialNumber().substring(0, 3).equals("JHD")) {
							count++;
						}
					}
				}			
			}
			//进货退货单
			if(type==TYPE_PURCHASE_BACK){
				if (purchaseList != null) {
					for (Iterator iterator = purchaseList.iterator(); iterator.hasNext();) {
						PurchaseReceiptPO purchaseReceiptPO = (PurchaseReceiptPO) iterator
								.next();
						if (purchaseReceiptPO.getSerialNumber().contains(date)&&purchaseReceiptPO.getSerialNumber().substring(0, 3).equals("JHT")) {
							count++;
						}
					}
				}
			}
			//销售单
			if(type==TYPE_SALES){
				if(salesList!=null){
					for (Iterator iterator = salesList.iterator(); iterator
							.hasNext();) {
						SalesReceiptPO salesReceiptPO = (SalesReceiptPO) iterator
								.next();
						if(salesReceiptPO.getSerialNumber().contains(date)&&salesReceiptPO.getSerialNumber().substring(0, 3).equals("XSD")){
							count++;
						}
						
					}
				}
			}
			//销售退货单
			if(type==TYPE_SALES_BACK){
				if(salesList!=null){
					for (Iterator iterator = salesList.iterator(); iterator
							.hasNext();) {
						SalesReceiptPO salesReceiptPO = (SalesReceiptPO) iterator
								.next();
						if(salesReceiptPO.getSerialNumber().contains(date)&&salesReceiptPO.getSerialNumber().substring(0, 3).equals("XST")){
							count++;
						}
						
					}
				}
			}

			if (count < 10) {
				order = "0000" + count;
			} else if (count < 100) {
				order = "000" + count;
			} else if (count < 1000) {
				order = "00" + count;
			} else if (count < 10000) {
				order = "0" + count;
			} else if (count < 100000) {
				order = "" + count;
			} else {
				System.out.println("You must be crazy!!");
			}

			if (type == TYPE_PURCHASE) {
				result = "JHD-" + date + "-" + order;
			} else if (type == TYPE_PURCHASE_BACK) {
				result = "JHTHD-" + date + "-" + order;
			} else if (type == TYPE_SALES) {
				result = "XSD-" + date + "-" + order;
			} else if (type == TYPE_SALES_BACK) {
				result = "XSTHD-" + date + "-" + order;
			}

			return result;

		}

		
	}
	
	public void updateCustomerPanelTable(){
		ArrayList<CustomerPO> customers = new CustomerController().show();

		customerTableData.removeAllElements();
		
		for (Iterator iterator = customers.iterator(); iterator.hasNext();) {
			CustomerPO customerPO = (CustomerPO) iterator.next();
			Vector tableRows = new Vector();
			tableRows.add(customerPO.getNumber());
			tableRows.add(customerPO.getSort());
			tableRows.add(customerPO.getLevel());
			tableRows.add(customerPO.getName());
			tableRows.add(customerPO.getPhone());
			tableRows.add(customerPO.getAddress());
			tableRows.add(customerPO.getZipCode());
			tableRows.add(customerPO.getMail());
			tableRows.add(customerPO.getClerk());
			tableRows.add(customerPO.getGetting());
			tableRows.add(customerPO.getPay());
			tableRows.add(customerPO.getDebt_upper_limit());

			customerTableData.add(tableRows);
			
		}
		
		customerTable.updateUI();
		
	}
	//更新操作日志
	public void updatePurchaseLogTable(){
		ArrayList<PurchaseLogPO> purchaseLogList=new PurchaseLogController().show();
		
		purchaseLogTableData.removeAllElements();
		
		for (Iterator iterator = purchaseLogList.iterator(); iterator
				.hasNext();) {
			PurchaseLogPO purchaseLog = (PurchaseLogPO) iterator.next();
			Vector logRow=new Vector();
			logRow.add(purchaseLog.getOperation());
			logRow.add(purchaseLog.getOperator());
			logRow.add(purchaseLog.getTime());
			
			purchaseLogTableData.add(logRow);
			
		}
	}

	public void updateSalesLogTable(){
		ArrayList<SalesLogPO> salesLogList=new SalesLogController().show();
		
		salesLogTableData.removeAllElements();
		for (Iterator iterator = salesLogList.iterator(); iterator
				.hasNext();) {
			SalesLogPO purchaseLog = (SalesLogPO) iterator.next();
			Vector logRow=new Vector();
			logRow.add(purchaseLog.getOperation());
			logRow.add(purchaseLog.getOperator());
			logRow.add(purchaseLog.getTime());
			
			salesLogTableData.add(logRow);
			
		}
		salesLogTable.updateUI();
	}
	
	public void findCustomer(String keyWord){
		ArrayList<CustomerPO> customers = new CustomerController().findCustomer(keyWord);

		customerTableData.removeAllElements();
		
		for (Iterator iterator = customers.iterator(); iterator.hasNext();) {
			CustomerPO customerPO = (CustomerPO) iterator.next();
			Vector tableRows = new Vector();
			tableRows.add(customerPO.getNumber());
			tableRows.add(customerPO.getSort());
			tableRows.add(customerPO.getLevel());
			tableRows.add(customerPO.getName());
			tableRows.add(customerPO.getPhone());
			tableRows.add(customerPO.getAddress());
			tableRows.add(customerPO.getZipCode());
			tableRows.add(customerPO.getMail());
			tableRows.add(customerPO.getClerk());
			tableRows.add(customerPO.getGetting());
			tableRows.add(customerPO.getPay());
			tableRows.add(customerPO.getDebt_upper_limit());

			customerTableData.add(tableRows);
			
		}
		
		customerTable.updateUI();
	}
	
	

	public static void main(String[] args) {
		new SalesmanFrame(new UserVO("gaoyang", "123",
				UserSort.PurchaseAndSaler, 1));
	}

}
