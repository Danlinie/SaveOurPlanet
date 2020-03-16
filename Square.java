package demo;

public class Square implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private int index;
	private String fieldName;
	private String areaName;
	private int ownership;
	private int areaCost;
	private int developmentCost;
	private int majorDevelopmentCost;
	private int numberOfDevelopments;
	private int totalCost;
	
	public Square(int index, String fieldName, String areaName, int ownership, int areaCost, int developmentCost, int majorDevelopmentCost) {
		this.index = index;
		this.fieldName = fieldName;
		this.areaName = areaName;
		this.ownership = ownership;
		this.areaCost = areaCost;
		this.developmentCost = developmentCost;
		this.majorDevelopmentCost = majorDevelopmentCost;
		this.numberOfDevelopments = 0;
		this.totalCost = 0;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public int getOwnership() {
		return ownership;
	}

	public void setOwnership(int ownership) {
		this.ownership = ownership;
	}

	public int getAreaCost() {
		return areaCost;
	}

	public void setAreaCost(int areaCost) {
		this.areaCost = areaCost;
	}

	public int getDevelopmentCost() {
		return developmentCost;
	}

	public void setDevelopmentCost(int developmentCost) {
		this.developmentCost = developmentCost;
	}

	public int getMajorDevelopmentCost() {
		return majorDevelopmentCost;
	}

	public void setMajorDevelopmentCost(int majorDevelopmentCost) {
		this.majorDevelopmentCost = majorDevelopmentCost;
	}

	public int getNumberOfDevelopments() {
		return numberOfDevelopments;
	}

	public void setNumberOfDevelopments(int numberOfDevelopments) {
		this.numberOfDevelopments = numberOfDevelopments;
	}

	public int getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}

	@Override
	public String toString() {
		return "Square [index=" + index + ", fieldName=" + fieldName + ", areaName=" + areaName + ", ownership=" + ownership + ", areaCost=£"
				+ areaCost + ", developmentCost=£" + developmentCost + ", majorDevelopmentCost=£" + majorDevelopmentCost
				+ ", numberOfDevelopments=" + numberOfDevelopments + ", totalCost=£" + totalCost + "]";
	}
	
}
