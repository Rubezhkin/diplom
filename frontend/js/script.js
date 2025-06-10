function checkAuth() {
  const token = localStorage.getItem("authToken");
  if (token) {
    // Если есть токен, получаем данные пользователя
    fetch("http://127.0.0.1:8080/api/users/me", {
      headers: {
        Authorization: "Bearer " + token,
      },
    })
      .then((response) => response.json())
      .then((data) => {
        document.getElementById("username-display").textContent = data.username;
      })
      .catch((error) => {
        console.error("Ошибка получения данных пользователя:", error);
      });
  } else {
    document.getElementById("username-display").textContent = "Гость";
  }
}

// Вызываем при загрузке каждой страницы
window.addEventListener("DOMContentLoaded", checkAuth);
