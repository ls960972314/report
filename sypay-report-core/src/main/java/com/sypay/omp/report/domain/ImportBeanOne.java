package com.sypay.omp.report.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 运营导入表  一
 * @author 887961
 *
 */
@Entity
@Table(name="RPT_IMPORT_ONE")
public class ImportBeanOne  implements Serializable  {

	private static final long serialVersionUID = 3745647197005309145L;

	/**
	 * 主键
	 */
	private Integer id;
	
	/**
	 * 财付通订单号
	 */
	private String cftOrderId;
	/**
	 * 交易时间
	 */
	private String tranTime;
	/**
	 * 类型 
	 */
	private String type;
	/**
	 * 交易对方
	 */
	private String tranTarget;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 收入(元)
	 */
	private String inMoney;
	/**
	 * 支出(元)
	 */
	private String outMoney;
	/**
	 * 余额(元)
	 */
	private String leftMoney;
	/**
	 * 交易信息
	 */
	private String tranInfo;
	/**
	 * 商户订单号
	 */
	private String mchtOrderId;

	@Id
    @Column(name="id")
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	/*@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="SEQ_RPT_IMPORT_ONE")
    @SequenceGenerator(name="SEQ_RPT_IMPORT_ONE",sequenceName="SEQ_RPT_IMPORT_ONE",allocationSize=1)*/
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="CFT_ORDER_ID")
	public String getCftOrderId() {
		return cftOrderId;
	}

	public void setCftOrderId(String cftOrderId) {
		this.cftOrderId = cftOrderId;
	}
	@Column(name="TRAN_TIME")
	public String getTranTime() {
		return tranTime;
	}

	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}

	public String getType() {
		return type;
	}
	@Column(name="TYPE")
	public void setType(String type) {
		this.type = type;
	}

	public String getTranTarget() {
		return tranTarget;
	}
	@Column(name="TRAN_TARGET")
	public void setTranTarget(String tranTarget) {
		this.tranTarget = tranTarget;
	}

	public String getName() {
		return name;
	}
	@Column(name="NAME")
	public void setName(String name) {
		this.name = name;
	}

	public String getInMoney() {
		return inMoney;
	}
	@Column(name="IN_MONEY")
	public void setInMoney(String inMoney) {
		this.inMoney = inMoney;
	}

	public String getOutMoney() {
		return outMoney;
	}
	@Column(name="OUT_MONEY")
	public void setOutMoney(String outMoney) {
		this.outMoney = outMoney;
	}

	public String getLeftMoney() {
		return leftMoney;
	}
	@Column(name="LEFT_MONEY")
	public void setLeftMoney(String leftMoney) {
		this.leftMoney = leftMoney;
	}

	public String getTranInfo() {
		return tranInfo;
	}
	@Column(name="TRAN_INFO")
	public void setTranInfo(String tranInfo) {
		this.tranInfo = tranInfo;
	}

	public String getMchtOrderId() {
		return mchtOrderId;
	}
	@Column(name="MCHT_ORDER_ID")
	public void setMchtOrderId(String mchtOrderId) {
		this.mchtOrderId = mchtOrderId;
	}
}
