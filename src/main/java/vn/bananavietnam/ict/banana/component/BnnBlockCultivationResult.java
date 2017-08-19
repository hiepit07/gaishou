package vn.bananavietnam.ict.banana.component;

import java.util.Date;

import vn.bananavietnam.ict.common.db.model.IvbTCultivationResult;

public class BnnBlockCultivationResult extends IvbTCultivationResult {

	// ブロック名
	private String blockName;

	// 作業実施日
	private String workingDateString;

	// プロセス名
	private String processName;

	// プロセス順
	private String processOrder;

	// タスク名
	private String taskName;

	// タスク順
	private String taskOrder;

	// Change point code
	private String changePointCode;

	// 作業内容
	private String workingContent;

	// 注意事項
	private String precaution;

	// ステータス名
	private String statusName;

	// 植え日
	private String plantingDate;

	// 開花日
	private String floweringDate;

	// 袋がけ日
	private String bagClosingDate;

	// 収穫日
	private String harvestDate;

	// 出荷日
	private String shippingDate;
	
	private Date lastUpdateDateCultivation;
	
	private Date lastUpdateDateProduct;
	
	// variable to store all selected ids on client
	private String currentProductIdString;
	private String currentBlockIdString;
	private String currentLastUpdateDateString;
	public String getBlockName() {
		return blockName;
	}

	public void setBlockName(String blockName) {
		this.blockName = blockName;
	}

	public String getWorkingDateString() {
		return workingDateString;
	}

	public void setWorkingDateString(String workingDateString) {
		this.workingDateString = workingDateString;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getProcessOrder() {
		return processOrder;
	}

	public void setProcessOrder(String processOrder) {
		this.processOrder = processOrder;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskOrder() {
		return taskOrder;
	}

	public void setTaskOrder(String taskOrder) {
		this.taskOrder = taskOrder;
	}

	public String getChangePointCode() {
		return changePointCode;
	}

	public void setChangePointCode(String changePointCode) {
		this.changePointCode = changePointCode;
	}

	public String getWorkingContent() {
		return workingContent;
	}

	public void setWorkingContent(String workingContent) {
		this.workingContent = workingContent;
	}

	public String getPrecaution() {
		return precaution;
	}

	public void setPrecaution(String precaution) {
		this.precaution = precaution;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getPlantingDate() {
		return plantingDate;
	}

	public void setPlantingDate(String plantingDate) {
		this.plantingDate = plantingDate;
	}

	public String getFloweringDate() {
		return floweringDate;
	}

	public void setFloweringDate(String floweringDate) {
		this.floweringDate = floweringDate;
	}

	public String getBagClosingDate() {
		return bagClosingDate;
	}

	public void setBagClosingDate(String bagClosingDate) {
		this.bagClosingDate = bagClosingDate;
	}

	public String getHarvestDate() {
		return harvestDate;
	}

	public void setHarvestDate(String harvestDate) {
		this.harvestDate = harvestDate;
	}

	public String getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(String shippingDate) {
		this.shippingDate = shippingDate;
	}

	public String getCurrentProductIdString() {
		return currentProductIdString;
	}

	public void setCurrentProductIdString(String currentProductIdString) {
		this.currentProductIdString = currentProductIdString;
	}

	public String getCurrentBlockIdString() {
		return currentBlockIdString;
	}

	public void setCurrentBlockIdString(String currentBlockIdString) {
		this.currentBlockIdString = currentBlockIdString;
	}

	public Date getLastUpdateDateCultivation() {
		return lastUpdateDateCultivation;
	}

	public void setLastUpdateDateCultivation(Date lastUpdateDateCultivation) {
		this.lastUpdateDateCultivation = lastUpdateDateCultivation;
	}

	public Date getLastUpdateDateProduct() {
		return lastUpdateDateProduct;
	}

	public void setLastUpdateDateProduct(Date lastUpdateDateProduct) {
		this.lastUpdateDateProduct = lastUpdateDateProduct;
	}

	public String getCurrentLastUpdateDateString() {
		return currentLastUpdateDateString;
	}

	public void setCurrentLastUpdateDateString(String currentLastUpdateDateString) {
		this.currentLastUpdateDateString = currentLastUpdateDateString;
	}
}
