package vn.bananavietnam.ict.banana.component;

import vn.bananavietnam.ict.common.db.model.IvbTProduct;

public class Bnn0037ProductExtendObject extends IvbTProduct {

	// cultivation start date
	private String cultivationStartDateString;

	// planting date
	private String plantingDateString;

	// flowering date
	private String floweringDateString;

	// bag closing date
	private String bagClosingDateString;

	// harvest date
	private String harvestDateString;

	public String getCultivationStartDateString() {
		return cultivationStartDateString;
	}

	public void setCultivationStartDateString(String cultivationStartDateString) {
		this.cultivationStartDateString = cultivationStartDateString;
	}

	public String getPlantingDateString() {
		return plantingDateString;
	}

	public void setPlantingDateString(String plantingDateString) {
		this.plantingDateString = plantingDateString;
	}

	public String getFloweringDateString() {
		return floweringDateString;
	}

	public void setFloweringDateString(String floweringDateString) {
		this.floweringDateString = floweringDateString;
	}

	public String getBagClosingDateString() {
		return bagClosingDateString;
	}

	public void setBagClosingDateString(String bagClosingDateString) {
		this.bagClosingDateString = bagClosingDateString;
	}

	public String getHarvestDateString() {
		return harvestDateString;
	}

	public void setHarvestDateString(String harvestDateString) {
		this.harvestDateString = harvestDateString;
	}
}
