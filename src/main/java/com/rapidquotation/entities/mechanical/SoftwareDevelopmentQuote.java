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
import com.rapidquotation.entities.SoftwareDevelopmentItems;

@Entity
@Table
public class SoftwareDevelopmentQuote {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int softwareId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "company_id")
	private Company company;
	
	private Long quotationNumber;
	
	private Date creationDate;
	
	private Date expieryDate;
	
	private String title;
	
	private String description;
	
	private String technologies;
	
	private int contentWritingCost;
	
	private int codingDevelopmentCost;
	
	private int paymentGatewayCost;
	
	private int domainHosting;
	
	private int technicalSupportCost;
	
	private int testingCost;
	
	private int maintainanceCost;
	
	
	private String maintainancePeriod;
	
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "software_quote_id",referencedColumnName = "softwareId")
	private List<SoftwareDevelopmentItems> softwareItems;
	
	private String websiteLink;
	
	private long faxNumber;
	
	private String approxTime;
	
	private String termsAndConditions;
	
	private long total;
	
	
	private long totalIncludingAllCosts;
	
	private String gst;
	
	private long totalIncludingAllCostsWithGst;
	
	

	public long getTotalIncludingAllCosts() {
		return totalIncludingAllCosts;
	}

	public void setTotalIncludingAllCosts(long totalIncludingAllCosts) {
		this.totalIncludingAllCosts = totalIncludingAllCosts;
	}

	public int getSoftwareId() {
		return softwareId;
	}

	public void setSoftwareId(int softwareId) {
		this.softwareId = softwareId;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
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

	public String getTechnologies() {
		return technologies;
	}

	public void setTechnologies(String technologies) {
		this.technologies = technologies;
	}
	
	public int getContentWritingCost() {
		return contentWritingCost;
	}

	public void setContentWritingCost(int contentWritingCost) {
		this.contentWritingCost = contentWritingCost;
	}

	public int getCodingDevelopmentCost() {
		return codingDevelopmentCost;
	}

	public void setCodingDevelopmentCost(int codingDevelopmentCost) {
		this.codingDevelopmentCost = codingDevelopmentCost;
	}

	public int getPaymentGatewayCost() {
		return paymentGatewayCost;
	}

	public void setPaymentGatewayCost(int paymentGatewayCost) {
		this.paymentGatewayCost = paymentGatewayCost;
	}

	public int getDomainHosting() {
		return domainHosting;
	}

	public void setDomainHosting(int domainHosting) {
		this.domainHosting = domainHosting;
	}

	public int getTechnicalSupportCost() {
		return technicalSupportCost;
	}

	public void setTechnicalSupportCost(int technicalSupportCost) {
		this.technicalSupportCost = technicalSupportCost;
	}

	public int getTestingCost() {
		return testingCost;
	}

	public void setTestingCost(int testingCost) {
		this.testingCost = testingCost;
	}

	public int getMaintainanceCost() {
		return maintainanceCost;
	}

	public void setMaintainanceCost(int maintainanceCost) {
		this.maintainanceCost = maintainanceCost;
	}

	public String getMaintainancePeriod() {
		return maintainancePeriod;
	}

	public void setMaintainancePeriod(String maintainancePeriod) {
		this.maintainancePeriod = maintainancePeriod;
	}

	
	public List<SoftwareDevelopmentItems> getSoftwareItems() {
		if(softwareItems == null)
		{
			softwareItems = new ArrayList<>();
		}
		return softwareItems;
	}

	public void setSoftwareItems(List<SoftwareDevelopmentItems> softwareItems) {
		this.softwareItems = softwareItems;
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
