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
