package jp.inowa.myscent.admin;

import jakarta.validation.Valid;
import jp.inowa.myscent.reservation.Reservation;
import jp.inowa.myscent.reservation.ReservationForm;
import jp.inowa.myscent.reservation.ReservationService;
import jp.inowa.myscent.reservation.ReservationStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/reservations")
public class AdminReservationController {

  private final ReservationService reservationService;

  public AdminReservationController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @ModelAttribute("statusOptions")
  public ReservationStatus[] statusOptions() {
    return ReservationStatus.values();
  }

  @ModelAttribute("menuOptions")
  public String[] menuOptions() {
    return new String[] {
        "スタンダード調香プラン",
        "プレミアム調香プラン",
        "ペア調香プラン",
        "ギフト調香プラン"
    };
  }

  @GetMapping
  public String list(Model model) {
    model.addAttribute("activePage", "reservations");
    model.addAttribute("reservations", reservationService.findAll());
    model.addAttribute("pendingCount", reservationService.countPending());
    model.addAttribute("todayCount", reservationService.countToday());
    return "admin/reservations/list";
  }

  @GetMapping("/index.html")
  public String legacyDetailPath() {
    return "redirect:/admin/reservations";
  }

  @GetMapping("/new")
  public String newForm(Model model) {
    model.addAttribute("reservationForm", ReservationForm.empty());
    prepareFormModel(model, "create", null);
    return "admin/reservations/form";
  }

  @PostMapping
  public String create(
      @Valid @ModelAttribute("reservationForm") ReservationForm reservationForm,
      BindingResult bindingResult,
      Model model,
      RedirectAttributes redirectAttributes) {
    if (bindingResult.hasErrors()) {
      prepareFormModel(model, "create", null);
      return "admin/reservations/form";
    }

    Reservation reservation = reservationService.create(reservationForm);
    redirectAttributes.addFlashAttribute("message", "予約を登録しました。");
    return "redirect:/admin/reservations/" + reservation.getId();
  }

  @GetMapping("/{id}")
  public String detail(@PathVariable Long id, Model model) {
    model.addAttribute("activePage", "reservations");
    model.addAttribute("reservation", reservationService.get(id));
    return "admin/reservations/detail";
  }

  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable Long id, Model model) {
    Reservation reservation = reservationService.get(id);
    model.addAttribute("reservation", reservation);
    model.addAttribute("reservationForm", ReservationForm.from(reservation));
    prepareFormModel(model, "edit", id);
    return "admin/reservations/form";
  }

  @PostMapping("/{id}")
  public String update(
      @PathVariable Long id,
      @Valid @ModelAttribute("reservationForm") ReservationForm reservationForm,
      BindingResult bindingResult,
      Model model,
      RedirectAttributes redirectAttributes) {
    Reservation reservation = reservationService.get(id);
    if (bindingResult.hasErrors()) {
      model.addAttribute("reservation", reservation);
      prepareFormModel(model, "edit", id);
      return "admin/reservations/form";
    }

    reservationService.update(id, reservationForm);
    redirectAttributes.addFlashAttribute("message", "予約を更新しました。");
    return "redirect:/admin/reservations/" + id;
  }

  @PostMapping("/{id}/delete")
  public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    reservationService.delete(id);
    redirectAttributes.addFlashAttribute("message", "予約を削除しました。");
    return "redirect:/admin/reservations";
  }

  private void prepareFormModel(Model model, String formMode, Long reservationId) {
    model.addAttribute("activePage", "reservations");
    model.addAttribute("formMode", formMode);
    model.addAttribute("reservationId", reservationId);
    model.addAttribute("isEdit", "edit".equals(formMode));
  }
}
