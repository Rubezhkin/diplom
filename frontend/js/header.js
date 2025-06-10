const modal = document.getElementById("topup-modal");
const closeBtn = modal.querySelector(".close");
const topupBtn = document.getElementById("topup-btn");
const topupForm = document.getElementById("topup-form");
const topupMsg = document.getElementById("topup-msg");

topupBtn.addEventListener("click", () => {
  modal.style.display = "block";
  topupMsg.textContent = "";
});

closeBtn.addEventListener("click", () => {
  modal.style.display = "none";
});

window.addEventListener("click", (event) => {
  if (event.target == modal) {
    modal.style.display = "none";
  }
});

topupForm.addEventListener("submit", async (e) => {
  e.preventDefault();
  const amount = parseFloat(document.getElementById("amount").value);
  const token = localStorage.getItem("token");
  topupMsg.textContent = "";

  try {
    const response = await fetch("http://127.0.0.1:8080/api/users/balanse", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: "Bearer " + token,
      },
      body: JSON.stringify({ sum: amount }),
    });

    if (!response.ok) throw new Error("Ошибка пополнения");

    topupMsg.style.color = "green";
    topupMsg.textContent = "Баланс пополнен!";
    topupForm.reset();

    // Обновим баланс в шапке
    fetch("http://127.0.0.1:8080/api/users/me", {
      headers: { Authorization: "Bearer " + token },
    })
      .then((res) => res.json())
      .then((data) => {
        const balanceElement = document.getElementById("balance-display");
        if (balanceElement)
          balanceElement.textContent = `Баланс: ${data.balance} ₽`;
      });
  } catch (error) {
    topupMsg.style.color = "red";
    topupMsg.textContent = error.message;
  }
});
