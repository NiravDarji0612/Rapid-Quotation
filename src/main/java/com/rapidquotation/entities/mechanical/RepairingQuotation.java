package com.rapidquotation.entities.mechanical;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.rapidquotation.entities.Company;
import com.rapidquotation.entities.RepairingQuotationItems;

@Entity
@Table
public class RepairingQuotation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String title;
	private String description;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "company_id")
	private Company company;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "repairing_quote_id",referencedColumnName = "id")
	private List<RepairingQuotationItems> repairingQuotationItems;
	
	private int labourCost;
	private Long quotationNumber;
	private Date creationDate;
	private Date expieryDate;
	private String websiteLink;
	private long faxNumber;
	private String approxTime;
	private String termsAndConditions;
	private long total;
	private Long totalIncludingAllCosts;
	private String gst;
	private long totalIncludingAllCostsWithGst;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getLabourCost() {
		return labourCost;
	}
	public void setLabourCost(int labourCost) {
		this.labourCost = labourCost;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public List<RepairingQuotationItems> getRepairingQuotationItems() {
		if(repairingQuotationItems == null)
		{
			repairingQuotationItems = new ArrayList<>();
		}
		return repairingQuotationItems;
	}
	public void setRepairingQuotationItems(List<RepairingQuotationItems> repairingQuotationItems) {
		this.repairingQuotationItems = repairingQuotationItems;
	}
	public Long getQuotationNumber() {
		return quotationNumber;
	}
	public void setQuotationNumber(Long quotationNumber) {
		this.quotationNumber = quotationNumber;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getExpieryDate() {
		return expieryDate;
	}
	public void setExpieryDate(Date expieryDate) {
		this.expieryDate = expieryDate;
	}
	public String getWebsiteLink() {
		return websiteLink;
	}
	public void setWebsiteLink(String websiteLink) {
		this.websiteLink = websiteLink;
	}
	public long getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(long faxNumber) {
		this.faxNumber = faxNumber;
	}
	public String getApproxTime() {
		return approxTime;
	}
	public void setApproxTime(String approxTime) {
		this.approxTime = approxTime;
	}
	public String getTermsAndConditions() {
		return termsAndConditions;
	}
	public void setTermsAndConditions(String termsAndConditions) {
		this.termsAndConditions = termsAndConditions;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public Long getTotalIncludingAllCosts() {
		return totalIncludingAllCosts;
	}
	public void setTotalIncludingAllCosts(Long totalIncludingAllCosts) {
		this.totalIncludingAllCosts = totalIncludingAllCosts;
	}
	public String getGst() {
		return gst;
	}
	public void setGst(String gst) {
		this.gst = gst;
	}
	public long getTotalIncludingAllCostsWithGst() {
		return totalIncludingAllCostsWithGst;
	}
	public void setTotalIncludingAllCostsWithGst(long totalIncludingAllCostsWithGst) {
		this.totalIncludingAllCostsWithGst = totalIncludingAllCostsWithGst;
	}
	

}
