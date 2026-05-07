package jp.inowa.myscent.reservation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;

public class ReservationForm {

  @NotBlank
  @Size(max = 80)
  private String customerName;

  @Email
  @Size(max = 120)
  private String customerEmail;

  @Size(max = 40)
  private String customerPhone;

  @NotBlank
  @Size(max = 120)
  private String menuName;

  @NotNull
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private LocalDate reservationDate;

  @NotNull
  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  private LocalTime reservationTime;

  @NotNull
  @Min(1)
  @Max(6)
  private Integer partySize;

  @NotNull
  private ReservationStatus status;

  @Size(max = 1000)
  private String memo;

  public static ReservationForm empty() {
    ReservationForm form = new ReservationForm();
    form.menuName = "スタンダード調香プラン";
    form.reservationDate = LocalDate.now();
    form.reservationTime = LocalTime.of(10, 0);
    form.partySize = 2;
    form.status = ReservationStatus.CONFIRMED;
    return form;
  }

  public static ReservationForm from(Reservation reservation) {
    ReservationForm form = new ReservationForm();
    form.customerName = reservation.getCustomerName();
    form.customerEmail = reservation.getCustomerEmail();
    form.customerPhone = reservation.getCustomerPhone();
    form.menuName = reservation.getMenuName();
    form.reservationDate = reservation.getReservationDate();
    form.reservationTime = reservation.getReservationTime();
    form.partySize = reservation.getPartySize();
    form.status = reservation.getStatus();
    form.memo = reservation.getMemo();
    return form;
  }

  public void applyTo(Reservation reservation) {
    reservation.setCustomerName(customerName);
    reservation.setCustomerEmail(customerEmail);
    reservation.setCustomerPhone(customerPhone);
    reservation.setMenuName(menuName);
    reservation.setReservationDate(reservationDate);
    reservation.setReservationTime(reservationTime);
    reservation.setPartySize(partySize);
    reservation.setStatus(status);
    reservation.setMemo(memo);
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }

  public String getCustomerPhone() {
    return customerPhone;
  }

  public void setCustomerPhone(String customerPhone) {
    this.customerPhone = customerPhone;
  }

  public String getMenuName() {
    return menuName;
  }

  public void setMenuName(String menuName) {
    this.menuName = menuName;
  }

  public LocalDate getReservationDate() {
    return reservationDate;
  }

  public void setReservationDate(LocalDate reservationDate) {
    this.reservationDate = reservationDate;
  }

  public LocalTime getReservationTime() {
    return reservationTime;
  }

  public void setReservationTime(LocalTime reservationTime) {
    this.reservationTime = reservationTime;
  }

  public Integer getPartySize() {
    return partySize;
  }

  public void setPartySize(Integer partySize) {
    this.partySize = partySize;
  }

  public ReservationStatus getStatus() {
    return status;
  }

  public void setStatus(ReservationStatus status) {
    this.status = status;
  }

  public String getMemo() {
    return memo;
  }

  public void setMemo(String memo) {
    this.memo = memo;
  }
}
