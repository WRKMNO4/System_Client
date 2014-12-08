package VO;

import PO.GoodsPO;

/**
 * 
 * @author hutao gaoyang
 *
 */
public class GoodsVO {
	public String serialNumber; //即id
	public String name;
	public String model;
	public double price;
	public double totalPrice;
	public String comment;
	
	
	public String goodsClassName; //商品分类名
	public double salePrice;
	public double latestPrice;
	public double latestSalePrice;
	public int commodityQuantity; //库存数量
	
	
	public GoodsVO() {
		this.serialNumber = "/";
		this.name = "/";
		this.model = "/";
		this.price = -1;
		this.totalPrice = -1;
		this.comment = "/";
		this.goodsClassName = "/";
		this.salePrice = -1;
		this.latestPrice = -1;
		this.latestSalePrice = -1;
		this.commodityQuantity = -1;
	}
	public GoodsVO(String serialNumber, String name, String model, 
			double price, double totalPrice, String comment) {
		this();
		this.serialNumber = serialNumber;
		this.name = name;
		this.model = model;
		this.price = price;
		this.totalPrice = totalPrice;
		this.comment = comment;
	};
	public GoodsVO(String serialNumber, String name, String model, 
		 double price, double salePrice, double latestPrice,
		 double latestSalePrice, String goodsClassName) {
		this();
		this.serialNumber = serialNumber;
		this.name = name;
		this.model = model;
		this.price = price;
		this.salePrice = salePrice;
		this.latestPrice = latestPrice;
		this.latestSalePrice = latestSalePrice;
		this.goodsClassName = goodsClassName;
	};
	
	
	public GoodsPO toPO() {
		GoodsPO po = new GoodsPO();
		po.setSerialNumber(this.serialNumber);
		po.setName(this.name);
		po.setModel(this.model);
		po.setPrice(this.price);
		po.setTotalPrice(this.totalPrice);
		po.setComment(this.comment);
		po.setGoodsClassName(this.goodsClassName);
		po.setSalePrice(this.salePrice);
		po.setLatestPrice(this.latestPrice);
		po.setLatestSalePrice(this.latestSalePrice);
		po.setCommodityQuantity(this.commodityQuantity);
		return po;
	}

	public void toVO(GoodsPO po) {
		this.serialNumber = po.getSerialNumber();
		this.name = po.getName();
		this.model = po.getModel();
		this.price = po.getPrice();
		this.totalPrice = po.getTotalPrice();
		this.comment = po.getComment();
		this.goodsClassName = po.getGoodsClassName();
		this.salePrice = po.getSalePrice();
		this.latestPrice = po.getLatestPrice();
		this.latestSalePrice = po.getLatestSalePrice();
		this.commodityQuantity = po.getCommodityQuantity();
	}
	
	public GoodsVO(GoodsVO vo) {
		this.serialNumber = vo.serialNumber;
		this.name = vo.name;
		this.model = vo.model;
		this.price = vo.price;
		this.totalPrice = vo.totalPrice;
		this.comment = vo.comment;
		this.goodsClassName = vo.goodsClassName;
		this.salePrice = vo.salePrice;
		this.latestPrice = vo.latestPrice;
		this.latestSalePrice = vo.latestSalePrice;
		this.commodityQuantity = vo.commodityQuantity;
	}
}

