package vn.bananavietnam.ict.banana.component;

/**
 * 
 * @author NghiaNguyen
 *
 */
public class Bnn0017SearchBananaKindConditions {

    // Kind Id
    private String kindId;
    // Kind Name
    private String kindName;
    // expectedAmountHarvest
    private String expectedAmountHarvest;
    // delete Flag
    // private String deleteFlag;
    // Note
    private String note;
    // From parameter
    private String fromRow;
    // Number of items in a page
    private String itemCount;

    public String getKindId() {
        return kindId;
    }

    public void setKindId(String kindId) {
        this.kindId = kindId;
    }

    public String getKindName() {
        return kindName;
    }

    public void setKindName(String kindName) {
        this.kindName = kindName;
    }

    public String getExpectedAmountHarvest() {
        return expectedAmountHarvest;
    }

    public void setExpectedAmountHarvest(String expectedAmountHarvest) {
        this.expectedAmountHarvest = expectedAmountHarvest;
    }

    public String getFromRow() {
        return fromRow;
    }

    public void setFromRow(String fromRow) {
        this.fromRow = fromRow;
    }

    public String getItemCount() {
        return itemCount;
    }

    public void setItemCount(String itemCount) {
        this.itemCount = itemCount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}