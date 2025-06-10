document.addEventListener("DOMContentLoaded", function () {
  fetch("header.html")
    .then((response) => {
      if (!response.ok) {
        throw new Error(`Ошибка загрузки шапки: ${response.status}`);
      }
      return response.text();
    })
    .then((html) => {
      document.body.insertAdjacentHTML("afterbegin", html);
      initHeader(); // инициализация логики после вставки
    })
    .catch((error) => {
      console.error("Ошибка при загрузке header.html:", error);
    });
});

function initHeader() {
  const token = localStorage.getItem("token");

  if (!token) {
    window.location.href = "login.html";
    return;
  }

  // Получение информации о пользователе
  fetch("http://127.0.0.1:8080/api/users/me", {
    headers: {
      Authorization: "Bearer " + token,
    },
  })
    .then((res) => {
      if (!res.ok) {
        localStorage.removeItem("token");
        window.location.href = "login.html";
        throw new Error(`Ошибка авторизации: ${res.status}`);
      }
      return res.json();
    })
    .then((data) => {
      const usernameDisplay = document.getElementById("username-display");
      const balanceDisplay = document.getElementById("balance-display");
      if (usernameDisplay)
        usernameDisplay.textContent = data.username || "Пользователь";
      if (balanceDisplay)
        balanceDisplay.textContent = `Баланс: ${data.balance ?? 0} ₽`;
    })
    .catch((err) => {
      console.error("Ошибка получения профиля:", err);
    });

  // Обработка выхода
  const logoutBtn = document.getElementById("logout-btn");
  if (logoutBtn) {
    logoutBtn.addEventListener("click", () => {
      localStorage.removeItem("token");
      window.location.href = "login.html";
    });
  }

  // Обработка кнопки редактирования профиля
  const profileBtn = document.getElementById("edit-profile-btn");
  if (profileBtn) {
    profileBtn.addEventListener("click", () => {
      window.location.href = "profile.html"; // замените, если путь другой
    });
  }

  // Инициализация модального окна пополнения
  setupTopupModal();
}

function setupTopupModal() {
  const modal = document.getElementById("topup-modal");
  const openBtn = document.getElementById("topup-btn");
  const closeBtn = modal?.querySelector(".close");
  const form = modal?.querySelector("#topup-form");
  const msg = modal?.querySelector("#topup-msg");

  if (!modal || !openBtn || !closeBtn || !form || !msg) {
    console.warn("Модальное окно пополнения не инициализировано полностью");
    return;
  }

  openBtn.addEventListener("click", () => {
    modal.style.display = "block";
    msg.textContent = "";
  });

  closeBtn.addEventListener("click", () => {
    modal.style.display = "none";
  });

  window.addEventListener("click", (e) => {
    if (e.target === modal) modal.style.display = "none";
  });

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    const amount = parseFloat(document.getElementById("amount").value);
    const token = localStorage.getItem("token");
    msg.textContent = "";

    try {
      const res = await fetch("http://127.0.0.1:8080/api/users/balance", {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
          Authorization: "Bearer " + token,
        },
        body: JSON.stringify({ sum: amount }),
      });

      if (!res.ok) throw new Error("Ошибка пополнения");

      msg.style.color = "green";
      msg.textContent = "Баланс пополнен!";
      form.reset();

      // Обновим отображение баланса
      const profileRes = await fetch("http://127.0.0.1:8080/api/users/me", {
        headers: {
          Authorization: "Bearer " + token,
        },
      });

      const userData = await profileRes.json();
      const balanceDisplay = document.getElementById("balance-display");
      if (balanceDisplay)
        balanceDisplay.textContent = `Баланс: ${userData.balance ?? 0} ₽`;
    } catch (error) {
      msg.style.color = "red";
      msg.textContent = error.message;
    }
  });
}
