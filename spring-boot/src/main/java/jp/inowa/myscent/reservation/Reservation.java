package jp.inowa.myscent.reservation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Entity
@Table(name = "reservations")
public class Reservation {

  private static final DateTimeFormatter DATE_LABEL = DateTimeFormatter.ofPattern("yyyy.MM.dd");
  private static final DateTimeFormatter TIME_LABEL = DateTimeFormatter.ofPattern("HH:mm");

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 32, unique = true)
  private String bookingCode;

  @Column(nullable = false, length = 80)
  private String customerName;

  @Column(length = 120)
  private String customerEmail;

  @Column(length = 40)
  private String customerPhone;

  @Column(nullable = false, length = 120)
  private String menuName;

  @Column(nullable = false)
  private LocalDate reservationDate;

  @Column(nullable = false)
  private LocalTime reservationTime;

  @Column(nullable = false)
  private Integer partySize;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 24)
  private ReservationStatus status;

  @Column(length = 1000)
  private String memo;

  @Column(nullable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime updatedAt;

  @PrePersist
  void prePersist() {
    LocalDateTime now = LocalDateTime.now();
    createdAt = now;
    updatedAt = now;
    if (bookingCode == null || bookingCode.isBlank()) {
      bookingCode = "R-" + now.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "-"
          + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
  }

  @PreUpdate
  void preUpdate() {
    updatedAt = LocalDateTime.now();
  }

  public String getReservationDateLabel() {
    return reservationDate == null ? "" : reservationDate.format(DATE_LABEL);
  }

  public String getReservationTimeLabel() {
    return reservationTime == null ? "" : reservationTime.format(TIME_LABEL);
  }

  public String getMemoLabel() {
    return memo == null || memo.isBlank() ? "-" : memo;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getBookingCode() {
    return bookingCode;
  }

  public void setBookingCode(String bookingCode) {
    this.bookingCode = bookingCode;
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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }
}
