const menuToggle = document.querySelector(".menu-toggle");
const siteNav = document.querySelector(".site-nav");

if (menuToggle && siteNav) {
  const closeMenu = () => {
    siteNav.classList.remove("is-open");
    menuToggle.setAttribute("aria-expanded", "false");
  };

  menuToggle.addEventListener("click", () => {
    const isOpen = siteNav.classList.toggle("is-open");
    menuToggle.setAttribute("aria-expanded", isOpen ? "true" : "false");
  });

  siteNav.querySelectorAll("a").forEach((link) => {
    link.addEventListener("click", closeMenu);
  });

  window.addEventListener("resize", () => {
    if (window.innerWidth > 980) {
      closeMenu();
    }
  });
}

document.querySelectorAll(".faq-question").forEach((button) => {
  const initialIcon = button.querySelector(".faq-icon");
  if (initialIcon) {
    initialIcon.textContent =
      button.getAttribute("aria-expanded") === "true" ? "-" : "+";
  }

  button.addEventListener("click", () => {
    const answerId = button.getAttribute("aria-controls");
    const answer = answerId ? document.getElementById(answerId) : null;
    const expanded = button.getAttribute("aria-expanded") === "true";

    if (!answer) {
      return;
    }

    button.setAttribute("aria-expanded", expanded ? "false" : "true");
    answer.hidden = expanded;

    const icon = button.querySelector(".faq-icon");
    if (icon) {
      icon.textContent = expanded ? "+" : "-";
    }
  });
});

const liquidFill = document.querySelector(".liquid-fill");
const liquidDrop = document.querySelector(".liquid-drop");

if (liquidFill && liquidDrop) {
  let level = 18;
  let drops = 0;
  const maxDrops = 8;

  const dropOnce = () => {
    liquidDrop.classList.remove("is-falling");
    void liquidDrop.offsetWidth;
    liquidDrop.classList.add("is-falling");

    setTimeout(() => {
      if (level < 74) {
        level += 7;
        liquidFill.style.height = `${level}%`;
      }
    }, 920);

    drops += 1;

    if (drops < maxDrops) {
      const nextDelay = 1400 + Math.random() * 900;
      setTimeout(dropOnce, nextDelay);
    }
  };

  setTimeout(dropOnce, 900);
}

const reservationForm = document.querySelector("[data-reservation-form]");

if (reservationForm) {
  const reservationContainer = reservationForm.parentElement;
  const dateButtons = reservationForm.querySelectorAll(".calendar-day:not(.is-muted)");
  const timeButtons = reservationForm.querySelectorAll(".slot-button:not(.is-disabled)");
  const peopleButtons = reservationForm.querySelectorAll(".party-button");
  const selectedDate = reservationContainer?.querySelector("[data-summary-date]");
  const selectedTime = reservationContainer?.querySelector("[data-summary-time]");
  const selectedStatus = reservationForm.querySelector(".reservation-slots__status");
  const summaryPeople = document.getElementById("summary-people");
  const submitButton = reservationContainer?.querySelector(".reservation-submit__button");
  const priceDisplay = reservationContainer?.querySelector(".reservation-summary__price strong");

const getSelectedDate = () => {
  const selectedButton = reservationForm.querySelector(".calendar-day.is-selected");
  const monthLabel = document.querySelector(".reservation-calendar__month");

  if (!selectedButton) return "未選択";

  const day = selectedButton.textContent.trim();
  const monthText = monthLabel ? monthLabel.textContent.trim() : "";

  const match = monthText.match(/(\d{4})年(\d{1,2})月/);

  if (match) {
    const year = match[1];
    const month = match[2].padStart(2, "0");
    const dayPadded = day.padStart(2, "0");
    return `${year}.${month}.${dayPadded}`;
  }

  return selectedButton.dataset.dateLabel || day;
};

  const getSelectedTime = () =>
    reservationForm.querySelector(".slot-button.is-selected")?.dataset.timeLabel || "未選択";

  const getSelectedPeople = () =>
    reservationForm.querySelector(".party-button.is-selected")?.dataset.peopleLabel || "1名";

  const updateReservationUI = () => {
  const dateLabel = getSelectedDate();
  const timeLabel = getSelectedTime();
  const peopleLabel = getSelectedPeople();

  if (selectedDate) {
    selectedDate.textContent = dateLabel;
  }

  if (selectedTime) {
    selectedTime.textContent = timeLabel;
  }

  if (selectedStatus) {
    selectedStatus.textContent = `選択中: ${dateLabel} / ${timeLabel} / ${peopleLabel}`;
  }

  if (summaryPeople) {
    summaryPeople.textContent = peopleLabel;
  }

  const count = parseInt(peopleLabel, 10);
  if (priceDisplay && !Number.isNaN(count)) {
    priceDisplay.textContent = `${count * 5000}円`;
  }

  if (submitButton && timeLabel !== "未選択") {
    submitButton.innerHTML = `${dateLabel}に<br>予約を確定する`;
  }
};

  const setSelectedButton = (buttons, nextButton) => {
    buttons.forEach((button) => {
      const isCurrent = button === nextButton;
      button.classList.toggle("is-selected", isCurrent);
      button.setAttribute("aria-pressed", isCurrent ? "true" : "false");
    });
    updateReservationUI();
  };

  dateButtons.forEach((button) => {
    button.addEventListener("click", () => {
      setSelectedButton(dateButtons, button);
    });
  });

  timeButtons.forEach((button) => {
    button.addEventListener("click", () => {
      setSelectedButton(timeButtons, button);
    });
  });

  peopleButtons.forEach((button) => {
    button.addEventListener("click", () => {
      setSelectedButton(peopleButtons, button);
    });
  });

  updateReservationUI();
}
// ===== カレンダー月切り替え =====
document.addEventListener("DOMContentLoaded", () => {
  console.log("JS動いてる");

  const navButtons = document.querySelectorAll(".reservation-calendar__arrow");
  const monthEl = document.querySelector(".reservation-calendar__month");

  if (navButtons.length < 2 || !monthEl) {
    console.log("月切り替え要素が見つからない");
    return;
  }

  const prevBtn = navButtons[0];
  const nextBtn = navButtons[1];

  let currentDate = new Date(2024, 11);

  function renderMonth() {
    const year = currentDate.getFullYear();
    const month = currentDate.getMonth() + 1;
    monthEl.textContent = `${year}年${month}月`;
  }

  prevBtn.addEventListener("click", () => {
    currentDate.setMonth(currentDate.getMonth() - 1);
    renderMonth();
  });

  nextBtn.addEventListener("click", () => {
    currentDate.setMonth(currentDate.getMonth() + 1);
    renderMonth();
  });

  renderMonth();
});
// ===== 人数と料金 =====
const guestButtons = document.querySelectorAll(".guest-count");
const summarySections = document.querySelectorAll(".reservation-summary__value");
const priceDisplay = document.querySelector(".reservation-summary__price");

const guestDisplay = summarySections[2];
const PRICE = 5000;

if (guestButtons.length && guestDisplay && priceDisplay) {
  guestButtons.forEach((btn) => {
    btn.addEventListener("click", () => {
      guestButtons.forEach((b) => b.classList.remove("is-selected"));
      btn.classList.add("is-selected");

      const count = parseInt(btn.textContent, 10);

      guestDisplay.textContent = `${count}名`;
      priceDisplay.textContent = `${count * PRICE}円`;
    });
  });
}
