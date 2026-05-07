package jp.inowa.myscent.config;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import jp.inowa.myscent.reservation.Reservation;
import jp.inowa.myscent.reservation.ReservationRepository;
import jp.inowa.myscent.reservation.ReservationStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

  @Bean
  CommandLineRunner seedReservations(ReservationRepository reservationRepository) {
    return args -> {
      if (reservationRepository.count() > 0) {
        return;
      }

      LocalDate today = LocalDate.now();
      reservationRepository.saveAll(List.of(
          reservation("R-DEMO-001", "山田 花子", "hanako@example.com", "090-1111-2222",
              "スタンダード調香プラン", today, LocalTime.of(10, 0), 2,
              ReservationStatus.CONFIRMED, ""),
          reservation("R-DEMO-002", "佐藤 美咲", "misaki@example.com", "090-3333-4444",
              "プレミアム調香プラン", today, LocalTime.of(11, 30), 1,
              ReservationStatus.PENDING, "時間変更の相談あり"),
          reservation("R-DEMO-003", "鈴木 健太", "kenta@example.com", "090-5555-6666",
              "ペア調香プラン", today.plusDays(1), LocalTime.of(14, 0), 2,
              ReservationStatus.CONFIRMED, "ギフト利用"),
          reservation("R-DEMO-004", "高橋 由衣", "yui@example.com", "090-7777-8888",
              "ギフト調香プラン", today.plusDays(2), LocalTime.of(16, 0), 1,
              ReservationStatus.CANCELLED, "アレルギー配慮メモあり")
      ));
    };
  }

  private Reservation reservation(
      String bookingCode,
      String customerName,
      String customerEmail,
      String customerPhone,
      String menuName,
      LocalDate date,
      LocalTime time,
      int partySize,
      ReservationStatus status,
      String memo) {
    Reservation reservation = new Reservation();
    reservation.setBookingCode(bookingCode);
    reservation.setCustomerName(customerName);
    reservation.setCustomerEmail(customerEmail);
    reservation.setCustomerPhone(customerPhone);
    reservation.setMenuName(menuName);
    reservation.setReservationDate(date);
    reservation.setReservationTime(time);
    reservation.setPartySize(partySize);
    reservation.setStatus(status);
    reservation.setMemo(memo);
    return reservation;
  }
}
