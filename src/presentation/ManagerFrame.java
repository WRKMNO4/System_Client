package presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import Config.Level;
import PO.UserPO;

public class ManagerFrame extends JFrame{
	private JLabel backgroundLabel,exitButton,crLabel,infoLabel,promotionLabel;
	private CheckReceiptPanel crPanel=new CheckReceiptPanel(this);
	private InfoPanel infoPanel=new InfoPanel(this);
	private PromotionPanel proPanel=new PromotionPanel(this);
	
	public ManagerFrame(){   //总Frame
		this.setSize(1000, 600);
		this.setLocationRelativeTo(null);
		this.setTitle("welcome");
		this.setLayout(null);
		this.setUndecorated(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		exitButton = new JLabel("X", JLabel.CENTER);
		exitButton.setBounds(950, 0, 50, 50);
		exitButton.setFont(new Font("default", 1, 20));
		exitButton.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
	    });
		
		crLabel = new JLabel("审批单据", JLabel.CENTER);
		crLabel.setBounds(40, 100, 100, 50);
		crLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				crPanel.setVisible(true);
				infoPanel.setVisible(false);
				proPanel.setVisible(false);
			}
		});
		
		infoLabel = new JLabel("查看报表", JLabel.CENTER);
		infoLabel.setBounds(40, 160, 100, 50);
		infoLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				crPanel.setVisible(false);
				infoPanel.setVisible(true);
				proPanel.setVisible(false);
			}
		});
		
		promotionLabel = new JLabel("制定促销策略", JLabel.CENTER);
		promotionLabel.setBounds(40, 220, 100, 50);
		promotionLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				crPanel.setVisible(false);
				infoPanel.setVisible(false);
				proPanel.setVisible(true);
			}
		});
		
		
		this.add(exitButton);
		this.add(crLabel);
		this.add(infoLabel);
		this.add(promotionLabel);
		
		MoveOfFrame m = new MoveOfFrame(this);
		this.setVisible(true);
	}
	
	class CheckReceiptPanel extends JPanel {      //审批报表panel
		//private JTable goodsTable;
		public CheckReceiptPanel(JFrame theFrame) {
			this.setLayout(null);
			this.setBounds(140, 25, 835, 550);
			this.setBackground(new Color(147, 224, 255, 255));
			 //选择性标签
			JLabel Receipt1 = new JLabel("进货单和进货退货单",JLabel.CENTER);
			Receipt1.setBounds(200, 23, 120, 50);
			this.add(Receipt1);
			
			JLabel Receipt2 = new JLabel("销售单和销售退货单",JLabel.CENTER);
			Receipt2.setBounds(350, 23, 120, 50);
			this.add(Receipt2);
			
			JLabel Receipt3 = new JLabel("收款单和付款单",JLabel.CENTER);
			Receipt3.setBounds(500, 23, 120, 50);
			this.add(Receipt3);
			
			JPanel panel1=new JPanel();
			panel1.setLayout(null);
			panel1.setBounds(37, 63, 767, 438);
			panel1.setBackground(new Color(179, 197,135));
			this.add(panel1);
			
			JPanel panel2=new JPanel();
			panel2.setLayout(null);
			panel2.setBounds(37, 63, 767, 438);
			panel2.setBackground(new Color(140, 197,135));
			this.add(panel2);
			
			JPanel panel3=new JPanel();
			panel3.setLayout(null);
			panel3.setBounds(37, 63, 767, 438);
			panel3.setBackground(new Color(174, 221,129));
			this.add(panel3);
			
			Receipt1.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e) {
					panel1.setVisible(true);
					panel2.setVisible(false);
					panel3.setVisible(false);
				}
			});
			
			Receipt2.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e) {
					panel1.setVisible(false);
					panel2.setVisible(true);
					panel3.setVisible(false);
				}
			});
			
			Receipt3.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent e) {
					panel1.setVisible(false);
					panel2.setVisible(false);
					panel3.setVisible(true);
				}
			});
			// 表一
			String[] columnTitle1={"单据编号","供应商","仓库","操作员","入库商品列表","备注","总额合计","审批通过"};
			Object[][] tableData1={
					new Object[]{"JH123","胡韬","1号","高杨","暂无","^",1000,new Boolean(false)},
					new Object[]{"JH124","小宇","2号","高杨","暂无","^",2000,new Boolean(false)},
					           };
			JTable table1=new JTable(new MyTableModel(tableData1,columnTitle1));
			table1.setFillsViewportHeight(true);     //显示表头
			
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();   //设置单元格内容居中
		    render.setHorizontalAlignment(SwingConstants.CENTER);
		    table1.setDefaultRenderer(Object.class, render);
		    
			JScrollPane tablePane1=new JScrollPane(table1);
			tablePane1.setSize(630,400);
			tablePane1.setLocation(50, 10);
			panel1.add(tablePane1);
			
			JButton allButton1=new JButton("全选");
			allButton1.setBounds(685,150,73,30);
			panel1.add(allButton1);
			JButton allNotButton1=new JButton("全不选");
			allNotButton1.setBounds(685,185,73,30);
			panel1.add(allNotButton1);
			JButton doneButton1=new JButton("审批");
			doneButton1.setBounds(685, 379, 73, 30);
			panel1.add(doneButton1);
			
			allButton1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					for(int i=0;i<table1.getRowCount();i++)
							table1.setValueAt(true, i, 7);
					}
			});
			
			allNotButton1.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
				
						for(int i=0;i<table1.getRowCount();i++)
							table1.setValueAt(false, i, 7);
						}
			});     
			
			// 表二
			String[] columnTitle2={"单据编号","客户","业务员","操作员","仓库","出货商品清单","折让前总额","折让","使用代金券金额","折让后总额","备注","审批通过"};
			Object[][] tableData2={
					new Object[]{"TH123","胡韬","姚锰舟","高杨","0011","^",1000,20,50,980,"无",new Boolean(false)},
					new Object[]{"TH124","小宇","姚锰舟","高杨","0011","^",2000,40,100,1960,"无",new Boolean(false)},
					           };
			JTable table2=new JTable(new MyTableModel(tableData2,columnTitle2));
			table2.setFillsViewportHeight(true);     //显示表头
			
		    table2.setDefaultRenderer(Object.class, render);
		    
		    
			JScrollPane tablePane2=new JScrollPane(table2);
			tablePane2.setSize(630,400);
			tablePane2.setLocation(50, 10);
			panel2.add(tablePane2);
				
			JButton allButton2=new JButton("全选");
			allButton2.setBounds(685,150,73,30);
			panel2.add(allButton2);
			JButton allNotButton2=new JButton("全不选");
			allNotButton2.setBounds(685,185,73,30);
			panel2.add(allNotButton2);
			JButton doneButton2=new JButton("审批");
			doneButton2.setBounds(685, 379, 73, 30);
			panel2.add(doneButton2);
			
			allButton2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					for(int i=0;i<table2.getRowCount();i++)
							table2.setValueAt(true, i, 11);
					}
			});
			
			allNotButton2.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
						for(int i=0;i<table2.getRowCount();i++)
							table2.setValueAt(false, i, 11);
						}
			});     

			// 表三
			String[] columnTitle3={"单据类型","单据编号","客户","操作员","转账列表","总额汇总","审批通过"};
			Object[][] tableData3={
					new Object[]{"进货单","XY1321324","飞利浦","胡韬","暂无",1000,new Boolean(false)},
					new Object[]{"销售单","DI123143","东芝","高杨逸乔","暂无",2000,new Boolean(false)},
					           };
			JTable table3=new JTable(new MyTableModel(tableData3,columnTitle3));
			table3.setFillsViewportHeight(true);     //显示表头
			
		    table3.setDefaultRenderer(Object.class, render);
		    
		    
			JScrollPane tablePane3=new JScrollPane(table3);
			tablePane3.setSize(630,400);
			tablePane3.setLocation(50, 10);
			panel3.add(tablePane3);
			
			JButton allButton3=new JButton("全选");
			allButton3.setBounds(685,150,73,30);
			panel3.add(allButton3);
			JButton allNotButton3=new JButton("全不选");
			allNotButton3.setBounds(685,185,73,30);
			panel3.add(allNotButton3);
			JButton doneButton3=new JButton("审批");
			doneButton3.setBounds(685, 379, 73, 30);
			panel3.add(doneButton3);
			
			allButton3.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					for(int i=0;i<table3.getRowCount();i++)
							table3.setValueAt(true, i, 6);
					}
			});
			
			allNotButton3.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
				
						for(int i=0;i<table3.getRowCount();i++)
							table3.setValueAt(false, i, 6);
						}
			});          
			
			theFrame.add(this);
		}
	}
	
	class InfoPanel extends JPanel {     //查看报表信息panel
		//private JTable goodsTable;
		public InfoPanel(JFrame theFrame) {
			this.setBounds(140, 25, 835, 550);
			this.setBackground(new Color(185, 227, 217, 255));
			
			
			theFrame.add(this);
		}
	}
	
	class PromotionPanel extends JPanel {     //指定促销策略panel
		//private JTable goodsTable;
		public PromotionPanel(JFrame theFrame) {
			this.setLayout(null);
			this.setBounds(140, 25, 835, 550);
			this.setBackground(new Color(173, 137, 115, 255));
			
			String[] columnTitle={"策略类型","策略编号","组合商品降价","优惠需达金额","降价金额","赠品列表",
					"赠送代金券金额","起始时间","终止时间","客户最低等级"};
			Object[][] tableData={
					new Object[]{"Gifts","P123","无",100,20,"无",10,"2014-11-12","2015-01-01","无"},
					new Object[]{"Voucher","P124","无",300,60,"无",10,"2014-11-14","2015-01-23","无"},
					           };
			JTable table=new JTable(new MyTableModel(tableData,columnTitle));
			table.setFillsViewportHeight(true);     //显示表头
			DefaultTableCellRenderer render = new DefaultTableCellRenderer();   //设置单元格内容居中
		    render.setHorizontalAlignment(SwingConstants.CENTER);
            table.setDefaultRenderer(Object.class, render);
		    
		    
			JScrollPane tablePane=new JScrollPane(table);
			tablePane.setSize(700,400);
			tablePane.setLocation(80, 74);
			this.add(tablePane);
		
			theFrame.add(this);
		}
	}
	
	class MyTableModel extends AbstractTableModel{      //表格模型
		private Object[][] tableData;
		private String[] columnTitle;
		public MyTableModel(Object[][] data,String[] title) {
			tableData=data;
			columnTitle=title;
		}
		 
		public String getColumnName(int col)
	     {
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

	class TransferFrame extends JFrame{
		
	}

	

	       
	
	
	public static void main(String[] args){
		new ManagerFrame();
	}
}
