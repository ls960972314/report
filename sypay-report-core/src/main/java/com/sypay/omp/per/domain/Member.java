package com.sypay.omp.per.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@Entity
@Table(name = "UC_MEMBER")
public class Member implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String accNo;
	private String password;
	private String avatar;
	private String name;
	private String mobile;
	private Integer status;
	private Date createTime;
	private Date updateTime;
	private Integer memberType;
	private String idCard;
	private Date birth;
	private String extend;
	private String userNick;
	private String userCity;
	private Long bankId;
	private String bankName;
	private String cardNo;
	private Date authLastTime;
	private String qrcodeUrl;
	private String partner;
	private Double schId;
	private Date changeCardLastTime;
	private String qrEmailImageUrl;
	
	//	1：内部用户 2:外部用户3.临时用户
	private Integer casType;
	
	// 创建人id
	private Long createrId;

	// 修改人id
	private Long modifierId;

	// Property accessors
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	/*@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="sequence")
	@SequenceGenerator(name="sequence", sequenceName="seq_uc_member_id", allocationSize=1)*/
	@Column(name ="id")
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "ACC_NO", length = 30)
	public String getAccNo() {
		return this.accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	@Column(name = "PASSWORD", length = 32)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "AVATAR", length = 100)
	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Column(name = "NAME", length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "MOBILE", length = 15)
	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "STATUS", precision = 1, scale = 0)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "CREATE_TIME", length = 7)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "UPDATE_TIME", length = 7)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "MEMBER_TYPE", precision = 1, scale = 0)
	public Integer getMemberType() {
		return this.memberType;
	}

	public void setMemberType(Integer memberType) {
		this.memberType = memberType;
	}

	@Column(name = "ID_CARD", length = 256)
	public String getIdCard() {
		return this.idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Column(name = "BIRTH", length = 7)
	public Date getBirth() {
		return this.birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	@Column(name = "EXTEND", length = 100)
	public String getExtend() {
		return this.extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	@Column(name = "USER_NICK", length = 30)
	public String getUserNick() {
		return this.userNick;
	}

	public void setUserNick(String userNick) {
		this.userNick = userNick;
	}

	@Column(name = "USER_CITY", length = 30)
	public String getUserCity() {
		return this.userCity;
	}

	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}

	@Column(name = "BANK_ID", precision = 0)
	public Long getBankId() {
		return this.bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	@Column(name = "BANK_NAME", length = 60)
	public String getBankName() {
		return this.bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Column(name = "CARD_NO", length = 19)
	public String getCardNo() {
		return this.cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	@Column(name = "AUTH_LAST_TIME", length = 7)
	public Date getAuthLastTime() {
		return this.authLastTime;
	}

	public void setAuthLastTime(Date authLastTime) {
		this.authLastTime = authLastTime;
	}

	@Column(name = "QRCODE_URL", length = 500)
	public String getQrcodeUrl() {
		return this.qrcodeUrl;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	@Column(name = "PARTNER", length = 25)
	public String getPartner() {
		return this.partner;
	}

	public void setPartner(String partner) {
		this.partner = partner;
	}

	@Column(name = "SCH_ID", precision = 0)
	public Double getSchId() {
		return this.schId;
	}

	public void setSchId(Double schId) {
		this.schId = schId;
	}

	@Column(name = "CHANGE_CARD_LAST_TIME", length = 7)
	public Date getChangeCardLastTime() {
		return this.changeCardLastTime;
	}

	public void setChangeCardLastTime(Date changeCardLastTime) {
		this.changeCardLastTime = changeCardLastTime;
	}

	@Column(name = "QR_EMAIL_IMAGE_URL", length = 500)
	public String getQrEmailImageUrl() {
		return this.qrEmailImageUrl;
	}

	public void setQrEmailImageUrl(String qrEmailImageUrl) {
		this.qrEmailImageUrl = qrEmailImageUrl;
	}
	
	@Column(name="cas_type", length = 1, precision = 0)
	public Integer getCasType() {
		return casType;
	}

	public void setCasType(Integer casType) {
		this.casType = casType;
	}

	@Column(name = "CREATER_ID", length = 11, precision = 0)
	public Long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	@Column(name ="MODIFIER_ID", length = 11, precision = 0)
	public Long getModifierId() {
		return modifierId;
	}

	public void setModifierId(Long modifierId) {
		this.modifierId = modifierId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Member))
			return false;
		Member castOther = (Member) other;

		return ((this.getId() == castOther.getId()) || (this.getId() != null
				&& castOther.getId() != null && this.getId().equals(
				castOther.getId())))
				&& ((this.getAccNo() == castOther.getAccNo()) || (this
						.getAccNo() != null && castOther.getAccNo() != null && this
						.getAccNo().equals(castOther.getAccNo())))
				&& ((this.getPassword() == castOther.getPassword()) || (this
						.getPassword() != null
						&& castOther.getPassword() != null && this
						.getPassword().equals(castOther.getPassword())))
				&& ((this.getAvatar() == castOther.getAvatar()) || (this
						.getAvatar() != null && castOther.getAvatar() != null && this
						.getAvatar().equals(castOther.getAvatar())))
				&& ((this.getName() == castOther.getName()) || (this.getName() != null
						&& castOther.getName() != null && this.getName()
						.equals(castOther.getName())))
				&& ((this.getMobile() == castOther.getMobile()) || (this
						.getMobile() != null && castOther.getMobile() != null && this
						.getMobile().equals(castOther.getMobile())))
				&& ((this.getStatus() == castOther.getStatus()) || (this
						.getStatus() != null && castOther.getStatus() != null && this
						.getStatus().equals(castOther.getStatus())))
				&& ((this.getCreateTime() == castOther.getCreateTime()) || (this
						.getCreateTime() != null
						&& castOther.getCreateTime() != null && this
						.getCreateTime().equals(castOther.getCreateTime())))
				&& ((this.getUpdateTime() == castOther.getUpdateTime()) || (this
						.getUpdateTime() != null
						&& castOther.getUpdateTime() != null && this
						.getUpdateTime().equals(castOther.getUpdateTime())))
				&& ((this.getMemberType() == castOther.getMemberType()) || (this
						.getMemberType() != null
						&& castOther.getMemberType() != null && this
						.getMemberType().equals(castOther.getMemberType())))
				&& ((this.getIdCard() == castOther.getIdCard()) || (this
						.getIdCard() != null && castOther.getIdCard() != null && this
						.getIdCard().equals(castOther.getIdCard())))
				&& ((this.getBirth() == castOther.getBirth()) || (this
						.getBirth() != null && castOther.getBirth() != null && this
						.getBirth().equals(castOther.getBirth())))
				&& ((this.getExtend() == castOther.getExtend()) || (this
						.getExtend() != null && castOther.getExtend() != null && this
						.getExtend().equals(castOther.getExtend())))
				&& ((this.getUserNick() == castOther.getUserNick()) || (this
						.getUserNick() != null
						&& castOther.getUserNick() != null && this
						.getUserNick().equals(castOther.getUserNick())))
				&& ((this.getUserCity() == castOther.getUserCity()) || (this
						.getUserCity() != null
						&& castOther.getUserCity() != null && this
						.getUserCity().equals(castOther.getUserCity())))
				&& ((this.getBankId() == castOther.getBankId()) || (this
						.getBankId() != null && castOther.getBankId() != null && this
						.getBankId().equals(castOther.getBankId())))
				&& ((this.getBankName() == castOther.getBankName()) || (this
						.getBankName() != null
						&& castOther.getBankName() != null && this
						.getBankName().equals(castOther.getBankName())))
				&& ((this.getCardNo() == castOther.getCardNo()) || (this
						.getCardNo() != null && castOther.getCardNo() != null && this
						.getCardNo().equals(castOther.getCardNo())))
				&& ((this.getAuthLastTime() == castOther.getAuthLastTime()) || (this
						.getAuthLastTime() != null
						&& castOther.getAuthLastTime() != null && this
						.getAuthLastTime().equals(castOther.getAuthLastTime())))
				&& ((this.getQrcodeUrl() == castOther.getQrcodeUrl()) || (this
						.getQrcodeUrl() != null
						&& castOther.getQrcodeUrl() != null && this
						.getQrcodeUrl().equals(castOther.getQrcodeUrl())))
				&& ((this.getPartner() == castOther.getPartner()) || (this
						.getPartner() != null && castOther.getPartner() != null && this
						.getPartner().equals(castOther.getPartner())))
				&& ((this.getSchId() == castOther.getSchId()) || (this
						.getSchId() != null && castOther.getSchId() != null && this
						.getSchId().equals(castOther.getSchId())))
				&& ((this.getChangeCardLastTime() == castOther
						.getChangeCardLastTime()) || (this
						.getChangeCardLastTime() != null
						&& castOther.getChangeCardLastTime() != null && this
						.getChangeCardLastTime().equals(
								castOther.getChangeCardLastTime())))
				&& ((this.getQrEmailImageUrl() == castOther
						.getQrEmailImageUrl()) || (this.getQrEmailImageUrl() != null
						&& castOther.getQrEmailImageUrl() != null && this
						.getQrEmailImageUrl().equals(
								castOther.getQrEmailImageUrl())))
				&& ((this.getCreaterId() == castOther
						.getCreaterId()) || (this.getCreaterId() != null
						&& castOther.getCreaterId() != null && this
						.getCreaterId().equals(
								castOther.getCreaterId())))	
				&& ((this.getModifierId() == castOther
						.getModifierId()) || (this.getModifierId() != null
						&& castOther.getModifierId() != null && this
						.getModifierId().equals(
								castOther.getModifierId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getId() == null ? 0 : this.getId().hashCode());
		result = 37 * result
				+ (getAccNo() == null ? 0 : this.getAccNo().hashCode());
		result = 37 * result
				+ (getPassword() == null ? 0 : this.getPassword().hashCode());
		result = 37 * result
				+ (getAvatar() == null ? 0 : this.getAvatar().hashCode());
		result = 37 * result
				+ (getName() == null ? 0 : this.getName().hashCode());
		result = 37 * result
				+ (getMobile() == null ? 0 : this.getMobile().hashCode());
		result = 37 * result
				+ (getStatus() == null ? 0 : this.getStatus().hashCode());
		result = 37
				* result
				+ (getCreateTime() == null ? 0 : this.getCreateTime()
						.hashCode());
		result = 37
				* result
				+ (getUpdateTime() == null ? 0 : this.getUpdateTime()
						.hashCode());
		result = 37
				* result
				+ (getMemberType() == null ? 0 : this.getMemberType()
						.hashCode());
		result = 37 * result
				+ (getIdCard() == null ? 0 : this.getIdCard().hashCode());
		result = 37 * result
				+ (getBirth() == null ? 0 : this.getBirth().hashCode());
		result = 37 * result
				+ (getExtend() == null ? 0 : this.getExtend().hashCode());
		result = 37 * result
				+ (getUserNick() == null ? 0 : this.getUserNick().hashCode());
		result = 37 * result
				+ (getUserCity() == null ? 0 : this.getUserCity().hashCode());
		result = 37 * result
				+ (getBankId() == null ? 0 : this.getBankId().hashCode());
		result = 37 * result
				+ (getBankName() == null ? 0 : this.getBankName().hashCode());
		result = 37 * result
				+ (getCardNo() == null ? 0 : this.getCardNo().hashCode());
		result = 37
				* result
				+ (getAuthLastTime() == null ? 0 : this.getAuthLastTime()
						.hashCode());
		result = 37 * result
				+ (getQrcodeUrl() == null ? 0 : this.getQrcodeUrl().hashCode());
		result = 37 * result
				+ (getPartner() == null ? 0 : this.getPartner().hashCode());
		result = 37 * result
				+ (getSchId() == null ? 0 : this.getSchId().hashCode());
		result = 37
				* result
				+ (getChangeCardLastTime() == null ? 0 : this
						.getChangeCardLastTime().hashCode());
		result = 37
				* result
				+ (getQrEmailImageUrl() == null ? 0 : this.getQrEmailImageUrl()
						.hashCode());
		result = 37
				* result
				+ (getCreaterId() == null ? 0 : this.getCreaterId()
						.hashCode());
		result = 37
				* result
				+ (getModifierId() == null ? 0 : this.getModifierId()
						.hashCode());
		return result;
	}

}