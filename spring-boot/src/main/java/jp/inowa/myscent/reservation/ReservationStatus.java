package jp.inowa.myscent.reservation;

public enum ReservationStatus {
  CONFIRMED("確定", "status-chip--confirmed"),
  PENDING("未対応", "status-chip--pending"),
  CANCELLED("キャンセル", "status-chip--cancelled");

  private final String label;
  private final String cssClass;

  ReservationStatus(String label, String cssClass) {
    this.label = label;
    this.cssClass = cssClass;
  }

  public String getLabel() {
    return label;
  }

  public String getCssClass() {
    return cssClass;
  }
}
