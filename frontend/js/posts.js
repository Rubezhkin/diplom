const token = localStorage.getItem("token");
const postsDiv = document.getElementById("posts");

fetch("http://127.0.0.1:8080/api/posts", {
  headers: {
    Authorization: `Bearer ${token}`,
  },
})
  .then((response) => {
    if (!response.ok) {
      throw new Error("Ошибка при получении постов");
    }
    return response.json();
  })
  .then((posts) => {
    if (posts.length === 0) {
      postsDiv.innerHTML = "<p>Нет доступных постов</p>";
      return;
    }

    posts.forEach((post) => {
      const div = document.createElement("div");
      div.className = "post";
      div.innerHTML = `
        <h2>${post.title}</h2>
        <p><strong>Автор:</strong> ${post.authorName || "Неизвестно"}</p>
        <p>${
          post.access
            ? post.content
            : "<em>Нет доступа. Приобретите пост или подпишитесь.</em>"
        }</p>
        ${
          !post.access && post.price != null
            ? `<p>Цена: ${post.price} ₽</p>`
            : ""
        }
        ${!post.access && post.tier != null ? `<p>Доступ по подписке</p>` : ""}
      `;
      postsDiv.appendChild(div);
    });
  })
  .catch((error) => {
    postsDiv.innerHTML = `<p>${error.message}</p>`;
  });
