const BASE_URL = "http://localhost:8080";

function getToken() {
  return localStorage.getItem("jwt");
}

function request(method, path, body = null) {
  return fetch(BASE_URL + path, {
    method,
    headers: {
      "Content-Type": "application/json",
      ...(getToken() ? { Authorization: `Bearer ${getToken()}` } : {}),
    },
    body: body ? JSON.stringify(body) : undefined,
  }).then((res) => res.json());
}
