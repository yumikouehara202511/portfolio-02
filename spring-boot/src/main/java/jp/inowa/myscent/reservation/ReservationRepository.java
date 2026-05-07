package jp.inowa.myscent.reservation;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  List<Reservation> findAllByOrderByReservationDateAscReservationTimeAscIdAsc();

  List<Reservation> findByReservationDateOrderByReservationTimeAscIdAsc(LocalDate reservationDate);

  long countByReservationDate(LocalDate reservationDate);

  long countByStatus(ReservationStatus status);

  long countByReservationDateBetween(LocalDate startDate, LocalDate endDate);

  long countByReservationDateAndStatus(LocalDate reservationDate, ReservationStatus status);
}
