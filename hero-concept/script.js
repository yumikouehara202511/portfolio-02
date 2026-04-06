const hero = document.querySelector("[data-hero]");
const bottle = document.querySelector("[data-bottle]");

if (hero && bottle) {
  const prefersReducedMotion = window.matchMedia("(prefers-reduced-motion: reduce)").matches;
  const root = document.documentElement;

  const fillBottle = () => {
    if (prefersReducedMotion) {
      root.style.setProperty("--liquid-level", "84%");
      hero.classList.add("is-filled", "is-ready");
      return;
    }

    window.setTimeout(() => {
      root.style.setProperty("--liquid-level", "84%");
      hero.classList.add("is-filled");
    }, 520);

    window.setTimeout(() => {
      hero.classList.add("is-ready");
    }, 2850);
  };

  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", fillBottle, { once: true });
  } else {
    fillBottle();
  }
}
