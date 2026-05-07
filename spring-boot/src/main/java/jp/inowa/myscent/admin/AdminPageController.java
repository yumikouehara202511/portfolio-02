package jp.inowa.myscent.admin;

import jp.inowa.myscent.reservation.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminPageController {

  private final ReservationService reservationService;

  public AdminPageController(ReservationService reservationService) {
    this.reservationService = reservationService;
  }

  @GetMapping({"", "/"})
  public String dashboard(Model model) {
    model.addAttribute("activePage", "dashboard");
    model.addAttribute("todayCount", reservationService.countToday());
    model.addAttribute("weekCount", reservationService.countThisWeek());
    model.addAttribute("pendingCount", reservationService.countPending());
    model.addAttribute("todayPendingCount", reservationService.countTodayPending());
    model.addAttribute("monthlyRevenue", reservationService.estimateMonthlyRevenue());
    model.addAttribute("todayReservations", reservationService.findToday());
    return "admin/dashboard";
  }

  @GetMapping("/login")
  public String login() {
    return "admin/login";
  }

  @GetMapping("/login.html")
  public String legacyLogin() {
    return "redirect:/admin/login";
  }

  @GetMapping("/index.html")
  public String legacyDashboard() {
    return "redirect:/admin";
  }

  @GetMapping("/reservations.html")
  public String legacyReservations() {
    return "redirect:/admin/reservations";
  }

  @GetMapping("/customers")
  public String customers(Model model) {
    model.addAttribute("activePage", "customers");
    model.addAttribute("pageTitle", "顧客管理");
    model.addAttribute("pageEyebrow", "顧客");
    model.addAttribute("emptyTitle", "登録済み顧客はありません");
    model.addAttribute("emptyText", "予約情報が登録されると、顧客一覧へ反映できます。");
    return "admin/simple-page";
  }

  @GetMapping("/customers.html")
  public String legacyCustomers() {
    return "redirect:/admin/customers";
  }

  @GetMapping("/schedule")
  public String schedule(Model model) {
    model.addAttribute("activePage", "schedule");
    model.addAttribute("pageTitle", "空き枠管理");
    model.addAttribute("pageEyebrow", "スケジュール");
    model.addAttribute("emptyTitle", "空き枠は未登録です");
    model.addAttribute("emptyText", "予約枠テーブルを追加すると、この画面をDB管理へ拡張できます。");
    return "admin/simple-page";
  }

  @GetMapping("/schedule.html")
  public String legacySchedule() {
    return "redirect:/admin/schedule";
  }

  @GetMapping("/analytics")
  public String analytics(Model model) {
    model.addAttribute("activePage", "analytics");
    model.addAttribute("pageTitle", "分析");
    model.addAttribute("pageEyebrow", "レポート");
    model.addAttribute("emptyTitle", "集計データは予約情報から生成します");
    model.addAttribute("emptyText", "現在は予約件数と売上見込みをダッシュボードで確認できます。");
    return "admin/simple-page";
  }

  @GetMapping("/analytics.html")
  public String legacyAnalytics() {
    return "redirect:/admin/analytics";
  }
}
