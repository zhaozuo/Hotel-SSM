package cn.sdut.entity;

import org.springframework.stereotype.Component;

/**
 * �˿�ʵ����
 *
 */
@Component
public class Account {
	private Long id;//�˿�id
	private String name;//�˿͵�¼��
	private String password;//�˿͵�¼����
	private String photo;//��Ƭ��ַ
	private String realName;//��ʵ����
	private String idCard;//���֤����
	private String mobile;//�ֻ���
	private String address;//��ϵ��ַ
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
