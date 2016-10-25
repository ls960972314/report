package com.sypay.omp.per.domain;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**   
 *
 * @Description: 角色权限关联表
 * @date 2014-10-20 14:10:04
 * @version V1.0   
 *
 */
@Entity
@Table(name = "UC_ROLE_RES")
public class RoleRes implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    
	// id
	private Long id;
	// 系统编码
	private String sysCode;
	// 角色编码
	private String roleCode;
	// 角色id
	private Role role;
	// 资源id
	private Resource resource;
	

	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	/*@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="sequence")
	@SequenceGenerator(name="sequence",sequenceName="SEQ_ROLE_RESID",allocationSize=1)*/
	@Column(name ="ID",nullable=false,precision=11,scale=0)
	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id = id;
	}

	@Column(name ="SYS_CODE",nullable=true,length=64)
	public String getSysCode(){
		return sysCode;
	}

	public void setSysCode(String sysCode){
		this.sysCode = sysCode;
	}

	@Column(name ="ROLE_CODE",nullable=true,length=64)
	public String getRoleCode(){
		return roleCode;
	}

	public void setRoleCode(String roleCode){
		this.roleCode = roleCode;
	}


	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "ROLE_ID")
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "RESOURCE_ID")
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

}
