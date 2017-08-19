package vn.bananavietnam.ict.banana.component;

public class Bnn0087SearchAffiliationConditions {

    private String usersName;
    private String authorityId;
    private String searchOption;
    // From parameter
    private String fromRow;

    // Number of items in a page
    private String itemCount;

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

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }

    public String getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityName(String authorityId) {
        this.authorityId = authorityId;
    }

    public String getSearchOption() {
        return searchOption;
    }

    public void setSearchOption(String searchOption) {
        this.searchOption = searchOption;
    }

}
