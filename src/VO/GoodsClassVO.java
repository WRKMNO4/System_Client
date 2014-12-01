package VO;

import PO.GoodsClassPO;

/**
 * 
 * @author hutao
 *
 */
public class GoodsClassVO {
	public long fatherGoodsClassNum;
	public long Num;//商品分类编号
	public String goodsClassName;
	
	
	public GoodsClassVO() {}
	public GoodsClassVO(GoodsClassVO fatherGoodsClass, String name) {
		this.fatherGoodsClassNum = fatherGoodsClass.Num;
		this.goodsClassName = name;
	}
	public GoodsClassVO(String name) {
		this.fatherGoodsClassNum = 0;
		this.goodsClassName = name;
	}
	
	public GoodsClassPO toPO() {
		GoodsClassPO g = new GoodsClassPO();
		g.fatherGoodsClassNum = this.fatherGoodsClassNum;
		g.Num = this.Num;
		g.goodsClassName = this.goodsClassName;
		return g;
	}
	public void toVO(GoodsClassPO po) {
		po.fatherGoodsClassNum = this.fatherGoodsClassNum;
		po.Num = this.Num;
		po.goodsClassName = this.goodsClassName;
	}

}
