package jp.inowa.myscent.reservation;

import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@Transactional(readOnly = true)
public class ReservationService {

  private final ReservationRepository reservationRepository;

  public ReservationService(ReservationRepository reservationRepository) {
    this.reservationRepository = reservationRepository;
  }

  public List<Reservation> findAll() {
    return reservationRepository.findAllByOrderByReservationDateAscReservationTimeAscIdAsc();
  }

  public List<Reservation> findToday() {
    return reservationRepository.findByReservationDateOrderByReservationTimeAscIdAsc(LocalDate.now());
  }

  public Reservation get(Long id) {
    return reservationRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Reservation not found"));
  }

  @Transactional
  public Reservation create(ReservationForm form) {
    Reservation reservation = new Reservation();
    form.applyTo(reservation);
    return reservationRepository.save(reservation);
  }

  @Transactional
  public Reservation update(Long id, ReservationForm form) {
    Reservation reservation = get(id);
    form.applyTo(reservation);
    return reservationRepository.save(reservation);
  }

  @Transactional
  public void delete(Long id) {
    Reservation reservation = get(id);
    reservationRepository.delete(reservation);
  }

  public long countToday() {
    return reservationRepository.countByReservationDate(LocalDate.now());
  }

  public long countThisWeek() {
    LocalDate today = LocalDate.now();
    return reservationRepository.countByReservationDateBetween(today, today.plusDays(6));
  }

  public long countPending() {
    return reservationRepository.countByStatus(ReservationStatus.PENDING);
  }

  public long countTodayPending() {
    return reservationRepository.countByReservationDateAndStatus(LocalDate.now(), ReservationStatus.PENDING);
  }

  public long estimateMonthlyRevenue() {
    LocalDate today = LocalDate.now();
    LocalDate firstDay = today.withDayOfMonth(1);
    LocalDate lastDay = today.withDayOfMonth(today.lengthOfMonth());
    return reservationRepository.countByReservationDateBetween(firstDay, lastDay) * 5000;
  }
}
